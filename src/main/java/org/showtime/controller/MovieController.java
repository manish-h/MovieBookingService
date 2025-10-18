package org.showtime.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.showtime.domain.Movie;
import org.showtime.service.MovieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        log.info("Creating movie: {}", movie.getTitle());
        return movieService.saveMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        log.info("Fetching all movies");
        return movieService.getAllMovies();
    }

    @GetMapping("/search")
    public List<Movie> searchMovies(@RequestParam("keyword") String keyword) {
        log.info("Searching for movies with keyword: {}", keyword);
        return movieService.searchMoviesByTitle(keyword);
    }
}
