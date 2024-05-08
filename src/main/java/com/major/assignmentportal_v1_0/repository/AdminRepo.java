package com.major.assignmentportal_v1_0.repository;

import com.major.assignmentportal_v1_0.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin, String> {}
