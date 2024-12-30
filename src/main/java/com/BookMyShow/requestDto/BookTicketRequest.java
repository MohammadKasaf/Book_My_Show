package com.BookMyShow.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class BookTicketRequest {

    private Long userId;
    private String movieName;
    private String theaterName;
    private Long showId;
    private List<String> requestedSeats;
}
