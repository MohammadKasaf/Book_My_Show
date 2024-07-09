package com.BookMyShow.controllers;

import com.BookMyShow.dto.movieDTO;
import com.BookMyShow.models.Movie;
import com.BookMyShow.requests.updateMovieRequest;
import com.BookMyShow.services.movieService;
import com.BookMyShow.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movie")
public class movieController {

    @Autowired
    private movieService movieService;


    @GetMapping("/getAllMovies")
    public ResponseEntity<?> getAllMovies(){

        try {
            List<Movie> movies = movieService.findAllMovies();
            return new ResponseEntity<>(movies,HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findMovie")
    public ResponseEntity<?> findMovie(@RequestBody movieDTO movieDTO) {

        try {
            Optional<Movie> movie=movieService.findMovie(movieDTO);
            return new ResponseEntity<>(movie, HttpStatus.FOUND);
        }
        catch (Exception e) {
            e.printStackTrace();
            // Return an error message with HTTP status 500 (Internal Server Error)
            return new ResponseEntity<>("Failed to find movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMovie")
    public ResponseEntity<String> addMovie(@RequestBody movieDTO movieDTO) {
        try {
            // Call the service to add the movie
            String response = movieService.addMovie(movieDTO);
            // Return the response with HTTP status 201 (Created)
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {

            e.printStackTrace();
            // Return an error message with HTTP status 500 (Internal Server Error)
            return new ResponseEntity<>("Failed to add movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateMovie")
    public ResponseEntity<String> updateMovie(@RequestBody updateMovieRequest movieRequest) {

        try{
            // Call the service to update the movie
            String response = movieService.updateMovie(movieRequest);
            // Return the response with HTTP status 201 (Created)
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
            // Return an error message with HTTP status 500 (Internal Server Error)
            return new ResponseEntity<>("Failed to update movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<?> deleteMovie(@RequestBody movieDTO movieDTO){

        try{
            String response=movieService.deleteMovie(movieDTO);
            return new ResponseEntity<>(response,HttpStatus.GONE);
        }
        catch(Exception e){
            e.printStackTrace();
            // Return an error message with HTTP status 500 (Internal Server Error)
            return new ResponseEntity<>("Failed to delete movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
