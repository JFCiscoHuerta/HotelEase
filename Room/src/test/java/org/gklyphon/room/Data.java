package org.gklyphon.room;

import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.model.dtos.RoomImageRegisterDTO;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.RoomFeature;
import org.gklyphon.room.model.entities.RoomImage;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class that provides predefined static data to be used in unit or integration tests.
 * Contains instances of `Room`, `RoomFeature`, `RoomImage` entities along with their corresponding DTOs.
 * This class is designed to simplify the creation of repetitive test data and facilitate the validation
 * of business logic related to these entities.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
public class Data {

    /**
     * Predefined instance of `Room` with test values.
     * Represents a room with characteristics like room number, price per night, room type,
     * room state, images, and room features.
     */
    public static final Room ROOM = Room.builder()
            .id(1L)
            .roomNumber(101L)
            .priceByNight(BigDecimal.valueOf(150.00))
            .roomType(RoomType.SUITE)
            .roomState(RoomState.AVAILABLE)
            .roomImages(
                    List.of(
                            RoomImage.builder()
                                    .id(1L)
                                    .path("/path")
                                    .room(new Room())
                                    .build()
                    )
            )
            .roomFeatures(
                    List.of(
                            RoomFeature.builder()
                                    .id(1L)
                                    .serviceName("TV")
                                    .servicePrice(new BigDecimal("5.00"))
                                    .build(),
                            RoomFeature.builder()
                                    .id(2L)
                                    .serviceName("XBOX")
                                    .servicePrice(new BigDecimal("3.00"))
                                    .build()
                    )
            )
            .build();

    /**
     * Predefined instance of `RoomRegisterDTO` with test values.
     * Used to simulate registering a new room with attributes like room number, price per night,
     * room type, room state, images, and associated room features.
     */
    public static final RoomRegisterDTO ROOM_REGISTER_DTO = RoomRegisterDTO.builder()
            .roomNumber(101L)
            .priceByNight(BigDecimal.valueOf(150.00))
            .roomType("SUITE")
            .roomState("AVAILABLE")
            .roomImages(
                    List.of(
                            RoomImageRegisterDTO.builder()
                                    .path("/path")
                                    .build()
                    )
            )
            .roomFeatureIds(List.of(1L))
            .build();

    /**
     * List of `Room` used in tests where multiple rooms are needed.
     */
    public static final List<Room> ROOMS = List.of(ROOM);

    /**
     * A page of `Room` (`Page<Room>`) used to simulate paginated room results in tests.
     */
    public static final Page<Room> PAGE_ROOMS = new PageImpl<Room>(ROOMS);

    /**
     * Predefined instance of `RoomFeature` with test values.
     * Represents a room feature with a service name and a price associated.
     */
    public static final RoomFeature ROOM_FEATURE = RoomFeature.builder()
                    .id(1L)
                    .serviceName("Wifi")
                    .servicePrice(new BigDecimal("12"))
                    .build();

    /**
     * List of `RoomFeature` used in tests that involve multiple room features.
     */
    public static final List<RoomFeature> ROOM_FEATURES = List.of(
            ROOM_FEATURE
    );

    /**
     * A page of room features (`Page<RoomFeature>`) used to simulate paginated feature results.
     */
    public static final Page<RoomFeature> ROOM_FEATURES_PAGE = new PageImpl<>(ROOM_FEATURES);

    /**
     * Predefined instance of `RoomFeatureRegisterDTO` with test values.
     * Used to simulate registering a new room feature with service name and price.
     */
    public static final RoomFeatureRegisterDTO ROOM_FEATURE_REGISTER_DTO = RoomFeatureRegisterDTO.builder()
            .serviceName("Wifi")
            .servicePrice(new BigDecimal("12"))
            .build();

    /**
     * List of `RoomFeatureRegisterDTO` used in tests that require multiple room feature registrations.
     */
    public static final List<RoomFeatureRegisterDTO> ROOM_FEATURES_DTO = List.of(
        ROOM_FEATURE_REGISTER_DTO
    );

    /**
     * Predefined list of `RoomImage` instances for test purposes.
     * Represents images associated with rooms.
     */
    public static final List<RoomImage> ROOM_IMAGES = List.of(
            RoomImage.builder()
                    .id(1L)
                    .room(new Room())
                    .path("")
                    .build()
    );

    /**
     * Predefined list of `RoomImageRegisterDTO` instances for test purposes.
     * Used for registering room images.
     */

    public static final List<RoomImageRegisterDTO> ROOM_IMAGES_REGISTER_DTO = List.of(
            RoomImageRegisterDTO.builder()
                    .path("")
                    .build()
    );
}
