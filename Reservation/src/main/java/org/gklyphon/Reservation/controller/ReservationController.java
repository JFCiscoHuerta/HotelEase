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

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final IReservationService service;
    private final PagedResourcesAssembler<Reservation> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Reservation> reservations = service.findAll();
        if (reservations.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        Reservation reservation = service.findById(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/by-user/{user_id}")
    public ResponseEntity<?> getByUserId(
            @PathVariable(name = "user_id") Long userId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handleEntityModels(service.findByUserId(userId, pageable), pageable));
    }

    @GetMapping("/by-start-date")
    public ResponseEntity<?> getByStartDate(
            @RequestParam(name = "start-date") LocalDate startDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handleEntityModels(service.findByStartDate(startDate, pageable), pageable));
    }

    @GetMapping("/by-end-date")
    public ResponseEntity<?> getByEndDate(
            @RequestParam(name = "end-date") LocalDate endDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handleEntityModels(service.findByStartDate(endDate, pageable), pageable));
    }

    @PostMapping("/create")
    public ResponseEntity<?> saveReservation(
            @Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = service.save(reservationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReservation(
            @PathVariable Long id,
            @Valid @RequestBody ReservationDTO reservationDTO) {
        Reservation reservation = service.update(id, reservationDTO);
        return ResponseEntity.ok(reservation);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(
            @PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private PagedModel<EntityModel<Reservation>> handleEntityModels(Page<Reservation> page, Pageable pageable) {
        return pagedResourcesAssembler.toModel(page);
    }

}
