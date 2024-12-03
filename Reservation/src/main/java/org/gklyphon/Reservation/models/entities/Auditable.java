package org.gklyphon.Reservation.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Base class to automatically handle auditing fields for entities.
 * Provides creation and update timestamps.
 * <p>
 * This class uses JPA lifecycle hooks to populate audit information before persisting and updating entities.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 2-Dec-2024
 */
@Getter
@Setter
@MappedSuperclass
public class Auditable {

    /**
     * The timestamp when the entity was created.
     * This value is set once at the time of creation and is not updated.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * The timestamp when the entity was last updated.
     * This value is set initially during creation and updated on each modification.
     */
    private LocalDateTime updatedAt;

    /**
     * Sets the {@code createdAt} and {@code updatedAt} timestamps to the current time
     * before the entity is persisted for the first time.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the {@code updatedAt} timestamp to the current time
     * before the entity is updated.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
