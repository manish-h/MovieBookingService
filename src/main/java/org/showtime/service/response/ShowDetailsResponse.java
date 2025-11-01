package org.showtime.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.showtime.domain.Movie;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowDetailsResponse {
    private Long showId;
    private Movie movie;
    private LocalDateTime showTime;
    private List<SeatAvailabilityResponse> seats;
}