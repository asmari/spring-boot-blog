package com.example.demo.repositories;

import com.example.demo.entities.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorRepository
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByUsername(String username);


}
