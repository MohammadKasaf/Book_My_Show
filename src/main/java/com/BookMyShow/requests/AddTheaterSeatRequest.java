package com.BookMyShow.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTheaterSeatRequest {

    private int theaterId;
    private int numberOfClassicSeats;
    private int numberOfPremiumSeats;
}
