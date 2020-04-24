package com.example.demo.service;

import com.example.demo.dto.request.AuthorPasswordRequestDto;
import com.example.demo.dto.request.AuthorRequestDto;
import com.example.demo.dto.response.AuthorResponseDTo;
import com.example.demo.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * AuthorService
 */
public interface AuthorService {
    Page<AuthorResponseDTo> findAll(Pageable pageable);
    Page<AuthorResponseDTo> findByNameContaining(Pageable pageable,String name);
    Author changePassword (Integer id, AuthorPasswordRequestDto authorPasswordRequestDto);
    AuthorResponseDTo findById(Integer id);
    Author save(AuthorRequestDto request);
    Author update(Integer id, AuthorRequestDto request);
    void deleteById(Integer id);
}