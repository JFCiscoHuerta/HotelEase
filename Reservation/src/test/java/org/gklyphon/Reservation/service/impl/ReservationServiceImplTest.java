package org.gklyphon.Reservation.service.impl;

import org.gklyphon.Reservation.Data;
import org.gklyphon.Reservation.mapper.IReservationMapper;
import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.models.entities.Reservation;
import org.gklyphon.Reservation.repository.IReservationRepository;
import org.hibernate.service.spi.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    IReservationRepository repository;

    @Mock
    IReservationMapper mapper;

    @InjectMocks
    ReservationServiceImpl service;

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(anyLong());
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.RESERVATION));
        assertDoesNotThrow(()-> {
            service.deleteById(1L);
        });
        verify(repository).deleteById(anyLong());
        verify(repository).findById(anyLong());
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(Data.RESERVATIONS);
        List<Reservation> reservations = service.findAll();
        assertNotNull(reservations);
        assertEquals(1L, reservations.getFirst().getId());
        assertEquals(2, reservations.getFirst().getRoomReservations().size());
        verify(repository).findAll();
    }

    @Test
    void findById() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.RESERVATION));
        Reservation reservation = service.findById(1L);
        assertEquals(1L, reservation.getId());
        assertEquals(1L, reservation.getRoomReservations().getFirst().getId());
        verify(repository).findById(anyLong());
    }

    @Test
    void save() {
        when(repository.save(any(Reservation.class))).thenReturn(Data.RESERVATION);
        when(mapper.toReservation(any(ReservationDTO.class))).thenReturn(Data.RESERVATION);
        Reservation reservation = service.save(Data.RESERVATION_DTO);
        assertEquals(LocalDate.of(2024,12,3), reservation.getStartDate());
        verify(repository).save(any(Reservation.class));
    }

    @Test
    void save_shouldThrowServiceException_whenFails() {
        doThrow(ServiceException.class).when(repository).save(any(Reservation.class));
        when(mapper.toReservation(any(ReservationDTO.class))).thenReturn(Data.RESERVATION);
        assertThrows(ServiceException.class, ()-> service.save(Data.RESERVATION_DTO));
        verify(repository).save(any(Reservation.class));
    }

    @Test
    void update() {
        when(repository.save(any(Reservation.class))).thenReturn(Data.RESERVATION);
        when(mapper.toReservation(any(ReservationDTO.class))).thenReturn(Data.RESERVATION);
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.RESERVATION));
        Reservation reservation = service.update(1L, Data.RESERVATION_DTO);
        assertEquals(1L, reservation.getId());
        assertEquals(LocalDate.of(2024,12,3), reservation.getStartDate());
        verify(repository).save(any(Reservation.class));
        verify(repository).findById(anyLong());
    }

    @Test
    void update_shouldThrowServiceException_whenFails() {
        doThrow(ServiceException.class).when(repository).save(any(Reservation.class));
        when(mapper.toReservation(any(ReservationDTO.class))).thenReturn(Data.RESERVATION);
        when(repository.findById(anyLong())).thenReturn(Optional.of(Data.RESERVATION));
        assertThrows(ServiceException.class, ()-> service.update(1L, Data.RESERVATION_DTO));
        verify(repository).save(any(Reservation.class));
        verify(repository).findById(anyLong());
    }

    @Test
    void findByStartDate() {
        when(repository.findByStartDate(any(LocalDate.class), any(Pageable.class))).thenReturn(Data.RESERVATION_PAGE);
        Page<Reservation> reservationPage = service.findByStartDate(LocalDate.of(2024, 12, 3), mock(Pageable.class));
        assertThat(reservationPage.getContent()).hasSize(1);
        assertThat(reservationPage.getContent().getFirst().getStartDate()).isEqualTo(LocalDate.of(2024, 12, 3));
        verify(repository).findByStartDate(any(LocalDate.class), any(Pageable.class));
    }

    @Test
    void findByEndDate() {
        when(repository.findByEndDate(any(LocalDate.class), any(Pageable.class))).thenReturn(Data.RESERVATION_PAGE);
        Page<Reservation> reservationPage = service.findByEndDate(LocalDate.of(2024, 12, 18), mock(Pageable.class));
        assertThat(reservationPage.getContent()).hasSize(1);
        assertThat(reservationPage.getContent().getFirst().getEndDate()).isEqualTo(LocalDate.of(2024, 12, 18));
        verify(repository).findByEndDate(any(LocalDate.class), any(Pageable.class));
    }

    @Test
    void findByUserId() {
        when(repository.findByUserId(anyLong(), any(Pageable.class))).thenReturn(Data.RESERVATION_PAGE);
        Page<Reservation> reservationPage = service.findByUserId(1L, mock(Pageable.class));
        assertThat(reservationPage.getContent()).hasSize(1);
        assertThat(reservationPage.getContent().getFirst().getUserId()).isEqualTo(3L);
        verify(repository).findByUserId(anyLong(), any(Pageable.class));
    }
}