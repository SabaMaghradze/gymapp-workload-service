package com.gymapp.workload_service.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymapp.common.WorkloadRequest;
import com.gymapp.workload_service.service.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkloadMessageConsumer {

    private final WorkloadService workloadService;
    private final ObjectMapper objectMapper;

    @JmsListener(destination = "workload.queue")
    public void receiveMessage(String jsonMessage) {
        try {
            WorkloadRequest workloadRequest = objectMapper.readValue(jsonMessage, WorkloadRequest.class);
            workloadService.processWorkload(workloadRequest);
        } catch (Exception e) {
            throw new RuntimeException("Invalid message payload", e);
        }
    }
}
