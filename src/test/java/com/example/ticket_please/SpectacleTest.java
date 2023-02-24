package com.example.ticket_please;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import com.example.ticket_please.Entity.Spectacle;
import com.example.ticket_please.repo.SpectacleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpectacleTest {
    @Autowired
    private SpectacleRepository spectacleRepository;

    @Test
    @Order(1)
    public void testCreateSpectacle() {
        Spectacle spectacle = Spectacle.builder()
                .title("The Lion King")
                .description("A musical about a lion cub's journey to become king")
                .length(120)
                .minAge(5)
                .imageUrl("https://example.com/lionking.jpg")
                .build();

        Spectacle savedSpectacle = spectacleRepository.save(spectacle);
        assertNotNull(savedSpectacle.getId());
        assertEquals("The Lion King", savedSpectacle.getTitle());
    }
    @Test
    @Order(2)
    public void testReadSpectacle() {
        Spectacle spectacle = Spectacle.builder()
                .title("The Lion King")
                .description("A musical about a lion cub's journey to become king")
                .length(120)
                .minAge(5)
                .imageUrl("https://example.com/lionking.jpg")
                .build();

        Spectacle savedSpectacle = spectacleRepository.save(spectacle);

        Spectacle readSpectacle = spectacleRepository.findById(savedSpectacle.getId()).orElse(null);
        assertNotNull(readSpectacle);
        assertEquals("The Lion King", readSpectacle.getTitle());
    }
    @Test
    @Order(3)
    public void testUpdateSpectacle() {
        Spectacle spectacle = Spectacle.builder()
                .title("The Lion King")
                .description("A musical about a lion cub's journey to become king")
                .length(120)
                .minAge(5)
                .imageUrl("https://example.com/lionking.jpg")
                .build();

        Spectacle savedSpectacle = spectacleRepository.save(spectacle);
        savedSpectacle.setTitle("The Lion King - Live Action");
        spectacleRepository.save(savedSpectacle);

        Spectacle updatedSpectacle = spectacleRepository.findById(savedSpectacle.getId()).orElse(null);
        assertNotNull(updatedSpectacle);
        assertEquals("The Lion King - Live Action", updatedSpectacle.getTitle());
    }
    @Test
    @Order(4)
    public void testDeleteSpectacle() {
        Spectacle spectacle = Spectacle.builder()
                .title("The Lion King")
                .description("A musical about a lion cub's journey to become king")
                .length(120)
                .minAge(5)
                .imageUrl("https://example.com/lionking.jpg")
                .build();

        Spectacle savedSpectacle = spectacleRepository.save(spectacle);
        spectacleRepository.delete(savedSpectacle);

        Spectacle deletedSpectacle = spectacleRepository.findById(savedSpectacle.getId()).orElse(null);
        assertNull(deletedSpectacle);
    }



}
