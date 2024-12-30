package com.BookMyShow.services;

import com.BookMyShow.enums.SeatType;
import com.BookMyShow.models.*;
import com.BookMyShow.repositories.MovieRepository;
import com.BookMyShow.repositories.ShowRepository;
import com.BookMyShow.repositories.TheaterRepository;
import com.BookMyShow.repositories.TheaterSeatRepository;
import com.BookMyShow.requestDto.AddTheaterSeatRequest;
import com.BookMyShow.requestDto.AddTheaterRequest;
import com.BookMyShow.responseDto.GetMovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TheaterService {

    @Autowired
    private TheaterRepository theaterRepository;
    @Autowired
    private TheaterSeatRepository theaterSeatRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private MovieRepository movieRepository;

    public String addTheater(AddTheaterRequest addTheaterRequest) {

        Theater theater = new Theater();
        theater.setTheaterName(addTheaterRequest.getName());
        theater.setAddress(addTheaterRequest.getAddress());
        theater.setNumberOfScreens(addTheaterRequest.getNumberOfScreens());
        theaterRepository.save(theater);
        return "Theater added successfully";
    }



    public String associateTheaterSeats(AddTheaterSeatRequest addTheaterSeatRequest) {

        Long theaterId = addTheaterSeatRequest.getTheaterId();
        int numberOfClassicSeats = addTheaterSeatRequest.getNumberOfClassicSeats();
        int numberOfPremiumSeats = addTheaterSeatRequest.getNumberOfPremiumSeats();

        // 1. Get the theaterEntity from database
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found"));

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


    public List<GetMovieResponse> getAllMoviesInTheater(Long theaterId) {
        // Find the theater by its name
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(()->new RuntimeException("theater is not found with this id:" + theaterId));


        // Fetch all shows
        List<Show> allShows = showRepository.findAll();

        List<GetMovieResponse> movieResponses=new ArrayList<>();

        for(Show show:allShows){

            if(show.getTheater().getTheaterId()==theaterId){
                Movie movie=show.getMovie();
                GetMovieResponse movieResponse=new GetMovieResponse();
                movieResponse.setMovieName(movie.getMovieName());
                movieResponse.setDuration(movie.getDuration());
                movieResponse.setReleaseDate(movie.getReleaseDate());
                movieResponse.setLanguage(movie.getLanguage());
                movieResponse.setRating(movie.getRating());
                movieResponses.add(movieResponse);
            }
        }

       return movieResponses;
    }

    //find one day revenue of theater
    public String oneDayRevenueOfTheater(Long theaterId, LocalDate date){

        int totalRevenue=0;
        Theater theater=theaterRepository.findById(theaterId)
                .orElseThrow(()->new RuntimeException("theater is not found with this id: " +theaterId ));

        for(Show show:theater.getShowList()){

            if(show.getShowDate().equals(date)){

                for(Ticket ticket:show.getTicketList()){

                    totalRevenue+=ticket.getTotalAmount();

                }
            }
        }

        return "Total revenue of theater "+theater.getTheaterName()+" on "+date+" is "+totalRevenue;
    }

    //lifeTime revenue of theater
    public String lifeTimeRevenueOfTheater(Long theaterId){

        int totalRevenue=0;
        Theater theater=theaterRepository.findById(theaterId)
                .orElseThrow(()->new RuntimeException("theater is not found with this id: " +theaterId ));

        for(Show show:theater.getShowList()){

            for(Ticket ticket:show.getTicketList()){

                totalRevenue+=ticket.getTotalAmount();

            }
        }

        return "Total revenue of theater "+theater.getTheaterName()+" is "+totalRevenue;
    }




}