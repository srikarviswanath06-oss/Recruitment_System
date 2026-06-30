package com.example.recruitment.dto;

public class ApplicationResponseDTO {

    private Long id;
    private String candidateName;
    private String jobTitle;
    private String status;
    private String resumeFileName;

    public ApplicationResponseDTO(Long id,
                                  String candidateName,
                                  String jobTitle,
                                  String status,
                                  String resumeFileName) {
        this.id = id;
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.status = status;
        this.resumeFileName = resumeFileName;
    }

    public Long getId() { return id; }
    public String getCandidateName() { return candidateName; }
    public String getJobTitle() { return jobTitle; }
    public String getStatus() { return status; }
    public String getResumeFileName() { return resumeFileName; }
}
