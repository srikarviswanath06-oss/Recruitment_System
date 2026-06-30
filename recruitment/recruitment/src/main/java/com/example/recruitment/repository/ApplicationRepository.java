package com.example.recruitment.repository;

import com.example.recruitment.entity.Application;
import com.example.recruitment.entity.User;
import com.example.recruitment.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUserId(Long userId);

    boolean existsByUserAndJob(User user, Job job);

    long countByStatus(String status);
}
