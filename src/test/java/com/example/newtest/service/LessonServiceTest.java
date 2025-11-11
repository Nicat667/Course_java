package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.model.Teacher;
import com.example.newtest.repository.LessonRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private LessonService lessonService;

    private Lesson lesson;
    private Student student;
    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacher = new Teacher(1, "Samir", "samir@mail.com", new HashSet<>());
        lesson = new Lesson(1, "Math", "test", teacher, new HashSet<>());
        student = new Student(1, "Zeyneb", "zeyneb@mail.com", new HashSet<>());
    }

    @Test
    void getAllLessons() {
        when(lessonRepository.findAll()).thenReturn(List.of(lesson));
        List<Lesson> lessons = lessonService.getAllLessons();
        assertEquals(1, lessons.size());
        assertEquals("Math", lessons.get(0).getName());
    }

    @Test
    void getLessonById_success() {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        Lesson result = lessonService.getLessonById(1);
        assertEquals("Math", result.getName());
    }

    @Test
    void getLessonById_fail() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.getLessonById(1));
    }

    @Test
    void createNewLesson() {
        when(lessonRepository.save(lesson)).thenReturn(lesson);
        Lesson result = lessonService.createNewLesson(lesson);
        assertEquals("Math", result.getName());
        verify(lessonRepository).save(lesson);
    }

    @Test
    void updateLesson_success() {
        Lesson updatedLesson = new Lesson();
        updatedLesson.setName("Physics");
        updatedLesson.setDescription("test2");

        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(lessonRepository.save(any())).thenReturn(updatedLesson);

        Lesson result = lessonService.updateLesson(1, updatedLesson);
        assertEquals("Physics", result.getName());
        assertEquals("test2", result.getDescription());
        verify(lessonRepository).save(lesson);
    }

    @Test
    void updateLesson_fail() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());
        Lesson updatedLesson = new Lesson();
        assertThrows(ResponseStatusException.class, () -> lessonService.updateLesson(1, updatedLesson));
    }

    @Test
    void deleteLessonById_success() {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        lessonService.deleteLessonById(1);
        verify(lessonRepository).deleteById(1);
    }

    @Test
    void deleteLessonById_fail() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.deleteLessonById(1));
    }

    @Test
    void getTeacherOfLesson_success() {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        Teacher result = lessonService.getTeacherOfLesson(1);
        assertEquals("Samir", result.getName());
    }

    @Test
    void getTeacherOfLesson_fail() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.getTeacherOfLesson(1));
    }

    @Test
    void getStudentsOfLesson_success() {
        lesson.getStudents().add(student);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        Set<Student> students = lessonService.getStudentsOfLesson(1);
        assertTrue(students.contains(student));
    }

    @Test
    void getStudentsOfLesson_fail() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.getStudentsOfLesson(1));
    }

    @Test
    void addStudentToLesson_success() {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(lessonRepository.save(any())).thenReturn(lesson);

        Lesson result = lessonService.addStudentToLesson(1, 1);
        assertTrue(result.getStudents().contains(student));
        verify(lessonRepository).save(lesson);
    }

    @Test
    void addStudentToLesson_LessonNotFound() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.addStudentToLesson(1, 1));
    }

    @Test
    void addStudentToLesson_StudentNotFound() {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.addStudentToLesson(1, 1));
    }

    @Test
    void deleteStudentFromLesson_success() {
        lesson.getStudents().add(student);
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(lessonRepository.save(any())).thenReturn(lesson);

        Lesson result = lessonService.deleteStudentFromLesson(1, 1);
        assertFalse(result.getStudents().contains(student));
        verify(lessonRepository).save(lesson);
    }

    @Test
    void deleteStudentFromLesson_LessonNotFound() {
        when(lessonRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.deleteStudentFromLesson(1, 1));
    }

    @Test
    void deleteStudentFromLesson_StudentNotFound() {
        when(lessonRepository.findById(1)).thenReturn(Optional.of(lesson));
        when(studentRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> lessonService.deleteStudentFromLesson(1, 1));
    }
}
