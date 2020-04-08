package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;

/**
 * ResponseBaseConfiguration
 */
@Getter
@Setter
public class ResponseBaseConfiguration<Any> {
    private Boolean status = true;
    private Integer code = 200;
    private String message = "Success";
    private Any data;

    @Override
    public String toString() {
        return "{\"status\": " + status + ", \"code\": " + code + ", \"message\": \"" + message + "\", \"data\": " + data + "}";
    }
}
