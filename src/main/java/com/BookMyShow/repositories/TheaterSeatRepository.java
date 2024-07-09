package com.BookMyShow.repositories;

import com.BookMyShow.models.TheaterSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheaterSeatRepository extends JpaRepository<TheaterSeat,Integer> {


}
