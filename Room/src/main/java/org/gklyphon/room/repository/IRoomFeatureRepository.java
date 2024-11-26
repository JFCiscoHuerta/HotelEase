package org.gklyphon.room.repository;

import org.gklyphon.room.model.entities.RoomFeature;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for accessing and managing {@link RoomFeature} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations and
 * additional query methods for {@link RoomFeature} entities.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
public interface IRoomFeatureRepository extends JpaRepository<RoomFeature, Long> {
}
