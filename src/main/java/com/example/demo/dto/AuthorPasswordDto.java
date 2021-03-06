package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

/**
 * AuthorPasswordDto
 */
@Getter
@Setter
public class AuthorPasswordDto {

    @Column(length = 150, nullable = false)
    private String password;
}