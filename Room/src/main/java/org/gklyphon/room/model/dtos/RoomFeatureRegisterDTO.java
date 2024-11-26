package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for registering a new room feature.
 * This class is used to capture the details of a room feature such as its name and price.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomFeatureRegisterDTO {

    /**
     * The name of the service or feature associated with the room.
     * This field cannot be blank.
     */
    @NotBlank
    private String serviceName;

    /**
     * The price of the service or feature associated with the room.
     * The value must be greater than or equal to 0.
     */
    @Min(value = 0)
    private BigDecimal servicePrice;
}
