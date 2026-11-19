package com.shraddha.job_scheduler.dto;

import com.shraddha.job_scheduler.entity.JobStatus;
import com.shraddha.job_scheduler.entity.JobType;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(UUID id,
                          JobType jobType,
                          JobStatus status,
                          Instant scheduledAt,
                          Instant createdAt) {
}
