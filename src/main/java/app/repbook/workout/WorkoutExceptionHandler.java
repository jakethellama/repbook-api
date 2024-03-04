package app.repbook.workout;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WorkoutExceptionHandler {
    public record exResponse (String message) {}

    @ExceptionHandler(WorkoutDoesNotExistException.class)
    public ResponseEntity<exResponse> handleWorkoutDNEException(WorkoutDoesNotExistException ex) {
        return new ResponseEntity<>(
                new exResponse("Workout not found"),
                HttpStatus.NOT_FOUND
        );
    }
}
