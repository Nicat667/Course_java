package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        student = new Student(1, "Zeyneb", "zeyneb@mail.com", new HashSet<>());
    }

    @Test
    void getStudent_ReturnsStudent() {
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Student result = studentService.getStudent(1);
        assertEquals("Zeyneb", result.getName());
        assertEquals("zeyneb@gmail.com", result.getEmail());
    }

    @Test
    void deleteStudent_RemovesStudent() {
        Lesson lesson = new Lesson();
        lesson.setStudents(new HashSet<>());
        Set<Lesson> lessons = new HashSet<>();
        lessons.add(lesson);
        student.setLessons(lessons);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1);

        verify(studentRepository).deleteById(1);
    }

    @Test
    void getAllLessonsOfStudent_ReturnsLessons() {
        Lesson lesson = new Lesson();
        student.getLessons().add(lesson);
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        Set<Lesson> lessons = studentService.getAllLessonsOfStudent(1);
        assertTrue(lessons.contains(lesson));
    }
}
