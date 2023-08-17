package com.example.User.DB.UserController;


import com.example.User.DB.CorsConfig;
import com.example.User.DB.Entity.User;
import com.example.User.DB.Exception.UserNotFound;
import com.example.User.DB.LoginRequest;
import com.example.User.DB.UserRepository.Repository;
import com.example.User.DB.UserService.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class Controller {
    @Autowired
    Service service;
    @Autowired
    private CorsConfig corsConfig;
    @Autowired
    Repository repo;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/allUsers")
    public ResponseEntity<?> getAllUsers() throws UserNotFound {
        if (repo.findAll().isEmpty()) {
            throw new UserNotFound("User is not found");
        } else {

            return new ResponseEntity<>(service.getAllUsers(), HttpStatus.OK);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) throws UserNotFound {
        String userName = loginRequest.getUserName();
        String password = loginRequest.getPassword();

        boolean isAuthenticated = service.authenticateUser(userName, password);

        if (isAuthenticated) {
            // Return a success response
            return ResponseEntity.ok("Login successful");
        } else {
            throw new UserNotFound("Invalid credentials");
        }
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addUser")
    public ResponseEntity<?> saveUser(@RequestBody User user) throws UserNotFound {
        Optional<User> opt = repo.findByUsername(user.getUserName());
        if (opt.isPresent()) {
            throw new UserNotFound("User is already existed");
        } else {
            service.saveUser(user);
            return new ResponseEntity<>("user is created", HttpStatus.CREATED);
        }

    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable Integer id) throws UserNotFound {
        Optional<User> userOptional = repo.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new UserNotFound("User not found");
        }
    }
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> UpdateUser(@RequestBody User user) throws UserNotFound {
        if (repo.existsById(user.getId())) {
            service.updateUser(user);
            return new ResponseEntity<>("id" + user.getId() + "is updated successfully", HttpStatus.ACCEPTED);
        } else {

            throw new UserNotFound("id" + user.getId() + "is not updated");
        }
    }
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) throws UserNotFound {
        Optional<User> opt = repo.findById(id);
        if (opt.isPresent()) {
            service.deleteUser(id);
            return new ResponseEntity<>("id" + "deleted successfully", HttpStatus.OK);
        } else {
            throw new UserNotFound("id" + "is not found");
        }
    }

}
