package com.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class showDTO {

    private int id;
    private String name;
    private String time;
    private String date;
    private int screenNumber;
}
