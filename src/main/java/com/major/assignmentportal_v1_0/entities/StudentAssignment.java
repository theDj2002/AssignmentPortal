    package com.major.assignmentportal_v1_0.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student_assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAssignment {
    @Id
    private String assignment_id;
    private String assignment_name;
    @Lob
    @Column(length = 10000000)
    private Byte[] assignment_file;
    private String comment;
    private String mark = "evaluate soon";

    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

}
