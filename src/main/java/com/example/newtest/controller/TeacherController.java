package com.example.newtest.controller;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Teacher;
import com.example.newtest.repository.TeacherRepository;
import com.example.newtest.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/getAll")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable int id) {
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<Teacher> addTeacher(@RequestBody Teacher teacher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createNewTeacher(teacher));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable int id) {
        teacherService.deleteTeacherById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher, @PathVariable int id) {
        return ResponseEntity.ok(teacherService.updateTeacherById(id, teacher));
    }

    @GetMapping("/getLessons/{id}")
    public ResponseEntity<Set<Lesson>> getLessonsById(@PathVariable int id) {
        return ResponseEntity.ok(teacherService.getLessonsByTeacherId(id));
    }

    @PutMapping("/addLesson/{teacherId}/{lessonId}")
    public ResponseEntity<Teacher> addLesson(@PathVariable int lessonId, @PathVariable int teacherId) {
        return ResponseEntity.ok(teacherService.addLessonToTeacher(teacherId, lessonId));
    }

    @PutMapping("/removeLesson/{teacherId}/{lessonId}")
    public ResponseEntity<Teacher> removeLesson(@PathVariable int lessonId, @PathVariable int teacherId) {
        return ResponseEntity.ok(teacherService.deleteLessonFromTeacher(teacherId, lessonId));
    }
}
