package ca.neilwhite.hrservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ca.neilwhite.hrservice.models.Author;
import ca.neilwhite.hrservice.models.Book;
import ca.neilwhite.hrservice.models.requests.CreateBookRequest;
import ca.neilwhite.hrservice.services.BookService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService service;

    @GetMapping
    public Flux<Book> getBooks() {
        return this.service.getBooks();
    }

    @GetMapping("/{id}")
    public Mono<Book> getBook(@PathVariable Long id) {
        return this.service.getBook(id);
    }

    @GetMapping("/{id}/Books")
    public Flux<Author> getBookAuthors(@PathVariable Long id) {
        return this.service.getBookAuthors(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> createBook(@Valid @RequestBody CreateBookRequest request) {
        return this.service.createBook(request);
    }

    @PutMapping("/{id}")
    public Mono<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return this.service.updateBook(id, book);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBook(@PathVariable Long id) {
        return this.service.deleteBook(id);
    }
}
