package com.BookMyShow.controllers;

import com.BookMyShow.requests.AddTheaterSeatRequest;
import com.BookMyShow.requests.addTheaterRequest;
import com.BookMyShow.services.theaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("theater")
public class theaterController {

    @Autowired
    private theaterService theaterService;

    @PostMapping("/addTheater")
    public ResponseEntity<String> addTheater(@RequestBody addTheaterRequest addTheaterRequest){
        try{
            String response=theaterService.addTheater(addTheaterRequest);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/associateSeats")
    public ResponseEntity<?> associateSeats(@RequestBody AddTheaterSeatRequest theaterSeatRequest){
        try{
            String response=theaterService.associateTheaterSeats(theaterSeatRequest);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
