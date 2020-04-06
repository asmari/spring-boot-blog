package com.example.demo.controller;

import com.example.demo.entities.ResponseBase;
import com.example.demo.entities.model.Tags;
import com.example.demo.repositories.TagsRepository;
import com.example.demo.service.TagsService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tags")
public class TagsController {
    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private TagsService tagsService;

    @GetMapping()
    public ResponseEntity<ResponseBase> getTags(){
        ResponseBase response = new ResponseBase<>();
        try{
            response.setData(tagsRepository.findAll());
        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase> getTagsById(@PathVariable Integer id)throws NotFoundException {
        ResponseBase response = new ResponseBase<>();


        Tags Tags = tagsRepository.findById(id).orElseThrow(() -> new NotFoundException("Tags id " + id + " NotFound"));

        response.setData(Tags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<ResponseBase> postTags(@RequestBody Tags Tags){
        ResponseBase response =new ResponseBase<>();
        try{
            response.setData(tagsRepository.save(Tags));

        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBase> putTags(@PathVariable Integer id, @RequestBody Tags Tags)throws NotFoundException{
        ResponseBase response = new ResponseBase();
        tagsRepository.findById(id).orElseThrow(()->new NotFoundException("Tags id " + id + " NotFound"));
        try{
            response.setData(tagsService.update(id,Tags));
        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<ResponseBase> deleteTags(@RequestBody Tags Tags){
        ResponseBase response = new ResponseBase();
        try {
            tagsRepository.deleteById(Tags.getId());
        }catch (Exception e){
            response.setStatus(false);
            response.setCode(500);
            response.setMessage(e.getMessage());

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
