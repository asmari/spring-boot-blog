package com.example.demo.repositories;

import com.example.demo.entities.model.Category;
import com.example.demo.entities.model.Tags;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagsRepository extends JpaRepository<Tags,Integer> {
}
