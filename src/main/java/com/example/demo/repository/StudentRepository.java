package com.example.demo.repository;

import com.example.demo.model.Student;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StudentRepository {
    private List<Student> students = new ArrayList<>();

    public List<Student> findAll() {
        return students;
    }
}
