package org.gklyphon.room;

import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class Data {

    public static final Room ROOM = Room.builder()
            .roomNumber(101L)
            .priceByNight(BigDecimal.valueOf(150.00))
            .roomType(RoomType.SUITE)
            .roomState(RoomState.AVAILABLE)
            .build();

    public static final RoomRegisterDTO ROOM_REGISTER_DTO = RoomRegisterDTO.builder()
            .roomNumber(101L)
            .priceByNight(BigDecimal.valueOf(150.00))
            .roomType("SUITE")
            .roomState("AVAILABLE")
            .build();

    public static final List<Room> ROOMS = List.of(ROOM);

    public static final Page<Room> PAGE_ROOMS = new PageImpl<Room>(ROOMS);
}
