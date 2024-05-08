package com.major.assignmentportal_v1_0.repository;

import com.major.assignmentportal_v1_0.entities.StudentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface StudentAssignmentRepo extends JpaRepository<StudentAssignment, String> {
//    Optional<StudentAssignment> findByAssignment_id(String s);
}
