package com.BookMyShow.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class AddTheaterSeatRequest {

    private int theaterId;
    private int numberOfClassicSeats;
    private int numberOfPremiumSeats;
}
