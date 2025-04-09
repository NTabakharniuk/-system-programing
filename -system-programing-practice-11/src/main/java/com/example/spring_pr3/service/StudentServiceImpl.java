
package com.example.spring_pr3.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.dto.ResponseStudentDTO;
import com.example.spring_pr3.model.Student;
import com.example.spring_pr3.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<ResponseStudentDTO> getAllStudents() {
        logger.info("Getting all students");
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(student -> modelMapper.map(student, ResponseStudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseStudentDTO getStudentById(Long id) {
        logger.info("Fetching student with id: {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student not found with id: {}", id);
                    return new EntityNotFoundException("Student not found with id: " + id);
                });
        return modelMapper.map(student, ResponseStudentDTO.class);
    }

    @Override
    public ResponseStudentDTO createStudent(RequestStudentDTO studentDTO) {
        try {
            logger.info("Creating student from DTO: {}", studentDTO);
            Student student = modelMapper.map(studentDTO, Student.class);
            student.setId(null); // Ensure we're creating a new entity
            Student savedStudent = studentRepository.save(student);
            return modelMapper.map(savedStudent, ResponseStudentDTO.class);
        } catch (Exception e) {
            logger.error("Error creating student", e);
            throw new RuntimeException("Error creating student: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseStudentDTO updateStudent(Long id, RequestStudentDTO studentDTO) {
        logger.info("Updating student with id: {}", id);
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student not found with id: {}", id);
                    return new EntityNotFoundException("Student not found with id: " + id);
                });

        try {
            modelMapper.map(studentDTO, existingStudent);
            existingStudent.setId(id); // Preserve the ID
            Student updatedStudent = studentRepository.save(existingStudent);
            return modelMapper.map(updatedStudent, ResponseStudentDTO.class);
        } catch (Exception e) {
            logger.error("Error updating student with id: {}", id, e);
            throw new RuntimeException("Error updating student: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteStudent(Long id) {
        logger.info("Deleting student with id: {}", id);
        if (!studentRepository.existsById(id)) {
            logger.error("Student not found with id: {}", id);
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        try {
            studentRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting student with id: {}", id, e);
            throw new RuntimeException("Error deleting student: " + e.getMessage(), e);
        }
    }
}
