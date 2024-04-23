package ca.neilwhite.hrservice.exceptions;


    public class AuthorNotFoundException extends RuntimeException {
        public AuthorNotFoundException(Long id) {
            super(String.format("Author not found. Id: %d", id));
        }
    }

