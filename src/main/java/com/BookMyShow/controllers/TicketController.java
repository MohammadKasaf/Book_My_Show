package com.BookMyShow.controllers;

import com.BookMyShow.requestDto.BookTicketRequest;
import com.BookMyShow.responseDto.GetTicketResponse;
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
         try{

             String ticket=ticketService.bookTicket(bookTicketRequest);
             return new ResponseEntity<>(ticket, HttpStatus.OK);
         }
         catch(Exception e){

             return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
         }
    }

    @GetMapping("/generateTicket")
    public ResponseEntity<?> generateTicket(@RequestParam("tickedId")Long ticketId){

        try{
            GetTicketResponse ticketResponse=ticketService.generateTicket(ticketId);
            return new ResponseEntity<>(ticketResponse, HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/cancelTicket")
    public ResponseEntity<?> cancelTicket(@RequestParam("ticketId")Long ticketId){

        try{
            String response=ticketService.cancelTicket(ticketId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        catch(Exception e){

            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
