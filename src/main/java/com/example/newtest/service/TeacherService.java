package com.example.newtest.service;

import com.example.newtest.model.Teacher;
import com.example.newtest.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;


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

//    public Teacher AddLesson(Teacher teacher, Integer lesson_id) {
//
//        teacher.addLesson();
//    }
}
