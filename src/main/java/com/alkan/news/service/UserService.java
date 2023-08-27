package com.alkan.news.service;

import com.alkan.news.dto.UserDto;
import com.alkan.news.entity.News;
import com.alkan.news.entity.User;
import com.alkan.news.repository.NewsRepository;
import com.alkan.news.repository.UserRepository;
import com.alkan.news.request.LoginRequest;
import com.alkan.news.request.SignUpRequest;
import com.alkan.news.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    UserRepository userRepository;
    NewsRepository newsRepository;
    public UserService(UserRepository userRepository, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    public UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.id = user.getId();
        userDto.name = user.getName();
        userDto.surname = user.getSurname();
        userDto.email = user.getEmail();
        userDto.password = user.getPassword();
        userDto.active = user.isActive();
        return userDto;
    }

    public User toEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.id);
        user.setName(userDto.name);
        user.setSurname(userDto.surname);
        user.setEmail(userDto.email);
        user.setPassword(userDto.password);
        user.setActive(userDto.active);
        return user;
    }

    public String signUp(SignUpRequest signUpRequest){
        String email = signUpRequest.getEmail();
        if (userRepository.findByEmail(email) != null){
            return "User already exists";
        }
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setSurname(signUpRequest.getSurname());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        userRepository.save(user);
        return "Successfully registered";
    }

    public String login(LoginRequest loginRequest){
        User user = userRepository.findByEmail(loginRequest.getEmail());
        if(user.getPassword() == loginRequest.getPassword()){
            return "Successfully logged in";
        }
        return "Wrong email or password";
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean addFavorite(int newsId, int userId){
        User user = userRepository.findById(userId).get();
        user.getFavoriteList().add(newsRepository.findNewsById(newsId));
        newsRepository.findNewsById(newsId).setFavorite(true);
        return true;
    }

    public boolean deleteFavorite(int userId, int newsId) {
        User user = userRepository.findById(userId).get();
        user.getFavoriteList().remove(newsRepository.findNewsById(newsId));
        newsRepository.findNewsById(newsId).setFavorite(false);
        return false;
    }

    public List<News> getFavorites(int userId){
        return userRepository.findById(userId).get().getFavoriteList();
    }

    public List<News> getFeed(){
        return newsRepository.findAll();
    }
}
