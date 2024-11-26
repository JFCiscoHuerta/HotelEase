package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * Data Transfer Object (DTO) for registering a new room.
 * This class is used to capture the necessary details for registering a room in the system.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRegisterDTO {

    /**
     * The room number, must be a positive value.
     * This field is required for identifying the room.
     */
    @Min(value = 1)
    private Long roomNumber;

    /**
     * The price per night for the room.
     * This field must be zero or a positive value.
     */
    @PositiveOrZero
    private BigDecimal priceByNight;

    /**
     * The type of the room (e.g., single, double, suite).
     * This field cannot be blank.
     */
    @NotBlank
    private String roomType;

    /**
     * The current state of the room (e.g., available, occupied, maintenance).
     * This field cannot be blank.
     */
    @NotBlank
    private String roomState;

    /**
     * List of images associated with the room.
     * This field is optional and can contain multiple images.
     */
    private List<RoomImageRegisterDTO> roomImages;

    /**
     * List of IDs representing the features associated with the room.
     * This field is optional.
     */
    private List<Long> roomFeatureIds;
}
