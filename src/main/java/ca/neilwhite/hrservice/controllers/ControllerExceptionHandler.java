package ca.neilwhite.hrservice.controllers;

import ca.neilwhite.hrservice.exceptions.AuthorNotFoundException;
import ca.neilwhite.hrservice.exceptions.BookAlreadyExist;
import ca.neilwhite.hrservice.exceptions.BookNotFound;
import ca.neilwhite.hrservice.exceptions.DepartmentAlreadyExistsException;
import ca.neilwhite.hrservice.exceptions.DepartmentNotFoundException;
import ca.neilwhite.hrservice.exceptions.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({
            DepartmentNotFoundException.class,
            EmployeeNotFoundException.class,
            BookNotFound.class,
            AuthorNotFoundException.class
    })
    ResponseEntity<String> handleNotFound(RuntimeException exception) {
        log.info("handling exception:: " + exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler({DepartmentAlreadyExistsException.class,BookAlreadyExist.class,
        org.springframework.dao.DataIntegrityViolationException.class})
    ResponseEntity<String> handleBadRequest(RuntimeException exception) {
        log.debug("handling exception:: " + exception);
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(exception.getMessage());
    }



    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<List<String>> handleException(WebExchangeBindException e) {
        List<String> errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        List<String> isvalid = new ArrayList<>();
        isvalid.add("failed validation");
        // errors.add("status:400");
       
        return ResponseEntity.badRequest().body(isvalid);
    }
}
