package ca.neilwhite.hrservice.repositories;
import ca.neilwhite.hrservice.models.Author;
import ca.neilwhite.hrservice.models.Book;
import ca.neilwhite.hrservice.models.Department;
    
    import lombok.RequiredArgsConstructor;
    import org.springframework.r2dbc.core.DatabaseClient;
    import org.springframework.stereotype.Component;
    import org.springframework.transaction.annotation.Transactional;
    import reactor.core.publisher.Flux;
    import reactor.core.publisher.Mono;
    
    import java.util.List;

    
    
    @Component
    @RequiredArgsConstructor
    public class BookRepositoryImpl implements BookRepository {
        private final AuthorRepository authorRepository;
        private final DatabaseClient client;
        private static final String SELECT_QUERY = """
                SELECT b.id b_id, b.name b_name
                    , a.id a_id, a.first_name a_firstName,
                    a.last_name a_lastName, 
                FROM books b
                
                LEFT JOIN book_authors ba ON ba.book_id = b.id
                LEFT JOIN authors a ON a.id = ba.author_id
                """;
    
        /**
         * Returns all Departments.
         *
         * @return Flux of {@link Department}
         */
        @Override
        public Flux<Book> findAll() {
            String query = String.format("%s ORDER BY b.id", SELECT_QUERY);
    
            return client.sql(query)
                    .fetch()
                    .all()
                    .bufferUntilChanged(result -> result.get("b_id"))
                    .flatMap(Book::fromRows);
        }
    
        /**
         * Returns a Department by ID.
         *
         * @param id Department ID
         * @return Mono of {@link Department}
         */
        @Override
        public Mono<Book> findById(long id) {
            String query = String.format("%s WHERE b.id = :id", SELECT_QUERY);
    
            return client.sql(query)
                    .bind("id", id)
                    .fetch()
                    .all()
                    .bufferUntilChanged(result -> result.get("b_id"))
                    .flatMap(Book::fromRows)
                    .singleOrEmpty();
        }
    
        /**
         * Returns a Department by name.
         *
         * @param name Department Name
         * @return Mono of {@link Department}
         */
        @Override
        public Mono<Book> findByName(String name) {
            String query = String.format("%s WHERE b.name = :name", SELECT_QUERY);
    
            return client.sql(query)
                    .bind("name", name)
                    .fetch()
                    .all()
                    .bufferUntilChanged(result -> result.get("b_id"))
                    .flatMap(Book::fromRows)
                    .singleOrEmpty();
        }
    
        /**
         * Saves and returns a Department.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
        @Override
        @Transactional
        public Mono<Book> save(Book book) {
            return this.saveBook(book)
                    
                    .flatMap(this::saveAuthors)
                    
                    .flatMap(this::deleteBookAuthor)
                    .flatMap(this::saveBookAuthors);
        }
    
        /**
         * Deletes a Department.
         *
         * @param department {@link Department}
         * @return Mono of {@link Void}
         */
        @Override
        @Transactional
        public Mono<Void> delete(Book book) {
            return this.deleteBookAuthor(book)
                    .flatMap(this::deleteBook)
                    .then();
        }
    
        /**
         * Saves a Department.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
        private Mono<Book> saveBook(Book book) {
            if (book.getId() == null) {
                return client.sql("INSERT INTO books(ame) VALUES(:name)")
                        .bind("name", book.getName())
                        .filter((statement, executeFunction) -> statement.returnGeneratedValues("id").execute())
                        .fetch().first()
                        .doOnNext(result -> book.setId(Long.parseLong(result.get("id").toString())))
                        .thenReturn(book);
            } else {
                return this.client.sql("UPDATE books SET name = :name WHERE id = :id")
                        .bind("name", book.getName())
                        .bind("id", book.getId())
                        .fetch().first()
                        .thenReturn(book);
            }
        }
    
        /**
         * Saves a Department Manager.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
       
    
        /**
         * Saves Department Employees.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
        private Mono<Book> saveAuthors(Book book) {
            return Flux.fromIterable(book.getAuthors())
                    .flatMap(this.authorRepository::save)
                    .collectList()
                    // .doOnNext(department::setEmployees)
                    .thenReturn(book);
        }
    
        /**
         * Saves the relationship between Department and Manager.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
       
    
        /**
         * Saves the relationship between Department and Employees.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
        private Mono<Book> saveBookAuthors(Book book) {
            String query = "INSERT INTO book_authors(book_id, author_id) VALUES (:id, :autId)";
    
            return Flux.fromIterable(book.getAuthors())
                    .flatMap(authors -> client.sql(query)
                            .bind("id", book.getId())
                            .bind("empId", authors.getId())
                            .fetch().rowsUpdated())
                    .collectList()
                    .thenReturn(book);
        }
    
        /**
         * Deletes a Department.
         *
         * @param department {@link Department}
         * @return Mono of {@link Void}
         */
        private Mono<Void> deleteBook(Book book) {
            return client.sql("DELETE FROM books WHERE id = :id")
                    .bind("id", book.getId())
                    .fetch().first()
                    .then();
        }
    
        /**
         * Deletes the relationship between Department and Manager.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
        
    
        /**
         * Deletes the relationship between Department and Employees.
         *
         * @param department {@link Department}
         * @return Mono of {@link Department}
         */
        private Mono<Book> deleteBookAuthor(Book book) {
            String query = "DELETE FROM book_authors WHERE book_id = :id OR author_id in (:ids)";
    
            List<Long> authorIds = book.getAuthors().stream().map(Author::getId).toList();
    
            return Mono.just(book)
                    .flatMap(bok -> client.sql(query)
                            .bind("id", book.getId())
                            .bind("ids", authorIds.isEmpty() ? List.of(0) : authorIds)
                            .fetch().rowsUpdated())
                    .thenReturn(book);
        }
    }
    

