package com.BookMyShow.controllers;

import com.BookMyShow.requestDto.AddTheaterSeatRequest;
import com.BookMyShow.requestDto.AddTheaterRequest;
import com.BookMyShow.services.TheaterService;
import com.BookMyShow.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @Autowired
    private TicketService ticketService;


    @PostMapping("/addTheater")
    public ResponseEntity<?> addTheater(@RequestBody AddTheaterRequest addTheaterRequest) {
        try {
            String response = theaterService.addTheater(addTheaterRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/associateSeats")
    public ResponseEntity<?> associateSeats(@RequestBody AddTheaterSeatRequest theaterSeatRequest) {
        try {
            String response = theaterService.associateTheaterSeats(theaterSeatRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/movies")
    public ResponseEntity<?> getAllMoviesInTheater(@RequestParam("theateId") Long theaterId) {
        try {
            // Assuming the service returns a list of movie names
            return new ResponseEntity<>(theaterService.getAllMoviesInTheater(theaterId),HttpStatus.OK);
        } catch (Exception e) {
            // Return a list containing the error message
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/oneDayRevenueOfTheater")
    public ResponseEntity<?> oneDayRevenueOfTheater(@RequestParam("theaterName")Long theaterId, @RequestParam("date") LocalDate date){

        try{
            String revenue=theaterService.oneDayRevenueOfTheater(theaterId,date);
            return new ResponseEntity<>(revenue, HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/lifeTimeRevenueOfTheater")
    public ResponseEntity<?> lifeTimeRevenueOfTheater(@RequestParam("theaterName")Long theaterId){

        try{
            String revenue=theaterService.lifeTimeRevenueOfTheater(theaterId);
            return new ResponseEntity<>(revenue, HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
