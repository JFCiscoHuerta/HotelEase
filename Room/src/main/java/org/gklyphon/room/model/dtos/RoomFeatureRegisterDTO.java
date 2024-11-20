package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomFeatureRegisterDTO {

    @NotBlank
    private String serviceName;

    @Min(value = 0)
    private BigDecimal servicePrice;
}
