package org.gklyphon.room.repository;

import org.gklyphon.room.model.entities.RoomFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomFeatureRepository extends JpaRepository<RoomFeature, Long> {
}
