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
import java.util.concurrent.Future;

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
        statusQueueService
            .iterator()
            .forEachRemaining(statusJob -> {
                System.out.println(statusJob);
                status.add(statusJob);
            });
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PostMapping("/status/add-job")
    @Timed
    public ResponseEntity<Void> addJob() {
        statusQueueService.add("test");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/status/take-job")
    @Timed
    public ResponseEntity<StatusJob> takeJob() {
        StatusJob res = statusQueueService.take();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping("/status/execute")
    @Timed
    public ResponseEntity<Long> execute() {
        Future<String> future = statusQueueService.add("my job");



//        StatusJob service = new StatusJob();
//        Future<String> future = statusExecutor.submit(service);
//        BlockingQueue<Runnable> queue = statusExecutor.getQueue();
//        queue.contains(service);
//        Runnable r  = queue.take();

//        try {
//            result = future.get();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
        return new ResponseEntity<>(0L, HttpStatus.OK);
    }
}
