package org.showtime.service;

import lombok.RequiredArgsConstructor;
import org.showtime.domain.Auditorium;
import org.showtime.repository.AuditoriumRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;

    public Auditorium saveAuditorium(Auditorium auditorium) {
        return auditoriumRepository.save(auditorium);
    }

    public List<Auditorium> getAllAuditoriums() {
        return auditoriumRepository.findAll();
    }

    public Optional<Auditorium> getAuditoriumById(Long id) {
        return auditoriumRepository.findById(id);
    }

    public void deleteAuditorium(Long id) {
        auditoriumRepository.deleteById(id);
    }
}
