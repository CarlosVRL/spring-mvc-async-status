package com.cif.service;

import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class StatusQueueService {

    private final AtomicLong jobId;

    private final BlockingQueue<String> queue;

    //
    // Constructor
    //

    public StatusQueueService() {
        jobId = new AtomicLong(0);
        queue = new ArrayBlockingQueue<>(1000);
        addJob("init");
        addJob("init");
        addJob("init");
    }

    //
    // API
    //

    public Iterator<String> iterator() {
        return queue.iterator();
    }

    public boolean add(String e) {
        return addJob(e);
    }

    public String take() {
        String res = null;
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

    private boolean addJob(String e) {
        return queue.add("job-" + jobId.incrementAndGet() + ":" + e);
    }
}
