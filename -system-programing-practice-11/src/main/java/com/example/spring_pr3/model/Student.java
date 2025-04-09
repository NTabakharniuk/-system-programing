package com.example.spring_pr3.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "students") // Added table name
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Added for easier object creation in tests
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    @Column(nullable = false) // Added column constraint
    private String name;

    @NotNull(message = "Age is mandatory")
    @Min(value = 16, message = "Age must be at least 16")
    @Max(value = 100, message = "Age must be less than 100")
    @Column(nullable = false) // Added column constraint
    private Integer age;
}
