package com.student.management.controller;

import com.student.management.model.Student;
import com.student.management.service.StudentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getStudentById(@PathVariable UUID id) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isEmpty()) {
            return createErrorResponse(
                    HttpStatus.NOT_FOUND,
                    "Student with ID " + id + " not found"
            );
        }
        return ResponseEntity.ok(student.get());
    }

    @PostMapping
    public ResponseEntity<Object> createStudent(@RequestBody Student student) {
        try {
            Student createdStudent = studentService.saveStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
        } catch (DataIntegrityViolationException e) {
            return createErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "A student with this email already exists"
            );
        } catch (Exception e) {
            return createErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error creating student: " + e.getMessage()
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateStudent(@PathVariable UUID id, @RequestBody Student updatedStudent) {
        try {
            Optional<Student> existingStudent = studentService.getStudentById(id);
            if (existingStudent.isEmpty()) {
                return createErrorResponse(
                        HttpStatus.NOT_FOUND,
                        "Student with ID " + id + " not found"
                );
            }

            Student studentToUpdate = existingStudent.get();
            updateStudentFields(studentToUpdate, updatedStudent);
            Student savedStudent = studentService.saveStudent(studentToUpdate);
            return ResponseEntity.ok(savedStudent);

        } catch (DataIntegrityViolationException e) {
            return createErrorResponse(
                    HttpStatus.BAD_REQUEST,
                    "Email already exists"
            );
        } catch (Exception e) {
            return createErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error updating student: " + e.getMessage()
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudent(@PathVariable UUID id) {
        try {
            Optional<Student> existingStudent = studentService.getStudentById(id);
            if (existingStudent.isEmpty()) {
                return createErrorResponse(
                        HttpStatus.NOT_FOUND,
                        "Student with ID " + id + " not found"
                );
            }

            studentService.deleteStudent(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return createErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error deleting student: " + e.getMessage()
            );
        }
    }

    private void updateStudentFields(Student existingStudent, Student updatedStudent) {
        if (updatedStudent.getFirstName() != null) {
            existingStudent.setFirstName(updatedStudent.getFirstName());
        }
        if (updatedStudent.getLastName() != null) {
            existingStudent.setLastName(updatedStudent.getLastName());
        }
        if (updatedStudent.getEmail() != null) {
            existingStudent.setEmail(updatedStudent.getEmail());
        }
        if (updatedStudent.getLocation() != null) {
            existingStudent.setLocation(updatedStudent.getLocation());
        }
    }

    private ResponseEntity<Object> createErrorResponse(HttpStatus status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", message);

        return ResponseEntity.status(status).body(errorResponse);
    }
}