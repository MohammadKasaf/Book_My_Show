package com.BookMyShow.controllers;

import com.BookMyShow.dto.MovieDTO;
import com.BookMyShow.models.Movie;
import com.BookMyShow.requests.UpdateMovieRequest;
import com.BookMyShow.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/getAllMovies")
    public ResponseEntity<?> getAllMovies() {
        try {
            List<Movie> movies = movieService.findAllMovies();
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findMovie")
    public ResponseEntity<?> findMovieById(@RequestParam("movieId") Integer movieId) {
        try {
            Optional<Movie> movie = movieService.findMovieById(movieId);
            if (movie.isPresent()) {
                return new ResponseEntity<>(movie.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Movie not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to find movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addMovie")
    public ResponseEntity<String> addMovie(@RequestBody MovieDTO movieDTO) {
        try {
            String response = movieService.addMovie(movieDTO);
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
    public ResponseEntity<String> deleteMovieById(@RequestParam("movieId") Integer movieId) {
        try {
            String response = movieService.deleteMovieById(movieId);
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete movie: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/findTheaterNamesByMovieName")
    public ResponseEntity<?> findTheaterNamesByMovieName(@RequestParam("movieName") String movieName) {
        try {
            List<String> theaterNames = movieService.findTheaterNameByMovieName(movieName);
            return new ResponseEntity<>(theaterNames, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to find theater names: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
