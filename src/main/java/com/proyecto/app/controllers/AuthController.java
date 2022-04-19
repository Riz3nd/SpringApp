package com.proyecto.app.controllers;

import com.proyecto.app.entity.User;
import com.proyecto.app.repository.UserRepository;
import com.proyecto.app.utils.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    private User user;
    private Message message;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "api/auth/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody User userReq){
        try{
            message = new Message();
            user = userRepository.findByEmail(user.getEmail());
            if(passwordEncoder.matches(userReq.getPassword(), user.getPassword())) {
                return message.viewMessage(HttpStatus.OK, "Login", "The user logged in");
            }
            return message.viewMessage(HttpStatus.UNAUTHORIZED,"UNAUTHORIZED","invalid username or password");
            }catch (Exception e){
            return message.viewMessage(HttpStatus.INTERNAL_SERVER_ERROR, "error", "An error ocurred! "+e.toString());
        }
    }
}
