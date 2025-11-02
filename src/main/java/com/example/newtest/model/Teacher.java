package com.example.newtest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @OneToMany(mappedBy = "teacher",  cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new LinkedHashSet<>();

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setTeacher(this);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
        lesson.setTeacher(null);
    }
}
