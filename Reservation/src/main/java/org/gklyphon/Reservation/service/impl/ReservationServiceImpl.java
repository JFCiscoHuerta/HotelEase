package org.gklyphon.Reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.gklyphon.Reservation.exception.exception.ElementNotFoundException;
import org.gklyphon.Reservation.mapper.IReservationMapper;
import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.gklyphon.Reservation.repository.IReservationRepository;
import org.gklyphon.Reservation.service.IReservationService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the {@link IReservationService} interface for managing {@link Reservation} entities.
 * <p>
 * This service handles the CRUD operations for {@link Reservation} entities, including:
 * saving, updating, deleting, and retrieving reservations by different criteria such as
 * {@code startDate}, {@code endDate}, and {@code userId}.
 * </p>
 * <p>
 * The class utilizes a repository for data access and a mapper for transforming
 * between {@link ReservationDTO} and {@link Reservation} objects.
 * </p>
 *
 * @see IReservationService
 * @see Reservation
 * @see ReservationDTO
 * @see IReservationRepository
 * @see IReservationMapper
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 3-Dec-2024
 */
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository repository;
    private final IReservationMapper mapper;

    /**
     * Deletes a reservation by its ID.
     * <p>
     * This method first checks if the reservation with the given {@code id} exists, and if so,
     * it deletes it from the repository. If no reservation is found, no action is taken.
     * </p>
     *
     * @param id the ID of the reservation to delete
     * @throws ServiceException if an error occurs during deletion
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            if (findById(id) != null) {
                repository.deleteById(id);
            }
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while deleting the reservation.", e);
        }
    }

    /**
     * Retrieves all reservations.
     * <p>
     * This method fetches all reservations from the repository.
     * </p>
     *
     * @return a list of all reservations
     */
    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieves a reservation by its ID.
     * <p>
     * This method retrieves the reservation associated with the given {@code id}.
     * If no reservation is found, an exception will be thrown.
     * </p>
     *
     * @param id the ID of the reservation to retrieve
     * @return the reservation with the given ID
     * @throws ServiceException if no reservation is found for the given ID
     */
    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new ElementNotFoundException("Reservation with id " + id + " not found."));
    }

    /**
     * Saves a new reservation.
     * <p>
     * This method saves a new reservation based on the provided {@link ReservationDTO}.
     * The {@link ReservationDTO} is converted to a {@link Reservation} entity before being saved.
     * </p>
     *
     * @param reservationDTO the DTO containing reservation data to save
     * @return the saved reservation entity
     * @throws ServiceException if an error occurs during saving
     */
    @Override
    @Transactional
    public Reservation save(ReservationDTO reservationDTO) {
        try {
            return repository.save(mapper.toReservation(reservationDTO));
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while saving reservation.", e);
        }
    }

    /**
     * Updates an existing reservation.
     * <p>
     * This method updates an existing reservation with the data provided in the {@link ReservationDTO}.
     * The properties from the DTO are copied to the original reservation entity, excluding the {@code id}.
     * </p>
     *
     * @param id the ID of the reservation to update
     * @param reservationDTO the DTO containing updated reservation data
     * @return the updated reservation entity
     * @throws ServiceException if an error occurs during update
     */
    @Override
    @Transactional
    public Reservation update(Long id, ReservationDTO reservationDTO) {
        try {
            Reservation reservation = mapper.toReservation(reservationDTO);
            Reservation originalReservation = findById(id);
            BeanUtils.copyProperties(reservation, originalReservation, "id");
            return repository.save(originalReservation);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while updating reservation.", e);
        }
    }

    /**
     * Retrieves reservations by start date.
     * <p>
     * This method retrieves reservations whose {@code startDate} matches the given date.
     * The results are returned in a paginated format.
     * </p>
     *
     * @param startDate the start date of the reservation
     * @param pageable pagination information
     * @return a page of reservations with the given start date
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findByStartDate(LocalDate startDate, Pageable pageable) {
        return repository.findByStartDate(startDate, pageable);
    }

    /**
     * Retrieves reservations by end date.
     * <p>
     * This method retrieves reservations whose {@code endDate} matches the given date.
     * The results are returned in a paginated format.
     * </p>
     *
     * @param endDate the end date of the reservation
     * @param pageable pagination information
     * @return a page of reservations with the given end date
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findByEndDate(LocalDate endDate, Pageable pageable) {
        return repository.findByEndDate(endDate, pageable);
    }

    /**
     * Retrieves reservations by user ID.
     * <p>
     * This method retrieves reservations associated with the given {@code userId}.
     * The results are returned in a paginated format.
     * </p>
     *
     * @param userId the ID of the user whose reservations to retrieve
     * @param pageable pagination information
     * @return a page of reservations for the specified user
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findByUserId(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }
}
