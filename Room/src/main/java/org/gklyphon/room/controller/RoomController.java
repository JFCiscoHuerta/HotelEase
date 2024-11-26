package org.gklyphon.room.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.gklyphon.room.service.IRoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * Controller responsible for handling all HTTP requests related to rooms.
 * Provides endpoints for CRUD operations and filtered queries for rooms.
 *
 * <p>This controller exposes the following endpoints:</p>
 * <ul>
 *     <li>GET /rooms: Retrieve all rooms, with pagination.</li>
 *     <li>GET /rooms/{id}: Retrieve a room by its ID.</li>
 *     <li>POST /rooms/create: Create a new room.</li>
 *     <li>PUT /rooms/update/{id}: Update an existing room by its ID.</li>
 *     <li>DELETE /rooms/delete/{id}: Delete a room by its ID.</li>
 *     <li>GET /rooms/price-by-night-between: Retrieve rooms with a price between a given range.</li>
 *     <li>GET /rooms/price-by-night-greater-than: Retrieve rooms with a price greater than the given value.</li>
 *     <li>GET /rooms/price-by-night-less-than: Retrieve rooms with a price less than the given value.</li>
 *     <li>GET /rooms/by-room-state: Retrieve rooms by their state.</li>
 *     <li>GET /rooms/by-room-type: Retrieve rooms by their type.</li>
 *     <li>GET /rooms/by-room-type-and-room-state: Retrieve rooms by both their type and state.</li>
 * </ul>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;
    private final PagedResourcesAssembler<Room> pagedResourcesAssembler;

    /**
     * Retrieves a paginated list of rooms.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated rooms
     */
    @GetMapping
    public ResponseEntity<?> getAllRooms(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handlePageModels(roomService.findAll(pageable)));
    }

    /**
     * Retrieves a room by its ID.
     *
     * @param id the ID of the room to retrieve
     * @return a ResponseEntity containing the room data
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    /**
     * Creates a new room.
     *
     * @param roomRegisterDTO the data transfer object containing room details
     * @return a ResponseEntity containing the created room
     */
    @PostMapping("/create")
    public ResponseEntity<?> createRoom(
            @Valid @RequestBody RoomRegisterDTO roomRegisterDTO) {
        Room room = roomService.save(roomRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    /**
     * Updates an existing room.
     *
     * @param id the ID of the room to update
     * @param roomRegisterDTO the data transfer object containing updated room details
     * @return a ResponseEntity containing the updated room
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRegisterDTO roomRegisterDTO) {
        Room room = roomService.update(id, roomRegisterDTO);
        return ResponseEntity.ok(room);
    }

    /**
     * Deletes a room by its ID.
     *
     * @param id the ID of the room to delete
     * @return a ResponseEntity with no content (HTTP status 204)
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves rooms with a price between a given range.
     *
     * @param min the minimum price per night
     * @param max the maximum price per night
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated rooms within the price range
     */
    @GetMapping("/price-by-night-between")
    public ResponseEntity<?> getByPriceByNightBetween(
            @RequestParam(name = "min") BigDecimal min,
            @RequestParam(name = "max") BigDecimal max,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handlePageModels(roomService.findByPriceByNightBetween(min, max, pageable)));
    }

    /**
     * Retrieves rooms with a price greater than the given value.
     *
     * @param priceByNight the price per night to compare against
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated rooms with a price greater than the given value
     */
    @GetMapping("/price-by-night-grater-than")
    public ResponseEntity<?> getByPriceByNightGreaterThan(
            @RequestParam(name = "price-by-night") BigDecimal priceByNight,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handlePageModels(roomService.findByPriceByNightGreaterThan(priceByNight, pageable)));
    }

    /**
     * Retrieves rooms with a price less than the given value.
     *
     * @param priceByNight the price per night to compare against
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated rooms with a price less than the given value
     */
    @GetMapping("/price-by-night-less-than")
    public ResponseEntity<?> getByPriceByNightLessThan(
            @RequestParam(name = "price-by-night") BigDecimal priceByNight,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(handlePageModels(
                roomService.findByPriceByNightLessThan(priceByNight, pageable)));
    }

    /**
     * Retrieves rooms by their state.
     *
     * @param roomState the state of the room to filter by
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated rooms with the specified room state
     */
    @GetMapping("/by-room-state")
    public ResponseEntity<?> getByRoomState(
            @RequestParam(name = "room-state") RoomState roomState,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handlePageModels(roomService.findByRoomState(roomState, pageable)));
    }

    /**
     * Retrieves rooms by their type.
     *
     * @param roomType the type of the room to filter by
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated rooms with the specified room type
     */
    @GetMapping("/by-room-type")
    public ResponseEntity<?> getByRoomType(
            @RequestParam(name = "room-type") RoomType roomType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(handlePageModels(roomService.findByRoomType(roomType, pageable)));
    }

    /**
     * Retrieves rooms by both their type and state.
     *
     * @param roomType the type of the room to filter by
     * @param roomState the state of the room to filter by
     * @param page the page number to retrieve (default is 0)
     * @param size the number of elements per page (default is 10)
     * @return a ResponseEntity containing the paginated rooms with the specified type and state
     */
    @GetMapping("/by-room-type-and-room-state")
    public ResponseEntity<?> getByRoomTypeAndRoomState(
            @RequestParam(name = "room-type") RoomType roomType,
            @RequestParam(name = "room-state") RoomState roomState,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(
                handlePageModels(roomService.findByRoomTypeAndRoomState(roomType, roomState, pageable))
        );
    }

    /**
     * Converts a {@link Page} of {@link Room} entities into a {@link PagedModel} of {@link EntityModel} to support
     * pagination in the response.
     *
     * @param page the {@link Page} of {@link Room} entities to be converted
     * @return a {@link PagedModel} containing {@link EntityModel} representations of the {@link Room} entities
     *         from the provided {@link Page}
     */
    private PagedModel<EntityModel<Room>> handlePageModels(Page<Room> page) {
        return pagedResourcesAssembler.toModel(page);
    }
}
