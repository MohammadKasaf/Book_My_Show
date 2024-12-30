package com.BookMyShow.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class AddShowRequest {


    LocalDate showDate;
    LocalTime showTime;
    private Long movieId;
    private Long theaterId;
}
