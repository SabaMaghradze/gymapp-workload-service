package com.gymapp.workload_service.exception;

public record ErrorResponse(int status, String message, String path) {
}
