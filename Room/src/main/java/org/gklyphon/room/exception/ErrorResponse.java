package org.gklyphon.room.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * A class representing an error response that is returned to the client.
 *
 * <p>This class contains the error message, status code, and timestamp of the error response.</p>
 *
 * <p>The {@link ErrorResponse} is used to provide a structured response when an error occurs in the system,
 * making it easier for clients to handle and interpret errors returned by the API.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@Getter
@Setter
public class ErrorResponse {

    /**
     * The error message describing the nature of the error.
     */
    private String message;

    /**
     * The HTTP status code associated with the error.
     */
    private int status;

    /**
     * The timestamp when the error occurred.
     */
    private LocalDateTime timestamp;

    /**
     * Constructs an {@link ErrorResponse} with a message and the current timestamp.
     *
     * @param message the error message
     */
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an {@link ErrorResponse} with a message, status, and the current timestamp.
     *
     * @param message the error message
     * @param status the HTTP status code
     */
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an {@link ErrorResponse} with a message, status, and the current timestamp.
     *
     * @param message the error message
     * @param status the {@link HttpStatus} corresponding to the error
     */

    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
    }

}
