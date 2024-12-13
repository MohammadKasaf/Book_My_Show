package com.BookMyShow.dto;

import com.BookMyShow.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MovieDTO {

    private int movieId;
    private String movieName;
    private Language language;
    private Double rating;
    private LocalDate releaseDate;
    private Double duration;
}
