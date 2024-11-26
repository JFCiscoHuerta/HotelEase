package org.gklyphon.room.model.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Base class for auditing entities. It automatically manages creation and update timestamps.
 *
 * <p>Classes extending this superclass will inherit auditing fields for tracking entity creation
 * and modification times.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@Getter
@Setter
@MappedSuperclass
public class Auditable {

    /**
     * The date and time when the entity was created.
     * This field is set only once during the entity's creation.
     */
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * The date and time when the entity was last updated.
     * This field is updated every time the entity is modified.
     */
    private LocalDateTime updatedAt;

    /**
     * Initializes the creation and update timestamps before persisting a new entity.
     * This method is automatically called by the JPA provider.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the modification timestamp before updating an existing entity.
     * This method is automatically called by the JPA provider.
     */
    @PreUpdate
    protected  void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
