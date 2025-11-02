package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Teacher;
import com.example.newtest.repository.LessonRepository;
import com.example.newtest.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher getTeacherById(int id) {
        return teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteTeacherById(int id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        teacherRepository.deleteById(id);
    }

    public Teacher createNewTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher addLessonToTeacher(int teacherId, int lessonId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        teacher.addLesson(lesson);
        return teacherRepository.save(teacher);
    }

    public Teacher deleteLessonFromTeacher(int teacherId, int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        teacher.removeLesson(lesson);
        return teacherRepository.save(teacher);
    }

    public Teacher updateTeacherById(int teacherId, Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        return teacherRepository.save(existingTeacher);
    }

    public Set<Lesson> getLessonsByTeacherId(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return teacher.getLessons();
    }
}
