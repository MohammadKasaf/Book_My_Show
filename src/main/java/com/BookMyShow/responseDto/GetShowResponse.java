package com.BookMyShow.responseDto;

import com.BookMyShow.models.Movie;
import com.BookMyShow.models.Theater;
import com.BookMyShow.models.Ticket;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class GetShowResponse {

    private LocalDate showDate;
    private LocalTime showTime;
    private String  movieName;
    private String theaterName;


}
