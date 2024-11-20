package org.gklyphon.room.mapper;

import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRoomMapper {

    RoomRegisterDTO toRoomRegisterDTO(Room room);

    @InheritInverseConfiguration
    Room toRoom(RoomRegisterDTO roomRegisterDTO);
}
