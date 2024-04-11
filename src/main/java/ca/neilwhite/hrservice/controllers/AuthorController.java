package ca.neilwhite.hrservice.controllers;
import ca.neilwhite.hrservice.models.Author;
// import ca.neilwhite.hrservice.models.Employee;
import ca.neilwhite.hrservice.models.requests.CreateAuthorRequest;

import ca.neilwhite.hrservice.services.AuthorService;
// import ca.neilwhite.hrservice.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/Authors")
public class AuthorController {
    



    private final AuthorService service;

    @GetMapping
    public Flux<Author> getAuthors() {
        return this.service.getAuthors();
    }

    @GetMapping("/{id}")
    public Mono<Author> getAuthor(@PathVariable Long id) {
        return this.service.getAuthor(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Author> createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        return this.service.createAuthor(request);
    }

    @PutMapping("/{id}")
    public Mono<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        return this.service.updateAuthor(id, author);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteAuthor(@PathVariable Long id) {
        return this.service.deleteAuthor(id);
    }
}



