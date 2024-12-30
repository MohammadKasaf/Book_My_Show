package com.BookMyShow.services;

import com.BookMyShow.enums.*;
import com.BookMyShow.models.*;
import com.BookMyShow.repositories.*;
import com.BookMyShow.requestDto.*;
import com.BookMyShow.responseDto.GetTicketResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import org.springframework.transaction.annotation.Transactional;

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
    private JavaMailSender javaMailSender;

    @Transactional
    public String bookTicket(@RequestBody BookTicketRequest ticketRequest){

        //for sending email for confirmation ticket details
        SimpleMailMessage message = new SimpleMailMessage();

        //1. find the show entity
        Show show = showRepository.findById(ticketRequest.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found with this id: " + ticketRequest.getShowId()));

        //2. find the user entity
        User user = userRepository.findById(ticketRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with this id: " + ticketRequest.getUserId()));

        //3. Mark those seats as booked now and calculate total amount
        int totalAmount = 0;
        boolean allSeatsAvailable = true;

        for (ShowSeat showSeat : show.getShowSeatList()) {
            String seatNumber = showSeat.getSeatNumber();
            if (ticketRequest.getRequestedSeats().contains(seatNumber)) {
                if (showSeat.getIsBooked() == Boolean.TRUE) {
                    allSeatsAvailable = false;
                    break;
                }
            }
        }

        if (!allSeatsAvailable) {
            return "Some seats are already booked";
        }

        for (ShowSeat showSeat : show.getShowSeatList()) {
            String seatNumber = showSeat.getSeatNumber();
            if (ticketRequest.getRequestedSeats().contains(seatNumber)) {
                showSeat.setIsBooked(Boolean.TRUE);

                if (showSeat.getSeatType().equals(SeatType.CLASSIC)) {
                    totalAmount += 100;
                } else {
                    totalAmount += 200;
                }
            }
        }

        //4. Create the ticket entity and set the attributes
        Ticket ticket = Ticket.builder()
                .totalAmount(totalAmount)
                .showDate(show.getShowDate())
                .showTime(show.getShowTime())
                .user(user)
                .show(show)
                .movieName(show.getMovie().getMovieName())
                .theaterName(show.getTheater().getTheaterName())
                .bookedSeats(ticketRequest.getRequestedSeats().toString())
                .build();

        // Send email
        message.setSubject("Ticket Details");
        message.setText("Your ticket details are: " + ticket.toString());
        message.setTo(user.getEmailId());
        message.setFrom("springboot471@gmail.com"); // Should match the actual sender email
        javaMailSender.send(message);

        //5. Save the ticket and update the show seat list
        ticket = ticketRepository.save(ticket);
        showSeatsRepository.saveAll(show.getShowSeatList());

        return "Ticket successfully booked";
    }

    public GetTicketResponse generateTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found with this id: " + ticketId));

        return GetTicketResponse.builder()
                .movieName(ticket.getMovieName())
                .theaterName(ticket.getTheaterName())
                .bookedSeats(ticket.getBookedSeats())
                .showDate(ticket.getShowDate())
                .showTime(ticket.getShowTime())
                .totalAmount(ticket.getTotalAmount())
                .build();
    }

    // Cancel all seats booked by a ticket
    @Transactional
    public String cancelTicket(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        if (ticket == null) {
            return "Ticket with id: " + ticketId + " not found";
        }

        Show show = ticket.getShow();
        boolean seatUpdated = false;

        for (ShowSeat showSeat : show.getShowSeatList()) {
            if (ticket.getBookedSeats().contains(showSeat.getSeatNumber())) {
                showSeat.setIsBooked(Boolean.FALSE);
                showSeatsRepository.save(showSeat);
                seatUpdated = true;
            }
        }

        if (seatUpdated) {
            ticketRepository.delete(ticket);
            sendCancellationEmail(ticket);
            return "Ticket with id: " + ticketId + " cancelled successfully";
        }

        return "Ticket with id: " + ticketId + " not found or you are not allowed to cancel this ticket";
    }

    private void sendCancellationEmail(Ticket ticket) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(ticket.getUser().getEmailId());
        simpleMailMessage.setFrom("springboot471@gmail.com"); // Should match the actual sender email
        simpleMailMessage.setSubject("Ticket Cancellation Notification");
        simpleMailMessage.setText("Dear " + ticket.getUser().getUsername() + ",\n\n"
                + "Your ticket with ID " + ticket.getTicketId() + " has been successfully cancelled.\n\n"
                + "Thank you for using BookMyShow.\n\n"
                + "Best Regards,\n"
                + "BookMyShow Team");
        javaMailSender.send(simpleMailMessage);
    }
}
