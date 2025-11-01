package org.showtime.exception;

public class RetryableException extends BookingServiceBaseException {
    public RetryableException(String message) {
        super(message);
    }

    public RetryableException(String message, Throwable cause) {
        super(message, cause);
    }
}
