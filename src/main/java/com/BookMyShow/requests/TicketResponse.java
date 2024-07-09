package com.BookMyShow.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketResponse {

    private String movieName;
    private LocalTime showTime;
    private LocalDate showDate;
    private String theaterName;
    private String bookedSeats;
    private int totalAmount;
}
