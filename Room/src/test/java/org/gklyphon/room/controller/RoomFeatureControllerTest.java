package org.gklyphon.room.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.gklyphon.room.Data;
import org.gklyphon.room.exception.custom.ElementNotFoundException;
import org.gklyphon.room.model.dtos.RoomFeatureRegisterDTO;
import org.gklyphon.room.service.IRoomFeatureService;
import org.hibernate.service.spi.ServiceException;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomFeatureControllerTest {

    @MockBean
    IRoomFeatureService service;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;
    final String API_PATH = "/rooms/features";

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void getAllRoomFeatures() throws Exception {
        when(service.findAll(any(Pageable.class))).thenReturn(Data.ROOM_FEATURES_PAGE);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_PATH)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.roomFeatureList[0].id").value(1L));
        verify(service).findAll(any(Pageable.class));
    }

    @Test
    void getById() throws Exception {
        when(service.findById(anyLong())).thenReturn(Data.ROOM_FEATURE);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_PATH + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).findById(anyLong());
    }

    @Test
    void createRoomFeature() throws Exception {
        when(service.save(any(RoomFeatureRegisterDTO.class))).thenReturn(Data.ROOM_FEATURE);

        mockMvc.perform(
                MockMvcRequestBuilders.post(API_PATH + "/create")
                        .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Data.ROOM_FEATURE_REGISTER_DTO))
                        .with(csrf()))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.serviceName").value("Wifi"));
        verify(service).save(any(RoomFeatureRegisterDTO.class));
    }

    @Test
    void createRoomFeature_shouldReturnBadRequestStatus() throws Exception {
        RoomFeatureRegisterDTO invalid = new RoomFeatureRegisterDTO();
        mockMvc.perform(
                        MockMvcRequestBuilders.post(API_PATH + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalid))
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createRoomFeature_shouldReturnInternalServerErrorStatus() throws Exception {
        doThrow(ServiceException.class).when(service).save(any(RoomFeatureRegisterDTO.class));
        mockMvc.perform(
                        MockMvcRequestBuilders.post(API_PATH + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(Data.ROOM_FEATURE_REGISTER_DTO))
                                .with(csrf()))
                .andExpect(status().isInternalServerError());
        verify(service).save(any(RoomFeatureRegisterDTO.class));
    }

    @Test
    void updateRoomFeature() throws Exception {
        when(service.update(anyLong(), any(RoomFeatureRegisterDTO.class))).thenReturn(Data.ROOM_FEATURE);

        mockMvc.perform(
                MockMvcRequestBuilders.put(API_PATH + "/update/1")
                        .content(objectMapper.writeValueAsString(Data.ROOM_FEATURE_REGISTER_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(service).update(anyLong(), any(RoomFeatureRegisterDTO.class));
    }

    @Test
    void updateRoomFeature_shouldReturnNotFoundStatus() throws Exception {
        doThrow(ElementNotFoundException.class).when(service).update(anyLong(), any(RoomFeatureRegisterDTO.class));
        mockMvc.perform(
                        MockMvcRequestBuilders.put(API_PATH + "/update/1")
                                .content(objectMapper.writeValueAsString(Data.ROOM_FEATURE_REGISTER_DTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isNotFound());
        verify(service).update(anyLong(), any(RoomFeatureRegisterDTO.class));
    }

    @Test
    void updateRoomFeature_shouldReturnBadRequestStatus() throws Exception {
        RoomFeatureRegisterDTO invalid = new RoomFeatureRegisterDTO();
        mockMvc.perform(
                        MockMvcRequestBuilders.put(API_PATH + "/update/1")
                                .content(objectMapper.writeValueAsString(invalid))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateRoomFeature_shouldReturnInternalServerErrorStatus() throws Exception {
        doThrow(ServiceException.class).when(service).update(anyLong(), any(RoomFeatureRegisterDTO.class));
        mockMvc.perform(
                        MockMvcRequestBuilders.put(API_PATH + "/update/1")
                                .content(objectMapper.writeValueAsString(Data.ROOM_FEATURE_REGISTER_DTO))
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf()))
                .andExpect(status().isInternalServerError());
        verify(service).update(anyLong(), any(RoomFeatureRegisterDTO.class));
    }

    @Test
    void deleteRoomFeature() throws Exception {
        doNothing().when(service).delete(anyLong());
        mockMvc.perform(
                MockMvcRequestBuilders.delete(API_PATH + "/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isNoContent());
        verify(service).delete(anyLong());
    }

}