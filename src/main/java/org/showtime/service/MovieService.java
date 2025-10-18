package org.showtime.service;

import lombok.RequiredArgsConstructor;
import org.showtime.domain.Movie;
import org.showtime.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public List<Movie> searchMoviesByTitle(String keyword) {
        return movieRepository.searchByTitleContainingIgnoreCase(keyword);
    }
}
