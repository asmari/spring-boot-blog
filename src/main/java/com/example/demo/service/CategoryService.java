package com.example.demo.service;

import com.example.demo.entities.model.Category;
import com.example.demo.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category update(Integer id, Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }
}
