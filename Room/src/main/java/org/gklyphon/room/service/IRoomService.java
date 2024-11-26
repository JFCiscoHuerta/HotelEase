package org.gklyphon.room.service;

import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

/**
 * Service interface for managing {@link Room} entities.
 *
 * <p>This service provides methods to find rooms based on various criteria such as price,
 * room type, and room state. It also provides methods for saving and updating room information.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
public interface IRoomService extends IService<Room> {

    /**
     * Finds rooms where the price per night is greater than the given value.
     *
     * @param priceByNight the minimum price per night
     * @param pageable pagination information
     * @return a page of rooms with price per night greater than the specified value
     */
    Page<Room> findByPriceByNightGreaterThan(BigDecimal priceByNight, Pageable pageable);

    /**
     * Finds rooms where the price per night is less than the given value.
     *
     * @param priceByNight the maximum price per night
     * @param pageable pagination information
     * @return a page of rooms with price per night less than the specified value
     */
    Page<Room> findByPriceByNightLessThan(BigDecimal priceByNight, Pageable pageable);

    /**
     * Finds rooms where the price per night is between the given range.
     *
     * @param min the minimum price per night
     * @param max the maximum price per night
     * @param pageable pagination information
     * @return a page of rooms with price per night within the specified range
     */
    Page<Room> findByPriceByNightBetween(BigDecimal min, BigDecimal max, Pageable pageable);

    /**
     * Finds rooms by their type.
     *
     * @param roomType the room type to filter by
     * @param pageable pagination information
     * @return a page of rooms with the specified room type
     */
    Page<Room> findByRoomType(RoomType roomType, Pageable pageable);

    /**
     * Finds rooms by their state.
     *
     * @param roomState the room state to filter by
     * @param pageable pagination information
     * @return a page of rooms with the specified room state
     */
    Page<Room> findByRoomState(RoomState roomState, Pageable pageable);

    /**
     * Finds rooms by both room type and room state.
     *
     * @param roomType the room type to filter by
     * @param roomState the room state to filter by
     * @param pageable pagination information
     * @return a page of rooms with the specified room type and room state
     */
    Page<Room> findByRoomTypeAndRoomState(RoomType roomType, RoomState roomState, Pageable pageable);

    /**
     * Saves a new {@link Room} based on the provided {@link RoomRegisterDTO}.
     *
     * @param roomRegisterDTO the DTO containing the room data
     * @return the saved room
     */
    Room save(RoomRegisterDTO roomRegisterDTO);

    /**
     * Updates an existing room with new data based on the provided {@link RoomRegisterDTO}.
     *
     * @param id the id of the room to update
     * @param roomRegisterDTO the DTO containing the updated room data
     * @return the updated room
     */
    Room update(Long id, RoomRegisterDTO roomRegisterDTO);
}
