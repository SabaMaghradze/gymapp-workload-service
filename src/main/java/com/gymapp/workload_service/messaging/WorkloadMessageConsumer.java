package com.gymapp.workload_service.messaging;

import com.gymapp.workload_service.model.WorkloadRequest;
import com.gymapp.workload_service.service.WorkloadService;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkloadMessageConsumer {

    private final WorkloadService workloadService;

    @JmsListener(destination = "workload.queue")
    public void receiveMessage(WorkloadRequest req) {
        workloadService.processWorkload(req);
    }

}
