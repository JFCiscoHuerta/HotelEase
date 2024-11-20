package org.gklyphon.room.service.impl;

import lombok.RequiredArgsConstructor;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.gklyphon.room.repository.IRoomRepository;
import org.gklyphon.room.service.IRoomService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements IRoomService {

    private final IRoomRepository repository;

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
    public Page<Room> findByPriceByNightLowerThan(BigDecimal priceByNight, Pageable pageable) {
        return repository.findByPriceByNightLowerThan(priceByNight, pageable);
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
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("");
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
                .orElseThrow();
    }

    @Override
    @Transactional
    public Room save(RoomRegisterDTO roomRegisterDTO) {
        return null;
    }

    @Override
    @Transactional
    public Room update(Long id, RoomRegisterDTO roomRegisterDTO) {
        return null;
    }
}