package com.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class theaterSeatsDTO {

    private int id;
    private int seatNumber;
    private String seatType;
}
