package org.showtime.service.response;

import java.time.LocalDateTime;
import java.util.List;

public record BookingResponse(Long bookingId,
                              Long showId,
                              String movieTitle,
                              String customerName,
                              List<String> bookedSeats,
                              LocalDateTime bookingTime) {
}