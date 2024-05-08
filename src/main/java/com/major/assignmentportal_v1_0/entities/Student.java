package com.major.assignmentportal_v1_0.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student_credential")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    private String std_id;
    private String std_name;
    private String std_password;
    private String subject_name;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentAssignment> studentAssignmentList = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<TeacherAssignment> teacherAssignmentList = new ArrayList<>();

    @ManyToOne
    private Admin admin;
}