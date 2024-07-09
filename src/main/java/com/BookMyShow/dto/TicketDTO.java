package com.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ticketDTO {

    private int id;
    private String movieName;
    private String showTime;
    private String showDate;
    private int ticketPrice;
}
