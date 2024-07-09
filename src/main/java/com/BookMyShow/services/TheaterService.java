package com.BookMyShow.services;

import com.BookMyShow.enums.SeatType;
import com.BookMyShow.models.Theater;
import com.BookMyShow.models.TheaterSeat;
import com.BookMyShow.repositories.TheaterRepository;
import com.BookMyShow.repositories.TheaterSeatRepository;
import com.BookMyShow.requests.AddTheaterSeatRequest;
import com.BookMyShow.requests.addTheaterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class theaterService {

    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private TheaterSeatRepository theaterSeatRepository;

    public String addTheater(addTheaterRequest addTheaterRequest) {

        Theater theater = new Theater();
        theater.setName(addTheaterRequest.getName());
        theater.setAddress(addTheaterRequest.getAddress());
        theater.setNumberOfScreens(addTheaterRequest.getNumberOfScreens());
        theaterRepository.save(theater);
        return "Theater added successfully";
    }



    public String associateTheaterSeats(AddTheaterSeatRequest addTheaterSeatRequest) {
        int theaterId = addTheaterSeatRequest.getTheaterId();
        int numberOfClassicSeats = addTheaterSeatRequest.getNumberOfClassicSeats();
        int numberOfPremiumSeats = addTheaterSeatRequest.getNumberOfPremiumSeats();

        // 1. Get the theaterEntity from database
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(() -> new RuntimeException("Theater not found"));

        // 2. Generate those seat numbers through for loop
        int numberOfRowOfClassicSeats = numberOfClassicSeats / 5;
        int numberOfSeatsInLastRow = numberOfClassicSeats % 5;
        List<TheaterSeat> theaterSeatList = new ArrayList<>();
        int row = 1;

        for (row = 1; row <= numberOfRowOfClassicSeats; row++) {
            for (int i = 1; i <= 5; i++) {
                char ch = (char) ('A' + i - 1);
                String seatNumber = "" + row + ch;

                TheaterSeat theaterSeat = TheaterSeat.builder()
                        .seatNumber(seatNumber)
                        .seatType(SeatType.CLASSIC)  // Use uppercase enum value
                        .theater(theater)
                        .build();

                theaterSeatList.add(theaterSeat);
            }
        }

        // for the last row
        for (int i = 1; i <= numberOfSeatsInLastRow; i++) {
            char ch = (char) ('A' + i - 1);
            String seatNumber = "" + row + ch;

            TheaterSeat theaterSeat = TheaterSeat.builder()
                    .seatNumber(seatNumber)
                    .seatType(SeatType.CLASSIC)  // Use uppercase enum value
                    .theater(theater)
                    .build();

            theaterSeatList.add(theaterSeat);
        }

        // same logic for premium seats
        int numberOfRowOfPremiumSeats = numberOfPremiumSeats / 5;
        numberOfSeatsInLastRow = numberOfPremiumSeats % 5;

        for (row = 1; row <= numberOfRowOfPremiumSeats; row++) {
            for (int i = 1; i <= 5; i++) {
                char ch = (char) ('A' + i - 1);
                String seatNumber = "" + row + ch;

                TheaterSeat theaterSeat = TheaterSeat.builder()
                        .seatNumber(seatNumber)
                        .seatType(SeatType.PREMIUM)  // Use uppercase enum value
                        .theater(theater)
                        .build();

                theaterSeatList.add(theaterSeat);
            }
        }

        // for the last row
        for (int i = 1; i <= numberOfSeatsInLastRow; i++) {
            char ch = (char) ('A' + i - 1);
            String seatNumber = "" + row + ch;

            TheaterSeat theaterSeat = TheaterSeat.builder()
                    .seatNumber(seatNumber)
                    .seatType(SeatType.PREMIUM)  // Use uppercase enum value
                    .theater(theater)
                    .build();

            theaterSeatList.add(theaterSeat);
        }

        // save all the generated theater seats in database
        theater.setTheaterSeatList(theaterSeatList);
        theaterRepository.save(theater);
        theaterSeatRepository.saveAll(theaterSeatList);
        return "Theater seats added successfully";

    }
}