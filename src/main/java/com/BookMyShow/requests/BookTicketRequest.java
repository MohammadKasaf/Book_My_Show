package com.BookMyShow.requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class BookTicketRequest {

    private int userId;
    private String movieName;
    private String theaterName;
    private LocalTime  showTime;
    private LocalDate showDate;
    private int showId;
    private List<String> requestedSeats;
}
