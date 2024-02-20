package com.example.controler;

import com.example.entity.Student;
import com.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
@RestController

public class StudentControler {
    private static final Logger LOGGER = Logger.getLogger(StudentControler.class.getName());
    @Autowired
    StudentRepository repo;
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        long startTime = System.currentTimeMillis();
        List<Student> students = repo.findAll();
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        LOGGER.info("Time taken to execute getAllStudents: " + elapsedTime + " milliseconds");

        return students;
    }

   /* public List<Student> getAllStudents(){
        List<Student> students = repo.findAll();
        return students;
    }
*/

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        long startTime = System.currentTimeMillis();
        Optional<Student> optionalStudent = repo.findById(id);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (optionalStudent.isPresent()) {
            LOGGER.info("Time taken to execute getStudent: " + elapsedTime + " milliseconds");
            return ResponseEntity.ok(optionalStudent.get());
        } else {
            LOGGER.warning("Student not found with ID: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    /*public ResponseEntity<Student> getStudent(@PathVariable int id){
        Student student = repo.findById(id).orElse(null);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
*/

    @PostMapping("/students/add")
    public ResponseEntity<Void> createStudent(@RequestBody Map<String,String> data) {
        long startTime = System.currentTimeMillis();
        LOGGER.info("Student details"+data.entrySet());
        Student student=new Student();
        student.setRollNo(Integer.parseInt(data.get("rollNo")));
        student.setName(data.get("student_name"));
        student.setPercentage(Float.parseFloat(data.get("student_percentage")));
        student.setBranch(data.get("student_branch"));
        student.setPassword(data.get("password"));
        LOGGER.info("Student object details"+student.toString());
        repo.save(student);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        LOGGER.info("Time taken to execute createStudent: " + elapsedTime + " milliseconds");

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*public ResponseEntity<Void> createStudent(@RequestBody Student student) {
        repo.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
*/
    @PutMapping("/students/update/{rollNo}")
    public ResponseEntity<Student> updateStudent(@PathVariable int rollNo, @RequestBody Student updatedStudent) {
        long startTime = System.currentTimeMillis();
        Optional<Student> optionalStudent = repo.findById(rollNo);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (optionalStudent.isPresent()) {
            Student existingStudent = optionalStudent.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setBranch(updatedStudent.getBranch());
            existingStudent.setPercentage(updatedStudent.getPercentage());

            repo.save(existingStudent);

            LOGGER.info("Time taken to execute updateStudent: " + elapsedTime + " milliseconds");
            return ResponseEntity.ok(existingStudent);
        } else {
            LOGGER.warning("Student not found with ID: " + rollNo);
            return ResponseEntity.notFound().build();
        }
    }
    /*public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
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
*/
    @DeleteMapping("/students/delete/{rollNo}")
    public ResponseEntity<Void> removeStudent(@PathVariable int rollNo) {
        long startTime = System.currentTimeMillis();
        Optional<Student> optionalStudent = repo.findById(rollNo);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            repo.delete(student);

            LOGGER.info("Time taken to execute removeStudent: " + elapsedTime + " milliseconds");
            return ResponseEntity.noContent().build();
        } else {
            LOGGER.warning("Student not found with ID: " + rollNo);
            return ResponseEntity.notFound().build();
        }
    }
   /* public ResponseEntity<Void> removeStudent(@PathVariable int id) {
        Optional<Student> optionalStudent = repo.findById(id);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            repo.delete(student);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    */

    @GetMapping("/students/login")
    @ResponseBody
    public ResponseEntity<Student> loginStudent(@RequestParam("roll_no") Integer roll_no, @RequestParam("password") String password) {
        LOGGER.info( "roll_no" + roll_no);
        LOGGER.info("password"+password);
        Optional<Student> optionalStudent = repo.findById(roll_no);
        if (optionalStudent.isPresent()) {
            Student student = optionalStudent.get();
            if (student.getPassword().equals(password)) {
                return ResponseEntity.ok(student);
            } else {
                LOGGER.warning("Incorrect password for Student with ID: " + roll_no);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            LOGGER.warning("Student not found with ID: " + roll_no);
            return ResponseEntity.notFound().build();
        }
    }
}
