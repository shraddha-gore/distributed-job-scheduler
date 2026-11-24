package com.shraddha.job_scheduler.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@ConfigurationProperties(prefix = "scheduler")
@Getter
@Setter
public class SchedulerProperties {
    private int batchSize;
    private Duration leaseDuration;
    private Duration fixedDelay;
}
