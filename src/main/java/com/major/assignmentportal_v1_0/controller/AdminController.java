package com.major.assignmentportal_v1_0.controller;

import com.major.assignmentportal_v1_0.entities.Admin;
import com.major.assignmentportal_v1_0.entities.Student;
import com.major.assignmentportal_v1_0.entities.Teacher;
import com.major.assignmentportal_v1_0.repository.AdminRepo;
import com.major.assignmentportal_v1_0.repository.StudentRepo;
import com.major.assignmentportal_v1_0.repository.TeacherRepo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
                            
@Controller
@RequestMapping("/assignment_portal")
public class AdminController {
    @Autowired
    private AdminRepo adminRepo;
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private StudentRepo studentRepo;


    @GetMapping("load_admin_login_form")
    public String loadLoginForm(Model model) {
        Admin admin = new Admin();
        model.addAttribute("admin", admin);
        return "html/admin_login";
    }

    @PostMapping("admin_login")
    public String login(@ModelAttribute("admin") Admin admin, Model model) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(admin.getAdmin_password(), adminRepo.findById(admin.getAdmin_id()).orElse(new Admin()).getAdmin_password())) {
            System.out.println("credential matches");
            model.addAttribute("admin_id", admin.getAdmin_id());
        }
        return "html/admin_dashboard";
    }

    @GetMapping("view_credential_details")
    public String loadCredentialDetails(Model model) {
        List<Teacher> teacherList = teacherRepo.findAll();
        List<Student> studentList = studentRepo.findAll();

        List<Credentials> credentialsList = new ArrayList<>();

        for (int i = 0; i < teacherList.size(); i++) {
            credentialsList.add(new Credentials(teacherList.get(i).getTch_id(), "", teacherList.get(i).getTch_name(), teacherList.get(i).getSubject_name()));
        }

        for (int i = 0; i < studentList.size(); i++) {
            credentialsList.add(new Credentials(studentList.get(i).getStd_id(), "", studentList.get(i).getStd_name(), studentList.get(i).getSubject_name()));
        }

        model.addAttribute("credentials", credentialsList);

        for (int i = 0; i < credentialsList.size(); i++) {
            System.out.println(credentialsList.get(i).getId());
        }

        return "html/admin_view_credentials";
    }

    @GetMapping("admin_delete_user/{user_id}")
    public String deleteUser(@PathVariable String user_id, Model model) {

        studentRepo.deleteById(user_id);
        teacherRepo.deleteById(user_id);

        List<Teacher> teacherList = teacherRepo.findAll();
        List<Student> studentList = studentRepo.findAll();

        List<Credentials> credentialsList = new ArrayList<>();

        for (int i = 0; i < teacherList.size(); i++) {
            credentialsList.add(new Credentials(teacherList.get(i).getTch_id(), "", teacherList.get(i).getTch_name(), teacherList.get(i).getSubject_name()));
        }

        for (int i = 0; i < studentList.size(); i++) {
            credentialsList.add(new Credentials(studentList.get(i).getStd_id(), "", studentList.get(i).getStd_name(), studentList.get(i).getSubject_name()));
        }

        model.addAttribute("credentials", credentialsList);

        for (int i = 0; i < credentialsList.size(); i++) {
            System.out.println(credentialsList.get(i).getId());
        }

        return "html/admin_view_credentials";
    }
}


@Data
@AllArgsConstructor
@NoArgsConstructor
class Credentials {
    private String id;
    private String pass;
    private String user_name;
    private String sub_name;
}