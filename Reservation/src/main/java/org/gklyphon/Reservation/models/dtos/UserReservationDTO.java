package org.gklyphon.Reservation.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a user reservation.
 * <p>
 * This class encapsulates the data for a user linked to a reservation, specifically the user's
 * unique identifier for reservation purposes.
 * </p>
 *
 * <p>It includes the user identifier for the reservation.</p>
 *
 * <p>Annotations:</p>
 * <ul>
 *   <li>{@code @Builder} - Provides a builder pattern for creating instances.</li>
 *   <li>{@code @Getter} - Generates getter methods for all fields.</li>
 *   <li>{@code @Setter} - Generates setter methods for all fields.</li>
 *   <li>{@code @AllArgsConstructor} - Generates a constructor with all fields.</li>
 *   <li>{@code @NotNull} - Ensures that the user ID is not null when the object is validated.</li>
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
public class UserReservationDTO {

    /**
     * Unique identifier of the user reserved.
     * <p>This field cannot be {@code null} and must be provided when creating a user reservation.</p>
     */
    @NotNull
    private Long userId;

}
