package com.BookMyShow.services;

import com.BookMyShow.dto.UserDTO;
import com.BookMyShow.models.User;
import com.BookMyShow.repositories.UserRepository;
import com.BookMyShow.requests.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.core.userdetails.User.builder;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public String addUser(AddUserRequest userRequest) {
        // Check if userRequest is null
        if (userRequest == null) {
            throw new IllegalArgumentException("User request data cannot be null");
        }

        // Validate required fields
        if (userRequest.getUserName() == null || userRequest.getUserName().isEmpty()) {
            throw new IllegalArgumentException("User name is required");
        }
        if (userRequest.getEmail() == null || userRequest.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userRequest.getMobileNumber() == null || userRequest.getMobileNumber().isEmpty()) {
            throw new IllegalArgumentException("Mobile number is required");
        }
        // Assuming age is a required field and should be a positive number
        if (userRequest.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be a positive number");
        }

        // Create user entity
        User user = User.builder()
                .username(userRequest.getUserName())
                .password(userRequest.getPassword())
                .age(userRequest.getAge())
                .emailId(userRequest.getEmail())
                .mobileNumber(userRequest.getMobileNumber())
                .build();

        // Prepare and send email
        if (userRequest.getEmail() != null) {
            try {
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                simpleMailMessage.setTo(userRequest.getEmail());
                simpleMailMessage.setFrom("kaashifchishti611@gmail.com");
                simpleMailMessage.setSubject("User added successfully");
                simpleMailMessage.setText("Welcome " + user.getUsername() + " to BookMyShow");
                javaMailSender.send(simpleMailMessage);
            } catch (Exception e) {
                // Handle email sending failure
                throw new RuntimeException("Failed to send email", e);
            }
        }

        // Save user to repository
        user = userRepository.save(user);

        return "User added successfully with user id " + user.getUserId();
    }


    public String deleteUserByNameAndId(UserDTO userDTO){

        User user=userRepository.findById(userDTO.getId()).get();
        if(user.getUsername()==userDTO.getUsername()){

            userRepository.delete(user);

            SimpleMailMessage message=new SimpleMailMessage();
            message.setTo(user.getEmailId());
            message.setFrom("springboot471@gmail.com");
            message.setSubject("User deleted successfully");
            message.setText("Your account has been deleted successfully");
            javaMailSender.send(message);
            return "User deleted successfully";
        }

        return "User not found";
    }

    public String deleteAllUser(){

        userRepository.deleteAll();
        return "all users are deleted";
    }

    public User findUser(String name){

        Optional<User> user=userRepository.findByUsername(name);
        if(user.isPresent()) {
            return user.get();
        }else{
            throw new UsernameNotFoundException("User not found with username: " + name);
        }
    }

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{

        Optional<User> user=userRepository.findByUsername(username);
        if(user.isPresent()){

            var userObj=user.get();
            return   builder().username(userObj.getUsername())
                    .password(userObj.getPassword())
                    .roles(userObj.getRole())
                    .build();
        }
        else{

            throw new UsernameNotFoundException(username);
        }
    }

}
