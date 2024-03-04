package app.repbook.person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PersonExceptionHandler {
    public record exResponse (String message) {}

    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<exResponse> handlePersonExistsException(PersonAlreadyExistsException ex) {
        return new ResponseEntity<>(
                new exResponse("Username is taken"),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(PersonDoesNotExistException.class)
    public ResponseEntity<exResponse> handlePersonDoesNotExistException(PersonDoesNotExistException ex) {
        return new ResponseEntity<>(
                new exResponse("User not found"),
                HttpStatus.NOT_FOUND
        );
    }






}
