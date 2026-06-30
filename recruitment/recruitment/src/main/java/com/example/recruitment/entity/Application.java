package com.example.recruitment.entity;

import jakarta.persistence.*;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private String resumeFileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public Application() {}

    public Long getId() { return id; }
    public String getStatus() { return status; }
    public String getResumeFileName() { return resumeFileName; }
    public User getUser() { return user; }
    public Job getJob() { return job; }

    public void setId(Long id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
    public void setResumeFileName(String resumeFileName) { this.resumeFileName = resumeFileName; }
    public void setUser(User user) { this.user = user; }
    public void setJob(Job job) { this.job = job; }
}
