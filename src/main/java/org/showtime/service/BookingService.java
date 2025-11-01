package org.showtime.service;

import lombok.RequiredArgsConstructor;
import org.showtime.domain.Booking;
import org.showtime.domain.Seat;
import org.showtime.domain.Show;
import org.showtime.domain.Ticket;
import org.showtime.exception.InvalidInputException;
import org.showtime.repository.BookingRepository;
import org.showtime.repository.ShowRepository;
import org.showtime.repository.TicketRepository;
import org.showtime.service.request.BookingRequest;
import org.showtime.service.response.BookingResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final ShowRepository showRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    @Transactional
    public BookingResponse createBooking(BookingRequest request) {
        // Step 1: Find the show.
        Show show = showRepository.findById(request.showId())
                .orElseThrow(() -> new InvalidInputException("Show not found with id: " + request.showId()));

        // Step 2: Validate that requested seats exist in the show's auditorium layout.
        Set<String> validSeatNumbers = show.getSeats().stream()
                .map(Seat::getSeatNumber)
                .collect(Collectors.toSet());

        for (String requestedSeat : request.seatNumbers()) {
            if (!validSeatNumbers.contains(requestedSeat)) {
                throw new IllegalArgumentException("Seat " + requestedSeat + " does not exist for this show.");
            }
        }
        // Step 3: Create the booking and tickets
        Booking booking = new Booking();
        booking.setCustomerName(request.customerName());
        booking.setShow(show);
        booking.setBookingTime(LocalDateTime.now());
        Booking savedBooking = bookingRepository.save(booking);

        List<Ticket> tickets = request.seatNumbers().stream().map(seatNumber -> createTicket(savedBooking, show, seatNumber)).toList();

        try {
            ticketRepository.saveAll(tickets);
        } catch (DataIntegrityViolationException e) {
            // This is the crucial part. If the UNIQUE constraint on (show_id, seatNumber) is violated,
            // this exception is thrown, the transaction is rolled back, and we inform the user.
            throw new InvalidInputException("One or more requested seats were booked by another user", e);
        }

        // Step 4: Return a response
        return new BookingResponse(
                savedBooking.getId(),
                show.getId(),
                show.getMovie().getTitle(),
                savedBooking.getCustomerName(),
                request.seatNumbers(),
                savedBooking.getBookingTime()
        );
    }

    private Ticket createTicket(Booking booking, Show show, String seatNumber) {
        Ticket ticket = new Ticket();
        ticket.setBooking(booking);
        ticket.setShow(show);
        ticket.setSeatNumber(seatNumber);
        return ticket;
    }
}