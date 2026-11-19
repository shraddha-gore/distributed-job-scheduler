package com.shraddha.job_scheduler.controller;

import com.shraddha.job_scheduler.dto.JobRequest;
import com.shraddha.job_scheduler.dto.JobResponse;
import com.shraddha.job_scheduler.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobResponse> submitJob(@Valid @RequestBody JobRequest request) {
        JobResponse response = jobService.submitJob(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
