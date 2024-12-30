package com.BookMyShow.requestDto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
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


    private String userName;
    private String role;
    private String password;
    private String email;
    private String mobileNumber;
    private int age;
}
