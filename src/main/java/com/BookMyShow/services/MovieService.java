package com.BookMyShow.services;

import com.BookMyShow.models.Show;
import com.BookMyShow.models.Theater;
import com.BookMyShow.repositories.TheaterRepository;
import com.BookMyShow.requestDto.AddMovieRequest;
import com.BookMyShow.models.Movie;
import com.BookMyShow.repositories.MovieRepository;
import com.BookMyShow.requestDto.UpdateMovieRequest;
import com.BookMyShow.responseDto.GetMovieResponse;
import com.BookMyShow.responseDto.GetTheaterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    @Autowired
   private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    //add movie
    public String addMovie(AddMovieRequest movieRequest){

        Movie movie=new Movie();
        movie.setMovieName(movieRequest.getMovieName());
        movie.setLanguage(movieRequest.getLanguage());
        movie.setDuration(movieRequest.getDuration());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setRating(movieRequest.getRating());
        movieRepository.save(movie);
        return "Movie added successfully with movieId" +movie.getMovieId();
    }

         //update existing movie
         public String updateMovie(UpdateMovieRequest updateMovieRequest){

           Movie movie=movieRepository.findById(updateMovieRequest.getMovieId())
                           .orElseThrow(()->new RuntimeException("movie is not found with id: "+ updateMovieRequest.getMovieId()));
            movie.setRating(updateMovieRequest.getNewRating());
            movie.setLanguage(updateMovieRequest.getNewlanguage());

            movieRepository.save(movie);
            return "Movie updated successfully with movieId"+movie.getMovieId();
         }

         //find movie
         public GetMovieResponse findMovieById(Long movieId){

            Movie movie=movieRepository.findById(movieId)
                    .orElseThrow(()->new RuntimeException("movie is not found with this id: " + movieId));

            GetMovieResponse responseMovie=new GetMovieResponse();
            responseMovie.setMovieName(movie.getMovieName());
            responseMovie.setLanguage(movie.getLanguage());
            responseMovie.setDuration(movie.getDuration());
            responseMovie.setReleaseDate(movie.getReleaseDate());
            responseMovie.setRating(movie.getRating());

            return responseMovie;

         }

         //find all movies
         public List<GetMovieResponse> findAllMovies() {
             // Fetch all movies from the repository
             List<Movie> movies = movieRepository.findAll();

             // Create a list to hold the GetMovieResponse objects
             List<GetMovieResponse> responses = new ArrayList<>();

             // Convert each Movie entity to GetMovieResponse and add to the list
             for (Movie movie : movies) {
                 responses.add(new GetMovieResponse(
                         movie.getMovieName(),
                         movie.getDuration(),
                         movie.getReleaseDate(),
                         movie.getLanguage(),
                         movie.getRating()
                 ));
             }

             return responses;
         }


    //delete movie
       public String deleteMovieById(Long movieId){

           Movie movie=movieRepository.findById(movieId)
                   .orElseThrow(()->new RuntimeException("movie is not found with this id: " + movieId));

           movieRepository.delete(movie);
           return "Movie deleted successfully with movieId"+movieId;
    }

    public List<GetTheaterResponse> findTheaterByMovieId(Long movieid) {

           List<Theater> theaterList=theaterRepository.findAll();
           List<GetTheaterResponse> responseList=new ArrayList<>();

           for(Theater theater:theaterList){
               for(Show show:theater.getShowList()){

                   Movie movie=show.getMovie();
                   if(movie.getMovieId()==movieid){
                       GetTheaterResponse theaterResponse=new GetTheaterResponse();
                       theaterResponse.setTheaterName(theater.getTheaterName());
                       theaterResponse.setAddress(theater.getAddress());
                       theaterResponse.setNumberOfScreens(theater.getNumberOfScreens());
                       responseList.add(theaterResponse);
                   }
               }
           }

          return responseList;
    }
}
