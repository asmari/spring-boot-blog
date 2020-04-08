package com.example.demo.service;

import com.example.demo.model.Tags;
import com.example.demo.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagsService {
    @Autowired
    private TagsRepository tagsRepository;

    public Tags update(Integer id, Tags tags){
        tags.setId(id);
        return tagsRepository.save(tags);
    }
}
