package com.BookMyShow.requests;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class AddUserRequest {


    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Name is mandatory")
    private String userName;
    private String role;
    private String password;
    private String email;
    private String mobileNumber;
    private int age;
}
