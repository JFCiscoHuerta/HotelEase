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

@Builder
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "rooms")
public class Room extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 1)
    private Long roomNumber;

    @PositiveOrZero
    private BigDecimal priceByNight;

    @Enumerated(value = EnumType.STRING)
    private RoomType roomType;

    @Enumerated(value = EnumType.STRING)
    private RoomState roomState;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomImage> roomImages;

    @ManyToMany
    @JoinTable(
            name = "room_additional_features",
            joinColumns = @JoinColumn(name = "room_id"),
            inverseJoinColumns = @JoinColumn(name = "room_feature_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"room_id","room_feature_id"})
    )
    private List<RoomFeature> roomFeatures;

    public Room() {
        this.roomImages = new ArrayList<>();
        this.roomFeatures = new ArrayList<>();
    }

    public void addRoomImage(RoomImage roomImage) {
        roomImages.add(roomImage);
    }

    public void removeRoomImage(RoomImage roomImage) {
        roomImages.remove(roomImage);
    }

    public void addAdditionalService(RoomFeature roomFeature) {
        roomFeatures.add(roomFeature);
    }

    public void removeAdditionalService(RoomFeature roomFeature) {
        roomFeatures.remove(roomFeature);
    }


}
