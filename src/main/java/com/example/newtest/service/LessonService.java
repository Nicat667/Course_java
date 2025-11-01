package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.model.Teacher;
import com.example.newtest.repository.LessonRepository;
import com.example.newtest.repository.StudentRepository;
import com.example.newtest.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    public List<Lesson> GetAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson GetLessonById(int id) {
        return lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
    }

    public void DeleteLessonById(int id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        lessonRepository.deleteById(id);
    }

    public Lesson CreateNewLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson UpdateLesson(int lessonId, Lesson lesson) {
        Lesson existingLesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        existingLesson.setName(lesson.getName());
        return lessonRepository.save(existingLesson);
    }

    public Teacher GetTeacherOfLesson(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        return lesson.getTeacher();
    }

    public Set<Student> GetStudentsOfLesson(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        return lesson.getStudents();
    }

    public Lesson AddStudentToLesson(int lessonId, int studentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        lesson.AddStudent(student);
        return lessonRepository.save(lesson);
    }

    public Lesson DeleteStudentFromLesson(int lessonId, int studentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        lesson.RemoveStudent(student);
        return lessonRepository.save(lesson);
    }
}
