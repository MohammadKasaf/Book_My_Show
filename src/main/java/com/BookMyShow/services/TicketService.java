package com.BookMyShow.services;

import com.BookMyShow.enums.SeatType;
import com.BookMyShow.models.*;
import com.BookMyShow.repositories.*;
import com.BookMyShow.requests.BookTicketRequest;
import com.BookMyShow.requests.TicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

@Service
public class ticketService {

    @Autowired
    private ShowSeatsRepository showSeatsRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TheaterRepository theaterRepository;


    public String bookTicket(@RequestBody BookTicketRequest ticketRequest){


        //1.find the show entity
        Show show=showRepository.findById(ticketRequest.getShowId()).orElse(null);

        //2.find the user Entity
        User user=userRepository.findById(ticketRequest.getUserId()).orElse(null);

        //3.mark those seats as booked now and calculate total amount
         int totalAmount=0;

         for(ShowSeat showSeat : show.getShowSeatList()){

             String seatNumber=showSeat.getSeatNumber();
             if(ticketRequest.getRequestedSeats().contains(seatNumber)){

                 showSeat.setIsBooked(Boolean.TRUE);

                 if(showSeat.getSeatType().equals(SeatType.CLASSIC)){
                     //assume that classic seat price is 100
                     totalAmount+=100;
                 }else{
                     //assume that premimum seat price is 200
                     totalAmount+=200;
                 }
             }

         }

        //4.create the ticket entity and set the attributes
        Ticket ticket=Ticket.builder().totalAmount(totalAmount)
                .showDate(show.getShowDate()).showTime(show.getShowTime())
                .user(user).show(show).movieName(show.getMovie().getMovieName())
                .theaterName(show.getTheater().getName()).bookedSeats(ticketRequest.getRequestedSeats().toString())
                .build();

         ticket=ticketRepository.save(ticket);
         showSeatsRepository.saveAll(show.getShowSeatList());

         return ticket.getTicketId();
    }

    public TicketResponse generateTicket(String ticketId){

        Ticket ticket=ticketRepository.findById(ticketId).orElse(null);
        if(ticket==null){
            return null;
        }

        return TicketResponse.builder().movieName(ticket.getMovieName())
                .theaterName(ticket.getTheaterName()).bookedSeats(ticket.getBookedSeats())
                .showDate(ticket.getShowDate()).showTime(ticket.getShowTime()).totalAmount(ticket.getTotalAmount())
                .build();
    }

    //cancel all seats booked by a ticket
    public String cancelTicket(String ticketId){

        Ticket ticket=ticketRepository.findById(ticketId).orElse(null);
        if(ticket==null){
            return "Ticket with id : "+ticketId+" not found";
        }

        Show show=ticket.getShow();
        boolean seatUpdated=false;
        for(ShowSeat showSeat:show.getShowSeatList()){

            if(ticket.getBookedSeats().contains(showSeat.getSeatNumber())){

                showSeat.setIsBooked(Boolean.FALSE);
                showSeatsRepository.save(showSeat);
                seatUpdated=true;

            }

        }

        if(seatUpdated){
            ticketRepository.delete(ticket);
            return "Ticket with id : "+ticketId+" cancelled successfully";
        }

         return "Ticket with id : "+ticketId+" not found or" +
                 " you are not allowed to cancel this ticket";
    }


    //find one day revenue of theater
    public String oneDayRevenueOfTheater(Integer theaterId, LocalDate date){

        int totalRevenue=0;
        Theater theater = theaterRepository.findById(theaterId).orElse(null); // Use of orElse to handle Optional

        if (theater == null) {
            return "Theater with ID " + theaterId + " not found.";
        }

        for(Show show:theater.getShowList()){

            if(show.getShowDate().equals(date)){

            for(Ticket ticket:show.getTicketList()){

                totalRevenue+=ticket.getTotalAmount();

            }
        }
      }

        return "Total revenue of theater "+theater.getName()+" on "+date+" is "+totalRevenue;
    }

    //lifeTime revenue of theater
    public String lifeTimeRevenueOfTheater(Integer theaterId){

        int totalRevenue=0;
        Theater theater = theaterRepository.findById(theaterId).orElse(null); // Use of orElse to handle Optional

        if (theater == null) {
            return "Theater with ID " + theaterId + " not found.";
        }
        for(Show show:theater.getShowList()){

            for(Ticket ticket:show.getTicketList()){

                totalRevenue+=ticket.getTotalAmount();

            }
        }

        return "Total revenue of theater "+theater.getName()+" is "+totalRevenue;
    }
}
