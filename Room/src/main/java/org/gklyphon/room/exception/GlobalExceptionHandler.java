package org.gklyphon.room.exception;

import org.gklyphon.room.exception.custom.ElementNotFoundException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler to manage exceptions in the entire application.
 *
 * <p>This class handles different types of exceptions globally, providing custom error responses
 * for {@link ElementNotFoundException}, {@link MethodArgumentNotValidException}, {@link ServiceException},
 * and a generic {@link Exception}.</p>
 *
 * <p>Each exception is caught and returned with a custom {@link ErrorResponse} that includes a message and
 * an appropriate HTTP status code.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link ElementNotFoundException} and returns a custom error response with a 404 status code.
     *
     * @param e the {@link ElementNotFoundException} to handle
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse} with a 404 status code
     */
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleElementNotFoundException(ElementNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles {@link MethodArgumentNotValidException} (validation errors) and returns a map of field errors
     * with a 400 status code.
     *
     * @param e the {@link MethodArgumentNotValidException} to handle
     * @return a {@link ResponseEntity} containing a map of field error messages with a 400 status code
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((err) -> {
            String field = ((FieldError) err).getField();
            String errorMessage = err.getDefaultMessage();
            errors.put(field, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    /**
     * Handles {@link ServiceException} and returns a custom error response with a 500 status code.
     *
     * @param e the {@link ServiceException} to handle
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse} with a 500 status code
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles generic {@link Exception} and returns a custom error response with a 500 status code.
     *
     * @param e the {@link Exception} to handle
     * @return a {@link ResponseEntity} containing the {@link ErrorResponse} with a 500 status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
