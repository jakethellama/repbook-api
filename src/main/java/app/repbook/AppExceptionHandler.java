package app.repbook;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class AppExceptionHandler {
    public record exResponse (String message) {}

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<exResponse> handleRouteNotFoundException(NoHandlerFoundException ex) {
        return new ResponseEntity<>(
                new exResponse("Not Found"),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<exResponse> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(
                new exResponse("Access Forbidden"),
                HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<exResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(
                new exResponse("Unauthorized"),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<exResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(
                new exResponse("Bad Request"),
                HttpStatus.BAD_REQUEST
        );
    }




}
