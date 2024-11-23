package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomImageRegisterDTO {

    @NotBlank
    private String path;
}
