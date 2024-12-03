package org.gklyphon.Reservation.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "reservations")
public class Reservation extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<UserReservation> userReservations;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "room_id")
    private List<RoomReservation> roomReservations;

    public void addUserReservations(UserReservation userReservation) {
        userReservations.add(userReservation);
    }

    public void removeUserReservations(UserReservation userReservation) {
        userReservations.remove(userReservation);
    }

    public void addRoomReservations(RoomReservation roomReservation) {
        roomReservations.add(roomReservation);
    }

    public void removeRoomReservations(RoomReservation roomReservation) {
        roomReservations.remove(roomReservation);
    }

    public Reservation() {
        this.userReservations = new ArrayList<>();
        this.roomReservations = new ArrayList<>();
    }

}
