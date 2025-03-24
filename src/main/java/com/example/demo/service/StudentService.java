package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public void addStudent(Student student) {
        repository.findAll().add(student);
    }

    public void updateStudent(Long id, Student updatedStudent) {
        for (Student student : repository.findAll()) {
            if (student.getId().equals(id)) {
                student.setName(updatedStudent.getName());
                student.setAge(updatedStudent.getAge());
                break;
            }
        }
    }

    public void deleteStudent(Long id) {
        repository.findAll().removeIf(student -> student.getId().equals(id));
    }

    public Student getStudentById(Long id) {
        return repository.findAll()
                .stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
