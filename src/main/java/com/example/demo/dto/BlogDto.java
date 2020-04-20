package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BlogDto {
    @Column(length = 150, nullable = false)
    @Size(min = 3, max = 150)
    @NotBlank
    private String title;

    @Column(length = 1000, nullable = false, columnDefinition = "TEXT")
    @Size(min = 10, max = 1000)
    @NotBlank
    private String content;

}
