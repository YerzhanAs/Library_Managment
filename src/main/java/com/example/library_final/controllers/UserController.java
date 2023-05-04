package com.example.library_final.controllers;

import com.example.library_final.entities.Books;
import com.example.library_final.entities.Users;
import com.example.library_final.services.BookService;
import com.example.library_final.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    private BookService bookService;

    @Autowired
    public UserController(UserService userService, BookService bookService) {
        this.userService = userService;
        this.bookService = bookService;
    }

    @GetMapping(value = "/")
    public String main(Model model){
        return "mainpage";
    }

    @GetMapping(value = "/users")
    public String allUsers(Model model){
        List<Users> users=userService.findAllUsers();

        model.addAttribute("allusers", users);

        return "users";
    }



    @GetMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("currentUser", userService.currentUser());
        return "login";
    }

    @GetMapping(value="/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model){
        model.addAttribute("currentUser", userService.currentUser());
        return "profile";
    }

    @GetMapping(value = "/register")
    public String register(Model model){

        model.addAttribute("currentUser", userService.currentUser());

        return "register";
    }

    @PostMapping(value="/register")
    public String toRegister(@RequestParam(name="user_email") String email,
                             @RequestParam(name="user_password") String password,
                             @RequestParam(name="re_user_password") String rePassword,
                             @RequestParam(name="user_full_name") String fullName){

        System.out.println(email+" "+password+" "+fullName);
        if(password.equals(rePassword)){

            System.out.println("Ok");
            Users newUser=new Users();

            newUser.setFullName(fullName);
            newUser.setPassword(password);
            newUser.setEmail(email);

            if(userService.createUser(newUser)!=null){
                return "redirect:/register?success";
            }

        }

        return "redirect:/register?error";


    }

}
