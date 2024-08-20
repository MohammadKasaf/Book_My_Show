package com.BookMyShow.controllers;

import com.BookMyShow.requests.BookTicketRequest;
import com.BookMyShow.requests.TicketResponse;
import com.BookMyShow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/bookTicket")
    public ResponseEntity<?> bookTicket(@RequestBody BookTicketRequest  bookTicketRequest) {
        // Book the ticket
         try{

             String ticket=ticketService.bookTicket(bookTicketRequest);
             return new ResponseEntity<>(ticket, HttpStatus.OK);
         }
         catch(Exception e){

             return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
         }
    }

    @GetMapping("/generateTicket")
    public ResponseEntity<?> generateTicket(@RequestParam("tickedId")String ticketId) {
        // Generate the ticket
        try{
            TicketResponse ticketResponse=ticketService.generateTicket(ticketId);
            return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/cancelTicket")
    public ResponseEntity<?> cancelTicket(@RequestParam("ticketId")String ticketId){

        try{
            String response=ticketService.cancelTicket(ticketId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/oneDayRevenueOfTheater")
    public ResponseEntity<?> oneDayRevenueOfTheater(@RequestParam("theaterName")String theaterName, @RequestParam("date")LocalDate date){

        try{
            String revenue=ticketService.oneDayRevenueOfTheater(theaterName,date);
            return new ResponseEntity<>(revenue, HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lifeTimeRevenueOfTheater")
    public ResponseEntity<?> lifeTimeRevenueOfTheater(@RequestParam("theaterName")String theaterName){

        try{
            String revenue=ticketService.lifeTimeRevenueOfTheater(theaterName);
            return new ResponseEntity<>(revenue, HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
