package com.cif.service;

import com.cif.domain.JobStatus;

import javax.print.attribute.standard.JobState;
import java.util.concurrent.Callable;

/**
 * StatusJob simulates out a "long-running" method
 */
public class StatusJob implements Callable<String> {

    private final Long id;

    private final String msg;

    private JobStatus state;

    public StatusJob(Long id, String msg) {
        this.id = id;
        this.msg = msg;
        this.state = JobStatus.PENDING;
    }

    //
    // API
    //

//    @Override
//    public void run() {
//        System.out.println("starting execute task id : " + id);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("finished execute task id : " + id);
//        //return "result";
//    }

    @Override
    public String call() throws Exception {
        state = JobStatus.PROCESSING;
        System.out.println("starting execute task id : " + id);
        Thread.sleep(10000);
        System.out.println("finished execute task id : " + id);
        state = JobStatus.COMPLETED;
        return "result";
    }

    //
    // Accessors
    //

    public Long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public JobStatus getState() {
        return state;
    }

    @Override
    public String toString() {
        return "StatusJob{" +
            "id=" + id +
            ", msg='" + msg + '\'' +
            '}';
    }
}
