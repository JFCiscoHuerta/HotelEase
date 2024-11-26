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

/**
 * Unit tests for the {@link RoomFeatureController}. This class tests the endpoints for the management of
 * room features, including retrieving, creating, updating, and deleting room features.
 * It uses {@link MockMvc} to simulate HTTP requests and mock service layer interactions with {@link IRoomFeatureService}.
 *
 * @author Javier Gonzalez
 * @version 1.0
 * @since 25-Nov-2024
 */
@SpringBootTest
@AutoConfigureMockMvc
class RoomFeatureControllerTest {

    @MockBean
    IRoomFeatureService service;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;
    final String API_PATH = "/rooms/features";

    /**
     * Sets up the ObjectMapper with a JavaTimeModule before each test.
     * This module is used to handle serialization and deserialization of Java 8 Date and Time API objects.
     */
    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Tests the {@link RoomFeatureController#getAllRoomFeatures} method.
     * Verifies that the controller correctly returns a paginated list of room features.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the {@link RoomFeatureController#getById(Long)} method.
     * Verifies that the controller correctly returns a room feature by ID.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the {@link RoomFeatureController#createRoomFeature(RoomFeatureRegisterDTO)} method.
     * Verifies that the controller successfully creates a room feature and returns a CREATED status.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the scenario where creating a room feature returns a BAD_REQUEST status.
     * Verifies that the controller handles invalid input correctly.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the scenario where creating a room feature throws an Internal Server Error (500).
     * Verifies that the controller returns the correct error status in case of a service exception.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the {@link RoomFeatureController#updateRoomFeature(Long, RoomFeatureRegisterDTO)} method.
     * Verifies that the controller successfully updates a room feature and returns an OK status.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the scenario where updating a room feature throws a {@link ElementNotFoundException}.
     * Verifies that the controller returns a NOT_FOUND status in case the room feature is not found.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the scenario where updating a room feature returns a BAD_REQUEST status.
     * Verifies that the controller correctly handles invalid input during the update process.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the scenario where updating a room feature throws an Internal Server Error (500).
     * Verifies that the controller returns the correct error status in case of a service exception during the update process.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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

    /**
     * Tests the {@link RoomFeatureController#deleteRoomFeature(Long)} method.
     * Verifies that the controller successfully deletes a room feature and returns a NO_CONTENT status.
     *
     * @throws Exception If an error occurs during the mock request.
     */
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