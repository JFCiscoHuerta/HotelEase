package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Data Transfer Object (DTO) for registering a new room image.
 * This class is used to capture the path of the image associated with the room.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomImageRegisterDTO {

    /**
     * The path where the image is stored.
     * This field cannot be blank.
     */
    @NotBlank
    private String path;
}
