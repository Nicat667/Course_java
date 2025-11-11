package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private Lesson lesson;

    @BeforeEach
    void setUp() {
        student = new Student(1, "Zeyneb", "zeyneb@mail.com", new HashSet<>());
        lesson = new Lesson(1, "Math", "test", null, new HashSet<>());
    }

    @Test
    void getStudent_success() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Student result = studentService.getStudent(1);

        assertEquals("Zeyneb", result.getName());
        assertEquals("zeyneb@mail.com", result.getEmail());
    }

    @Test
    void getStudent_fail() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> studentService.getStudent(1));
    }

    @Test
    void getAllStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> students = studentService.getAllStudents();

        assertEquals(1, students.size());
        assertEquals("Zeyneb", students.get(0).getName());

    }

    @Test
    void createNewStudent() {
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.createNewStudent(student);

        assertEquals("Zeyneb", result.getName());
        verify(studentRepository).save(student);
    }

    @Test
    void updateStudent_success() {
        Student updated = new Student();
        updated.setName("Cavid");
        updated.setEmail("cavid@mail.com");

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        Student result = studentService.updateStudent(1, updated);

        assertEquals("Cavid", result.getName());
        assertEquals("cavid@mail.com", result.getEmail());
        verify(studentRepository).save(student);
    }

    @Test
    void updateStudent_fail() {
        Student updated = new Student();
        when(studentRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> studentService.updateStudent(1, updated));
    }

    @Test
    void deleteStudent_success() {
        lesson.setStudents(new HashSet<>());
        Set<Lesson> lessons = new HashSet<>();
        lessons.add(lesson);
        student.setLessons(lessons);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1);

        assertTrue(student.getLessons().isEmpty());
        verify(studentRepository).deleteById(1);
    }

    @Test
    void deleteStudent_fail() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> studentService.deleteStudent(1));
    }

    @Test
    void getAllLessonsOfStudent_success() {
        student.getLessons().add(lesson);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Set<Lesson> lessons = studentService.getAllLessonsOfStudent(1);

        assertTrue(lessons.contains(lesson));
    }

    @Test
    void getAllLessonsOfStudent_fail() {
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> studentService.getAllLessonsOfStudent(1));
    }
}
