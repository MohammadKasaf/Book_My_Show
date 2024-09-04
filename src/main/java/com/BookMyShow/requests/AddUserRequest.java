package com.BookMyShow.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequest {


    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Name is mandatory")
    private String userName;
    private String password;
    private String email;
    private String mobileNumber;
    private int age;
}
