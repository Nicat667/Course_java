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
        return lessonRepository.findById(id).get();
    }

    public void DeleteLessonById(int id) {
        lessonRepository.deleteById(id);
    }

    public Lesson CreateNewLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson UpdateLesson(int lessonId, Lesson lesson) {
        Lesson existingLesson = lessonRepository.findById(lessonId).orElseThrow();
        existingLesson.setName(lesson.getName());
        return lessonRepository.save(existingLesson);
    }

    public Teacher GetTeacherOfLesson(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        return lesson.getTeacher();
    }

    public Set<Student> GetStudentsOfLesson(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        return lesson.getStudents();
    }

    public Lesson AddStudentToLesson(int lessonId, int studentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();
        lesson.getStudents().add(student);
        return lessonRepository.save(lesson);
    }

    public Lesson DeleteStudentFromLesson(int lessonId, int studentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();
        lesson.RemoveStudent(student);
        return lessonRepository.save(lesson);
    }
}
