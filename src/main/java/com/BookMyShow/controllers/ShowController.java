package com.BookMyShow.controllers;

import com.BookMyShow.requestDto.AddShowRequest;
import com.BookMyShow.responseDto.GetShowResponse;
import com.BookMyShow.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/addShow")
    public ResponseEntity<?> addShow(@RequestBody AddShowRequest showRequest) {
        try {
            String response = showService.addShow(showRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);  // Return response string with HTTP status OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // Return error message with HTTP status BAD REQUEST
        }
    }

    @DeleteMapping("/deleteShow")
    public ResponseEntity<?> deleteShowById(@RequestParam("showId")Long showId) {
        try {
            String response = showService.deleteShow(showId);
            return new ResponseEntity<>(response, HttpStatus.OK);  // Return response string with HTTP status OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // Return error message with HTTP status BAD REQUEST
        }
    }

    @GetMapping("/getAllShows")
    public ResponseEntity<?> findAllShowsByTheaterId(@RequestParam Long theaterId) {
        try {
            List<GetShowResponse> shows = showService.getAllShowsByTheaterId(theaterId);
            if (shows.isEmpty()) {
                return new ResponseEntity<>("No shows found", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(shows, HttpStatus.OK);
        } catch (Exception e) {
            // Log the error for internal tracking
            return new ResponseEntity<>("Failed to retrieve shows. Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
