package org.gklyphon.room.controller;

import lombok.RequiredArgsConstructor;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.service.IRoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
