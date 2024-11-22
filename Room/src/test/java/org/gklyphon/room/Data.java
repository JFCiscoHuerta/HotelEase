package org.gklyphon.room;

import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.model.dtos.RoomImageRegisterDTO;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.RoomFeature;
import org.gklyphon.room.model.entities.RoomImage;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

public class Data {

    public static final Room ROOM = Room.builder()
            .id(1L)
            .roomNumber(101L)
            .priceByNight(BigDecimal.valueOf(150.00))
            .roomType(RoomType.SUITE)
            .roomState(RoomState.AVAILABLE)
            .roomImages(
                    List.of(
                            RoomImage.builder()
                                    .id(1L)
                                    .path("/path")
                                    .room(new Room())
                                    .build()
                    )
            )
            .roomFeatures(
                    List.of(
                            RoomFeature.builder()
                                    .id(1L)
                                    .serviceName("TV")
                                    .servicePrice(new BigDecimal("5.00"))
                                    .build(),
                            RoomFeature.builder()
                                    .id(2L)
                                    .serviceName("XBOX")
                                    .servicePrice(new BigDecimal("3.00"))
                                    .build()
                    )
            )
            .build();

    public static final RoomRegisterDTO ROOM_REGISTER_DTO = RoomRegisterDTO.builder()
            .roomNumber(101L)
            .priceByNight(BigDecimal.valueOf(150.00))
            .roomType("SUITE")
            .roomState("AVAILABLE")
            .roomImages(
                    List.of(
                            RoomImageRegisterDTO.builder()
                                    .path("/path")
                                    .roomId(1L)
                                    .build()
                    )
            )
            .roomFeatures(
                    List.of(
                            RoomFeatureRegisterDTO.builder()
                                    .serviceName("TV")
                                    .servicePrice(new BigDecimal("5.00"))
                                    .build(),
                            RoomFeatureRegisterDTO.builder()
                                    .serviceName("XBOX")
                                    .servicePrice(new BigDecimal("3.00"))
                                    .build()
                    )
            )
            .build();

    public static final List<Room> ROOMS = List.of(ROOM);

    public static final Page<Room> PAGE_ROOMS = new PageImpl<Room>(ROOMS);
}
