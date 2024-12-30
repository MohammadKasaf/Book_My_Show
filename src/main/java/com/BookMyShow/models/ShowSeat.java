package com.BookMyShow.models;

import com.BookMyShow.enums.SeatType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "show_seats")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShowSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showSeatId;
    private String seatNumber;
    @Enumerated(value = EnumType.STRING)
    private SeatType seatType;
    private Boolean isBooked;
    private Boolean isFoodAttached;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;
}