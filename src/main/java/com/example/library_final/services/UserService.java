package com.example.library_final.services;

import com.example.library_final.entities.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    Users getUserByEmail(String email);
    Users createUser(Users user);

    Users saveUser(Users users);

    List<Users> findAllUsers();

    Users currentUser();



}
