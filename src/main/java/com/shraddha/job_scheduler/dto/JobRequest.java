package com.shraddha.job_scheduler.dto;

import com.shraddha.job_scheduler.entity.JobType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Map;

public record JobRequest(@NotNull JobType jobType, @NotEmpty Map<String, Object> payload,
                         @NotNull Instant scheduledAt) {
}
