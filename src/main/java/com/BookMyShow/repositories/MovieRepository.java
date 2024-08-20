package com.BookMyShow.repositories;

import com.BookMyShow.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    Movie findMovieByMovieName(String movieName);

    @Query("SELECT t.theaterName FROM Theater t JOIN t.movies m WHERE m.movieName = :movieName")
    List<String> findTheaterNamesByMovieName(@Param("movieName") String movieName);}
