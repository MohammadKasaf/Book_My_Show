package com.BookMyShow.controllers;

import com.BookMyShow.requestDto.AddMovieRequest;
import com.BookMyShow.requestDto.UpdateMovieRequest;
import com.BookMyShow.responseDto.GetMovieResponse;
import com.BookMyShow.responseDto.GetTheaterResponse;
import com.BookMyShow.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/getAllMovies")
    public ResponseEntity<?> getAllMovies() {
        try {
            List<GetMovieResponse> movies = movieService.findAllMovies();
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findMovie")
    public ResponseEntity<?> findMovieById(@RequestParam("movieId") Long movieId) {

        try{
            return new ResponseEntity<>(movieService.findMovieById(movieId),HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addMovie")
    public ResponseEntity<?> addMovie(@RequestBody AddMovieRequest movie) {
        try {
            String response = movieService.addMovie(movie);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/updateMovie")
    public ResponseEntity<String> updateMovie(@RequestBody UpdateMovieRequest movieRequest) {
        try {
            String response = movieService.updateMovie(movieRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMovie")
    public ResponseEntity<?> deleteMovieById(@RequestParam("movieId") Long movieId) {
        try {
            String response = movieService.deleteMovieById(movieId);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findTheaterByMovieId")
    public ResponseEntity<?> findTheaterByMovieId(@RequestParam("movieId") Long movieId) {
        try {
            List<GetTheaterResponse> theaterNames = movieService.findTheaterByMovieId(movieId);
            return new ResponseEntity<>(theaterNames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to find theater names: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
