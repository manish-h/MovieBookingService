package org.showtime.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.showtime.domain.Movie;
import org.showtime.repository.MovieRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie;

    @BeforeEach
    void setUp() {
        movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");
    }

    @Test
    void whenSaveMovie_shouldReturnMovie() {
        when(movieRepository.save(any(Movie.class))).thenReturn(movie);

        Movie savedMovie = movieService.saveMovie(new Movie());

        assertThat(savedMovie.getTitle()).isEqualTo("Inception");
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void whenGetAllMovies_shouldReturnMovieList() {
        when(movieRepository.findAll()).thenReturn(List.of(movie));

        List<Movie> movies = movieService.getAllMovies();

        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getTitle()).isEqualTo("Inception");
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    void whenSearchMoviesByTitle_shouldReturnMatchingMovieList() {
        String keyword = "Incep";
        when(movieRepository.searchByTitleContainingIgnoreCase(keyword)).thenReturn(List.of(movie));

        List<Movie> movies = movieService.searchMoviesByTitle(keyword);

        assertThat(movies).hasSize(1);
        assertThat(movies.get(0).getTitle()).isEqualTo("Inception");
        verify(movieRepository, times(1)).searchByTitleContainingIgnoreCase(keyword);
    }
}
