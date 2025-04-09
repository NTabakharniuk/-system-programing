package com.example.spring_pr3.controller;

import com.example.spring_pr3.dto.RequestStudentDTO;
import com.example.spring_pr3.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students"; // HTML-шаблон
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("student", new RequestStudentDTO());
        return "create-student"; // HTML-шаблон
    }

    @PostMapping
    public String saveStudent(@ModelAttribute("student") RequestStudentDTO student) {
        studentService.createStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        var student = studentService.getStudentById(id);
        model.addAttribute("student", student);
        return "edit-student"; // HTML-шаблон
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute RequestStudentDTO student) {
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
