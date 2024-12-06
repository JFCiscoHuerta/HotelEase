package org.gklyphon.Reservation.models.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entity representing a room reservation. This class extends {@link Auditable}
 * to inherit audit fields for tracking creation and update timestamps.
 * <p>
 * This entity links a specific room to a reservation, storing the room's unique identifier.
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
@Table(name = "room_reservations")
public class RoomReservation extends Auditable {

    /**
     * Unique identifier for the room reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The unique identifier of the room associated with this reservation.
     */
    @Column(name = "room_id")
    private Long roomId;

}
