package org.gklyphon.room.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * Represents an image associated with a room.
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
@Table(name = "images")
public class RoomImage extends Auditable {

    /**
     * The unique identifier for the image.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The path or URL of the image. Cannot be blank.
     */
    @NotBlank
    private String path;

    /**
     * The room to which the image is associated.
     * This field is ignored during JSON serialization.
     */
    @ManyToOne
    @JsonIgnore
    private Room room;
}
