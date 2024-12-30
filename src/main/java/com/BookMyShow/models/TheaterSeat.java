package com.BookMyShow.models;

import com.BookMyShow.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name="theater_seats")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TheaterSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterSeatId;
    private String seatNumber;
    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;

    @ManyToOne
    @JoinColumn(name = "theater_id",nullable = false)
    private Theater theater;

}
