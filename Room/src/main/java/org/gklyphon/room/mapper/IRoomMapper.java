package org.gklyphon.room.mapper;

import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.model.dtos.RoomImageRegisterDTO;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.RoomFeature;
import org.gklyphon.room.model.entities.RoomImage;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRoomMapper {

    RoomRegisterDTO toRoomRegisterDTO(Room room);
    List<RoomRegisterDTO> toRoomRegisterDTOS(Iterable<Room> rooms);

    @InheritInverseConfiguration
    Room toRoom(RoomRegisterDTO roomRegisterDTO);
    List<Room> toRooms(Iterable<RoomRegisterDTO> roomRegisterDTOS);

    RoomImage toRoomImage(RoomImageRegisterDTO roomImageRegisterDTO);
    List<RoomImage> toRoomImages(Iterable<RoomImageRegisterDTO> roomImageRegisterDTOS);

    RoomFeature toRoomFeature(RoomFeatureRegisterDTO roomFeatureRegisterDTO);
    List<RoomFeature> toRoomFeatures(Iterable<RoomFeatureRegisterDTO> roomFeatureRegisterDTOS);
}
