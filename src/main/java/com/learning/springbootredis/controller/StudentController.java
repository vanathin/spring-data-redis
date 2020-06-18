package com.learning.springbootredis.controller;

import com.learning.springbootredis.bean.Student;
import com.learning.springbootredis.dao.StudentRepository;
import com.learning.springbootredis.dao.StudentRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @Autowired
    public StudentRepositoryImpl repo;

    @PostMapping(path="/employees")
    public ResponseEntity<Student> save(@RequestBody Student stu){
        repo.save(stu);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(stu.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
    @GetMapping(path="/employees")
    public List<Student> getEmployee(){
        return repo.getAll();
    }

}
