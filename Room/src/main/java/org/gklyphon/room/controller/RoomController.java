package org.gklyphon.room.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.gklyphon.room.service.IRoomService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final IRoomService roomService;
    private final PagedResourcesAssembler<Room> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<?> getAllRooms(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findAll(pageable);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoom(
            @Valid @RequestBody RoomRegisterDTO roomRegisterDTO, BindingResult result) {
        ResponseEntity<?> build = handleBindingResult(result);
        if (build != null) return build;
        Room room = roomService.save(roomRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomRegisterDTO roomRegisterDTO, BindingResult result) {
        ResponseEntity<?> build = handleBindingResult(result);
        if (build != null) return build;
        Room room = roomService.update(id, roomRegisterDTO);
        return ResponseEntity.ok(room);
    }

    private static ResponseEntity<Object> handleBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorList = result.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().build();
        }
        return null;
    }

    @GetMapping("/price-by-night-between")
    public ResponseEntity<?> getByPriceByNightBetween(
            @RequestParam(name = "min") BigDecimal min,
            @RequestParam(name = "max") BigDecimal max,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findByPriceByNightBetween(min, max, pageable);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/price-by-night-grater-than")
    public ResponseEntity<?> getByPriceByNightGreaterThan(
            @RequestParam(name = "price-by-night") BigDecimal priceByNight,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findByPriceByNightGreaterThan(priceByNight, pageable);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/price-by-night-lower-than")
    public ResponseEntity<?> getByPriceByNighLowerThan(
            @RequestParam(name = "price-by-night") BigDecimal priceByNight,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findByPriceByNightLowerThan(priceByNight, pageable);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/by-room-state")
    public ResponseEntity<?> getByRoomState(
            @RequestParam(name = "room-state") RoomState roomState,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findByRoomState(roomState, pageable);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/by-room-type")
    public ResponseEntity<?> getByRoomType(
            @RequestParam(name = "room-type") RoomType roomType,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findByRoomType(roomType, pageable);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/by-room-type-and-room-state")
    public ResponseEntity<?> getByRoomTypeAndRoomState(
            @RequestParam(name = "room-type") RoomType roomType,
            @RequestParam(name = "room-state") RoomState roomState,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Room> roomPage = roomService.findByRoomTypeAndRoomState(roomType, roomState, pageable);
        PagedModel<EntityModel<Room>> pagedModel = pagedResourcesAssembler.toModel(roomPage);
        return ResponseEntity.ok(pagedModel);
    }

}
