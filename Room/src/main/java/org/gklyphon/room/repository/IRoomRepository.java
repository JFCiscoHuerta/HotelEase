package org.gklyphon.room.repository;

import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;

/**
 * Repository interface for accessing and managing {@link Room} entities.
 *
 * <p>This interface extends {@link JpaRepository} and provides custom query methods
 * for querying rooms based on different attributes such as price, type, and state.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
public interface IRoomRepository extends JpaRepository<Room, Long> {

    /**
     * Finds rooms with a nightly price greater than the specified value.
     *
     * @param priceByNight the price to compare against
     * @param pageable pagination information
     * @return a page of rooms with a nightly price greater than the specified value
     */
    Page<Room> findByPriceByNightGreaterThan(BigDecimal priceByNight, Pageable pageable);

    /**
     * Finds rooms with a nightly price less than the specified value.
     *
     * @param priceByNight the price to compare against
     * @param pageable pagination information
     * @return a page of rooms with a nightly price less than the specified value
     */
    Page<Room> findByPriceByNightLessThan(BigDecimal priceByNight, Pageable pageable);

    /**
     * Finds rooms with a nightly price between the specified range.
     *
     * @param min the minimum price
     * @param max the maximum price
     * @param pageable pagination information
     * @return a page of rooms with a nightly price between the specified range
     */
    Page<Room> findByPriceByNightBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    /**
     * Finds rooms of a specific type.
     *
     * @param roomType the room type to search for
     * @param pageable pagination information
     * @return a page of rooms of the specified type
     */
    Page<Room> findByRoomType(RoomType roomType, Pageable pageable);

    /**
     * Finds rooms in a specific state.
     *
     * @param roomState the room state to search for
     * @param pageable pagination information
     * @return a page of rooms in the specified state
     */
    Page<Room> findByRoomState(RoomState roomState, Pageable pageable);

    /**
     * Finds rooms of a specific type and state.
     *
     * @param roomType the room type to search for
     * @param roomState the room state to search for
     * @param pageable pagination information
     * @return a page of rooms of the specified type and state
     */
    Page<Room> findByRoomTypeAndRoomState(RoomType roomType, RoomState roomState, Pageable pageable);
}
