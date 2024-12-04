package org.gklyphon.Reservation.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Transfer Object (DTO) representing a reservation.
 * <p>
 * This class encapsulates data for transferring reservation information
 * between different application layers, particularly in service and controller layers.
 * </p>
 *
 * <p>It includes user information, reservation dates, and associated user and room reservations.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Builder} - Provides a builder pattern for creating instances.</li>
 *   <li>{@code @Getter} - Generates getter methods for all fields.</li>
 *   <li>{@code @Setter} - Generates setter methods for all fields.</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields.</li>
 * </ul>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 3-Dec-2024
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {

    /**
     * Unique identifier of the user associated with the reservation.
     */
    private Long userId;

    /**
     * The start date of the reservation.
     */
    private LocalDate startDate;

    /**
     * The end date of the reservation.
     */
    private LocalDate endDate;

    /**
     * List of user reservations associated with this reservation.
     * <p>This may include details about multiple users linked to the reservation.</p>
     */
    private List<UserReservationDTO> userReservations;

    /**
     * List of room reservations associated with this reservation.
     * <p>Each entry represents a specific room linked to the reservation.</p>
     */
    private List<RoomReservationDTO> roomReservations;
}
