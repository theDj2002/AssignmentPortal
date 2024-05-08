package com.major.assignmentportal_v1_0.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "teacher_assignment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherAssignment{
    @Id
    private String assignment_id = getCurrentDateTimeAsInt();
    private String assignment_name;
//    private String tch_id;
    @Lob
    @Column(length = 10000000)
    private Byte[] assignment_file;
    private String comment;


    @ManyToOne
    private Student student;

    @ManyToOne
    private Teacher teacher;

    private static String getCurrentDateTimeAsInt() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return currentDateTime.format(formatter);
    }
}