package org.showtime.service;

import lombok.RequiredArgsConstructor;
import org.showtime.domain.Show;
import org.showtime.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;

    public Show saveShow(Show show) {
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Optional<Show> getShowById(Long id) {
        return showRepository.findById(id);
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
}
