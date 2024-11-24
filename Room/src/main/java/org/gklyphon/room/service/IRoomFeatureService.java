package org.gklyphon.room.service;

import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.model.entities.RoomFeature;

public interface IRoomFeatureService extends IService<RoomFeature> {
    RoomFeature save(RoomFeatureRegisterDTO roomFeatureRegisterDTO);
    RoomFeature update(Long id, RoomFeatureRegisterDTO roomFeatureRegisterDTO);
}
