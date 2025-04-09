package com.example.spring_pr3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseStudentDTO {

    private Long id;
    private String name;
    private Integer age;
}
