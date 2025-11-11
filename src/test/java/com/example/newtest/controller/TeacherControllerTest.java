package com.example.newtest.controller;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Teacher;
import com.example.newtest.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeacherService teacherService;

    private Teacher teacher;
    private Lesson lesson;

    @BeforeEach
    void setup() {
        teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("Samir");
        teacher.setEmail("samir@gmail.com");

        lesson = new Lesson();
        lesson.setId(1);
        lesson.setName("Math");
        lesson.setDescription("test");
    }

    @Test
    void testGetAllTeachers() throws Exception {
        when(teacherService.getAllTeachers()).thenReturn(List.of(teacher));

        mockMvc.perform(get("/teachers/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Samir"));
    }

    @Test
    void testGetTeacherById() throws Exception {
        when(teacherService.getTeacherById(1)).thenReturn(teacher);

        mockMvc.perform(get("/teachers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samir"));
    }

    @Test
    void testAddTeacher() throws Exception {
        when(teacherService.createNewTeacher(teacher)).thenReturn(teacher);

        mockMvc.perform(post("/teachers/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Samir",
                                  "email": "samir@gmail.com"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateTeacher() throws Exception {
        Teacher updated = new Teacher();
        updated.setId(1);
        updated.setName("Ruslan");
        updated.setEmail("ruslan@gmail.com");

        when(teacherService.updateTeacherById(Mockito.eq(1), Mockito.any(Teacher.class))).thenReturn(updated);

        mockMvc.perform(put("/teachers/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Ruslan",
                                  "email": "ruslan@gmail.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ruslan"))
                .andExpect(jsonPath("$.email").value("ruslan@gmail.com"));
    }

    @Test
    void testDeleteTeacher() throws Exception {
        doNothing().when(teacherService).deleteTeacherById(1);

        mockMvc.perform(delete("/teachers/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetLessonsOfTeacher() throws Exception {
        when(teacherService.getLessonsByTeacherId(1)).thenReturn(Set.of(lesson));

        mockMvc.perform(get("/teachers/getLessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Math"));

    }
}
