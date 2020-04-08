package com.example.demo.config;


import lombok.Getter;
import lombok.Setter;

/**
 * ResponseFailConfiguration
 */
@Getter
@Setter
public class ResponseFailConfiguration {

    private Boolean status = false;
    private Integer code = 500;
    private String message = "Internal Server Error";

    @Override
    public String toString() {
        return "{\"status\": " + status + ", \"code\": " + code + ", \"message\": \"" + message  + "\"}";
    }
}