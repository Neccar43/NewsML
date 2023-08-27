package com.alkan.news.controller;

import com.alkan.news.entity.User;
import com.alkan.news.request.LoginRequest;
import com.alkan.news.request.SignUpRequest;
import com.alkan.news.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/signIn")
    public String signUp(@RequestBody SignUpRequest signUpRequest) {
        return userService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/add_favorite")
    public boolean addToFavorite(@RequestParam int userId, @RequestParam int newsId){
        return userService.addFavorite(userId, newsId);
    }

    @DeleteMapping("/delete_favorite")
    public boolean deleteFromFavorite(@RequestParam int userId, @RequestParam int newsId){
        return userService.deleteFavorite(userId, newsId);
    }


}
