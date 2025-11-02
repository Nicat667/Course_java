package com.example.newtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String email;

    @ManyToMany(mappedBy = "students")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Lesson> lessons = new LinkedHashSet<>();

//    public void addLesson(Lesson lesson) {
//        lessons.add(lesson);
//        lesson.getStudents().add(this);
//    }
//
//    public void removeLesson(Lesson lesson) {
//        lessons.remove(lesson);
//        lesson.getStudents().remove(this);
//    }
}
