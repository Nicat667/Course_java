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
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher Not Found"));
    }

    public void DeleteTeacherById(int id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Teacher Not Found"));
        teacherRepository.deleteById(id);
    }

    public Teacher CreateNewTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    public Teacher AddLessonToTeacher(int teacherId, int lessonId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher Not Found"));
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        teacher.addLesson(lesson);
        return teacherRepository.save(teacher);
    }

    public Teacher DeleteLessonFromTeacher(int teacherId, int lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() -> new RuntimeException("Lesson Not Found"));
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher Not Found"));
        teacher.removeLesson(lesson);
        return teacherRepository.save(teacher);
    }

    public Teacher UpdateTeacherById(int teacherId, Teacher teacher) {
        Teacher existingTeacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher Not Found"));
        existingTeacher.setName(teacher.getName());
        existingTeacher.setEmail(teacher.getEmail());
        return teacherRepository.save(existingTeacher);
    }

    public Set<Lesson> GetLessonsByTeacherId(int teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new RuntimeException("Teacher Not Found"));
        return teacher.getLessons();
    }
}
