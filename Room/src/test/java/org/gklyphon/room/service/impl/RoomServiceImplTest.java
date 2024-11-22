package org.gklyphon.room.service.impl;

import org.gklyphon.room.Data;
import org.gklyphon.room.mapper.IRoomMapper;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
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

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @Mock
    IRoomRepository repository;

    @Mock
    IRoomMapper mapper;

    @InjectMocks
    RoomServiceImpl service;

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

    @Test
    void findByPriceByNightGreaterThan() {
        when(repository.findByPriceByNightGreaterThan(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByPriceByNightGreaterThan(new BigDecimal("1000"), mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomState()).isEqualTo(RoomState.AVAILABLE);
        verify(repository).findByPriceByNightGreaterThan(any(BigDecimal.class), any(Pageable.class));
    }

    @Test
    void findByPriceByNightLessThan() {
        when(repository.findByPriceByNightLessThan(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByPriceByNightLessThan(new BigDecimal("123"), mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomState()).isEqualTo(RoomState.AVAILABLE);
        verify(repository).findByPriceByNightLessThan(any(BigDecimal.class), any(Pageable.class));
    }

    @Test
    void findByRoomState() {
        when(repository.findByRoomState(any(RoomState.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByRoomState(RoomState.AVAILABLE, mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomNumber()).isEqualTo(101L);
        verify(repository).findByRoomState(any(RoomState.class), any(Pageable.class));
    }

    @Test
    void findByRoomType() {
        when(repository.findByRoomType(any(RoomType.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByRoomType(RoomType.SUITE, mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomType()).isEqualTo(RoomType.SUITE);
        verify(repository).findByRoomType(any(RoomType.class), any(Pageable.class));
    }

    @Test
    void findByRoomTypeAndRoomState() {
        when(repository.findByRoomTypeAndRoomState(any(RoomType.class), any(RoomState.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findByRoomTypeAndRoomState(RoomType.SUITE, RoomState.AVAILABLE, mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        assertThat(roomsPage.getContent().getFirst().getRoomState()).isEqualTo(RoomState.AVAILABLE);
        verify(repository).findByRoomTypeAndRoomState(any(RoomType.class), any(RoomState.class), any(Pageable.class));
    }

    @Test
    void delete() {
        doNothing().when(repository).deleteById(anyLong());
        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).deleteById(anyLong());
    }

    @Test
    void findAll() {
        when(repository.findAll(any(Pageable.class))).thenReturn(Data.PAGE_ROOMS);
        Page<Room> roomsPage = service.findAll(mock(Pageable.class));
        assertThat(roomsPage.getContent()).hasSize(1);
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    void findById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.ROOM));
        Room room = service.findById(1L);
        assertEquals(RoomState.AVAILABLE, room.getRoomState());
        verify(repository).findById(anyLong());
    }

    @Test
    void save() {
        when(mapper.toRoom(any(RoomRegisterDTO.class))).thenReturn(Data.ROOM);
        when(repository.save(any(Room.class))).thenReturn(Data.ROOM);
        Room room = service.save(Data.ROOM_REGISTER_DTO);
        assertEquals(101L, room.getRoomNumber());
        verify(repository).save(any(Room.class));
        verify(mapper).toRoom(any(RoomRegisterDTO.class));
    }

    @Test
    void update() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.ROOM));
        when(repository.save(any(Room.class))).thenReturn(Data.ROOM);
        Room room = service.update(1L, Data.ROOM_REGISTER_DTO);
        assertEquals(101L, room.getRoomNumber());
        verify(repository).save(any(Room.class));
    }
}