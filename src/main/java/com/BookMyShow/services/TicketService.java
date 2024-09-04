package com.BookMyShow.services;

import com.BookMyShow.enums.*;
import com.BookMyShow.models.*;
import com.BookMyShow.repositories.*;
import com.BookMyShow.requests.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TicketService {

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

    @Autowired
    JavaMailSender javaMailSender;


    public String bookTicket(@RequestBody BookTicketRequest ticketRequest){

        //for sending email for confirmation ticket details
        SimpleMailMessage message=new SimpleMailMessage();

        //1.find the show entity
        Show show=showRepository.findById(ticketRequest.getShowId()).orElse(null);

        //2.find the user Entity
        User user=userRepository.findById(ticketRequest.getUserId()).orElse(null);

        //3.mark those seats as booked now and calculate total amount
         int totalAmount=0;

         for(ShowSeat showSeat : show.getShowSeatList()){

             String seatNumber=showSeat.getSeatNumber();
             if(ticketRequest.getRequestedSeats().contains(seatNumber)){

                 if(showSeat.getIsBooked()==Boolean.TRUE){
                     return "Seat already booked";
                 }
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
                .theaterName(show.getTheater().getTheaterName()).bookedSeats(ticketRequest.getRequestedSeats().toString())
                .build();

         //for email
         message.setSubject("Ticket Details");
         message.setText("Your ticket details are : "+ticket.toString());
         message.setTo(user.getEmailId());
         message.setFrom("springboot471@gmail.com");
         javaMailSender.send(message);

         //5. save the ticket and update the show seat list
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
            sendCancellationEmail(ticket);
            return "Ticket with id : "+ticketId+" cancelled successfully";
        }

         return "Ticket with id : "+ticketId+" not found or" +
                 " you are not allowed to cancel this ticket";
    }

    private void sendCancellationEmail(Ticket ticket) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(ticket.getUser().getEmailId());
        simpleMailMessage.setFrom("kaashifchishti611@gmail.com");
        simpleMailMessage.setSubject("Ticket Cancellation Notification");
        simpleMailMessage.setText("Dear " + ticket.getUser().getUsername() + ",\n\n"
                + "Your ticket with ID " + ticket.getTicketId() + " has been successfully cancelled.\n\n"
                + "Thank you for using BookMyShow.\n\n"
                + "Best Regards,\n"
                + "BookMyShow Team");
        javaMailSender.send(simpleMailMessage);
    }


    //find one day revenue of theater
    public String oneDayRevenueOfTheater(String theaterName, LocalDate date){

        int totalRevenue=0;
        Theater theater = theaterRepository.findByTheaterName(theaterName); // Use of orElse to handle Optional

        if (theater == null) {
            return "Theater with Name " + theaterName + " not found.";
        }

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
    public String lifeTimeRevenueOfTheater(String theaterName){

        int totalRevenue=0;
        Theater theater = theaterRepository.findByTheaterName(theaterName);

        if (theater == null) {
            return "Theater with I name " + theaterName + " not found.";
        }
        for(Show show:theater.getShowList()){

            for(Ticket ticket:show.getTicketList()){

                totalRevenue+=ticket.getTotalAmount();

            }
        }

        return "Total revenue of theater "+theater.getTheaterName()+" is "+totalRevenue;
    }
}
