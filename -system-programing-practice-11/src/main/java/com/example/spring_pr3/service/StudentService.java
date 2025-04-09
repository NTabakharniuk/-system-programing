package com.example.spring_pr3.service;

import java.util.List;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.dto.ResponseStudentDTO;

public interface StudentService {

    List<ResponseStudentDTO> getAllStudents();

    ResponseStudentDTO getStudentById(Long id);

    ResponseStudentDTO createStudent(RequestStudentDTO studentDTO);

    ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO);

    void deleteStudent(Long id);
}
