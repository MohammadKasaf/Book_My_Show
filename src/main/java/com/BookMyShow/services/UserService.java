package com.BookMyShow.services;

import com.BookMyShow.models.User;
import com.BookMyShow.repositories.UserRepository;
import com.BookMyShow.requestDto.AddUserRequest;
import com.BookMyShow.responseDto.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.builder;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public String addUser(AddUserRequest userRequest) {

        SimpleMailMessage message=new SimpleMailMessage();

        // Create user entity
        User user = User.builder()
                .username(userRequest.getUserName())
                .role(userRequest.getRole())
                .password(userRequest.getPassword())
                .age(userRequest.getAge())
                .emailId(userRequest.getEmail())
                .mobileNumber(userRequest.getMobileNumber())
                .build();

        //send email
        message.setFrom("kaashifchishti611@gmail.com");
        message.setTo(userRequest.getEmail());
        message.setSubject("user account created successfully");
        message.setText("congratulations "+ userRequest.getUserName() + "your account is successfully created at bookmyshow app");

        javaMailSender.send(message);


        // Save user to repository after email (or if no email is needed)
        user = userRepository.save(user);

        // Return success message
        return "User added successfully with user id " + user.getUserId();
    }


    public String deleteUserByNameAndId(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user is not found with this id: " + userId));

        userRepository.delete(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmailId());
        message.setFrom("springboot471@gmail.com");
        message.setSubject("User deleted successfully");
        message.setText("Your account has been deleted successfully");
        javaMailSender.send(message);

        return "User deleted successfully";


    }

    public String deleteAllUser() {

        userRepository.deleteAll();
        return "all users are deleted";
    }

    public GetUserResponse findUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("user is not found with this id: " + userId));

        GetUserResponse userResponse = GetUserResponse.builder()
                .username(user.getUsername())
                .age(user.getAge())
                .role(user.getRole())
                .emailId(user.getEmailId())
                .mobileNumber(user.getMobileNumber())
                .build();

        return userResponse;
    }

    public List<GetUserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<GetUserResponse> responseList = new ArrayList<>();

        for (User user : users) {
            GetUserResponse response = new GetUserResponse();
            response.setUsername(user.getUsername());
            response.setEmailId(user.getEmailId());
            response.setMobileNumber(user.getMobileNumber());
            response.setAge(user.getAge());
            response.setRole(user.getRole());
            responseList.add(response);
        }

        return responseList;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {

            var userObj = user.get();
            return builder().username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole())
                    .build();
        } else {

            throw new UsernameNotFoundException(username);
        }
    }

}
