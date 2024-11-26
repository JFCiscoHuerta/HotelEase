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

@RestController
@RequestMapping("/rooms/features")
@RequiredArgsConstructor
public class RoomFeatureController {

    private final IRoomFeatureService service;
    private final IRoomMapper mapper;
    private final PagedResourcesAssembler<RoomFeature> pagedResourcesAssembler;

    @GetMapping
    public ResponseEntity<?> getAllRoomFeatures(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RoomFeature> roomFeaturePage = service.findAll(pageable);
        PagedModel<EntityModel<RoomFeature>> pagedModel = pagedResourcesAssembler.toModel(roomFeaturePage);
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById (
            @PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRoomFeature(
            @Valid @RequestBody RoomFeatureRegisterDTO roomFeatureRegisterDTO) {
        RoomFeature roomFeature = service.save(roomFeatureRegisterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(roomFeature);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateRoomFeature(
            @PathVariable Long id,
            @Valid @RequestBody RoomFeatureRegisterDTO roomFeatureRegisterDTO) {
        RoomFeature roomFeature = service.update(id, roomFeatureRegisterDTO);
        return ResponseEntity.ok(roomFeature);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoomFeature(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
