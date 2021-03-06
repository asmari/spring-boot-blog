package com.example.demo.repository;

import com.example.demo.model.Author;
import com.example.demo.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags,Integer> {
    Tags findByName(String name);
}
