package org.showtime.repository;

import org.showtime.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    /**
     * Finds all movies where the title contains the given keyword, ignoring case.
     * This is a custom query using JPQL (Java Persistence Query Language).
     *
     * @param keyword The keyword to search for in the movie titles.
     * @return A list of matching movies.
     */
    @Query("SELECT m FROM Movie m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Movie> searchByTitleContainingIgnoreCase(@Param("keyword") String keyword);

}
