package org.gklyphon.room.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.gklyphon.room.Data;
import org.gklyphon.room.model.dtos.RoomRegisterDTO;
import org.gklyphon.room.model.entities.Room;
import org.gklyphon.room.model.entities.enums.RoomState;
import org.gklyphon.room.model.entities.enums.RoomType;
import org.gklyphon.room.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

    @MockBean
    RoomServiceImpl service;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;
    final String API_URL = "/rooms";

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllRoomsTest() throws Exception {
        when(service.findAll(any(Pageable.class))).thenReturn(Data.PAGE_ROOMS);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL)
                        .param("page","0")
                        .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomList[0].roomNumber").value(101L));
        verify(service).findAll(any(Pageable.class));
    }

    @Test
    void getByIdTest() throws Exception {
        when(service.findById(anyLong())).thenReturn(Data.ROOM);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomNumber").value(101L));
        verify(service).findById(anyLong());
    }

    @Test
    void createRoom() throws Exception {
        when(service.save(any(RoomRegisterDTO.class))).thenReturn(Data.ROOM);
        mockMvc.perform(
                MockMvcRequestBuilders.post(API_URL + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Data.ROOM_REGISTER_DTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomNumber").value(101L));
        verify(service).save(any(RoomRegisterDTO.class));
    }

    @Test
    void updateRoom() throws Exception {
        when(service.update(anyLong(), any(RoomRegisterDTO.class))).thenReturn(Data.ROOM);
        mockMvc.perform(
                MockMvcRequestBuilders.put(API_URL + "/update/1")
                        .content(objectMapper.writeValueAsString(Data.ROOM_REGISTER_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomNumber").value(101L));
        verify(service).update(anyLong(), any(RoomRegisterDTO.class));
    }

    @Test
    void deleteRoomTest() throws Exception {
        doNothing().when(service).delete(anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL + "/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service).delete(anyLong());
    }

    @Test
    void getByPriceByNightBetweenTest() throws Exception {
        when(service.findByPriceByNightBetween(any(BigDecimal.class), any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/price-by-night-between")
                                .param("min", "100")
                                .param("max", "1250")
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomList[0].id").value(1L));
        verify(service).findByPriceByNightBetween(any(BigDecimal.class), any(BigDecimal.class), any(Pageable.class));
    }

    @Test
    void getByPriceByNightGreaterThanTest() throws Exception {
        when(service.findByPriceByNightGreaterThan(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL + "/price-by-night-grater-than")
                        .param("price-by-night", "100")
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomList[0].id").value(1L));
        verify(service).findByPriceByNightGreaterThan(any(BigDecimal.class), any(Pageable.class));
    }

    @Test
    void getByPriceByNightLessThanTest() throws Exception {
        when(service.findByPriceByNightLessThan(any(BigDecimal.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/price-by-night-less-than")
                                .param("price-by-night", "100")
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomList[0].id").value(1L));
        verify(service).findByPriceByNightLessThan(any(BigDecimal.class), any(Pageable.class));
    }

    @Test
    void getByRoomStateTest() throws Exception {
        when(service.findByRoomState(any(RoomState.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-room-state")
                                .param("room-state", "AVAILABLE")
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomList[0].id").value(1L));
        verify(service).findByRoomState(any(RoomState.class), any(Pageable.class));
    }

    @Test
    void getByRoomTypeTest() throws Exception {
        when(service.findByRoomType(any(RoomType.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-room-type")
                                .param("room-type", "SUITE")
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomList[0].id").value(1L));
        verify(service).findByRoomType(any(RoomType.class), any(Pageable.class));
    }

    @Test
    void getByRoomTypeAndRoomStateTest() throws Exception {
        when(service.findByRoomTypeAndRoomState(any(RoomType.class), any(RoomState.class), any(Pageable.class)))
                .thenReturn(Data.PAGE_ROOMS);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-room-type-and-room-state")
                                .param("room-type", "SUITE")
                                .param("room-state", "AVAILABLE")
                                .param("page", "0")
                                .param("size", "10")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomList[0].id").value(1L));
        verify(service).findByRoomTypeAndRoomState(any(RoomType.class), any(RoomState.class), any(Pageable.class));
    }
}