package com.example.spring_pr3.service;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.dto.ResponseStudentDTO;
import com.example.spring_pr3.model.Student;
import com.example.spring_pr3.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void testGetAllStudents() {
        // Підготовка
        Student student1 = Student.builder()
                .id(1L)
                .name("John Doe")
                .age(20)
                .build();
        Student student2 = Student.builder()
                .id(2L)
                .name("Jane Smith")
                .age(22)
                .build();
        List<Student> mockStudents = Arrays.asList(student1, student2);

        ResponseStudentDTO dto1 = new ResponseStudentDTO(1L, "John Doe", 20);
        ResponseStudentDTO dto2 = new ResponseStudentDTO(2L, "Jane Smith", 22);

        when(studentRepository.findAll()).thenReturn(mockStudents);
        when(modelMapper.map(student1, ResponseStudentDTO.class)).thenReturn(dto1);
        when(modelMapper.map(student2, ResponseStudentDTO.class)).thenReturn(dto2);

        // Виклик методу
        List<ResponseStudentDTO> result = studentService.getAllStudents();

        // Перевірка
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    void testCreateStudent() {
        // Підготовка
        RequestStudentDTO requestDTO = new RequestStudentDTO();
        requestDTO.setName("Test Student");
        requestDTO.setAge(20);

        Student unmappedStudent = Student.builder()
                .name("Test Student")
                .age(20)
                .build();

        Student savedStudent = Student.builder()
                .id(1L)
                .name("Test Student")
                .age(20)
                .build();

        ResponseStudentDTO expectedResponse = new ResponseStudentDTO(1L, "Test Student", 20);

        when(modelMapper.map(requestDTO, Student.class)).thenReturn(unmappedStudent);
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);
        when(modelMapper.map(savedStudent, ResponseStudentDTO.class)).thenReturn(expectedResponse);

        // Виклик методу
        ResponseStudentDTO result = studentService.createStudent(requestDTO);

        // Перевірка
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Student", result.getName());
        assertEquals(20, result.getAge());
    }
}
