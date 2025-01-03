package com.BookMyShow.requestDto;

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
public class AddMovieRequest {

    private String movieName;
    private Language language;
    private Double rating;
    private LocalDate releaseDate;
    private Double duration;
}
