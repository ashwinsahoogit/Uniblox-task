package com.uniblox_store.uniblox.assignment;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uniblox_store.uniblox.assignment.controller.UserController;
import com.uniblox_store.uniblox.assignment.dto.UserRequest;
import com.uniblox_store.uniblox.assignment.model.User;
import com.uniblox_store.uniblox.assignment.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private static final String TEST_USER_ID = "test-user-id";
    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_USER_TYPE = "user";
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(TEST_USER_ID);
        testUser.setName(TEST_USER_NAME);
        testUser.setType(TEST_USER_TYPE);
    }

    @Test
    void testRegisterUser_Success() throws Exception {
        UserRequest request = new UserRequest();
        request.setName(TEST_USER_NAME);
        request.setType(TEST_USER_TYPE);

        when(userService.registerUser(any(User.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_USER_ID))
                .andExpect(jsonPath("$.name").value(TEST_USER_NAME))
                .andExpect(jsonPath("$.type").value(TEST_USER_TYPE));
    }

    @Test
    void testRegisterUser_InvalidRequest() throws Exception {
        UserRequest request = new UserRequest();
        // Missing required fields

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetUser_Success() throws Exception {
        when(userService.getUser(TEST_USER_ID)).thenReturn(testUser);

        mockMvc.perform(get("/api/users/" + TEST_USER_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_USER_ID))
                .andExpect(jsonPath("$.name").value(TEST_USER_NAME))
                .andExpect(jsonPath("$.type").value(TEST_USER_TYPE));
    }

    @Test
    void testGetUser_NotFound() throws Exception {
        when(userService.getUser("non-existent")).thenReturn(null);

        mockMvc.perform(get("/api/users/non-existent"))
                .andExpect(status().isNotFound());
    }
} 