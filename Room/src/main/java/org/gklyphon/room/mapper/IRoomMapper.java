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

/**
 * Mapper interface for converting between {@link Room}, {@link RoomRegisterDTO}, {@link RoomImage},
 * {@link RoomImageRegisterDTO}, {@link RoomFeature}, and {@link RoomFeatureRegisterDTO}.
 *
 * <p>This interface defines the mappings between entity objects and DTOs for {@link Room},
 * {@link RoomImage}, and {@link RoomFeature}. It uses MapStruct to automatically generate
 * the implementation of the methods.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@Mapper(componentModel = "spring")
public interface IRoomMapper {

    /**
     * Converts a {@link Room} entity to a {@link RoomRegisterDTO}.
     *
     * @param room the {@link Room} entity to convert
     * @return the converted {@link RoomRegisterDTO}
     */
    RoomRegisterDTO toRoomRegisterDTO(Room room);

    /**
     * Converts a list of {@link Room} entities to a list of {@link RoomRegisterDTO}s.
     *
     * @param rooms the list of {@link Room} entities to convert
     * @return the converted list of {@link RoomRegisterDTO}s
     */
    List<RoomRegisterDTO> toRoomRegisterDTOS(Iterable<Room> rooms);

    /**
     * Converts a {@link RoomRegisterDTO} to a {@link Room} entity.
     * The mapping configuration is inherited from {@link #toRoomRegisterDTO(Room)}.
     *
     * @param roomRegisterDTO the {@link RoomRegisterDTO} to convert
     * @return the converted {@link Room} entity
     */
    @InheritInverseConfiguration
    Room toRoom(RoomRegisterDTO roomRegisterDTO);

    /**
     * Converts a list of {@link RoomRegisterDTO}s to a list of {@link Room} entities.
     *
     * @param roomRegisterDTOS the list of {@link RoomRegisterDTO}s to convert
     * @return the converted list of {@link Room} entities
     */
    List<Room> toRooms(Iterable<RoomRegisterDTO> roomRegisterDTOS);

    /**
     * Converts a {@link RoomImageRegisterDTO} to a {@link RoomImage} entity.
     *
     * @param roomImageRegisterDTO the {@link RoomImageRegisterDTO} to convert
     * @return the converted {@link RoomImage} entity
     */
    RoomImage toRoomImage(RoomImageRegisterDTO roomImageRegisterDTO);

    /**
     * Converts a list of {@link RoomImageRegisterDTO}s to a list of {@link RoomImage} entities.
     *
     * @param roomImageRegisterDTOS the list of {@link RoomImageRegisterDTO}s to convert
     * @return the converted list of {@link RoomImage} entities
     */
    List<RoomImage> toRoomImages(Iterable<RoomImageRegisterDTO> roomImageRegisterDTOS);

    /**
     * Converts a {@link RoomFeatureRegisterDTO} to a {@link RoomFeature} entity.
     *
     * @param roomFeatureRegisterDTO the {@link RoomFeatureRegisterDTO} to convert
     * @return the converted {@link RoomFeature} entity
     */
    RoomFeature toRoomFeature(RoomFeatureRegisterDTO roomFeatureRegisterDTO);

    /**
     * Converts a list of {@link RoomFeatureRegisterDTO}s to a list of {@link RoomFeature} entities.
     *
     * @param roomFeatureRegisterDTOS the list of {@link RoomFeatureRegisterDTO}s to convert
     * @return the converted list of {@link RoomFeature} entities
     */
    List<RoomFeature> toRoomFeatures(Iterable<RoomFeatureRegisterDTO> roomFeatureRegisterDTOS);
}
