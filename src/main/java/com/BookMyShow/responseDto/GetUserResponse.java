package com.BookMyShow.responseDto;

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
public class GetUserResponse {

    private String username;
    private String role;
    private Integer age;
    private String emailId;
    private String mobileNumber;

}
