package org.gklyphon.Reservation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.gklyphon.Reservation.service.IReservationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST Controller for managing {@link Reservation} entities.
 * <p>
 * This controller provides endpoints for handling CRUD operations and filtering reservations by user, start date, or end date.
 * It returns responses in standard HTTP formats with appropriate status codes.
 * </p>
 *
 * @see Reservation
 * @see ReservationDTO
 * @see IReservationService
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 4-Dec-2024
 */
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService service;
    private final PagedResourcesAssembler<Reservation> pagedResourcesAssembler;

    /**
     * Retrieves all reservations.
     *
     * @return a list of all reservations or a 204 No Content status if none are found.
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Reservation> reservations = service.findAll();
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(service.findAll());
    }


    /**
     * Retrieves a reservation by its ID.
     *
     * @param id the ID of the reservation to retrieve
     * @return the reservation with the given ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Reservation reservation = service.findById(id);
        return ResponseEntity.ok(reservation);
    }

    /**
     * Retrieves reservations by user ID with pagination.
     *
     * @param userId the ID of the user whose reservations are retrieved
     * @param page the page number (default is 0)
     * @param size the number of records per page (default is 10)
     * @return a paginated list of reservations for the specified user
     */
    @GetMapping("/by-user/{user_id}")
    public ResponseEntity<?> getByUserId(
            @PathVariable(name = "user_id") Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handleEntityModels(service.findByUserId(userId, pageable), pageable));
    }

    /**
     * Retrieves reservations by start date with pagination.
     *
     * @param startDate the start date of the reservations to filter
     * @param page the page number (default is 0)
     * @param size the number of records per page (default is 10)
     * @return a paginated list of reservations starting from the specified date
     */
    @GetMapping("/by-start-date")
    public ResponseEntity<?> getByStartDate(
            @RequestParam(name = "start-date") LocalDate startDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handleEntityModels(service.findByStartDate(startDate, pageable), pageable));
    }

    /**
     * Retrieves reservations by end date with pagination.
     *
     * @param endDate the end date of the reservations to filter
     * @param page the page number (default is 0)
     * @param size the number of records per page (default is 10)
     * @return a paginated list of reservations ending on the specified date
     */
    @GetMapping("/by-end-date")
    public ResponseEntity<?> getByEndDate(
            @RequestParam(name = "end-date") LocalDate endDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handleEntityModels(service.findByStartDate(endDate, pageable), pageable));
    }

    /**
     * Creates a new reservation.
     *
     * @param reservationDTO the data transfer object containing reservation details
     * @return the created reservation with a 201 Created status
     */
    @PostMapping("/create")
    public ResponseEntity<?> saveReservation(
            @Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = service.save(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    /**
     * Updates an existing reservation by ID.
     *
     * @param id the ID of the reservation to update
     * @param reservationDTO the data transfer object containing updated reservation details
     * @return the updated reservation entity
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = service.update(id, reservationDTO);
        return ResponseEntity.ok(reservation);
    }

    /**
     * Deletes a reservation by its ID.
     *
     * @param id the ID of the reservation to delete
     * @return a 204 No Content status on successful deletion
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles pagination and conversion of {@link Reservation} entities into HATEOAS-compatible paginated models.
     *
     * @param page the paginated result set
     * @param pageable pagination information
     * @return a paginated model with HATEOAS support
     */
    private PagedModel<EntityModel<Reservation>> handleEntityModels(Page<Reservation> page, Pageable pageable) {
        return pagedResourcesAssembler.toModel(page);
    }

}
