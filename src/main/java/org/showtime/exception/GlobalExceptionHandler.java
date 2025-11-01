package org.showtime.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookingServiceBaseException.class)
    public ResponseEntity<Object> handleBookingServiceBaseException(BookingServiceBaseException ex, WebRequest request) {
        var status = switch (ex) {
            case InvalidInputException e -> HttpStatus.BAD_REQUEST;
            case RetryableException e -> HttpStatus.SERVICE_UNAVAILABLE;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
        if (status.is4xxClientError()) {
            log.info("Client error occurred:", ex);
        } else {
            log.info("Server error occurred:", ex);
        }
        return new ResponseEntity<>(ex.getMessage(), status);
    }
}
