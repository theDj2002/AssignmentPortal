package com.major.assignmentportal_v1_0.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teacher_credential")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    private String tch_id;
    private String tch_name;
    private String tch_password;
    private String subject_name;

    @OneToMany(mappedBy = "teacher",cascade = CascadeType.ALL)
    private List<StudentAssignment> studentAssignmentList = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<TeacherAssignment> teacherAssignmentList = new ArrayList<>();

    @ManyToOne
    private Admin admin;

}
