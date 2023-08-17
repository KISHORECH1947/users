package com.example.User.DB.UserRepository;

import com.example.User.DB.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface Repository extends JpaRepository<User,Integer> {
@Query("SELECT u FROM User u  where u.userName=:username")
   public Optional<User>findByUsername(@Param("username") String username);
}
