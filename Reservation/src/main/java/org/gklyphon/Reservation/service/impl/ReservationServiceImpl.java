package org.gklyphon.Reservation.service.impl;

import lombok.RequiredArgsConstructor;
import org.gklyphon.Reservation.mapper.IReservationMapper;
import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.gklyphon.Reservation.repository.IReservationRepository;
import org.gklyphon.Reservation.service.IReservationService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository repository;
    private final IReservationMapper mapper;

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            if (findById(id) != null) {
                repository.deleteById(id);
            }
        } catch (Exception e) {
            throw new ServiceException("");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Reservation findById(Long id) {
        return repository.findById(id)
                .orElseThrow();
    }

    @Override
    @Transactional
    public Reservation save(ReservationDTO reservationDTO) {
        try {
            return repository.save(mapper.toReservation(reservationDTO));
        } catch (Exception ex) {
            throw new ServiceException("");
        }
    }

    @Override
    @Transactional
    public Reservation update(Long id, ReservationDTO reservationDTO) {
        try {
            Reservation reservation = mapper.toReservation(reservationDTO);
            Reservation originalReservation = findById(id);
            BeanUtils.copyProperties(reservation, originalReservation,"id");
            return repository.save(originalReservation);
        } catch (Exception e) {
            throw new ServiceException("");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findByStartDate(LocalDate startDate, Pageable pageable) {
        return repository.findByStartDate(startDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findByEndDate(LocalDate endDate, Pageable pageable) {
        return repository.findByEndDate(endDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reservation> findByUserId(Long userId, Pageable pageable) {
        return repository.findByUserId(userId, pageable);
    }
}
