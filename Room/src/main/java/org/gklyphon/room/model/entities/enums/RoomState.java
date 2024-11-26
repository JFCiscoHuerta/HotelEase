package org.gklyphon.room.model.entities.enums;

/**
 * Enum representing the possible states of a room.
 *
 * <p>This enum defines the various states that a room can be in, such as whether it is available,
 * occupied, or undergoing cleaning or maintenance.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
public enum RoomState {

    /**
     * The room is available for booking.
     */
    AVAILABLE,

    /**
     * The room is not available for booking, potentially due to being out of service or undergoing maintenance.
     */
    NOT_AVAILABLE,

    /**
     * The room is currently occupied by guests.
     */
    OCCUPIED,

    /**
     * The room is being cleaned and is not available for booking.
     */
    ON_CLEANING,

    /**
     * The room is undergoing maintenance and is not available for booking.
     */
    ON_MAINTAINING
}
