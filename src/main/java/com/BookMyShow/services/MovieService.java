package com.BookMyShow.services;

import com.BookMyShow.dto.movieDTO;
import com.BookMyShow.models.Movie;
import com.BookMyShow.repositories.MovieRepository;
import com.BookMyShow.requests.updateMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class movieService {

    @Autowired
   private MovieRepository movieRepository;

    //add movie
    public String addMovie(movieDTO movieDTO){

        Movie movie=new Movie();
        movie.setMovieName(movieDTO.getMovieName());
        movie.setMovieId(movieDTO.getMovieId());
        movie.setLanguage(movieDTO.getLanguage());
        movie.setDuration(movieDTO.getDuration());
        movie.setReleaseDate(movieDTO.getReleaseDate());
        movie.setRating(movieDTO.getRating());
        movieRepository.save(movie);
        return "Movie added successfully with movieId"+movie.getMovieId();
    }

         //update existing movie
         public String updateMovie(updateMovieRequest updateMovieRequest){

            Movie movie=movieRepository.findMovieByMovieName(updateMovieRequest.getMovieName());
            movie.setRating(updateMovieRequest.getNewRating());
            movie.setLanguage(updateMovieRequest.getNewlanguage());

            movieRepository.save(movie);
            return "Movie updated successfully with movieId"+movie.getMovieId();
         }

         //find movie
         public Optional<Movie> findMovie(movieDTO movieDTO){

            Optional<Movie> movie=movieRepository.findById(movieDTO.getMovieId());
            return movie;
         }

         //find all movies
         public List<Movie> findAllMovies(){

            return movieRepository.findAll();
         }

         //delete movie
       public String deleteMovie(movieDTO movieDTO){

        movieRepository.deleteById(movieDTO.getMovieId());
        return "Movie deleted successfully with movieId"+movieDTO.getMovieId();
    }

    // i want to create a method when i put movie name they return all the theater name where this movie is playing

}
