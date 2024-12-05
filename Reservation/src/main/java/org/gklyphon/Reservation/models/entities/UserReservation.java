package org.gklyphon.Reservation.models.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a user's reservation. This class extends {@link Auditable}
 * to inherit audit fields, such as creation and update timestamps.
 * <p>
 * This entity links a specific user to a reservation, storing the user's unique identifier.
 * </p>
 *
 * <p>
 * It is part of the {@code user_reservations} table, which maps users to their respective reservations.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 2-Dec-2024
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_reservations")
public class UserReservation extends Auditable {

    /**
     * Unique identifier for the user reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique identifier of the user associated with this reservation.
     */
    @Column(name = "user_id")
    private Long userId;
}
