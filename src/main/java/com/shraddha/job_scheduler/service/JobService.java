package com.shraddha.job_scheduler.service;

import com.shraddha.job_scheduler.dto.JobRequest;
import com.shraddha.job_scheduler.dto.JobResponse;
import com.shraddha.job_scheduler.entity.Job;
import com.shraddha.job_scheduler.entity.JobType;
import com.shraddha.job_scheduler.exception.InvalidJobScheduleException;
import com.shraddha.job_scheduler.mapper.JobMapper;
import com.shraddha.job_scheduler.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Transactional
    public JobResponse submitJob(JobRequest request) {
        JobType requestJobType = request.jobType();
        Map<String, Object> requestPayload = request.payload();
        Instant requestScheduledAt = request.scheduledAt();
        Instant now = Instant.now();

        if (requestScheduledAt.isBefore(now) || requestScheduledAt.equals(now)) {
            throw new InvalidJobScheduleException("Job must be scheduled in the future");
        }

        Job job = Job.create(requestJobType, requestPayload, requestScheduledAt);

        Job submittedJob = jobRepository.save(job);

        return jobMapper.toResponse(submittedJob);
    }
}
