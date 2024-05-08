package com.major.assignmentportal_v1_0.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "admin")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    private String admin_id;
    private String admin_password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Student> studentList = new ArrayList<>();

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Teacher> teachersList = new ArrayList<>();
}
