package org.gklyphon.Reservation.service;

import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface IReservationService extends IService<Reservation> {
    Page<Reservation> findByStartDate(LocalDate startDate, Pageable pageable);
    Page<Reservation> findByEndDate(LocalDate endDate, Pageable pageable);
    Page<Reservation> findByUserId(Long userId, Pageable pageable);
    public Reservation save(ReservationDTO reservationDTO);
    public Reservation update(Long id, ReservationDTO reservationDTO);

}
