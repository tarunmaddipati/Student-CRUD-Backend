package com.example.controler;

import com.example.entity.Subject;
import com.example.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubjectController {
    @Autowired
    SubjectRepository repo;
    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = repo.findAll();
        return subjects;
    }
    @GetMapping("/subjects/{id}")
    public Subject getSubject(@PathVariable long id){
        Subject subjects = repo.findById(id).get();
        return subjects;
    }
    @PostMapping("/subjects/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createSubject(@RequestBody Subject subject){
        repo.save(subject);
    }
    @PutMapping("/subjects/update/{id}")
    public Subject updateSubject(@PathVariable long id, @RequestBody Subject subject){
        Subject subject1 = repo.findById(id).get();
        subject1.setName(subject.getName());
        subject1.setDecription(subject.getDecription());
        subject1.setTopic(subject.getTopic());
        repo.save(subject1);
        return subject;
    }
    @DeleteMapping("/subjects/delete/{id}")
    public void removeSubject(@PathVariable long id){
        Subject subject = repo.findById(id).get();
        repo.delete(subject);
    }

}
