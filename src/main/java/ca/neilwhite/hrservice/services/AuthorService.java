package ca.neilwhite.hrservice.services;

import ca.neilwhite.hrservice.exceptions.AuthorNotFoundException;
// import ca.neilwhite.hrservice.exceptions.EmployeeNotFoundException;
import ca.neilwhite.hrservice.models.Author;
import ca.neilwhite.hrservice.models.Employee;
import ca.neilwhite.hrservice.models.requests.CreateAuthorRequest;
import ca.neilwhite.hrservice.models.requests.CreateEmployeeRequest;
import ca.neilwhite.hrservice.repositories.AuthorRepository;
// import ca.neilwhite.hrservice.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class AuthorService {
    

    private final AuthorRepository repository;

    /**
     * Returns all Employees, optionally filtered by position or full time status.
     *
     * @param position   Employee Position
     * @param isFullTime Is Employee Full Time
     * @return Flux of {@link Employee}
     */
    public Flux<Author> getAuthors() {
        
        return this.repository.findAll();
     
    }

    /**
     * Returns an Employee by ID.
     *
     * @param id Employee ID
     * @return Mono of {@link Employee}
     */
    public Mono<Author> getAuthor(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(id)));
    }

    /**
     * Creates and returns a new Employee.
     *
     * @param request {@link CreateEmployeeRequest}
     * @return Mono of {@link Employee}
     */
    
    public Mono<Author> createAuthor(CreateAuthorRequest request) {
        return this.repository.save(
                Author.builder()
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        
                        .build());
    }

    /**
     * Updates and returns an Employee.
     *
     * @param id       Employee ID
     * @param employee {@link Employee}
     * @return Mono of {@link Employee}
     */
    public Mono<Author> updateAuthor(Long id, Author author ){
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(id)))
                .flatMap(existingAuthor -> {
                    existingAuthor.setFirstName(author.getFirstName());
                    existingAuthor.setLastName(author.getLastName());
                    
                    return this.repository.save(existingAuthor);
                });
    }

    /**
     * Deletes an Employee by ID.
     *
     * @param id Employee ID
     * @return Mono of {@link Void}
     */
    public Mono<Void> deleteAuthor(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(new AuthorNotFoundException(id)))
                .flatMap(this.repository::delete)
                .then();
    }
}





