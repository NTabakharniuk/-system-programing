
package com.example.spring_pr3.service;
@SpringBootTest
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllStudents() {
        // Підготовка
        List<Student> mockStudents = Arrays.asList(
                new Student(1L, "John Doe", "CS"),
                new Student(2L, "Jane Smith", "SE")
        );
        when(studentRepository.findAll()).thenReturn(mockStudents);

        // Виклик методу
        List<Student> students = studentService.getAllStudents();

        // Перевірка
        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testCreateStudent() {
        // Підготовка
        Student studentToCreate = new Student(null, "Test Student", "IT");
        Student savedStudent = new Student(1L, "Test Student", "IT");
        when(studentRepository.save(any(Student.class))).thenReturn(savedStudent);

        // Виклик методу
        Student created = studentService.createStudent(studentToCreate);

        // Перевірка
        assertNotNull(created);
        assertEquals("Test Student", created.getName());
        verify(studentRepository, times(1)).save(studentToCreate);
    }
}
