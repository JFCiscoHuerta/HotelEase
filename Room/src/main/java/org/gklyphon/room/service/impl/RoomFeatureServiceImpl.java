package org.gklyphon.room.service.impl;

import lombok.RequiredArgsConstructor;
import org.gklyphon.room.exception.custom.ElementNotFoundException;
import org.gklyphon.room.mapper.IRoomMapper;
import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.model.entities.RoomFeature;
import org.gklyphon.room.repository.IRoomFeatureRepository;
import org.gklyphon.room.service.IRoomFeatureService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing {@link RoomFeature} entities.
 *
 * <p>This service provides methods to create, update, find, and delete room features,
 * as well as to retrieve all room features with pagination support.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@Service
@RequiredArgsConstructor
public class RoomFeatureServiceImpl implements IRoomFeatureService {

    private final IRoomFeatureRepository featureRepository;
    private final IRoomMapper mapper;

    /**
     * Finds a {@link RoomFeature} by its id.
     *
     * @param id the id of the room feature to find
     * @return the found room feature
     * @throws ElementNotFoundException if no room feature is found with the given id
     */
    @Override
    @Transactional(readOnly = true)
    public RoomFeature findById(Long id) {
        return featureRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Room Feature with id:" + id + " not found."));
    }

    /**
     * Finds all room features, with pagination support.
     *
     * @param pageable pagination information
     * @return a page of room features
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RoomFeature> findAllPageable(Pageable pageable) {
        return featureRepository.findAll(pageable);
    }

    /**
     * Deletes a room feature by its id.
     *
     * @param id the id of the room feature to delete
     * @throws ServiceException if an unexpected error occurs during deletion
     */
    @Override
    @Transactional
    public void delete(Long id) {
        try {
            if (findById(id) != null) {
                featureRepository.deleteById(id);
            }
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while deleting the room feature", e);
        }
    }

    /**
     * Saves a new {@link RoomFeature} based on the provided {@link RoomFeatureRegisterDTO}.
     *
     * @param roomFeatureRegisterDTO the DTO containing the data for the new room feature
     * @return the saved room feature
     * @throws ServiceException if an unexpected error occurs during saving
     */
    @Override
    @Transactional
    public RoomFeature save(RoomFeatureRegisterDTO roomFeatureRegisterDTO) {
        try {
            return featureRepository.save(mapper.toRoomFeature(roomFeatureRegisterDTO));
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while saving room feature");
        }
    }

    /**
     * Updates an existing room feature with new data.
     *
     * @param id the id of the room feature to update
     * @param roomFeatureRegisterDTO the DTO containing the updated data
     * @return the updated room feature
     * @throws ElementNotFoundException if no room feature is found with the given id
     * @throws ServiceException if an unexpected error occurs during the update
     */
    @Override
    @Transactional
    public RoomFeature update(Long id, RoomFeatureRegisterDTO roomFeatureRegisterDTO) {
        try {
            RoomFeature originalRoomFeature = findById(id);
            BeanUtils.copyProperties(roomFeatureRegisterDTO, originalRoomFeature);
            return featureRepository.save(originalRoomFeature);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while updating room feature");
        }
    }

    /**
     * Finds all room features.
     *
     * @return a list of room features
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomFeature> findAll() {
        return featureRepository.findAll();
    }
}
