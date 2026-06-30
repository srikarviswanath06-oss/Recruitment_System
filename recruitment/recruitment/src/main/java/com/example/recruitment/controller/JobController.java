package com.example.recruitment.controller;

import com.example.recruitment.entity.Job;
import com.example.recruitment.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
@CrossOrigin
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    // Get all jobs
    @GetMapping("/all")
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    // Add job (for admin testing)
    @PostMapping("/add")
    public Job addJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }
}
