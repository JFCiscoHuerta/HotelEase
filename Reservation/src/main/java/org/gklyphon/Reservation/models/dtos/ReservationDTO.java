package org.gklyphon.Reservation.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.gklyphon.Reservation.models.entities.RoomReservation;
import org.gklyphon.Reservation.models.entities.UserReservation;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {

    private Long userId;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<UserReservationDTO> userReservations;

    private List<RoomReservationDTO> roomReservations;
}
