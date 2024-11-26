package org.gklyphon.room.mapper;

import org.gklyphon.room.Data;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link IRoomMapper} interface implementation. This class uses Mockito to mock the
 * {@link IRoomMapper} and tests the methods that convert between {@link Room} and {@link RoomRegisterDTO}.
 * The tests ensure that the mapping methods correctly convert the objects and handle expected data properly.
 *
 * @author Javier Gonzalez
 * @version 1.0
 * @since 25-Nov-2024
 */
@ExtendWith(MockitoExtension.class)
class IRoomMapperTest {

    @Mock
    IRoomMapper mapper;

    /**
     * Tests the {@link IRoomMapper#toRoomRegisterDTO(Room)} method. This test ensures that the method correctly
     * converts a {@link Room} object into a {@link RoomRegisterDTO}.
     *
     * <p>The method checks that the room number, room type, and room state are mapped correctly from the
     * {@link Room} object to the {@link RoomRegisterDTO} object.</p>
     *
     * @see IRoomMapper#toRoomRegisterDTO(Room)
     */
    @Test
    void toRoomRegisterDTO() {
        when(mapper.toRoomRegisterDTO(any(Room.class))).thenReturn(Data.ROOM_REGISTER_DTO);
        RoomRegisterDTO roomRegisterDTO = mapper.toRoomRegisterDTO(Data.ROOM);
        assertEquals(101L, roomRegisterDTO.getRoomNumber());
        assertEquals("SUITE", roomRegisterDTO.getRoomType());
        assertEquals("AVAILABLE", roomRegisterDTO.getRoomState());
        verify(mapper).toRoomRegisterDTO(any(Room.class));
    }

    /**
     * Tests the {@link IRoomMapper#toRoom(RoomRegisterDTO)} method. This test ensures that the method correctly
     * converts a {@link RoomRegisterDTO} object into a {@link Room}.
     *
     * <p>The method verifies that the {@link RoomState}, room number, and room type are correctly mapped
     * from the {@link RoomRegisterDTO} to the {@link Room} object.</p>
     *
     * @see IRoomMapper#toRoom(RoomRegisterDTO)
     */
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