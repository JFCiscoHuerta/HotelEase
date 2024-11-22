package org.gklyphon.room.model.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomImageRegisterDTO {

    @NotBlank
    private String path;

    @NotNull
    private Long roomId;
}
