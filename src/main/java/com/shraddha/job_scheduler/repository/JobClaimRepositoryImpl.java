package com.shraddha.job_scheduler.repository;

import com.shraddha.job_scheduler.entity.Job;
import com.shraddha.job_scheduler.entity.JobStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class JobClaimRepositoryImpl implements JobClaimRepository {
    private final EntityManager entityManager;
    private static final String SELECT_DUE_JOBS_FOR_CLAIM_SQL = """
            SELECT *
            FROM jobs
            WHERE status = 'PENDING'
              AND next_run_at <= :now
            ORDER BY next_run_at ASC
            LIMIT :batchSize
            FOR UPDATE SKIP LOCKED
            """;

    @Override
    @Transactional
    public List<Job> claimDueJobs(UUID workerId, Instant now, Instant leaseExpiresAt, int batchSize) {
        Query query = entityManager.createNativeQuery(SELECT_DUE_JOBS_FOR_CLAIM_SQL, Job.class);

        query.setParameter("now", now);
        query.setParameter("batchSize", batchSize);

        List<Job> jobs = query.getResultList()
                .stream()
                .map(Job.class::cast)
                .toList();

        for (Job job : jobs) {
            job.setStatus(JobStatus.RUNNING);
            job.setWorkerId(workerId);
            job.setLeaseExpiresAt(leaseExpiresAt);
            job.setFencingToken(job.getFencingToken() + 1);
        }

        return jobs;
    }
}
