package org.gklyphon.Reservation.service;

import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * Service interface for managing reservations.
 * <p>
 * This interface defines the contract for operations related to reservation management, including
 * finding, saving, and updating reservations. It extends from the generic {@link IService} interface,
 * which provides basic CRUD operations for entities.
 * </p>
 * <p>
 * It supports searching reservations by start date, end date, and user ID, as well as saving and updating
 * reservation data using {@link ReservationDTO}.
 * </p>
 *
 * <p>Methods:</p>
 * <ul>
 *   <li>{@code findByStartDate} - Retrieves a paginated list of reservations starting on a specific date.</li>
 *   <li>{@code findByEndDate} - Retrieves a paginated list of reservations ending on a specific date.</li>
 *   <li>{@code findByUserId} - Retrieves a paginated list of reservations for a specific user ID.</li>
 *   <li>{@code save} - Saves a new reservation using the provided {@link ReservationDTO} data.</li>
 *   <li>{@code update} - Updates an existing reservation based on its ID and provided {@link ReservationDTO} data.</li>
 * </ul>
 *
 * @see Reservation
 * @see ReservationDTO
 * @see IService
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 3-Dec-2024
 */
public interface IReservationService extends IService<Reservation> {

    /**
     * Finds reservations that start on the given date.
     * <p>
     * This method retrieves a paginated list of reservations where the start date matches the provided
     * {@code startDate}.
     * </p>
     *
     * @param startDate the start date to search for
     * @param pageable  the pagination information
     * @return a page of reservations that start on the specified date
     */
    Page<Reservation> findByStartDate(LocalDate startDate, Pageable pageable);

    /**
     * Finds reservations that end on the given date.
     * <p>
     * This method retrieves a paginated list of reservations where the end date matches the provided
     * {@code endDate}.
     * </p>
     *
     * @param endDate  the end date to search for
     * @param pageable the pagination information
     * @return a page of reservations that end on the specified date
     */
    Page<Reservation> findByEndDate(LocalDate endDate, Pageable pageable);

    /**
     * Finds reservations by a specific user ID.
     * <p>
     * This method retrieves a paginated list of reservations for a specific user identified by {@code userId}.
     * </p>
     *
     * @param userId   the user ID to search for
     * @param pageable the pagination information
     * @return a page of reservations for the specified user
     */
    Page<Reservation> findByUserId(Long userId, Pageable pageable);

    /**
     * Saves a new reservation.
     * <p>
     * This method creates a new reservation from the provided {@link ReservationDTO}.
     * </p>
     *
     * @param reservationDTO the reservation data to save
     * @return the saved {@link Reservation}
     */
    public Reservation save(ReservationDTO reservationDTO);

    /**
     * Updates an existing reservation.
     * <p>
     * This method updates an existing reservation identified by its {@code id} using the provided
     * {@link ReservationDTO}.
     * </p>
     *
     * @param id              the ID of the reservation to update
     * @param reservationDTO  the updated reservation data
     * @return the updated {@link Reservation}
     */
    public Reservation update(Long id, ReservationDTO reservationDTO);
}
