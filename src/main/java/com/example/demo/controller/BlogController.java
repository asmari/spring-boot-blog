package com.example.demo.controller;

import com.example.demo.config.ResponseBaseConfiguration;
import com.example.demo.dto.BlogDto;
import com.example.demo.model.Author;
import com.example.demo.model.Blog;
import com.example.demo.model.Category;
import com.example.demo.model.Tags;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BlogRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TagsRepository;
import com.example.demo.service.BlogService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private CategoryRepository CategoryRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private BlogService blogService;

    public BlogController() {
    }

    @GetMapping()
    public ResponseEntity<ResponseBaseConfiguration> getBlog() {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        response.setData(blogRepository.findAll());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseBaseConfiguration> getBlogById(@PathVariable Integer id) throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Tags id " + id + " NotFound"));

        response.setData(blog);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ResponseBaseConfiguration> postBlog(@RequestBody Blog blog) throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        Author author = authorRepository.findById(blog.getAuthor_id()).orElseThrow(() -> new NotFoundException("Author id " + blog.getAuthor_id() + " NotFound"));
        Category Category = CategoryRepository.findById(blog.getCategories_id()).orElseThrow(() -> new NotFoundException("Category id " + blog.getCategories_id() + " NotFound"));

        List<Integer> tagtag = blog.getTags_id();
        System.out.println(tagtag);
        ArrayList<Tags> tags = new ArrayList<Tags>();

        for (Integer tag : tagtag) {
            Tags val = tagsRepository.findById(tag).orElseThrow(() -> new NotFoundException("Tags id " + tag + " NotFound"));
            tags.add(val);
        }

        blog.setAuthor(author);
        blog.setCategory(Category);
        blog.setTag(tags);

        try {
            response.setData(blogRepository.save(blog));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBaseConfiguration> putBlog(@PathVariable Integer id, @RequestBody BlogDto blogDto) throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        Blog blog = blogRepository.findById(id).orElseThrow(() -> new NotFoundException("Blog id " + id + " NotFound"));

        try {
            blog.setTitle(blogDto.getTitle());
            blog.setContent(blogDto.getContent());

            response.setData(blogService.update(id, blog));
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBaseConfiguration> deleteBlog(@PathVariable Integer id) {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();

        try {
            blogRepository.deleteById(id);
        } catch (Exception e) {
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}