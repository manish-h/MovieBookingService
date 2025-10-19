package org.showtime.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import org.showtime.converter.SeatListConverter;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"auditorium_id", "showTime"})
})
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime showTime;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "auditorium_id")
    private Auditorium auditorium;

    @Convert(converter = SeatListConverter.class)
    @Column(columnDefinition = "TEXT")
    private List<Seat> seats;
}
