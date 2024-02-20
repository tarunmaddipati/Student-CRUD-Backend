package com.example.controler;

import com.example.entity.Subject;
import com.example.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class SubjectController {
    private static final Logger LOGGER = Logger.getLogger(StudentControler.class.getName());
    @Autowired
    SubjectRepository repo;

    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        long startTime = System.currentTimeMillis();
        List<Subject> subjects = repo.findAll();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        LOGGER.info("Time taken to execute getAllSubjects: " + elapsedTime + " milliseconds");

        return subjects;
    }
    /*public List<Subject> getAllSubjects() {
        List<Subject> subjects = repo.findAll();
        return subjects;
    }

     */

    @GetMapping("/subjects/{id}")
    public ResponseEntity<Subject> getSubject(@PathVariable long id) {
        long startTime = System.currentTimeMillis();
        Optional<Subject> optionalSubject = repo.findById(id);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (optionalSubject.isPresent()) {
            LOGGER.info("Time taken to execute getSubject: " + elapsedTime + " milliseconds");
            return ResponseEntity.ok(optionalSubject.get());
        } else {
            LOGGER.warning("Subject not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
    /*public ResponseEntity<Subject> getSubject(@PathVariable long id) {
        Subject subject = repo.findById(id).orElse(null);
        if (subject != null) {
            return ResponseEntity.ok(subject);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     */

    @PostMapping("/subjects/add")
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        long startTime = System.currentTimeMillis();
        Subject createdSubject = repo.save(subject);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        LOGGER.info("Time taken to execute createSubject: " + elapsedTime + " milliseconds");

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }
    /*public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) {
        Subject createdSubject = repo.save(subject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSubject);
    }

     */

    @PutMapping("/subjects/update/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable long id, @RequestBody Subject updatedSubject) {
        long startTime = System.currentTimeMillis();
        Optional<Subject> optionalSubject = repo.findById(id);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (optionalSubject.isPresent()) {
            Subject existingSubject = optionalSubject.get();
            existingSubject.setName(updatedSubject.getName());
            existingSubject.setDecription(updatedSubject.getDecription());
            existingSubject.setTopic(updatedSubject.getTopic());

            repo.save(existingSubject);

            LOGGER.info("Time taken to execute updateSubject: " + elapsedTime + " milliseconds");
            return ResponseEntity.ok(existingSubject);
        } else {
            LOGGER.warning("Subject not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
    /*public ResponseEntity<Subject> updateSubject(@PathVariable long id, @RequestBody Subject updatedSubject) {
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

     */

    @DeleteMapping("/subjects/delete/{id}")
    public ResponseEntity<Void> removeSubject(@PathVariable long id) {
        long startTime = System.currentTimeMillis();
        Optional<Subject> optionalSubject = repo.findById(id);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            repo.delete(subject);

            LOGGER.info("Time taken to execute removeSubject: " + elapsedTime + " milliseconds");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.warning("Subject not found with ID: " + id);
            return ResponseEntity.notFound().build();
        }
    }
    /*public ResponseEntity<Void> removeSubject(@PathVariable long id) {
        Optional<Subject> optionalSubject = repo.findById(id);
        if (optionalSubject.isPresent()) {
            Subject subject = optionalSubject.get();
            repo.delete(subject);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     */
}
