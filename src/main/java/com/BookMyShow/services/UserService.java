package com.BookMyShow.services;

import com.BookMyShow.dto.UserDTO;
import com.BookMyShow.models.User;
import com.BookMyShow.repositories.UserRepository;
import com.BookMyShow.requests.AddUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public String addUser(AddUserRequest userRequest){

        User user=User.builder().name(userRequest.getUserName())
                .age(userRequest.getAge()).emailId(userRequest.getEmail())
                .mobileNumber(userRequest.getMobileNumber()).build();

        //send email to user
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(userRequest.getEmail());
        simpleMailMessage.setFrom("springboot471@gmail.com");
        simpleMailMessage.setSubject("User added successfully");
        simpleMailMessage.setText("Welcome "+user.getName()+" to BookMyShow ");
        javaMailSender.send(simpleMailMessage);


        user=userRepository.save(user);
        return "User added successfully with user id "+user.getUserId();
    }

    public String deleteUserByNameAndId(UserDTO userDTO){

        User user=userRepository.findById(userDTO.getId()).get();
        if(user.getName()==userDTO.getName()){

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

    public List<User> getAllUsers(){

        return userRepository.findAll();
    }

}
