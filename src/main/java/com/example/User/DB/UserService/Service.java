package com.example.User.DB.UserService;

import com.example.User.DB.Entity.User;

import java.util.List;

public interface Service {
    public List<User> getAllUsers();
    public User saveUser(User user);
    public void updateUser(User user);
    public void deleteUser(int id);
    public  User findUser(int id);
    boolean authenticateUser(String userName, String password);

}
