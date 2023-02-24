package com.example.ticket_please;

import java.util.Optional;

import com.example.ticket_please.Entity.Movie;
import com.example.ticket_please.repo.MovieRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieEntityTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @Order(1)
    public void testCreateMovie() {
        Movie movie = Movie.builder()
                .title("Avengers: Endgame")
                .description("An epic conclusion to the Marvel Cinematic Universe")
                .length(180)
                .minAge(12)
                .imageUrl("https://example.com/endgame.jpg")
                .build();

        Movie savedMovie = movieRepository.save(movie);
        assertNotNull(savedMovie.getId());
        assertEquals("Avengers: Endgame", savedMovie.getTitle());
    }
    @Test
    @Order(2)
    public void testReadMovie() {
        Movie movie = Movie.builder()
                .title("Avengers: Endgame")
                .description("An epic conclusion to the Marvel Cinematic Universe")
                .length(180)
                .minAge(12)
                .imageUrl("https://example.com/endgame.jpg")
                .build();

        Movie savedMovie = movieRepository.save(movie);

        Movie readMovie = movieRepository.findById(savedMovie.getId()).orElse(null);
        assertNotNull(readMovie);
        assertEquals("Avengers: Endgame", readMovie.getTitle());
    }

    @Test
    @Order(3)
    public void testUpdateMovie() {
        Movie movie = Movie.builder()
                .title("Avengers: Endgame")
                .description("An epic conclusion to the Marvel Cinematic Universe")
                .length(180)
                .minAge(12)
                .imageUrl("https://example.com/endgame.jpg")
                .build();

        Movie savedMovie = movieRepository.save(movie);

        savedMovie.setTitle("Avengers: Endgame - Part 2");
        movieRepository.save(savedMovie);

        Movie updatedMovie = movieRepository.findById(savedMovie.getId()).orElse(null);
        assertNotNull(updatedMovie);
        assertEquals("Avengers: Endgame - Part 2", updatedMovie.getTitle());
    }


    @Test
    @Order(4)
    public void testDeleteMovie() {
        Movie movie = Movie.builder()
                .title("Avengers: Endgame")
                .description("An epic conclusion to the Marvel Cinematic Universe")
                .length(180)
                .minAge(12)
                .imageUrl("https://example.com/endgame.jpg")
                .build();

        Movie savedMovie = movieRepository.save(movie);
        movieRepository.delete(savedMovie);

        Movie deletedMovie = movieRepository.findById(savedMovie.getId()).orElse(null);
        assertNull(deletedMovie);
    }

}
