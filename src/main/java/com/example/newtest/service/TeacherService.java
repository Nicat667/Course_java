package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Teacher;
import com.example.newtest.repository.LessonRepository;
import com.example.newtest.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;

    public List<Teacher> GetAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher GetTeacherById(Integer id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public void DeleteTeacherById(Integer id) {
        teacherRepository.deleteById(id);
    }

    public Teacher CreateNewTeacher(String name, String email) {
        Teacher teacher = new Teacher();
        teacher.setName(name);
        teacher.setEmail(email);
        return teacherRepository.save(teacher);
    }

    public Teacher AddLesson(Integer teacherId, Integer lessonId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        teacher.addLesson(lesson);
        return teacherRepository.save(teacher);
    }

    public void DeleteLessonFromTeacher(Integer teacherId, Integer lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        teacher.removeLesson(lesson);
        teacherRepository.save(teacher);
    }

    public Teacher UpdateTeacherById(Integer teacherId, Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findById(teacherId).orElseThrow();
        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        return teacherRepository.save(existingTeacher);
    }
}
