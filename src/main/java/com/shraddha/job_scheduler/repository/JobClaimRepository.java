package com.shraddha.job_scheduler.repository;

import com.shraddha.job_scheduler.entity.Job;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface JobClaimRepository {
    List<Job> claimDueJobs(
            UUID workerId,
            Instant now,
            Instant leaseExpiresAt,
            int batchSize
    );
}