package com.example.demo.repository;

import com.example.demo.model.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;


public interface TagsRepository extends JpaRepository<Tags,Integer> {
    Tags findByName(String name);

    @Transactional(readOnly = true)
    Page<Tags> findByNameContaining(String search, Pageable pageable);
}
