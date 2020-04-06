package com.example.demo.controller;

import com.example.demo.entities.ResponseBase;
import com.example.demo.entities.model.Author;
import com.example.demo.entities.model.Category;
import com.example.demo.repositories.CategoryRepository;
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
    public ResponseEntity<ResponseBase> getCategory(){
        ResponseBase response = new ResponseBase<>();
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
    public ResponseEntity<ResponseBase> getCategoryById(@PathVariable Integer id)throws NotFoundException {
        ResponseBase response = new ResponseBase<>();


        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category id " + id + " NotFound"));

        response.setData(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<ResponseBase> postCategory(@RequestBody Category category){
        ResponseBase response =new ResponseBase<>();
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
    public ResponseEntity<ResponseBase> putCategory(@PathVariable Integer id, @RequestBody Category category)throws NotFoundException{
        ResponseBase response = new ResponseBase();
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
    public ResponseEntity<ResponseBase> deleteCategory(@RequestBody Category category){
        ResponseBase response = new ResponseBase();
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
