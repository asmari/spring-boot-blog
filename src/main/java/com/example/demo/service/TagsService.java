package com.example.demo.service;

import com.example.demo.dto.request.TagsRequestDto;
import com.example.demo.dto.response.TagsResponseDto;
import com.example.demo.model.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagsService {
    Page<TagsResponseDto> findAll(Pageable pageable);

    Page<TagsResponseDto> findByNameLike(Pageable pageable,String name);

    TagsResponseDto findById(Integer id);

    Tags save(TagsRequestDto request);

    Tags update(Integer id, TagsRequestDto request);

    void deleteById(Integer id);
}
