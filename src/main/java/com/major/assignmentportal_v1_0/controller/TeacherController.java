package com.major.assignmentportal_v1_0.controller;

import com.major.assignmentportal_v1_0.entities.Admin;
import com.major.assignmentportal_v1_0.entities.StudentAssignment;
import com.major.assignmentportal_v1_0.entities.Teacher;
import com.major.assignmentportal_v1_0.entities.TeacherAssignment;
import com.major.assignmentportal_v1_0.repository.AdminRepo;
import com.major.assignmentportal_v1_0.repository.StudentAssignmentRepo;
import com.major.assignmentportal_v1_0.repository.TeacherAssignmentRepo;
import com.major.assignmentportal_v1_0.repository.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/assignment_portal")
public class TeacherController extends Teacher {
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private TeacherAssignmentRepo teacherAssignmentRepo;
    @Autowired
    private StudentAssignmentRepo studentAssignmentRepo;
    @Autowired
    private AdminRepo adminRepo;

    // default page

    @GetMapping("")
    public String home() {
        return "html/assignment_portal_home";
    }

    @GetMapping("load_teacher_registration_form")
    public String loadRegisterForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "html/teacher_registration";
    }

    @PostMapping("teacher_registration")
    public String register(@ModelAttribute("teacher") Teacher teacher) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        teacher.setTch_password(encoder.encode(teacher.getTch_password()));
        teacher.setAdmin(adminRepo.findById("admin").orElse(new Admin()));
        teacherRepo.save(teacher);
        return "html/assignment_portal_home";
    }

    @GetMapping("load_teacher_login_form")
    public String loadLoginForm(Model model) {
        Teacher teacher = new Teacher();
        model.addAttribute("teacher", teacher);
        return "html/teacher_login";
    }

    @PostMapping("teacher_login")
    public String login(@ModelAttribute("teacher") Teacher teacher, Model model) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(teacher.getTch_password(), teacherRepo.findById(teacher.getTch_id()).orElse(teacher).getTch_password())) {
            System.out.println("credential are correct !!");
            model.addAttribute("tch_id", teacher.getTch_id());
            return "html/teacher_dashboard";
        } else {
            System.out.println("credential are not correct !!");
            return "html/assignment_portal_home";
        }
    }

    @GetMapping("load_assignment_upload_form")
    public String loadAssignmentForm(Model model, @RequestParam("tch_id") String tch_id) {
        TeacherAssignment teacherAssignment = new TeacherAssignment();
        model.addAttribute("teacherAssignment", teacherAssignment);
        model.addAttribute("tch_id", tch_id);
        return "html/teacher_assignment_upload";
    }

    @PostMapping("teacher_assign")
    public String assign(@ModelAttribute("teacherAssignment") TeacherAssignment teacherAssignment,
                         @RequestParam("file") MultipartFile assignment_file,
                         @RequestParam("tch_id") String tch_id,
                         Model model) throws IOException {

        File file = new File("D:\\AllProjects\\Assignment Portal\\src\\main\\resources\\templates\\file\\temp.pdf");
        assignment_file.transferTo(file);
        byte[] byteFile = new byte[(int) file.length()];
        FileInputStream inputStream = new FileInputStream(file);
        inputStream.read(byteFile);
        inputStream.close();
        teacherAssignment.setAssignment_file(convertByteArrayToObjectArray(byteFile));

        teacherAssignment.setTeacher(teacherRepo.findById(tch_id).orElse(new Teacher()));

        model.addAttribute("tch_id", tch_id);

        System.out.println("assigned !!");
        teacherAssignmentRepo.save(teacherAssignment);
        return "html/teacher_dashboard";
    }

    @GetMapping("teacher_assignment_view")
    public String view(Model model, @RequestParam("tch_id") String tch_id) {
        List<TeacherAssignment> teacherAssignmentListResult = teacherRepo.findById(tch_id).orElse(new Teacher()).getTeacherAssignmentList();
        model.addAttribute("teacherAssignmentList", teacherAssignmentListResult);
        model.addAttribute("tch_id", tch_id);
        return "html/teacher_view_assignment";
    }

    @GetMapping("delete_uploaded_assignment/{assignment_id}")
    public String deleteAssignment(@PathVariable String assignment_id, Model model) {
        String tch_id = teacherAssignmentRepo.findById(assignment_id).orElse(new TeacherAssignment()).getTeacher().getTch_id();
        teacherAssignmentRepo.deleteById(assignment_id);
        List<TeacherAssignment> teacherAssignmentList = teacherAssignmentRepo.findAll();
        List<TeacherAssignment> teacherAssignmentListResult = teacherRepo.findById(tch_id).orElse(new Teacher()).getTeacherAssignmentList();
        model.addAttribute("teacherAssignmentList", teacherAssignmentListResult);
        model.addAttribute("tch_id", tch_id);
        return "html/teacher_view_assignment";
    }

    @GetMapping("evaluate_assignments")
    public String evaluateAssignment(Model model, @RequestParam("tch_id") String tch_id) {
        List<StudentAssignment> studentAssignmentList = studentAssignmentRepo.findAll();
        List<StudentAssignment> evaluationAssignmentList = new ArrayList<>();
        StudentAssignment studentAssignment = new StudentAssignment();

        for (StudentAssignment assignment : studentAssignmentList) {
            evaluationAssignmentList.add(assignment);
        }
        model.addAttribute("assignment", new StudentAssignment());
        model.addAttribute("evaluationAssignmentList", evaluationAssignmentList);
        model.addAttribute("tch_id", tch_id);

        return "html/evaluation_submitted_assignment";
    }

    @PostMapping("marking")
    public String marking(Model model,
                          @ModelAttribute("assignment") StudentAssignment studentAssignment,
                          @RequestParam("tch_id") String tch_id,
                          @RequestParam("assignment_id") String assignment_id) {


        List<StudentAssignment> studentAssignmentList = studentAssignmentRepo.findAll();

        List<StudentAssignment> evaluationAssignmentList = new ArrayList<>();

        for (StudentAssignment assignment : studentAssignmentList) {
            if (assignment.getAssignment_id().equals(assignment_id)) {
                assignment.setMark(studentAssignment.getMark());
                studentAssignmentRepo.save(assignment);
            }
            evaluationAssignmentList.add(assignment);
        }

        model.addAttribute("evaluationAssignmentList", evaluationAssignmentList);
        model.addAttribute("assignment", new StudentAssignment());

        model.addAttribute("tch_id", tch_id);

        return "html/evaluation_submitted_assignment";
    }


//    VIEW submitted assignments by his students


    @GetMapping("teacher_assignment_file/{assignment_id}")
    public ResponseEntity<ByteArrayResource> downloadAssignmentFile(@PathVariable String assignment_id) throws IOException {
        Byte[] assignmentFile_Byte = teacherAssignmentRepo.findById(assignment_id).orElse(new TeacherAssignment()).getAssignment_file();
        if (assignmentFile_Byte == null) {
            assignmentFile_Byte = studentAssignmentRepo.findById(assignment_id).orElse(new StudentAssignment()).getAssignment_file();
        }
        byte[] assignmentFile_byte = convertByte_to_byte(assignmentFile_Byte);
        ByteArrayResource resource = new ByteArrayResource(assignmentFile_byte);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=assignment.xps");
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }


//----------------------------------------------------------------------------

    private static Byte[] convertByteArrayToObjectArray(byte[] byteArray) {
        Byte[] objectArray = new Byte[byteArray.length];
        int i = 0;
        for (byte b : byteArray) {
            objectArray[i++] = b;
        }
        return objectArray;
    }

    private static byte[] convertByte_to_byte(Byte[] ByteArray) {
        byte[] byteArray = new byte[ByteArray.length];
        for (int i = 0; i < ByteArray.length; i++) {
            byteArray[i] = ByteArray[i];
        }
        return byteArray;
    }
}
