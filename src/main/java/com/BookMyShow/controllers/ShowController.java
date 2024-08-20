package com.BookMyShow.controllers;

import com.BookMyShow.requests.AddShowRequest;
import com.BookMyShow.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> deleteShowById(@RequestParam("showId")Integer showId) {
        try {
            String response = showService.deleteShow(showId);
            return new ResponseEntity<>(response, HttpStatus.OK);  // Return response string with HTTP status OK
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);  // Return error message with HTTP status BAD REQUEST
        }
    }
}
