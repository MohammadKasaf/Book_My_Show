package com.BookMyShow.dto;

import com.BookMyShow.enums.Language;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class movieDTO {

    private int movieId;
    private String movieName;
    private Language language;
    private Double rating;
    private LocalDate releaseDate;
    private Double duration;
}
