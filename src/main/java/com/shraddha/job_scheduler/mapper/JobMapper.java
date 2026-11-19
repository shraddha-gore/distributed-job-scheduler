package com.shraddha.job_scheduler.mapper;

import com.shraddha.job_scheduler.dto.JobResponse;
import com.shraddha.job_scheduler.entity.Job;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {
    public JobResponse toResponse(Job job) {
        return new JobResponse(job.getId(),
                job.getJobType(),
                job.getStatus(),
                job.getScheduledAt(),
                job.getCreatedAt());

    }
}
