package com.example.ticket_please;
import com.example.ticket_please.Entity.User;
import com.example.ticket_please.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    @Order(1)
    public void saveUserTest() {
        User user = User.builder()
                .username("test_user")
                .email("test_user@example.com")
                .phone("1234567890")
                .name("Test")
                .surname("User")
                .role("ROLE_USER")
                .password("password")
                .isEnabled(true)
                .build();

        User savedUser = userRepository.save(user);

        assertNotNull(savedUser.getId());
        assertEquals("test_user", savedUser.getUsername());
        assertEquals("test_user@example.com", savedUser.getEmail());
        assertEquals("1234567890", savedUser.getPhone());
        assertEquals("Test", savedUser.getName());
        assertEquals("User", savedUser.getSurname());
        assertEquals("ROLE_USER", savedUser.getRole());
        assertTrue(savedUser.isEnabled());
    }

    @Test
    @Order(2)
    public void testFindUserById() {
        User user = User.builder()
                .username("testuser2")
                .password("password")
                .role("ROLE_USER")
                .email("testuser2@example.com")
                .phone("1234567890")
                .name("Test")
                .surname("User2")
                .isEnabled(true)
                .build();

        User savedUser = userRepository.save(user);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals("testuser2", foundUser.getUsername());
    }

    @Test
    @Order(3)
    public void testUpdateUser() {
        User user = User.builder()
                .username("testuser3")
                .password("password")
                .role("ROLE_USER")
                .email("testuser3@example.com")
                .phone("1234567890")
                .name("Test")
                .surname("User3")
                .isEnabled(true)
                .build();

        User savedUser = userRepository.save(user);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);
        foundUser.setUsername("testuser3-updated");
        User updatedUser = userRepository.save(foundUser);
        assertNotNull(updatedUser);
        assertEquals("testuser3-updated", updatedUser.getUsername());
    }
    @Test
    @Order(4)
    public void testDeleteUser() {
        User user = User.builder()
                .username("testuser4")
                .password("password")
                .email("testuser4@example.com")
                .phone("1234567890")
                .name("Test")
                .surname("User")
                .role("USER")
                .isEnabled(true)
                .build();
        User savedUser = userRepository.save(user);
        userRepository.delete(savedUser);
        User deletedUser = userRepository.findById(savedUser.getId()).orElse(null);
        assertNull(deletedUser);
    }

}
