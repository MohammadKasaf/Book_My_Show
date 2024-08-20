package com.BookMyShow.services;

import com.BookMyShow.dto.MovieDTO;
import com.BookMyShow.models.Movie;
import com.BookMyShow.repositories.MovieRepository;
import com.BookMyShow.requests.UpdateMovieRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
   private MovieRepository movieRepository;

    //add movie
    public String addMovie(MovieDTO movieDTO){

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
         public String updateMovie(UpdateMovieRequest updateMovieRequest){

            Movie movie=movieRepository.findMovieByMovieName(updateMovieRequest.getMovieName());
            movie.setRating(updateMovieRequest.getNewRating());
            movie.setLanguage(updateMovieRequest.getNewlanguage());

            movieRepository.save(movie);
            return "Movie updated successfully with movieId"+movie.getMovieId();
         }

         //find movie
         public Optional<Movie> findMovieById(Integer movieId){

            Optional<Movie> movie=movieRepository.findById(movieId);
            return movie;
         }

         //find all movies
         public List<Movie> findAllMovies(){

            return movieRepository.findAll();
         }

         //delete movie
       public String deleteMovieById(Integer movieId){

        movieRepository.deleteById(movieId);
        return "Movie deleted successfully with movieId"+movieId;
    }

    // method when i put movie name they return all the theater name where this movie is playing
    public List<String> findTheaterNameByMovieName(String movieName) {

        return movieRepository.findTheaterNamesByMovieName(movieName);

    }
}
