package org.gklyphon.Reservation.repository;

import org.gklyphon.Reservation.models.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Repository interface for managing {@link Reservation} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 * It includes methods to find reservations by start date, end date, and user ID.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 3-Dec-2024
 */
public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Finds reservations by the given start date.
     *
     * @param startDate The start date of the reservation.
     * @return A {@link Page} of {@link Reservation} entities with the specified start date.
     */
    Page<Reservation> findByStartDate(LocalDate startDate);

    /**
     * Finds reservations by the given end date.
     *
     * @param endDate The end date of the reservation.
     * @return A {@link Page} of {@link Reservation} entities with the specified end date.
     */
    Page<Reservation> findByEndDate(LocalDate endDate);

    /**
     * Finds reservations by the user ID associated with the reservation.
     *
     * @param userId The ID of the user who made the reservation.
     * @return A {@link Page} of {@link Reservation} entities associated with the given user ID.
     */
    Page<Reservation> findByUserId(Long userId);
}
