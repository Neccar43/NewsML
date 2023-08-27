package com.alkan.news.repository;

import com.alkan.news.entity.News;
import com.alkan.news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByEmail(String email);
}
