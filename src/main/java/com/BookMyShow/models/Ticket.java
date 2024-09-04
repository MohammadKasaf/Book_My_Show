package com.BookMyShow.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import com.BookMyShow.models.Show;

@Entity
@Data
@Table(name="tickets")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String ticketId;
    private String bookedSeats;
    private LocalDate showDate;
    private LocalTime showTime;
    private String movieName;
    private String theaterName;
    private Integer totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

}