package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 1)
    private List<RoomImageRegisterDTO> roomImages;
}
