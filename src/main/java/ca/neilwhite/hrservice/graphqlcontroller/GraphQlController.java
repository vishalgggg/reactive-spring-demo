package ca.neilwhite.hrservice.graphqlcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import ca.neilwhite.hrservice.models.Author;
import ca.neilwhite.hrservice.models.requests.CreateAuthorRequest;
import ca.neilwhite.hrservice.services.AuthorService;

@Slf4j

public class GraphQlController {
    @Autowired
    private AuthorService authorService;

    @QueryMapping("getAllAuthors")
    Flux<Author> getAllAuthors() {
        log.info("Get all players using 'getAllAuthors' query");
        return authorService.getAuthors();
    }

    @MutationMapping("addAuthor")
    Mono<Author> addAuthor(@Argument AuthorInput authorInput) {
        log.info("Add author using 'addAuthor' mutation");
        CreateAuthorRequest createAuthorRequest = new CreateAuthorRequest(authorInput.getFirstName(),
                authorInput.getLastName());

        return authorService.createAuthor(createAuthorRequest);
    }

}

@Getter
@Setter
class AuthorInput {
    private String firstName;

    private String lastName;
}
