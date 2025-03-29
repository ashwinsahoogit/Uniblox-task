package com.uniblox_store.uniblox.assignment;

import static org.junit.jupiter.api.Assertions.*;

import com.uniblox_store.uniblox.assignment.model.User;
import com.uniblox_store.uniblox.assignment.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    private static final String TEST_USER_ID = "testUser";
    private static final String TEST_USER_NAME = "Test User";
    private static final String TEST_USER_TYPE = "user";

    @BeforeEach
    void setUp() {
        // Reset any test data if needed
    }

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setName(TEST_USER_NAME);
        user.setType(TEST_USER_TYPE);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(TEST_USER_ID, registeredUser.getId());
        assertEquals(TEST_USER_NAME, registeredUser.getName());
        assertEquals(TEST_USER_TYPE, registeredUser.getType());
    }

    @Test
    void testRegisterUser_OverwriteExisting() {
        // First registration
        User originalUser = new User();
        originalUser.setId(TEST_USER_ID);
        originalUser.setName(TEST_USER_NAME);
        originalUser.setType(TEST_USER_TYPE);
        userService.registerUser(originalUser);

        // Update user
        User updatedUser = new User();
        updatedUser.setId(TEST_USER_ID);
        updatedUser.setName("Updated Name");
        updatedUser.setType("admin");
        User result = userService.registerUser(updatedUser);

        assertNotNull(result);
        assertEquals(TEST_USER_ID, result.getId());
        assertEquals("Updated Name", result.getName());
        assertEquals("admin", result.getType());
    }

    @Test
    void testGetUser_ExistingUser() {
        // Register a user first
        User user = new User();
        user.setId(TEST_USER_ID);
        user.setName(TEST_USER_NAME);
        user.setType(TEST_USER_TYPE);
        userService.registerUser(user);

        // Get the user
        User retrievedUser = userService.getUser(TEST_USER_ID);

        assertNotNull(retrievedUser);
        assertEquals(TEST_USER_ID, retrievedUser.getId());
        assertEquals(TEST_USER_NAME, retrievedUser.getName());
        assertEquals(TEST_USER_TYPE, retrievedUser.getType());
    }

    @Test
    void testGetUser_NonExistingUser() {
        User user = userService.getUser("non-existent-user");

        assertNull(user);
    }
} 