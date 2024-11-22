package org.gklyphon.room.service;

import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface IRoomService extends IService<Room> {
    Page<Room> findByPriceByNightGreaterThan(BigDecimal priceByNight, Pageable pageable);
    Page<Room> findByPriceByNightLessThan(BigDecimal priceByNight, Pageable pageable);
    Page<Room> findByPriceByNightBetween(BigDecimal min, BigDecimal max, Pageable pageable);
    Page<Room> findByRoomType(RoomType roomType, Pageable pageable);
    Page<Room> findByRoomState(RoomState roomState, Pageable pageable);
    Page<Room> findByRoomTypeAndRoomState(RoomType roomType, RoomState roomState, Pageable pageable);

    Room save(RoomRegisterDTO roomRegisterDTO);
    Room update(Long id, RoomRegisterDTO roomRegisterDTO);
}
