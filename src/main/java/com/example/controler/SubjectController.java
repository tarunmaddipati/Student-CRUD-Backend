package com.example.controler;

import com.example.entity.Subject;
import com.example.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
    public ResponseEntity<Subject> getSubject(@PathVariable long id) {
        Subject subject = repo.findById(id).orElse(null);
        if (subject != null) {
            return ResponseEntity.ok(subject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/subjects/add")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject createdSubject = repo.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

    @PutMapping("/subjects/update/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable long id, @RequestBody Subject updatedSubject) {
        Optional<Subject> optionalSubject = repo.findById(id);
        if (optionalSubject.isPresent()) {
            Subject existingSubject = optionalSubject.get();
            existingSubject.setName(updatedSubject.getName());
            existingSubject.setDecription(updatedSubject.getDecription());
            existingSubject.setTopic(updatedSubject.getTopic());
            repo.save(existingSubject);
            return ResponseEntity.ok(existingSubject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/subjects/delete/{id}")
    public ResponseEntity<Void> removeSubject(@PathVariable long id) {
        Optional<Subject> optionalSubject = repo.findById(id);
        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            repo.delete(subject);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
