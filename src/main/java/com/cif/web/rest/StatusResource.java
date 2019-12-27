package com.cif.web.rest;

import com.cif.service.StatusJob;
import com.cif.service.StatusQueueService;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StatusResource {

    private final StatusQueueService statusQueueService;

    public StatusResource(
        StatusQueueService statusQueueService
    ) {
        this.statusQueueService = statusQueueService;
    }

    @GetMapping("/status")
    @Timed
    public ResponseEntity<List<StatusJob>> getStatus() {
        List<StatusJob> status = new ArrayList<>();
        statusQueueService.iterator()
            .forEachRemaining(longStatusJobEntry ->
                status.add(longStatusJobEntry.getValue())
            );
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/status/add-job")
    @Timed
    public ResponseEntity<Void> addJob() {
        statusQueueService.add("test");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/status/execute")
    @Timed
    public ResponseEntity<Long> execute() {
        statusQueueService.add("my job");
        return new ResponseEntity<>(0L, HttpStatus.OK);
    }

    @PostMapping("/status/execute-and-wait")
    @Timed
    public ResponseEntity<StatusJob> executeAndWait() throws Exception {
        StatusJob result = statusQueueService.add("execute-and-wait");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
