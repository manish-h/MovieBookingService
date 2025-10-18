package org.showtime.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.showtime.domain.Movie;
import org.showtime.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Autowired
    private ObjectMapper objectMapper;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("The Dark Knight");
    }

    @Test
    void whenCreateMovie_shouldReturn200AndMovie() throws Exception {
        when(movieService.saveMovie(any(Movie.class))).thenReturn(movie);

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("The Dark Knight"));
    }

    @Test
    void whenGetAllMovies_shouldReturn200AndMovieList() throws Exception {
        when(movieService.getAllMovies()).thenReturn(List.of(movie));

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Dark Knight"));
    }

    @Test
    void whenSearchMovies_shouldReturn200AndMatchingMovieList() throws Exception {
        when(movieService.searchMoviesByTitle(anyString())).thenReturn(List.of(movie));

        mockMvc.perform(get("/movies/search").param("keyword", "Dark"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("The Dark Knight"));
    }
}
