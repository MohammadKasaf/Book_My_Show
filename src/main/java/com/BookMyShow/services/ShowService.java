package com.BookMyShow.services;

import com.BookMyShow.models.Show;
import com.BookMyShow.models.ShowSeat;
import com.BookMyShow.models.Theater;
import com.BookMyShow.models.TheaterSeat;
import com.BookMyShow.repositories.ShowRepository;
import com.BookMyShow.repositories.ShowSeatsRepository;
import com.BookMyShow.repositories.TicketRepository;
import com.BookMyShow.requests.AddShowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;


    private Theater theater=new Theater();

    @Autowired
    private ShowSeatsRepository showSeatsRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public String addShow(AddShowRequest showRequest){

        Show show= Show.builder().showDate(showRequest.getShowDate())
                .showTime(showRequest.getShowTime()).build();

        Show save=showRepository.save(show);

        //associate the corresponding show seats along with it
        List<TheaterSeat> theaterSeatList=theater.getTheaterSeatList();
        List<ShowSeat> showSeatList=new ArrayList<>();

        for(TheaterSeat theaterSeat:theaterSeatList){

            ShowSeat showSeat=ShowSeat.builder().seatNumber(theaterSeat.getSeatNumber())
                          .isBooked(Boolean.FALSE).seatType(theaterSeat.getSeatType())
                          .show(save).isFoodAttached(Boolean.FALSE).build();

                     showSeatList.add(showSeat);



        }

        //setting the bidirectional mapping
        show.setShowSeatList(showSeatList);

        showSeatsRepository.saveAll(showSeatList);

        return "Show added successfully with showId"+save.getShowId();

    }

    //delete show
    public String deleteShow(int showId){

        Show show=showRepository.findById(showId).orElse(null);

        if(show!=null){
            showRepository.deleteById(showId);
            ticketRepository.deleteByShow(showId);
            return "Show deleted successfully";
        }
        else{
            return "Show not found";
        }
    }
}
