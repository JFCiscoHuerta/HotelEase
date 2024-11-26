package org.gklyphon.room.exception.custom;

/**
 * Exception thrown when a requested element is not found in the system.
 *
 * <p>This exception is typically used in situations where a requested resource (e.g., a database entity)
 * does not exist, and a specific error message is needed to inform the caller of this issue.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
public class ElementNotFoundException extends RuntimeException {

    /**
     * Constructs a new {@link ElementNotFoundException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method)
     */
    public ElementNotFoundException(String message) {
        super(message);
    }
}
