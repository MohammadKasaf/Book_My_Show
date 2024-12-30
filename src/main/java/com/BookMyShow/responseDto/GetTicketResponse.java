package com.BookMyShow.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class GetTicketResponse {

    private String movieName;
    private LocalTime showTime;
    private LocalDate showDate;
    private String theaterName;
    private String bookedSeats;
    private int totalAmount;
}
