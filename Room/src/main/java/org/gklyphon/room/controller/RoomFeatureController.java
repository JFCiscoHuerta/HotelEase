package org.gklyphon.room.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gklyphon.room.mapper.IRoomMapper;
import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.model.entities.RoomFeature;
import org.gklyphon.room.service.IRoomFeatureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling all HTTP requests related to room features.
 * Provides endpoints for CRUD operations on room features.
 *
 * <p>This controller exposes the following endpoints:</p>
 * <ul>
 *     <li>GET /rooms/features: Retrieve all room features, with pagination.</li>
 *     <li>GET /rooms/features/{id}: Retrieve a room feature by its ID.</li>
 *     <li>POST /rooms/features/create: Create a new room feature.</li>
 *     <li>PUT /rooms/features/update/{id}: Update an existing room feature by its ID.</li>
 *     <li>DELETE /rooms/features/delete/{id}: Delete a room feature by its ID.</li>
 * </ul>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@RestController
@RequestMapping("/rooms/features")
@RequiredArgsConstructor
public class RoomFeatureController {

    private final IRoomFeatureService service;
    private final PagedResourcesAssembler<RoomFeature> pagedResourcesAssembler;

    /**
     * Retrieves a paginated list of room features.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated room features
     */
    @GetMapping
    public ResponseEntity<?> getAllRoomFeatures(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomFeature> roomFeaturePage = service.findAllPageable(pageable);
        PagedModel<EntityModel<RoomFeature>> pagedModel = pagedResourcesAssembler.toModel(roomFeaturePage);
        return ResponseEntity.ok(pagedModel);
    }

    /**
     * Retrieves a room feature by its ID.
     *
     * @param id the ID of the room feature to retrieve
     * @return a ResponseEntity containing the room feature data
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById (
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * Creates a new room feature.
     *
     * @param roomFeatureRegisterDTO the data transfer object containing room feature details
     * @return a ResponseEntity containing the created room feature
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRoomFeature(
            @Valid @RequestBody RoomFeatureRegisterDTO roomFeatureRegisterDTO) {
        RoomFeature roomFeature = service.save(roomFeatureRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomFeature);
    }

    /**
     * Updates an existing room feature.
     *
     * @param id the ID of the room feature to update
     * @param roomFeatureRegisterDTO the data transfer object containing updated room feature details
     * @return a ResponseEntity containing the updated room feature
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoomFeature(
            @PathVariable Long id,
            @Valid @RequestBody RoomFeatureRegisterDTO roomFeatureRegisterDTO) {
        RoomFeature roomFeature = service.update(id, roomFeatureRegisterDTO);
        return ResponseEntity.ok(roomFeature);
    }

    /**
     * Deletes a room feature by its ID.
     *
     * @param id the ID of the room feature to delete
     * @return a ResponseEntity with no content (HTTP status 204)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoomFeature(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves a list of room features.
     *
     * @return a ResponseEntity containing the room features
     */
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
