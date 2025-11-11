package com.example.newtest.controller;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.model.Teacher;
import com.example.newtest.service.LessonService;
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

@WebMvcTest(LessonController.class)
public class LessonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LessonService lessonService;

    private Lesson lesson;
    private Teacher teacher;
    private Student student;

    @BeforeEach
    void setup() {
        teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("Samir");
        teacher.setEmail("samir@gmail.com");

        student = new Student();
        student.setId(1);
        student.setName("Murad");
        student.setEmail("murad@gmail.com");

        lesson = new Lesson();
        lesson.setId(1);
        lesson.setName("math");
        lesson.setDescription("test");
        lesson.setTeacher(teacher);
        lesson.setStudents(Set.of(student));
    }

    @Test
    void testGetAllLessons() throws Exception {
        when(lessonService.getAllLessons()).thenReturn(List.of(lesson));

        mockMvc.perform(get("/lessons/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("math"))
                .andExpect(jsonPath("$[0].description").value("test"));
    }

    @Test
    void testGetLessonById() throws Exception {
        when(lessonService.getLessonById(1)).thenReturn(lesson);

        mockMvc.perform(get("/lessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("math"))
                .andExpect(jsonPath("$.description").value("test"));
    }

    @Test
    void testAddLesson() throws Exception {
        when(lessonService.createNewLesson(lesson)).thenReturn(lesson);

        mockMvc.perform(post("/lessons/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "math",
                                  "description": "test"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateLesson() throws Exception {
        Lesson updated = new Lesson();
        updated.setId(1);
        updated.setName("Physics");
        updated.setDescription("test2");

        when(lessonService.updateLesson(Mockito.eq(1), Mockito.any(Lesson.class))).thenReturn(updated);

        mockMvc.perform(put("/lessons/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Physics",
                                  "description": "test2"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Physics"))
                .andExpect(jsonPath("$.description").value("test2"));
    }

    @Test
    void testDeleteLesson() throws Exception {
        doNothing().when(lessonService).deleteLessonById(1);

        mockMvc.perform(delete("/lessons/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetTeacherOfLesson() throws Exception {
        when(lessonService.getTeacherOfLesson(1)).thenReturn(teacher);

        mockMvc.perform(get("/lessons/getTeacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samir"));
    }

    @Test
    void testGetStudentsOfLesson() throws Exception {
        when(lessonService.getStudentsOfLesson(1)).thenReturn(Set.of(student));

        mockMvc.perform(get("/lessons/getStudents/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Murad"));
    }
}
