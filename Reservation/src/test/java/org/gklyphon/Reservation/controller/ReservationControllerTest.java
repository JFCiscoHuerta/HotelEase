package org.gklyphon.Reservation.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.boot.test.mock.mockito.MockBean;
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

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    IReservationService service;

    ObjectMapper objectMapper;
    final String API_URL = "/reservation";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

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

    @Test
    void getById() throws Exception {
        when(service.findById(anyLong())).thenReturn(Data.RESERVATION);
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).findById(anyLong());
    }

    @Test
    void getByUserId() throws Exception {
        when(service.findByUserId(anyLong(), any(Pageable.class))).thenReturn(Data.RESERVATION_PAGE);
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/by-user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).findByUserId(anyLong(), any(Pageable.class));
    }

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