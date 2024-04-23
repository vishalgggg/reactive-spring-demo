package ca.neilwhite.hrservice.repositories;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import ca.neilwhite.hrservice.models.Author;

import reactor.core.publisher.Mono;



@Repository
public interface AuthorRepository extends R2dbcRepository<Author, Long> {
   
    Mono<Author> findByFirstName(String firstName);
}



