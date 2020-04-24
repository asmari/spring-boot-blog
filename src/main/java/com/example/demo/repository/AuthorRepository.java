package com.example.demo.repository;

import com.example.demo.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * AuthorRepository
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByUsername(String username);

    Page<Author> findByUsernameContainingOrLast_nameContainingOrLast_nameContaining(String search, Pageable pageable);
}
