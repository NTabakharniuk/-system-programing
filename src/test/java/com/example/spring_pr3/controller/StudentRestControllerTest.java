package com.example.spring_pr3.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.dto.ResponseStudentDTO;
import com.example.spring_pr3.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentRestController.class)
class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAllStudents() throws Exception {
        List<ResponseStudentDTO> students = Arrays.asList(
                new ResponseStudentDTO(1L, "John Doe", 20),
                new ResponseStudentDTO(2L, "Jane Smith", 22)
        );

        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    void testCreateStudent() throws Exception {
        RequestStudentDTO requestDTO = new RequestStudentDTO();
        requestDTO.setName("New Student");
        requestDTO.setAge(20);

        ResponseStudentDTO responseDTO = new ResponseStudentDTO(1L, "New Student", 20);

        when(studentService.createStudent(any(RequestStudentDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("New Student"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    void testUpdateStudent() throws Exception {
        RequestStudentDTO requestDTO = new RequestStudentDTO();
        requestDTO.setName("Updated Name");
        requestDTO.setAge(25);

        ResponseStudentDTO responseDTO = new ResponseStudentDTO(1L, "Updated Name", 25);

        when(studentService.updateStudent(eq(1L), any(RequestStudentDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void testDeleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isNoContent());

        verify(studentService, times(1)).deleteStudent(1L);
    }
}
