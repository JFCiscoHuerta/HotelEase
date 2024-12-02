package org.gklyphon.room.service;

import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.model.entities.RoomFeature;

import java.util.List;

/**
 * Service interface for handling {@link RoomFeature} entities.
 * Extends the {@link IService} interface to provide basic CRUD operations,
 * and includes additional methods specific to {@link RoomFeature} entities.
 */
public interface IRoomFeatureService extends IService<RoomFeature> {

    /**
     * Saves a new {@link RoomFeature} entity.
     *
     * @param roomFeatureRegisterDTO the data transfer object containing the details of the room feature to be created
     * @return the created {@link RoomFeature} entity
     */
    RoomFeature save(RoomFeatureRegisterDTO roomFeatureRegisterDTO);

    /**
     * Updates an existing {@link RoomFeature} entity.
     *
     * @param id the ID of the {@link RoomFeature} entity to be updated
     * @param roomFeatureRegisterDTO the data transfer object containing the updated details of the room feature
     * @return the updated {@link RoomFeature} entity
     */
    RoomFeature update(Long id, RoomFeatureRegisterDTO roomFeatureRegisterDTO);

    /**
     * Retrieves a list of all entities of type {@code RoomFeature}
     *
     * @return a {@link List} of entities of type {@code RoomFeature}
     */
    List<RoomFeature> findAll();
}
