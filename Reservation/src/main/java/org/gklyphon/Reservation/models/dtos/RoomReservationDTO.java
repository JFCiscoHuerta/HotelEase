package org.gklyphon.Reservation.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a room reservation.
 * <p>
 * This class encapsulates the data for a room reservation, which is used to transfer information
 * about the room linked to a reservation.
 * </p>
 *
 * <p>It includes the room identifier for the reservation.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Builder} - Provides a builder pattern for creating instances.</li>
 *   <li>{@code @Getter} - Generates getter methods for all fields.</li>
 *   <li>{@code @Setter} - Generates setter methods for all fields.</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields.</li>
 *   <li>{@code @NotNull} - Ensures that the room ID is not null when the object is validated.</li>
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
public class RoomReservationDTO {

    /**
     * Unique identifier of the room reserved.
     * <p>This field cannot be {@code null} and must be provided when creating a room reservation.</p>
     */
    @NotNull
    private Long roomId;
}
