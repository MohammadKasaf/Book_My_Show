package com.BookMyShow.controllers;

import com.BookMyShow.requests.AddTheaterSeatRequest;
import com.BookMyShow.requests.AddTheaterRequest;
import com.BookMyShow.services.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("theater")
public class TheaterController {

    @Autowired
    private TheaterService theaterService;

    @PostMapping("/addTheater")
    public ResponseEntity<String> addTheater(@RequestBody AddTheaterRequest addTheaterRequest) {
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
    public ResponseEntity<?> getAllMoviesInTheater(@RequestParam("theterName") String theaterName) {

        try {
            return new ResponseEntity<>(theaterService.getAllMoviesInTheater(theaterName), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  }
