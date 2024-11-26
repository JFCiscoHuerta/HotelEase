package org.gklyphon.room.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in the system with various attributes such as room number, price,
 * type, and state. Each room can have multiple images and additional features.
 *
 * <p>This class extends {@link Auditable} to include audit fields for creation and update timestamps.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room extends Auditable {

    /**
     * The unique identifier for the room.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * The room number. Must be greater than or equal to 1.
     */
    @Min(value = 1)
    private Long roomNumber;

    /**
     * The price per night for the room. Must be positive or zero.
     */
    @PositiveOrZero
    private BigDecimal priceByNight;

    /**
     * The type of the room, such as SINGLE, DOUBLE, or SUITE.
     */
    @Enumerated(value = EnumType.STRING)
    private RoomType roomType;

    /**
     * The current state of the room, such as AVAILABLE or OCCUPIED.
     */
    @Enumerated(value = EnumType.STRING)
    private RoomState roomState;

    /**
     * A list of images associated with the room.
     */
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> roomImages;

    /**
     * A list of additional features or services available in the room.
     */
    @ManyToMany
    @JoinTable(
            name = "room_additional_features",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "room_feature_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"room_id", "room_feature_id"})
    )
    private List<RoomFeature> roomFeatures;

    /**
     * Default constructor initializing lists for room images and additional features.
     */
    public Room() {
        this.roomImages = new ArrayList<>();
        this.roomFeatures = new ArrayList<>();
    }

    /**
     * Adds an image to the room.
     *
     * @param roomImage The image to be added.
     */
    public void addRoomImage(RoomImage roomImage) {
        roomImages.add(roomImage);
    }

    /**
     * Removes an image from the room.
     *
     * @param roomImage The image to be removed.
     */
    public void removeRoomImage(RoomImage roomImage) {
        roomImages.remove(roomImage);
    }

    /**
     * Adds an additional feature or service to the room.
     *
     * @param roomFeature The feature to be added.
     */
    public void addAdditionalService(RoomFeature roomFeature) {
        roomFeatures.add(roomFeature);
    }

    /**
     * Removes an additional feature or service from the room.
     *
     * @param roomFeature The feature to be removed.
     */
    public void removeAdditionalService(RoomFeature roomFeature) {
        roomFeatures.remove(roomFeature);
    }

}
