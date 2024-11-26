package org.gklyphon.room.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents an additional feature or service available in a room.
 *
 * <p>This class extends {@link Auditable} to include audit fields for creation and update timestamps.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "room_features")
public class RoomFeature extends Auditable {

    /**
     * The unique identifier for the room feature.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the service or feature. Cannot be blank.
     */
    @NotBlank
    private String serviceName;

    /**
     * The price of the service or feature. Must be zero or a positive value.
     */
    @Min(value = 0)
    private BigDecimal servicePrice;
}
