package ca.neilwhite.hrservice.exceptions;


    
public class BookNotFound extends RuntimeException{
    public BookNotFound(Long id) {
        super(String.format("Book not found. Id: %d", id));
    }
}


