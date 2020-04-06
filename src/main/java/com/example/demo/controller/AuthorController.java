package com.example.demo.controller;

import com.example.demo.entities.ResponseBase;
import com.example.demo.entities.model.Author;
import com.example.demo.entities.request.AuthorDto;
import com.example.demo.entities.request.AuthorPasswordDto;
import com.example.demo.repositories.AuthorRepository;
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
    public ResponseEntity<ResponseBase> getAuthor(@RequestParam(value = "username", required=false)  String username) {
        ResponseBase response = new ResponseBase<>();

        if (username != null) {
            response.setData(authorRepository.findByUsername(username));
        }else {
            response.setData(authorRepository.findAll());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBase> getAuthorById(@PathVariable Integer id) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

        Author author = authorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author id " + id + " NotFound"));

        response.setData(author);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseBase> postAuthor(@RequestBody Author author) {
        ResponseBase response = new ResponseBase<>();

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
    public ResponseEntity<ResponseBase> putAuthor(@PathVariable Integer id, @RequestBody AuthorDto authorDto) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

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
    public ResponseEntity<ResponseBase> putPaswordAuthor(@PathVariable Integer id, @RequestBody AuthorPasswordDto authorPasswordDto) throws NotFoundException {
        ResponseBase response = new ResponseBase<>();

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
    public ResponseEntity<ResponseBase> deleteAuthor(@RequestBody Author author) {
        ResponseBase response = new ResponseBase<>();

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