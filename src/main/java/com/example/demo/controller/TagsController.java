package com.example.demo.controller;

import com.example.demo.config.ResponseBaseConfiguration;
import com.example.demo.model.Tags;
import com.example.demo.repository.TagsRepository;
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
    public ResponseEntity<ResponseBaseConfiguration> getTags(){
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();
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
    public ResponseEntity<ResponseBaseConfiguration> getTagsById(@PathVariable Integer id)throws NotFoundException {
        ResponseBaseConfiguration response = new ResponseBaseConfiguration<>();


        Tags Tags = tagsRepository.findById(id).orElseThrow(() -> new NotFoundException("Tags id " + id + " NotFound"));

        response.setData(Tags);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping()
    public ResponseEntity<ResponseBaseConfiguration> postTags(@RequestBody Tags Tags){
        ResponseBaseConfiguration response =new ResponseBaseConfiguration<>();
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
    public ResponseEntity<ResponseBaseConfiguration> putTags(@PathVariable Integer id, @RequestBody Tags Tags)throws NotFoundException{
        ResponseBaseConfiguration response = new ResponseBaseConfiguration();
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
    public ResponseEntity<ResponseBaseConfiguration> deleteTags(@RequestBody Tags Tags){
        ResponseBaseConfiguration response = new ResponseBaseConfiguration();
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
