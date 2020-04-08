package com.example.demo.controller;

import com.example.demo.config.ResponseBaseConfiguration;
import com.example.demo.model.Author;
import com.example.demo.dto.AuthorDto;
import com.example.demo.dto.AuthorPasswordDto;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.service.AuthorService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthorController
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @GetMapping()
    public ResponseEntity<ResponseBaseConfiguration> getAuthor(@RequestParam(value = "username", required=false)  String username) {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        if (username != null) {
            response.setData(authorRepository.findByUsername(username));
        }else {
            response.setData(authorRepository.findAll());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBaseConfiguration> getAuthorById(@PathVariable Integer id) throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author id " + id + " NotFound"));

        response.setData(author);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseBaseConfiguration> postAuthor(@RequestBody Author author) {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        try {
            response.setData(authorService.save(author));

        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBaseConfiguration> putAuthor(@PathVariable Integer id, @RequestBody AuthorDto authorDto) throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author id " + id + " NotFound"));

        try {
            author.setFirst_name(authorDto.getFirst_name());
            author.setLast_name(authorDto.getLast_name());
            author.setUsername(authorDto.getUsername());

            response.setData(authorService.update(id, author));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<ResponseBaseConfiguration> putPaswordAuthor(@PathVariable Integer id, @RequestBody AuthorPasswordDto authorPasswordDto) throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author id " + id + " NotFound"));

        try {
            author.setPassword(authorPasswordDto.getPassword());

            response.setData(authorService.changePassword(id, author));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<ResponseBaseConfiguration> deleteAuthor(@RequestBody Author author) {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        try {
            authorRepository.deleteById(author.getId());
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}