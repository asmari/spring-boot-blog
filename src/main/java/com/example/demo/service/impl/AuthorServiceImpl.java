package com.example.demo.service.impl;

import com.example.demo.dto.request.AuthorPasswordRequestDto;
import com.example.demo.dto.request.AuthorRequestDto;
import com.example.demo.dto.response.AuthorResponseDTo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    private static final String RESOURCE = "Author";
    private static final String FIELD = "id";

    @Override
    public Page<AuthorResponseDTo> findAll(Pageable pageable) {
        try {
            return authorRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<AuthorResponseDTo> findByNameContaining(Pageable pageable, String search) {
        try {
            return authorRepository.findByUsernameContaining(search, pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author changePassword(Integer id, AuthorPasswordRequestDto authorPasswordRequestDto) {
        return null;
    }

    @Override
    public AuthorResponseDTo findById(Integer id) {
        try {
            Author author = authorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));

            return fromEntity(author);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author save(AuthorRequestDto request) {
        try {
            Author author = new Author();
            BeanUtils.copyProperties(request, author);
            author.setPassword(userPasswordEncoder.encode(author.getPassword()));
            return authorRepository.save(author);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author update(Integer id, AuthorRequestDto request) {
        try {
            Author author = authorRepository.findById(id).orElseThrow(
                    ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );

            BeanUtils.copyProperties(request, author);
            authorRepository.save(author);
            return author;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Author findByUsername(String username) {
        return authorRepository.findByUsername(username);
    }

    @Override
    public void deleteById(Integer id) {
        try {
            authorRepository.findById(id).orElseThrow(
                    ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );
            authorRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    private AuthorResponseDTo fromEntity(Author author) {
        AuthorResponseDTo response = new AuthorResponseDTo();
        BeanUtils.copyProperties(author, response);
        return response;
    }
}
