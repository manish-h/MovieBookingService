package org.showtime.controller;

import lombok.RequiredArgsConstructor;
import org.showtime.domain.Auditorium;
import org.showtime.service.AuditoriumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auditoriums")
@RequiredArgsConstructor
public class AuditoriumController {

    private final AuditoriumService auditoriumService;

    @PostMapping
    public Auditorium createAuditorium(@RequestBody Auditorium auditorium) {
        return auditoriumService.saveAuditorium(auditorium);
    }

    @GetMapping
    public List<Auditorium> getAllAuditoriums() {
        return auditoriumService.getAllAuditoriums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auditorium> getAuditoriumById(@PathVariable Long id) {
        return auditoriumService.getAuditoriumById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auditorium> updateAuditorium(@PathVariable Long id, @RequestBody Auditorium auditoriumDetails) {
        return auditoriumService.getAuditoriumById(id)
                .map(existingAuditorium -> {
                    existingAuditorium.setLocation(auditoriumDetails.getLocation());
                    return ResponseEntity.ok(auditoriumService.saveAuditorium(existingAuditorium));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuditorium(@PathVariable Long id) {
        if (auditoriumService.getAuditoriumById(id).isPresent()) {
            auditoriumService.deleteAuditorium(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
