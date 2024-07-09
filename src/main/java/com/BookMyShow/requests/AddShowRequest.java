package com.BookMyShow.requests;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AddShowRequest {


    LocalDate showDate;
    LocalTime showTime;
}
