package org.gklyphon.room.repository;

import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

public interface IRoomRepository extends JpaRepository<Room, Long> {

    Page<Room> findByPriceByNightGreaterThan(BigDecimal bigDecimal, Pageable pageable);
    Page<Room> findByPriceByNightLowerThan(BigDecimal bigDecimal, Pageable pageable);
    Page<Room> findByPriceByNightBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    Page<Room> findByRoomType(RoomType roomType, Pageable pageable);
    Page<Room> findByRoomState(RoomState roomState, Pageable pageable);

    Page<Room> findByRoomTypeAndRoomState(RoomType roomType, RoomState roomState);

}
