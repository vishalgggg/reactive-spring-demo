package ca.neilwhite.hrservice.repositories;
import org.springframework.stereotype.Component;

import ca.neilwhite.hrservice.models.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

   


@Component
public interface BookRepository {
    Flux<Book> findAll();

    Mono<Book> findById(long id);

    Mono<Book> findByName(String name);

    Mono<Book> save(Book book);

    Mono<Void> delete(Book book);
}



