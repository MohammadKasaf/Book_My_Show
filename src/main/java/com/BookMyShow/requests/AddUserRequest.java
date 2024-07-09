package com.BookMyShow.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {

    private String userName;
    private String email;
    private String mobileNumber;
    private int age;
}
