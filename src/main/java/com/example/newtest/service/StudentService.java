package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.repository.LessonRepository;
import com.example.newtest.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student getStudent(int studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public void deleteStudent(int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Set<Lesson> lessons = student.getLessons();
        lessons.iterator().forEachRemaining((lesson) -> {lesson.RemoveStudent(student);});

        student.getLessons().clear();
        studentRepository.deleteById(studentId);
    }

    public Student createNewStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(int studentId, Student student) {
        Student existingStudent = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        return studentRepository.save(existingStudent);
    }

    public Set<Lesson> getAllLessonsOfStudent(int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return student.getLessons();
    }
}
