package com.example.recruitment.controller;

import com.example.recruitment.entity.*;
import com.example.recruitment.dto.ApplicationResponseDTO;
import com.example.recruitment.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/applications")
@CrossOrigin
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobRepository jobRepository;

    // ================= APPLY =================
    @PostMapping("/apply")
    public ResponseEntity<?> apply(
            @RequestParam Long userId,
            @RequestParam Long jobId,
            @RequestParam("resume") MultipartFile resume
    ) throws IOException {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = System.currentTimeMillis() + "_" + resume.getOriginalFilename();
        File dest = new File(uploadDir + fileName);
        resume.transferTo(dest);

        User user = userRepository.findById(userId).orElse(null);
        Job job = jobRepository.findById(jobId).orElse(null);

        if (user == null || job == null)
            return ResponseEntity.badRequest().body("Invalid user or job");

        if (applicationRepository.existsByUserAndJob(user, job))
            return ResponseEntity.badRequest().body("Already applied");

        Application app = new Application();
        app.setUser(user);
        app.setJob(job);
        app.setResumeFileName(fileName);
        app.setStatus("PENDING");

        applicationRepository.save(app);

        return ResponseEntity.ok("Applied Successfully");
    }

    // ================= HR GET ALL =================
    @GetMapping("/all")
    public List<ApplicationResponseDTO> getAll() {
        return applicationRepository.findAll()
                .stream()
                .map(app -> new ApplicationResponseDTO(
                        app.getId(),
                        app.getUser().getName(),
                        app.getJob().getTitle(),
                        app.getStatus(),
                        app.getResumeFileName()
                ))
                .collect(Collectors.toList());
    }

    // ================= GET BY USER =================
    @GetMapping("/user/{userId}")
    public List<ApplicationResponseDTO> getByUser(@PathVariable Long userId) {
        return applicationRepository.findByUserId(userId)
                .stream()
                .map(app -> new ApplicationResponseDTO(
                        app.getId(),
                        app.getUser().getName(),
                        app.getJob().getTitle(),
                        app.getStatus(),
                        app.getResumeFileName()
                ))
                .collect(Collectors.toList());
    }

    // ================= APPROVE =================
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approve(@PathVariable Long id) {
        Application app = applicationRepository.findById(id).orElse(null);
        if (app == null) return ResponseEntity.notFound().build();

        app.setStatus("APPROVED");
        applicationRepository.save(app);
        return ResponseEntity.ok("Approved");
    }

    // ================= REJECT =================
    @PutMapping("/reject/{id}")
    public ResponseEntity<?> reject(@PathVariable Long id) {
        Application app = applicationRepository.findById(id).orElse(null);
        if (app == null) return ResponseEntity.notFound().build();

        app.setStatus("REJECTED");
        applicationRepository.save(app);
        return ResponseEntity.ok("Rejected");
    }

    // ================= RESUME VIEW =================
    @GetMapping("/resume/{fileName}")
    public ResponseEntity<FileSystemResource> getResume(@PathVariable String fileName) {

        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        File file = new File(uploadDir + fileName);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        FileSystemResource resource = new FileSystemResource(file);

        return ResponseEntity.ok()
                .header("Content-Disposition", "inline; filename=\"" + file.getName() + "\"")
                .header("Content-Type", "application/pdf")
                .body(resource);
    }

}
