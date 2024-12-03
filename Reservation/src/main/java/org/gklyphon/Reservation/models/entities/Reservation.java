package org.gklyphon.Reservation.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a reservation. This class extends {@link Auditable}
 * to include audit fields like creation and update timestamps.
 * <p>
 * A reservation contains details about the start and end dates and
 * manages associated users and rooms through relationships.
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
@Entity
@Table(name = "reservations")
public class Reservation extends Auditable {

    /**
     * Unique identifier for the reservation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The ID of the user who created the reservation
     */
    @NotNull
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
     * List of users associated with this reservation.
     * Managed as a one-to-many relationship.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserReservation> userReservations;

    /**
     * List of rooms associated with this reservation.
     * Managed as a one-to-many relationship.
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    private List<RoomReservation> roomReservations;

    /**
     * Default constructor initializing the lists for user and room reservations.
     */
    public Reservation() {
        this.userReservations = new ArrayList<>();
        this.roomReservations = new ArrayList<>();
    }

    /**
     * Adds a {@link UserReservation} to the list of user reservations.
     *
     * @param userReservation The user reservation to be added.
     */
    public void addUserReservations(UserReservation userReservation) {
        userReservations.add(userReservation);
    }

    /**
     * Removes a {@link UserReservation} from the list of user reservations.
     *
     * @param userReservation The user reservation to be removed.
     */
    public void removeUserReservations(UserReservation userReservation) {
        userReservations.remove(userReservation);
    }

    /**
     * Adds a {@link RoomReservation} to the list of room reservations.
     *
     * @param roomReservation The room reservation to be added.
     */
    public void addRoomReservations(RoomReservation roomReservation) {
        roomReservations.add(roomReservation);
    }

    /**
     * Removes a {@link RoomReservation} from the list of room reservations.
     *
     * @param roomReservation The room reservation to be removed.
     */
    public void removeRoomReservations(RoomReservation roomReservation) {
        roomReservations.remove(roomReservation);
    }
}
