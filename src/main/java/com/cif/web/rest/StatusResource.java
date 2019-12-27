package com.cif.web.rest;

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

    private StatusQueueService statusQueueService;

    public StatusResource(StatusQueueService statusQueueService) {
        this.statusQueueService = statusQueueService;
    }

    @GetMapping("/status")
    @Timed
    public ResponseEntity<List<String>> getStatus() {
        List<String> status = new ArrayList<>();
        statusQueueService
            .iterator()
            .forEachRemaining(status::add);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/status/add-job")
    @Timed
    public ResponseEntity<Boolean> addJob() {
        boolean res = statusQueueService.add("test");
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/status/take-job")
    @Timed
    public ResponseEntity<String> takeJob() {
        String res = statusQueueService.take();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
