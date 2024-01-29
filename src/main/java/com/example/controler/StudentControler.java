package com.example.controler;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentControler {
    @Autowired
    StudentRepository repo;
    @GetMapping("/students")
    public List<Student> getAllStudents(){
        List<Student> students = repo.findAll();
        return students;
    }
    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id){
        Student student = repo.findById(id).orElse(null);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/students/add")
    public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        repo.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/students/update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        Optional<Student> optionalStudent = repo.findById(id);
        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setBranch(updatedStudent.getBranch());
            existingStudent.setPercentage(updatedStudent.getPercentage());
            repo.save(existingStudent);
            return ResponseEntity.ok(existingStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/students/delete/{id}")
    public ResponseEntity<Void> removeStudent(@PathVariable int id) {
        Optional<Student> optionalStudent = repo.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            repo.delete(student);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
