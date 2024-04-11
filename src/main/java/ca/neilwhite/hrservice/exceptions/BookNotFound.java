package ca.neilwhite.hrservice.exceptions;


    
public class BookNotFound extends RuntimeException{
    public BookNotFound(Long id) {
        super(String.format("Department not found. Id: %d", id));
    }
}


