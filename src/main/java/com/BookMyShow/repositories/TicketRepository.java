package com.BookMyShow.repositories;

import com.BookMyShow.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,String>{

    @Query("DELETE FROM Ticket t WHERE t.show.showId = :showId")
    void deleteByShow(@Param("showId") int showId);
}