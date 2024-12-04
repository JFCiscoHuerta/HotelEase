package org.gklyphon.Reservation.mapper;

import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;


/**
 * Mapper interface for converting between {@link Reservation} and {@link ReservationDTO} objects.
 * <p>
 * This interface uses MapStruct to generate the implementation at compile time.
 * It provides methods to map entities to Data Transfer Objects (DTOs) and vice versa.
 * </p>
 *
 * <p>Annotation {@code @Mapper(componentModel = "spring")} enables integration with Spring's dependency injection.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 3-Dec-2024
 */
@Mapper(componentModel = "spring")
public interface IReservationMapper {

    /**
     * Converts a {@link Reservation} entity to a {@link ReservationDTO}.
     *
     * @param reservation the {@link Reservation} entity to convert
     * @return the converted {@link ReservationDTO}
     */
    ReservationDTO toReservationDTO(Reservation reservation);

    /**
     * Converts a {@link ReservationDTO} back to a {@link Reservation} entity.
     * <p>
     * This method uses the inverse configuration of {@link #toReservationDTO(Reservation)}.
     * </p>
     *
     * @param reservationDTO the {@link ReservationDTO} to convert
     * @return the converted {@link Reservation} entity
     */
    @InheritInverseConfiguration
    Reservation toReservation(ReservationDTO reservationDTO);

}
