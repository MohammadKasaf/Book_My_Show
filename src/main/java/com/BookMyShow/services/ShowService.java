package com.BookMyShow.services;

import com.BookMyShow.models.*;
import com.BookMyShow.repositories.*;
import com.BookMyShow.requestDto.AddShowRequest;
import com.BookMyShow.responseDto.GetShowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowSeatsRepository showSeatsRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieRepository movieRepository;

    public String addShow(AddShowRequest showRequest) {

        Theater theater = theaterRepository.findById(showRequest.getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater with ID " + showRequest.getTheaterId() + " not found"));


        Movie movie = movieRepository.findById(showRequest.getMovieId())
                .orElseThrow(()->new RuntimeException("movie not found with this id: " + showRequest.getMovieId()));

        // 2. Create and save the Show
        Show show = Show.builder()
                .showDate(showRequest.getShowDate())
                .showTime(showRequest.getShowTime())
                .movie(movie)
                .theater(theater)
                .build();

        Show savedShow = showRepository.save(show);

        // 3. Associate the corresponding show seats with the show
        List<TheaterSeat> theaterSeatList = theater.getTheaterSeatList();
        List<ShowSeat> showSeatList = new ArrayList<>();

        for (TheaterSeat theaterSeat : theaterSeatList) {
            ShowSeat showSeat = ShowSeat.builder()
                    .seatNumber(theaterSeat.getSeatNumber())
                    .isBooked(Boolean.FALSE)
                    .seatType(theaterSeat.getSeatType())
                    .show(savedShow)
                    .isFoodAttached(Boolean.FALSE)
                    .build();

            showSeatList.add(showSeat);
        }

        // 4. Set the bidirectional mapping and save the show seats
        savedShow.setShowSeatList(showSeatList);
        showSeatsRepository.saveAll(showSeatList);

        return "Show added successfully with showId " + savedShow.getShowId();
    }

    // Delete show
    public String deleteShow(Long showId) {

        Show show=showRepository.findById(showId)
                .orElseThrow(()->new RuntimeException("show is not found with this id: " + showId));

        showRepository.delete(show);
        return "show successfully deleted";
    }

    public List<GetShowResponse> getAllShowsByTheaterId(Long theaterId) {


            List<Show> shows = showRepository.findAll();
            List<GetShowResponse> showRequests = new ArrayList<>();

            for (Show show : shows) {

                if(show.getTheater().getTheaterId()==theaterId) {

                    String movieName = show.getMovie() != null ? show.getMovie().getMovieName() : "Movie not assigned";
                    String theaterName = show.getTheater() != null ? show.getTheater().getTheaterName() : null;

                    GetShowResponse showResponse = new GetShowResponse();
                    showResponse.setShowDate(show.getShowDate());
                    showResponse.setShowTime(show.getShowTime());
                    showResponse.setMovieName(movieName);
                    showResponse.setTheaterName(theaterName);

                    showRequests.add(showResponse);

                }
            }
            return showRequests;
    }



}
