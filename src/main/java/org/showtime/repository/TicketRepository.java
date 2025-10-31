package org.showtime.repository;

import org.showtime.domain.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t.seatNumber FROM Ticket t WHERE t.show.id = :showId")
    List<String> findBookedSeatNumbersByShowId(Long showId);
}