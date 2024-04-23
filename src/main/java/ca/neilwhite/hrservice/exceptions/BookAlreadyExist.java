package ca.neilwhite.hrservice.exceptions;

public class BookAlreadyExist extends RuntimeException {
    public BookAlreadyExist(String name) {
            super(String.format("Book with name \"%s\" already exists.", name));
    }
}


