package com.major.assignmentportal_v1_0;

import com.major.assignmentportal_v1_0.repository.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    @Autowired
    static AdminRepo adminRepo;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

}
