package app.repbook.movement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MovementExceptionHandler {
    public record exResponse (String message) {}

    @ExceptionHandler(MovementDoesNotExistException.class)
    public ResponseEntity<exResponse> handleMovementDoesNotExistException(MovementDoesNotExistException ex) {
        return new ResponseEntity<>(
                new exResponse("Movement not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(SsetDoesNotExistException.class)
    public ResponseEntity<exResponse> handleSsetDoesNotExistException(SsetDoesNotExistException ex) {
        return new ResponseEntity<>(
                new exResponse("Set not found"),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MovTypeDoesNotExistException.class)
    public ResponseEntity<exResponse> handleMovTypeDoesNotExistException(MovTypeDoesNotExistException ex) {
        return new ResponseEntity<>(
                new exResponse("Movement type not found"),
                HttpStatus.NOT_FOUND
        );
    }
}
