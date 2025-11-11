package com.example.newtest.controller;

import com.example.newtest.model.Lesson;
import com.example.newtest.model.Student;
import com.example.newtest.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService studentService;

    private Student student;
    private Lesson lesson;

    @BeforeEach
    public void setup() {
        lesson = new Lesson();
        lesson.setId(1);
        lesson.setName("math");
        lesson.setDescription("test");

        student = new Student();
        student.setId(1);
        student.setEmail("murad@gmail.com");
        student.setName("Murad");
        student.setLessons(Set.of(lesson));
    }

    @Test
    void testGetAllStudents() throws Exception {
        when(studentService.getAllStudents()).thenReturn(List.of(student));

        mockMvc.perform(get("/students/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Murad"))
                .andExpect(jsonPath("$[0].email").value("murad@gmail.com"));
    }

    @Test
    void testGetStudentById() throws Exception {
        when(studentService.getStudent(1)).thenReturn(student);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Murad"))
                .andExpect(jsonPath("$.email").value("murad@gmail.com"));
    }

    @Test
    void testAddStudent() throws Exception {
        when(studentService.createNewStudent(student)).thenReturn(student);

        mockMvc.perform(post("/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "Murad",
                                  "email": "murad@gmail.com"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student updated = new Student();
        updated.setId(1);
        updated.setName("updated");
        updated.setEmail("updated@gmail.com");

        when(studentService.updateStudent(Mockito.eq(1), Mockito.any(Student.class))).thenReturn(updated);

        mockMvc.perform(put("/students/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "name": "updated",
                                  "email": "updated@gmail.com"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("updated"))
                .andExpect(jsonPath("$.email").value("updated@gmail.com"));
    }

    @Test
    void testDeleteStudentById() throws Exception {

        doNothing().when(studentService).deleteStudent(1);

        mockMvc.perform(delete("/students/delete/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllLessonsOfStudent() throws Exception {
        when(studentService.getAllLessonsOfStudent(1)).thenReturn(Set.of(lesson));

        mockMvc.perform(get("/students/allLessons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("math"))
                .andExpect(jsonPath("$[0].description").value("test"));
    }
}
