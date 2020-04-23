package com.example.demo.controller;

import com.example.demo.config.CustomPageConfiguration;
import com.example.demo.config.CustomPageableConfiguration;
import com.example.demo.config.ResponseBaseConfiguration;
import com.example.demo.dto.request.TagsRequestDto;
import com.example.demo.dto.response.TagsResponseDto;
import com.example.demo.model.Tags;
import com.example.demo.repository.TagsRepository;
import com.example.demo.service.TagsService;
import com.example.demo.util.PageConverter;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/tags")
@Slf4j
public class TagsController {
    @Autowired
    private TagsService tagsService;

    @GetMapping()
    public ResponseBaseConfiguration<CustomPageConfiguration<TagsResponseDto>> listTags(
            CustomPageableConfiguration pageable, @RequestParam(required = false) String param, HttpServletRequest request
    ) {
        Page<TagsResponseDto> tags;
        log.info(String.valueOf(param));
        if (param == null || param.isEmpty()) {
            tags = tagsService.findAll(CustomPageableConfiguration.convertToPageable(pageable));
        } else {
            tags = tagsService.findByNameLike(CustomPageableConfiguration.convertToPageable(pageable),param);
        }

        PageConverter<TagsResponseDto> converter = new PageConverter<>();
        String url = String.format("%s://%s:%d/tags",request.getScheme(),  request.getServerName(), request.getServerPort());

        String search = "";

        if(param != null){
            search += "&param="+param;
        }

        CustomPageConfiguration<TagsResponseDto> response = converter.convert(tags, url, search);

        return ResponseBaseConfiguration.ok(response);
    }
    @GetMapping(value = "/{id}")
    public ResponseBaseConfiguration<TagsResponseDto> getOne(@PathVariable Integer id) {
        log.info(String.valueOf(tagsService.findById(id)));
        return ResponseBaseConfiguration.ok(tagsService.findById(id));
    }
    @PostMapping()
    public ResponseBaseConfiguration createTag(@RequestBody @Valid TagsRequestDto request) {
        tagsService.save(request);
        return ResponseBaseConfiguration.created();
    }
    @PutMapping(value = "/{id}")
    public ResponseBaseConfiguration updateTag(
            @RequestBody @Valid TagsRequestDto request, @PathVariable("id") Integer id
    ) {
        tagsService.update(id, request);
        return ResponseBaseConfiguration.ok();
    }
    @DeleteMapping()
    public ResponseBaseConfiguration deleteTag(@RequestBody Tags tags) {
        tagsService.deleteById(tags.getId());
        return ResponseBaseConfiguration.ok();
    }

}
