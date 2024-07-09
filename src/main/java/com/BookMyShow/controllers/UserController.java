package com.BookMyShow.controllers;

import com.BookMyShow.dto.userDTO;
import com.BookMyShow.requests.AddUserRequest;
import com.BookMyShow.services.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class userController {

    @Autowired
    private userService userService;

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody AddUserRequest addUserRequest) {
        try {
            String response = userService.addUser(addUserRequest);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<?> deleteUser(@RequestBody userDTO userDTO){
        try {
            String response = userService.deleteUserByNameAndId(userDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
