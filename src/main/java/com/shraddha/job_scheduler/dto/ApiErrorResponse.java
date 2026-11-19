
package com.shraddha.job_scheduler.dto;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldError> fieldErrors
) {
    public ApiErrorResponse(
            Instant timestamp,
            int status,
            String error,
            String message,
            String path
    ) {
        this(timestamp, status, error, message, path, List.of());
    }

    public ApiErrorResponse {
        fieldErrors = fieldErrors == null ? List.of() : List.copyOf(fieldErrors);
    }

    public record FieldError(String field, String message) {
    }
}
