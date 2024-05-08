package com.major.assignmentportal_v1_0.repository;

import com.major.assignmentportal_v1_0.entities.TeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeacherAssignmentRepo extends JpaRepository<TeacherAssignment, String> {

}
