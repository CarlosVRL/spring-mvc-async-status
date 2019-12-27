package com.cif.service;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class StatusQueueService {

    private final AtomicLong jobId;

    private final ConcurrentHashMap<Long, StatusJob> queue;

    private final ExecutorService statusExecutor;

    //
    // Constructor
    //

    public StatusQueueService(ExecutorService statusExecutor) {
        this.statusExecutor = statusExecutor;
        queue = new ConcurrentHashMap<>();
        jobId = new AtomicLong(0L);
    }

    //
    // API
    //

    public Iterator<Map.Entry<Long, StatusJob>> iterator() {
        return queue.entrySet().iterator();
    }

    public StatusJob doWork(String msg) {
        return newJob(msg);
    }

    //
    // Implementation
    //

    private StatusJob newJob(String msg) {
        Long id = jobId.incrementAndGet();
        StatusJob statusJob = new StatusJob(id, msg);
        queue.put(id, statusJob);
        Future<StatusJob> future = statusExecutor.submit(statusJob);
        StatusJob result = null;
        try {
            result = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        queue.remove(id);
        return result;
    }
}
