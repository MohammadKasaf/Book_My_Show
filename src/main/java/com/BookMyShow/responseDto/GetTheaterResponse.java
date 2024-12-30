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
public class GetTheaterResponse {

    private String theaterName;
    private String address;
    private Integer numberOfScreens;
}
