package com.example.library_final.services.impl;


import com.example.library_final.entities.Roles;
import com.example.library_final.entities.Users;
import com.example.library_final.repositories.RoleRepository;
import com.example.library_final.repositories.UserRepository;
import com.example.library_final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Users myUser =userRepository.findByEmail(s);

        if(myUser!=null){

            User secUser=new User(myUser.getEmail(), myUser.getPassword(), myUser.getRoles());
            return secUser;
        }


        throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public Users getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Users createUser(Users user) {
        Users checkUser=userRepository.findByEmail(user.getEmail());
        if(checkUser==null){
            Roles role=roleRepository.findByRole("ROLE_USER");
            if(role!=null){
                ArrayList<Roles> roles=new ArrayList<>();
                roles.add(role);
                user.setRoles(roles);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
            }
        }
        return null;
    }

    @Override
    public Users saveUser(Users user) {
        return userRepository.save(user);
    }


    @Override
    public Users currentUser() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser=(User)authentication.getPrincipal();
            Users myUser=userRepository.findByEmail(secUser.getUsername());
            return myUser;
        }

        return null;
    }

    @Override
    public List<Users> findAllUsers() {
        return userRepository.findAll();
    }
}
