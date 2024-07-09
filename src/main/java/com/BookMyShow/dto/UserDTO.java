package com.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userDTO {

    private int id;
    private String name;
    private String email;
    // we dont include password field here bcz password is sensititve data
}
