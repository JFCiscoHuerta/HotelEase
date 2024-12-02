package org.gklyphon.room.service.impl;

import org.gklyphon.room.Data;
import org.gklyphon.room.mapper.IRoomMapper;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.gklyphon.room.repository.IRoomFeatureRepository;
import org.gklyphon.room.repository.IRoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test class for the `RoomServiceImpl` class, responsible for testing the service layer
 * of room management in the application. This class ensures that the methods within `RoomServiceImpl`
 * work as expected by mocking the dependencies such as the `IRoomRepository`, `IRoomFeatureRepository`, and `IRoomMapper`.
 * All tests are written using the Mockito framework to mock external interactions and validate the logic of the service methods.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since  25-Nov-2024
 */
@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    /**
     * Mocked instance of the room repository interface.
     * Used to simulate interactions with the database for room-related operations.
     */
    @Mock
    IRoomRepository repository;

    /**
     * Mocked instance of the room feature repository interface.
     * Used to simulate interactions with the database for room feature-related operations.
     */
    @Mock
    IRoomFeatureRepository featureRepository;

    /**
     * Mocked instance of the room mapper.
     * Used to simulate the conversion between `RoomRegisterDTO` and `Room` entities.
     */
    @Mock
    IRoomMapper mapper;

    /**
     * Instance of the `RoomServiceImpl` class that is being tested.
     * The service layer class that contains business logic related to rooms.
     */
    @InjectMocks
    RoomServiceImpl service;

    /**
     * Test case to verify the `findByPriceByNightBetween` method in the `RoomServiceImpl` class.
     * Validates that the method correctly retrieves a list of rooms within a specified price range.
     */
    @Test
    void findByPriceByNightBetween() {
        when(repository.findByPriceByNightBetween(any(BigDecimal.class), any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByPriceByNightBetween(
                new BigDecimal("122"), new BigDecimal("12"),mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomNumber()).isEqualTo(101L);
        verify(repository).findByPriceByNightBetween(any(BigDecimal.class), any(BigDecimal.class), any(Pageable.class));
    }

    /**
     * Test case to verify the `findByPriceByNightGreaterThan` method in the `RoomServiceImpl` class.
     * Validates that the method retrieves rooms where the price per night is greater than a specified value.
     */
    @Test
    void findByPriceByNightGreaterThan() {
        when(repository.findByPriceByNightGreaterThan(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByPriceByNightGreaterThan(new BigDecimal("1000"), mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomState()).isEqualTo(RoomState.AVAILABLE);
        verify(repository).findByPriceByNightGreaterThan(any(BigDecimal.class), any(Pageable.class));
    }

    /**
     * Test case to verify the `findByPriceByNightLessThan` method in the `RoomServiceImpl` class.
     * Validates that the method retrieves rooms where the price per night is less than a specified value.
     */
    @Test
    void findByPriceByNightLessThan() {
        when(repository.findByPriceByNightLessThan(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByPriceByNightLessThan(new BigDecimal("123"), mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomState()).isEqualTo(RoomState.AVAILABLE);
        verify(repository).findByPriceByNightLessThan(any(BigDecimal.class), any(Pageable.class));
    }

    /**
     * Test case to verify the `findByRoomState` method in the `RoomServiceImpl` class.
     * Validates that the method correctly retrieves rooms with a specific room state.
     */
    @Test
    void findByRoomState() {
        when(repository.findByRoomState(any(RoomState.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByRoomState(RoomState.AVAILABLE, mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomNumber()).isEqualTo(101L);
        verify(repository).findByRoomState(any(RoomState.class), any(Pageable.class));
    }

    /**
     * Test case to verify the `findByRoomType` method in the `RoomServiceImpl` class.
     * Validates that the method correctly retrieves rooms of a specified room type.
     */
    @Test
    void findByRoomType() {
        when(repository.findByRoomType(any(RoomType.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByRoomType(RoomType.SUITE, mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomType()).isEqualTo(RoomType.SUITE);
        verify(repository).findByRoomType(any(RoomType.class), any(Pageable.class));
    }

    /**
     * Test case to verify the `findByRoomTypeAndRoomState` method in the `RoomServiceImpl` class.
     * Validates that the method correctly retrieves rooms that match both a specified room type and room state.
     */
    @Test
    void findByRoomTypeAndRoomState() {
        when(repository.findByRoomTypeAndRoomState(any(RoomType.class), any(RoomState.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByRoomTypeAndRoomState(RoomType.SUITE, RoomState.AVAILABLE, mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomState()).isEqualTo(RoomState.AVAILABLE);
        verify(repository).findByRoomTypeAndRoomState(any(RoomType.class), any(RoomState.class), any(Pageable.class));
    }

    /**
     * Test case to verify the `delete` method in the `RoomServiceImpl` class.
     * Validates that the room is deleted correctly without throwing any exception.
     */
    @Test
    void delete() {
        doNothing().when(repository).deleteById(anyLong());
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.ROOM));
        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).deleteById(anyLong());
    }

    /**
     * Test case to verify the `findAll` method in the `RoomServiceImpl` class.
     * Validates that the method correctly retrieves all rooms in a paginated manner.
     */
    @Test
    void findAllPageable() {
        when(repository.findAll(any(Pageable.class))).thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findAllPageable(mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        verify(repository).findAll(any(Pageable.class));
    }

    /**
     * Test case to verify the `findById` method in the `RoomServiceImpl` class.
     * Validates that the method correctly retrieves a room by its ID.
     */
    @Test
    void findById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.ROOM));
        Room room = service.findById(1L);
        assertEquals(RoomState.AVAILABLE, room.getRoomState());
        verify(repository).findById(anyLong());
    }

    /**
     * Test case to verify the `save` method in the `RoomServiceImpl` class.
     * Validates that the method correctly saves a new room based on the provided DTO.
     */
    @Test
    void save() {
        when(mapper.toRoom(any(RoomRegisterDTO.class))).thenReturn(Data.ROOM);
        when(featureRepository.findAllById(any())).thenReturn(Data.ROOM_FEATURES);
        when(repository.save(any(Room.class))).thenReturn(Data.ROOM);
        Room room = service.save(Data.ROOM_REGISTER_DTO);
        assertEquals(101L, room.getRoomNumber());
        verify(repository).save(any(Room.class));
        verify(mapper).toRoom(any(RoomRegisterDTO.class));
    }

    /**
     * Test case to verify the `update` method in the `RoomServiceImpl` class.
     * Validates that the method correctly updates an existing room based on the provided DTO.
     */
    @Test
    void update() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.ROOM));
        when(mapper.toRoom(any(RoomRegisterDTO.class))).thenReturn(Data.ROOM);
        when(featureRepository.findAllById(any())).thenReturn(Data.ROOM_FEATURES);
        when(mapper.toRoomImages(any())).thenReturn(Data.ROOM_IMAGES);
        when(repository.save(any(Room.class))).thenReturn(Data.ROOM);

        Room room = service.update(1L, Data.ROOM_REGISTER_DTO);
        assertEquals(101L, room.getRoomNumber());

        verify(mapper).toRoom(any(RoomRegisterDTO.class));
        verify(mapper).toRoomImages(any());
        verify(featureRepository).findAllById(any());
        verify(repository).save(any(Room.class));
    }

}