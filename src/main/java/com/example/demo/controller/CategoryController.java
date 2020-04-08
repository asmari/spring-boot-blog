package com.example.demo.controller;

import com.example.demo.config.ResponseBaseConfiguration;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<ResponseBaseConfiguration> getCategory(){
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();
        try{
            response.setData(categoryRepository.findAll());
        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBaseConfiguration> getCategoryById(@PathVariable Integer id)throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();


        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category id " + id + " NotFound"));

        response.setData(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<ResponseBaseConfiguration> postCategory(@RequestBody Category category){
        ResponseBaseConfiguration response =new ResponseBaseConfiguration<>();
        try{
            response.setData(categoryRepository.save(category));

        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBaseConfiguration> putCategory(@PathVariable Integer id, @RequestBody Category category)throws NotFoundException{
        ResponseBaseConfiguration response = new ResponseBaseConfiguration();
        categoryRepository.findById(id).orElseThrow(()->new NotFoundException("Category id " + id + " NotFound"));
        try{
            response.setData(categoryService.update(id,category));
        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<ResponseBaseConfiguration> deleteCategory(@RequestBody Category category){
        ResponseBaseConfiguration response = new ResponseBaseConfiguration();
        try {
            categoryRepository.deleteById(category.getId());
        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
