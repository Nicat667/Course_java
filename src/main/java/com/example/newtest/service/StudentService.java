package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.repository.LessonRepository;
import com.example.newtest.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student GetStudent(int studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Student> GetAllStudents() {
        return studentRepository.findAll();
    }

    public void DeleteStudent(int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        studentRepository.deleteById(studentId);
    }

    public Student CreateNewStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student UpdateStudent(int StudentId, Student student) {
        Student existingStudent = studentRepository.findById(StudentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
        return studentRepository.save(existingStudent);
    }

    public Set<Lesson> GetAllLessonsOfStudent(int studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return student.getLessons();
    }
}
