package com.example.bmsbookticket.repositories;


import com.example.bmsbookticket.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> getUsersByEmail(String email);
}
