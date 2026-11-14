CREATE TABLE jobs (
    id UUID PRIMARY KEY,

    job_type VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL,

    status VARCHAR(20) NOT NULL,

    scheduled_at TIMESTAMPTZ NOT NULL,
    next_run_at TIMESTAMPTZ NOT NULL,

    attempt_count INTEGER NOT NULL DEFAULT 0,
    max_attempts INTEGER NOT NULL DEFAULT 3,

    worker_id UUID,
    lease_expires_at TIMESTAMPTZ,
    last_heartbeat_at TIMESTAMPTZ,

    fencing_token BIGINT NOT NULL DEFAULT 0,

    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_jobs_status
        CHECK (status IN (
            'PENDING',
            'RUNNING',
            'SUCCEEDED',
            'FAILED',
            'CANCELLED'
        )),

    CONSTRAINT chk_jobs_attempt_count
        CHECK (
            attempt_count >= 0
            AND attempt_count <= max_attempts
        ),

    CONSTRAINT chk_jobs_max_attempts
        CHECK (max_attempts > 0),

    CONSTRAINT chk_jobs_fencing_token
        CHECK (fencing_token >= 0)
);

CREATE INDEX idx_jobs_pending_next_run
    ON jobs (next_run_at)
    WHERE status = 'PENDING';