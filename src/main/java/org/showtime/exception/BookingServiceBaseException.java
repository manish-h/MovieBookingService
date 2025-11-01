package org.showtime.exception;

public class BookingServiceBaseException extends RuntimeException {
    public BookingServiceBaseException(String message) {
        super(message);
    }

    public BookingServiceBaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
