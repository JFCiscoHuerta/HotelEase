package org.gklyphon.Reservation.mapper;

import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IReservationMapper {

    ReservationDTO toReservationDTO(Reservation reservation);

    @InheritInverseConfiguration
    Reservation toReservation(ReservationDTO reservationDTO);

}
