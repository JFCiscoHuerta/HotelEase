package org.gklyphon.room.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.gklyphon.room.Data;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.gklyphon.room.repository.IRoomRepository;
import org.gklyphon.room.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IRoomMapperTest {

    @Mock
    IRoomMapper mapper;

    @Test
    void toRoomRegisterDTO() {
        when(mapper.toRoomRegisterDTO(any(Room.class))).thenReturn(Data.ROOM_REGISTER_DTO);
        RoomRegisterDTO roomRegisterDTO = mapper.toRoomRegisterDTO(Data.ROOM);
        assertEquals(101L, roomRegisterDTO.getRoomNumber());
        assertEquals("SUITE", roomRegisterDTO.getRoomType());
        assertEquals("AVAILABLE", roomRegisterDTO.getRoomState());
        verify(mapper).toRoomRegisterDTO(any(Room.class));
    }

    @Test
    void toRoom() {
        when(mapper.toRoom(any(RoomRegisterDTO.class))).thenReturn(Data.ROOM);
        Room room = mapper.toRoom(Data.ROOM_REGISTER_DTO);
        assertEquals(RoomState.AVAILABLE, room.getRoomState());
        assertEquals(101L, room.getRoomNumber());
        assertEquals(RoomType.SUITE, room.getRoomType());
        verify(mapper).toRoom(any(RoomRegisterDTO.class));
    }
}