package com.example.newtest.service;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Teacher;
import com.example.newtest.repository.LessonRepository;
import com.example.newtest.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;

    public List<Teacher> GetAllTeachers() {
        return teacherRepository.findAll();
    }

    public Teacher GetTeacherById(int id) {
        return teacherRepository.findById(id).orElseThrow();
    }

    public void DeleteTeacherById(int id) {
        teacherRepository.deleteById(id);
    }

    public Teacher CreateNewTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher AddLessonToTeacher(int teacherId, int lessonId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        teacher.addLesson(lesson);
        return teacherRepository.save(teacher);
    }

    public Teacher DeleteLessonFromTeacher(int teacherId, int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        teacher.removeLesson(lesson);
        return teacherRepository.save(teacher);
    }

    public Teacher UpdateTeacherById(int teacherId, Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findById(teacherId).orElseThrow();
        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        return teacherRepository.save(existingTeacher);
    }

    public Set<Lesson> GetLessonsByTeacherId(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        return teacher.getLessons();
    }
}
