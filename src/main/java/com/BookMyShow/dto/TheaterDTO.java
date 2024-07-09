package com.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class theaterDTO {

    private int id;
    private int numberOfScreens;
    private String name;
    private String Address;
}
