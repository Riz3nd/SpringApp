package com.proyecto.app.controllers;

import com.proyecto.app.entity.User;
import com.proyecto.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    private ResponseEntity responseEntity;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable Long id){
        Optional<User> foundUser = userRepository.findById(id);
        if(foundUser.isPresent()) return  ResponseEntity.ok(foundUser.get());
        Map<String, String> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error","Not Found");
        errorResponse.put("message","User not found");
        errorResponse.put("status", HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User user){
        Map<String, String> response = new LinkedHashMap<>();
        try{
            userRepository.save(user);
            response.put("succes","Registered user");
            response.put("message","Registered user success!");
            response.put("status", HttpStatus.OK.toString());
            responseEntity = new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error","Rejected");
            response.put("message","An error occurred while registering the user");
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
            responseEntity = new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<User> listUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> editUser(@RequestBody User newUser,@PathVariable Long id){
        Map<String, String> response = new LinkedHashMap<>();
        try{
            User user = userRepository.findById(id).get();
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setEmail(newUser.getEmail());
            user.setPassword(newUser.getPassword());
            userRepository.save(user);
            response.put("succes","Accepted");
            response.put("message","User delete success!");
            response.put("status", HttpStatus.OK.toString());
            responseEntity = new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error","Rejected");
            response.put("message","User not found, impossible to modify");
            response.put("status", HttpStatus.NOT_FOUND.toString());
            responseEntity = new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        Map<String, String> response = new LinkedHashMap<>();
        try{
            User user = userRepository.findById(id).get();
            userRepository.delete(user);
            response.put("succes","Accepted");
            response.put("message","User delete success!");
            response.put("status", HttpStatus.OK.toString());
            responseEntity = new ResponseEntity(response, HttpStatus.OK);
        }catch (Exception e){
            response.put("error","Rejected");
            response.put("message","User not found, impossible to delete");
            response.put("status", HttpStatus.NOT_FOUND.toString());
            responseEntity = new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

}
