package org.gklyphon.room.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gklyphon.room.exception.custom.ElementNotFoundException;
import org.gklyphon.room.mapper.IRoomMapper;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.RoomFeature;
import org.gklyphon.room.model.entities.RoomImage;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.gklyphon.room.repository.IRoomFeatureRepository;
import org.gklyphon.room.repository.IRoomRepository;
import org.gklyphon.room.service.IRoomService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final IRoomRepository repository;
    private final IRoomFeatureRepository featureRepository;
    private final IRoomMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByPriceByNightBetween(BigDecimal min, BigDecimal max, Pageable pageable) {
        return repository.findByPriceByNightBetween(min, max, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByPriceByNightGreaterThan(BigDecimal priceByNight, Pageable pageable) {
        return repository.findByPriceByNightGreaterThan(priceByNight, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByPriceByNightLessThan(BigDecimal priceByNight, Pageable pageable) {
        return repository.findByPriceByNightLessThan(priceByNight, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByRoomState(RoomState roomState, Pageable pageable) {
        return repository.findByRoomState(roomState, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByRoomType(RoomType roomType, Pageable pageable) {
        return repository.findByRoomType(roomType, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByRoomTypeAndRoomState(RoomType roomType, RoomState roomState, Pageable pageable) {
        return repository.findByRoomTypeAndRoomState(roomType, roomState, pageable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            if (findById(id) != null) {
                repository.deleteById(id);
            }
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while deleting the room", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Room> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Room findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Room with id: " + id + " not found."));
    }

    @Override
    @Transactional
    public Room save(RoomRegisterDTO roomRegisterDTO) {
        try {
            Room room = mapper.toRoom(roomRegisterDTO);

            handleRoomFeatures(roomRegisterDTO, room);
            List<RoomImage> roomImages = handleRoomImages(roomRegisterDTO, room);

            room.setRoomImages(roomImages);
            return repository.save(room);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while saving room", e);
        }
    }

    @Override
    @Transactional
    public Room update(Long id, RoomRegisterDTO roomRegisterDTO) {
        try {
            Room originalRoom = findById(id);
            Room room = mapper.toRoom(roomRegisterDTO);
            BeanUtils.copyProperties(room, originalRoom,"id", "roomFeatures", "roomImages");

            handleRoomFeatures(roomRegisterDTO, originalRoom);
            List<RoomImage> roomImages = handleRoomImages(roomRegisterDTO, originalRoom);

            originalRoom.setRoomImages(new ArrayList<>(originalRoom.getRoomImages()));
            originalRoom.getRoomImages().clear();
            originalRoom.getRoomImages().addAll(roomImages);

            return repository.save(originalRoom);
        } catch (ElementNotFoundException e) {
            throw e;
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Error updating room. Possible data integrity issue", e);
        } catch (Exception e) {
            throw new ServiceException("Unexpected error while updating room", e);
        }
    }

    private List<RoomImage> handleRoomImages(RoomRegisterDTO roomRegisterDTO, Room room) {
        List<RoomImage> roomImages = mapper.toRoomImages(roomRegisterDTO.getRoomImages());
        for (RoomImage roomImage : roomImages) {
            roomImage.setRoom(room);
        }
        return roomImages;
    }

    private void handleRoomFeatures(RoomRegisterDTO roomRegisterDTO, Room room) {
        List<Long> roomFeaturesIds = roomRegisterDTO.getRoomFeatureIds();
        List<RoomFeature> existingRoomFeatures = featureRepository.findAllById(roomFeaturesIds);
        if (roomFeaturesIds.size() > existingRoomFeatures.size()) {
            throw new ElementNotFoundException("One or more Room Features not found");
        }
        room.setRoomFeatures(existingRoomFeatures);
    }
}