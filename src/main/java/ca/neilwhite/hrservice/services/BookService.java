package ca.neilwhite.hrservice.services;

import ca.neilwhite.hrservice.exceptions.BookAlreadyExist;
import ca.neilwhite.hrservice.exceptions.BookNotFound;

import ca.neilwhite.hrservice.models.Author;
import ca.neilwhite.hrservice.models.Book;
import ca.neilwhite.hrservice.models.requests.CreateBookRequest;
import ca.neilwhite.hrservice.repositories.BookRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor


public class BookService {
  




    private final BookRepository repository;

    /**
     * Returns all Departments.
     *
     * @return Flux of {@link Department}
     */
    public Flux<Book> getBooks() {
        return this.repository.findAll();
    }

    /**
     * Returns a Department by ID.
     *
     * @param id Department ID
     * @return Mono of {@link Department}
     */
    public Mono<Book> getBook(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFound(id)));
    }

    /**
     * Returns the Employees of a Department by ID.
     *
     * @param id         Department ID
     * @param isFullTime Filter employees on full time status
     * @return Flux of {@link Employee}
     */
    public Flux<Author> getBookAuthors(Long id ) {
        
            return this.repository.findById(id)
                    .switchIfEmpty(Mono.error(new BookNotFound(id)))
                    .flatMapMany(book -> Flux.fromIterable(book.getAuthors()));
        
    }

    /**
     * Creates and returns a new Department.
     *
     * @param request {@link CreateDepartmentRequest}
     * @return Mono of {@link Department}
     */
    public Mono<Book> createBook(CreateBookRequest request) {
        return this.repository.findByName(request.getName())
                .flatMap(book -> Mono.error(new BookAlreadyExist(book.getName())))
                .defaultIfEmpty(Book.builder().name(request.getName()).build()).cast(Book.class)
                .flatMap(this.repository::save);
    }

    /**
     * Updates and returns a Department.
     *
     * @param id         Department ID
     * @param department {@link Department}
     * @return Mono of {@link Department}
     */
    public Mono<Book> updateBook(Long id, Book book) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFound(id)))
                .doOnNext(currentBook -> {
                    currentBook.setName(book.getName());
                    currentBook.setAuthors(book.getAuthors());
                })
                .flatMap(this.repository::save);
    }

    /**
     * Deletes a Department by ID.
     *
     * @param id Department ID
     * @return Mono of {@link Void}
     */
    public Mono<Void> deleteBook(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new BookNotFound(id)))
                .flatMap(this.repository::delete)
                .then();
    }
}





