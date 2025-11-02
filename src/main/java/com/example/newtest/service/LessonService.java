package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.model.Teacher;
import com.example.newtest.repository.LessonRepository;
import com.example.newtest.repository.StudentRepository;
import com.example.newtest.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }

    public Lesson getLessonById(int id) {
        return lessonRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteLessonById(int id) {
        Lesson lesson = lessonRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        lessonRepository.deleteById(id);
    }

    public Lesson createNewLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson updateLesson(int lessonId, Lesson lesson) {
        Lesson existingLesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingLesson.setName(lesson.getName());
        existingLesson.setDescription(lesson.getDescription());
        return lessonRepository.save(existingLesson);
    }

    public Teacher getTeacherOfLesson(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return lesson.getTeacher();
    }

    public Set<Student> getStudentsOfLesson(int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return lesson.getStudents();
    }

    public Lesson addStudentToLesson(int lessonId, int studentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        lesson.AddStudent(student);
        return lessonRepository.save(lesson);
    }

    public Lesson deleteStudentFromLesson(int lessonId, int studentId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        lesson.RemoveStudent(student);
        return lessonRepository.save(lesson);
    }
}
