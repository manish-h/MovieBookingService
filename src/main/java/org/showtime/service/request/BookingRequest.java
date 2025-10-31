package org.showtime.service.request;

import java.util.List;

public record BookingRequest(Long showId,
                             List<String> seatNumbers,
                             String customerName) {
}