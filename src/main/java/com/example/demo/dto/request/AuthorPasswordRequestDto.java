package com.example.demo.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class AuthorPasswordRequestDto {

    @NotNull
    @NotBlank
    private String password;
}
