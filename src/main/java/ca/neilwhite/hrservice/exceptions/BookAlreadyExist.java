package ca.neilwhite.hrservice.exceptions;

public class BookAlreadyExist extends RuntimeException {
    public BookAlreadyExist(String name) {
            super(String.format("Department with name \"%s\" already exists.", name));
    }
}


