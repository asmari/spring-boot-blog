package com.example.demo.controller;

import com.example.demo.config.CustomPageConfiguration;
import com.example.demo.config.CustomPageableConfiguration;
import com.example.demo.config.ResponseBaseConfiguration;
import com.example.demo.dto.request.AuthorRequestDto;
import com.example.demo.dto.request.TagsRequestDto;
import com.example.demo.dto.response.AuthorResponseDTo;
import com.example.demo.service.AuthorService;
import com.example.demo.util.PageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
@Slf4j
public class AuthorController{
    @Autowired
    private AuthorService authorService;

    @GetMapping()
    public ResponseBaseConfiguration<CustomPageConfiguration<AuthorResponseDTo>> listTags(
            CustomPageableConfiguration pageable, @RequestParam(required = false) String search, HttpServletRequest request
    ) {
        Page<AuthorResponseDTo> author;
        log.info(String.valueOf(search));
        if (search == null || search.isEmpty()) {
            author = authorService.findAll(CustomPageableConfiguration.convertToPageable(pageable));
        } else {
            author = authorService.findByNameContaining(CustomPageableConfiguration.convertToPageable(pageable),search);
        }

        PageConverter<AuthorResponseDTo> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

        String searchParam = "";

        if(search != null){
            searchParam += "&search="+search;
        }

        CustomPageConfiguration<AuthorResponseDTo> response = converter.convert(author, url, searchParam);

        return ResponseBaseConfiguration.ok(response);
    }

    @PostMapping()
    public ResponseBaseConfiguration createAuthor(@RequestBody @Valid AuthorRequestDto request) {
        authorService.save(request);
        return ResponseBaseConfiguration.created();
    }
}