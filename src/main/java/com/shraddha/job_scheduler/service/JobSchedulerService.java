package com.shraddha.job_scheduler.service;

import com.shraddha.job_scheduler.config.SchedulerProperties;
import com.shraddha.job_scheduler.entity.Job;
import com.shraddha.job_scheduler.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class JobSchedulerService {
    private final JobRepository jobRepository;
    private final SchedulerProperties schedulerProperties;
    private final UUID workerId = UUID.randomUUID();

    @Scheduled(fixedDelayString = "${scheduler.fixed-delay}")
    public void pollAndClaimJobs() {
        Instant now = Instant.now();
        Instant leaseExpiresAt = now.plus(schedulerProperties.getLeaseDuration());
        List<Job> claimedJobs = jobRepository.claimDueJobs(workerId, now, leaseExpiresAt, schedulerProperties.getBatchSize());

        if (!claimedJobs.isEmpty()) {
            log.info("Jobs claimed: {}", claimedJobs.size());
        }
    }
}
