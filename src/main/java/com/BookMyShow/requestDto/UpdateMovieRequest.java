package com.BookMyShow.requestDto;

import com.BookMyShow.enums.Language;
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
public class UpdateMovieRequest {

    private Long movieId;
    private String movieName;
    private Language newlanguage;
    private Double newRating;

}
