package org.gklyphon.Reservation;

import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.dtos.RoomReservationDTO;
import org.gklyphon.Reservation.models.dtos.UserReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.gklyphon.Reservation.models.entities.RoomReservation;
import org.gklyphon.Reservation.models.entities.UserReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.List;

public class Data {

    public static final Reservation RESERVATION = Reservation.builder()
            .id(1L)
            .startDate(LocalDate.of(2024, 12,3))
            .endDate(LocalDate.of(2024,12, 18))
            .userId(3L)
            .roomReservations(
                    List.of(
                            RoomReservation.builder()
                                    .id(1L)
                                    .roomId(2L)
                                    .build(),
                            RoomReservation.builder()
                                    .id(2L)
                                    .roomId(3L)
                                    .build()
                    )
            )
            .userReservations(
                    List.of(
                            UserReservation.builder()
                                    .id(1L)
                                    .userId(1L)
                                    .build()
                    )
            )
            .build();

    public static final List<Reservation> RESERVATIONS = List.of(RESERVATION);

    public static final Page<Reservation> RESERVATION_PAGE = new PageImpl<Reservation>(RESERVATIONS);

    public static  final ReservationDTO RESERVATION_DTO = ReservationDTO.builder()
            .userId(3L)
            .startDate(LocalDate.of(2024, 12,3))
            .endDate(LocalDate.of(2024,12, 18))
            .roomReservations(List.of(
                    RoomReservationDTO.builder()
                            .roomId(1L)
                            .build()
                    ))
            .userReservations(List.of(
                    UserReservationDTO.builder()
                            .userId(1L)
                            .build()
                    ))
            .build();
}
