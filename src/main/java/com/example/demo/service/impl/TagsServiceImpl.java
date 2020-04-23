package com.example.demo.service.impl;

import com.example.demo.dto.request.TagsRequestDto;
import com.example.demo.dto.response.TagsResponseDto;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Tags;
import com.example.demo.repository.TagsRepository;
import com.example.demo.service.TagsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TagsServiceImpl implements TagsService {
    @Autowired
    private TagsRepository tagsRepository;

    private static final String RESOURCE = "Tags";
    private static final String FIELD = "id";
    @Override
    public Page<TagsResponseDto> findAll(Pageable pageable) {
        try {
            return tagsRepository.findAll(pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<TagsResponseDto> findByNameLike(Pageable pageable, String name) {
        try {
            return tagsRepository.findByNameContaining(name,pageable).map(this::fromEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public TagsResponseDto findById(Integer id) {
        try {
            Tags tags = tagsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE));

            return fromEntity(tags);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Tags save(TagsRequestDto request) {
        try {
            Tags tags = new Tags();
            BeanUtils.copyProperties(request, tags);

            return tagsRepository.save(tags);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Tags update(Integer id, TagsRequestDto request) {
        try {
            Tags tags = tagsRepository.findById(id).orElseThrow(
                    ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );

            BeanUtils.copyProperties(request, tags);
//            tags.setUpdated_at(dateTime.getCurrentDate());
            tagsRepository.save(tags);
            return tags;
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            tagsRepository.findById(id).orElseThrow(
                    ()->new ResourceNotFoundException(id.toString(), FIELD, RESOURCE)
            );
            tagsRepository.deleteById(id);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }
    private TagsResponseDto fromEntity(Tags tags) {
        TagsResponseDto response = new TagsResponseDto();
        BeanUtils.copyProperties(tags, response);
        return response;
    }
}
