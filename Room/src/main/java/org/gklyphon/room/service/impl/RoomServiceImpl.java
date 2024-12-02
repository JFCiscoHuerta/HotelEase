package org.gklyphon.room.service.impl;

import lombok.RequiredArgsConstructor;
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

/**
 * Implementation of the {@link IRoomService} interface for handling {@link Room} entities.
 * This service provides the business logic for managing rooms, including searching,
 * saving, updating, and deleting room records. It also handles room features and images.
 */
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final IRoomRepository repository;
    private final IRoomFeatureRepository featureRepository;
    private final IRoomMapper mapper;

    /**
     * Finds rooms with a price range between the specified minimum and maximum price per night.
     *
     * @param min the minimum price per night
     * @param max the maximum price per night
     * @param pageable pagination information
     * @return a paginated list of rooms within the specified price range
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByPriceByNightBetween(BigDecimal min, BigDecimal max, Pageable pageable) {
        return repository.findByPriceByNightBetween(min, max, pageable);
    }

    /**
     * Finds rooms with a price greater than the specified price per night.
     *
     * @param priceByNight the price per night
     * @param pageable pagination information
     * @return a paginated list of rooms with a price greater than the specified price
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByPriceByNightGreaterThan(BigDecimal priceByNight, Pageable pageable) {
        return repository.findByPriceByNightGreaterThan(priceByNight, pageable);
    }

    /**
     * Finds rooms with a price less than the specified price per night.
     *
     * @param priceByNight the price per night
     * @param pageable pagination information
     * @return a paginated list of rooms with a price less than the specified price
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByPriceByNightLessThan(BigDecimal priceByNight, Pageable pageable) {
        return repository.findByPriceByNightLessThan(priceByNight, pageable);
    }

    /**
     * Finds rooms based on the specified room state.
     *
     * @param roomState the state of the room
     * @param pageable pagination information
     * @return a paginated list of rooms with the specified room state
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByRoomState(RoomState roomState, Pageable pageable) {
        return repository.findByRoomState(roomState, pageable);
    }

    /**
     * Finds rooms based on the specified room type.
     *
     * @param roomType the type of the room
     * @param pageable pagination information
     * @return a paginated list of rooms with the specified room type
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByRoomType(RoomType roomType, Pageable pageable) {
        return repository.findByRoomType(roomType, pageable);
    }

    /**
     * Finds rooms based on both room type and room state.
     *
     * @param roomType the type of the room
     * @param roomState the state of the room
     * @param pageable pagination information
     * @return a paginated list of rooms with the specified room type and state
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Room> findByRoomTypeAndRoomState(RoomType roomType, RoomState roomState, Pageable pageable) {
        return repository.findByRoomTypeAndRoomState(roomType, roomState, pageable);
    }

    /**
     * Deletes a room by its ID.
     *
     * @param id the ID of the room to be deleted
     * @throws ServiceException if an unexpected error occurs during deletion
     */
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

    /**
     * Finds all rooms with pagination.
     *
     * @param pageable pagination information
     * @return a paginated list of all rooms
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Room> findAllPageable(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * Finds a room by its ID.
     *
     * @param id the ID of the room to be found
     * @return the room with the specified ID
     * @throws ElementNotFoundException if the room with the specified ID is not found
     */
    @Override
    @Transactional(readOnly = true)
    public Room findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Room with id: " + id + " not found."));
    }

    /**
     * Saves a new room based on the provided {@link RoomRegisterDTO}.
     *
     * @param roomRegisterDTO the data transfer object containing room details
     * @return the saved room entity
     * @throws ServiceException if an unexpected error occurs while saving the room
     */
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

    /**
     * Updates an existing room by its ID using the provided {@link RoomRegisterDTO}.
     *
     * @param id the ID of the room to be updated
     * @param roomRegisterDTO the data transfer object containing the updated room details
     * @return the updated room entity
     * @throws ServiceException if an unexpected error occurs while updating the room
     */
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

    /**
     * Handles the room images by mapping the provided DTO to {@link RoomImage} entities and associating them with the room.
     *
     * @param roomRegisterDTO the data transfer object containing the room images
     * @param room the room entity to associate the images with
     * @return a list of {@link RoomImage} entities
     */
    private List<RoomImage> handleRoomImages(RoomRegisterDTO roomRegisterDTO, Room room) {
        List<RoomImage> roomImages = mapper.toRoomImages(roomRegisterDTO.getRoomImages());
        for (RoomImage roomImage : roomImages) {
            roomImage.setRoom(room);
        }
        return roomImages;
    }

    /**
     * Handles the room features by validating the provided feature IDs and associating them with the room.
     *
     * @param roomRegisterDTO the data transfer object containing the room feature IDs
     * @param room the room entity to associate the features with
     * @throws ElementNotFoundException if one or more features are not found
     */
    private void handleRoomFeatures(RoomRegisterDTO roomRegisterDTO, Room room) {
        List<Long> roomFeaturesIds = roomRegisterDTO.getRoomFeatureIds();
        List<RoomFeature> existingRoomFeatures = featureRepository.findAllById(roomFeaturesIds);
        if (roomFeaturesIds.size() > existingRoomFeatures.size()) {
            throw new ElementNotFoundException("One or more Room Features not found");
        }
        room.setRoomFeatures(existingRoomFeatures);
    }
}