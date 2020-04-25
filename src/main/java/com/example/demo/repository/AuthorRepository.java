package com.example.demo.repository;

import com.example.demo.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * AuthorRepository
 */
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findByUsername(String username);
    @Transactional(readOnly = true)
    Page<Author> findByUsernameContaining(String search, Pageable pageable);
}
