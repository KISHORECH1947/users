package com.example.User.DB.UserServiceImpl;

import com.example.User.DB.Entity.User;
import com.example.User.DB.UserRepository.Repository;
import com.example.User.DB.UserService.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service {

    @Autowired
    Repository Repo;
    @Override
    public List<User> getAllUsers() {

        return Repo.findAll();
    }

    @Override
    public User saveUser(User user) {

        return Repo.save(user);
    }

    @Override
    public void updateUser(User user) {
        Repo.save(user);

    }

    @Override
    public void deleteUser(int id) {
        Repo.deleteById(id);

    }

    @Override
    public User findUser(int id) {
        return Repo.findById(id).get();
    }

    public boolean authenticateUser(String email, String password) {
        Optional<User> userOptional = Repo.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Implement your logic for password validation here
            return user.getPassword().equals(password);
        } else {
            return false;
        }
    }



}