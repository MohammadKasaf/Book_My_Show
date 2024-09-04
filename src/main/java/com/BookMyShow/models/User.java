package com.BookMyShow.models;

import com.BookMyShow.models.Ticket;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true)
    @NotBlank(message = "username is mandatory")
    private String username;
    private String password;
    private String role;
    private Integer age;
    private String emailId;
    private String mobileNumber;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Ticket> ticketList;


}
