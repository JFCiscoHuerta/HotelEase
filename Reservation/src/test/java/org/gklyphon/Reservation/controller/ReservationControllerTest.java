package org.gklyphon.Reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.gklyphon.Reservation.Data;
import org.gklyphon.Reservation.models.dtos.ReservationDTO;
import org.gklyphon.Reservation.service.IReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the {@link ReservationController} class.
 * Uses MockMvc to simulate HTTP requests and validate responses.
 * Verifies the interaction between the controller and the service layer.
 *
 * Tests include standard CRUD operations and query filtering for reservations.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 25-Nov-2024
 */
@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    IReservationService service;

    ObjectMapper objectMapper;
    final String API_URL = "/reservation";

    /**
     * Initializes the ObjectMapper with support for Java 8 date and time API.
     */
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Tests retrieval of all reservations.
     * Validates that the service returns a list of reservations with HTTP status 200.
     */
    @Test
    void getAll() throws Exception {
        when(service.findAll()).thenReturn(Data.RESERVATIONS);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
        verify(service).findAll();
    }

    /**
     * Tests retrieval of a reservation by ID.
     * Ensures the response matches the expected reservation details.
     */
    @Test
    void getById() throws Exception {
        when(service.findById(anyLong())).thenReturn(Data.RESERVATION);
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).findById(anyLong());
    }

    /**
     * Tests retrieval of reservations filtered by user ID with pagination.
     */
    @Test
    void getByUserId() throws Exception {
        when(service.findByUserId(anyLong(), any(Pageable.class))).thenReturn(Data.RESERVATION_PAGE);
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/by-user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).findByUserId(anyLong(), any(Pageable.class));
    }

    /**
     * Tests retrieval of reservations filtered by start date with pagination.
     */
    @Test
    void getByStartDate() throws Exception {
        when(service.findByStartDate(any(LocalDate.class), any(Pageable.class))).thenReturn(Data.RESERVATION_PAGE);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL + "/by-start-date")
                        .param("start-date","2024-12-3")
                        .param("page","0")
                        .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservationList[0].id").value(1L));
        verify(service).findByStartDate(any(LocalDate.class), any(Pageable.class));
    }

    /**
     * Tests retrieval of reservations filtered by end date with pagination.
     */
    @Test
    void getByEndDate() throws Exception {
        when(service.findByEndDate(any(LocalDate.class), any(Pageable.class))).thenReturn(Data.RESERVATION_PAGE);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-end-date")
                                .param("end-date","2024-12-18")
                                .param("page","0")
                                .param("size","10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.reservationList[0].id").value(1L));
        verify(service).findByStartDate(any(LocalDate.class), any(Pageable.class));
    }

    /**
     * Tests creating a new reservation.
     * Validates that the reservation is created successfully and returns HTTP status 201.
     */
    @Test
    void saveReservation() throws Exception {
        when(service.save(any(ReservationDTO.class))).thenReturn(Data.RESERVATION);
        mockMvc.perform(
                MockMvcRequestBuilders.post(API_URL + "/save")
                        .content(objectMapper.writeValueAsString(Data.RESERVATION_PAGE))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).save(any(ReservationDTO.class));
    }

    /**
     * Tests updating an existing reservation.
     * Ensures that the update process works and returns the correct reservation.
     */
    @Test
    void updateReservation() throws Exception {
        when(service.update(anyLong(), any(ReservationDTO.class))).thenReturn(Data.RESERVATION);
        when(service.findById(anyLong())).thenReturn(Data.RESERVATION);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(API_URL + "/save")
                                .content(objectMapper.writeValueAsString(Data.RESERVATION_PAGE))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).update(anyLong(), any(ReservationDTO.class));
        verify(service).findById(anyLong());
    }

    /**
     * Tests deleting a reservation by ID.
     * Validates that the deletion returns HTTP status 204.
     */
    @Test
    void deleteReservation() throws Exception {
        doNothing().when(service).deleteById(anyLong());
        when(service.findById(anyLong())).thenReturn(Data.RESERVATION);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL + "/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service).deleteById(anyLong());
        verify(service).findById(anyLong());
    }
}