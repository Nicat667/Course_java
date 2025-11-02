package com.example.newtest.controller;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.model.Teacher;
import com.example.newtest.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lessons")
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Lesson>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAllLessons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lesson> getLessonById(@PathVariable int id) {
        return ResponseEntity.ok(lessonService.getLessonById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLessonById(@PathVariable int id) {
        lessonService.deleteLessonById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/add")
    public ResponseEntity<Lesson> addLesson(@RequestBody Lesson lesson) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonService.createNewLesson(lesson));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable int id, @RequestBody Lesson lesson) {
        return ResponseEntity.ok(lessonService.updateLesson(id, lesson));
    }

    @GetMapping("/getTeacher/{id}")
    public ResponseEntity<Teacher> getTeacher(@PathVariable int id) {
        return ResponseEntity.ok(lessonService.getTeacherOfLesson(id));
    }

    @GetMapping("/getStudents/{id}")
    public ResponseEntity<Set<Student>> getStudents(@PathVariable int id) {
        return ResponseEntity.ok(lessonService.getStudentsOfLesson(id));
    }

    @PutMapping("/addStudent/{lessonId}/{studentId}")
    public ResponseEntity<Lesson> addStudent(@PathVariable int lessonId, @PathVariable int studentId) {
        return ResponseEntity.ok(lessonService.addStudentToLesson(lessonId, studentId));
    }

    @PutMapping("/removeStudent/{lessonId}/{studentId}")
    public ResponseEntity<Lesson> removeStudent(@PathVariable int lessonId, @PathVariable int studentId) {
        return ResponseEntity.ok(lessonService.deleteStudentFromLesson(lessonId, studentId));
    }
}
