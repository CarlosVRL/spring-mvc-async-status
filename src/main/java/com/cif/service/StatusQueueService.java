package com.cif.service;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class StatusQueueService {

    private final AtomicLong jobId;

    private final BlockingQueue<StatusJob> queue;

    private final ExecutorService statusExecutor;

    //
    // Constructor
    //

    public StatusQueueService(ExecutorService statusExecutor) {
        this.statusExecutor = statusExecutor;
        queue = new ArrayBlockingQueue<>(1000);
        jobId = new AtomicLong(0L);
        // test data
        add("job1");
        add("job2");
        add("job3");
    }

    //
    // API
    //

    public Iterator<StatusJob> iterator() {
        return queue.iterator();
    }

    public Future<String> add(String msg) {
        return addJob(msg);
    }

    public StatusJob take() {
        StatusJob res = null;
        try {
            res = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }

    //
    // Implementation
    //

    private Future<String> addJob(String msg) {
        Long id = jobId.incrementAndGet();
        StatusJob statusJob = new StatusJob(id, msg);
        queue.add(statusJob);
        return statusExecutor.submit(statusJob);
    }
}
