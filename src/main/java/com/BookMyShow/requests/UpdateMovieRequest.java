package com.BookMyShow.requests;

import com.BookMyShow.enums.Language;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class updateMovieRequest {

    private String movieName;
    private Language newlanguage;
    private Double newRating;

}
