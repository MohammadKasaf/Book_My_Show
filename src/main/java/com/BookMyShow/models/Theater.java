package com.BookMyShow.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="theaters")
@NoArgsConstructor
@AllArgsConstructor
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theaterId;

    private String name;

    private String address;

    private Integer numberOfScreens;

    @OneToMany(mappedBy = "Theater", cascade = CascadeType.ALL)
    private List<Show> showList = new ArrayList<>();

    //Bidirectional mapping in the parent to keep a record of the child
    @OneToMany(mappedBy = "Theater",cascade = CascadeType.ALL)
    private List<TheaterSeat> theaterSeatList = new ArrayList<>();

}
