package org.showtime.service;

import lombok.RequiredArgsConstructor;
import org.showtime.domain.Seat;
import org.showtime.domain.Show;
import org.showtime.repository.ShowRepository;
import org.showtime.repository.TicketRepository;
import org.showtime.service.response.SeatAvailabilityResponse;
import org.showtime.service.response.ShowDetailsResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;

    public Show saveShow(Show show) {
        return showRepository.save(show);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public Optional<ShowDetailsResponse> getShowById(Long id) {
        return showRepository.findById(id).map(this::toShowDetailsResponse);
    }

    public Optional<Show> updateShow(Long id, Show showDetails) {
        return showRepository.findById(id)
                .map(existingShow -> {
                    existingShow.setShowTime(showDetails.getShowTime());
                    existingShow.setMovie(showDetails.getMovie());
                    existingShow.setAuditorium(showDetails.getAuditorium());
                    existingShow.setSeats(showDetails.getSeats());
                    return saveShow(existingShow);
                });
    }

    public boolean showExists(Long id) {
        return showRepository.existsById(id);
    }

    private ShowDetailsResponse toShowDetailsResponse(Show show) {
        List<String> bookedSeatNumbers = ticketRepository.findBookedSeatNumbersByShowId(show.getId());
        List<SeatAvailabilityResponse> seatAvailability = show.getSeats().stream()
                .map(seat -> toSeatAvailabilityResponse(seat, bookedSeatNumbers))
                .collect(Collectors.toList());

        return new ShowDetailsResponse(
                show.getId(),
                show.getMovie(),
                show.getShowTime(),
                seatAvailability
        );
    }

    private SeatAvailabilityResponse toSeatAvailabilityResponse(Seat seat, List<String> bookedSeatNumbers) {
        return new SeatAvailabilityResponse(seat.getSeatNumber(), bookedSeatNumbers.contains(seat.getSeatNumber()));
    }

    public void deleteShow(Long id) {
        showRepository.deleteById(id);
    }
}
