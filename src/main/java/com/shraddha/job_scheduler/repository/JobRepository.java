package com.shraddha.job_scheduler.repository;

import com.shraddha.job_scheduler.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID>, JobClaimRepository {
}
