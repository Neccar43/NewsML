package com.alkan.news.repository;

import com.alkan.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer>{

    News findNewsById(int newsId);
    List<News> findAll();

}
