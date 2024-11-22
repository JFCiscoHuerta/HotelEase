package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomRegisterDTO {

    @Min(value = 1)
    private Long roomNumber;

    @PositiveOrZero
    private BigDecimal priceByNight;

    @NotBlank
    private String roomType;

    @NotBlank
    private String roomState;

    private List<RoomImageRegisterDTO> roomImages;

    private List<Long> roomFeatureIds;
}
