package com.BookMyShow.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addTheaterRequest {

    private int numberOfScreens;
    private String name;
    private String Address;
}
