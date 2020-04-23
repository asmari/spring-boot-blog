package com.example.demo.exception;

import com.example.demo.config.ResponseBaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ResponseBaseConfiguration> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        if(e.getResource() == null && e.getFieldName() == null && e.getFieldValue() == null){
            ResponseBaseConfiguration errorResponse = ResponseBaseConfiguration.error("400", "Data not found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
        ResponseBaseConfiguration errorResponse = ResponseBaseConfiguration.error("400", e.getResource() + " with " + e.getFieldName() + " " + e.getFieldValue() + " not found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}