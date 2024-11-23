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

@Service
@RequiredArgsConstructor
public class RoomFeatureServiceImpl implements IRoomFeatureService {

    private final IRoomFeatureRepository featureRepository;
    private final IRoomMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public RoomFeature findById(Long id) {
        return featureRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Room Feature with id:" + id + " not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomFeature> findAll(Pageable pageable) {
        return featureRepository.findAll(pageable);
    }

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

    @Override
    @Transactional
    public RoomFeature save(RoomFeatureRegisterDTO roomFeatureRegisterDTO) {
        try {
            return featureRepository.save(mapper.toRoomFeature(roomFeatureRegisterDTO));
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while saving room feature");
        }
    }

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
}
