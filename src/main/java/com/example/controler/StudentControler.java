package com.example.controler;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Student getStudent(@PathVariable int id){
        Student student = repo.findById(id).get();
        return student;
    }
    @PostMapping("/students/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createStudent(@RequestBody Student student){
        repo.save(student);
    }
    @PutMapping("/students/update/{id}")
    public Student updateStudents(@PathVariable int id,@RequestBody Student student){
        Student student1 = repo.findById(id).get();
        student1.setName(student.getName());
        student1.setBranch(student.getBranch());
        student1.setPercentage(student.getPercentage());
       repo.save(student1);
       return student;
    }
    @DeleteMapping("/students/delete/{id}")
    public void removeStudent(@PathVariable int id){
        Student student = repo.findById(id).get();
        repo.delete(student);
    }
}
