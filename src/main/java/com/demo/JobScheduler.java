package com.demo;

import java.util.Comparator;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;

public class JobScheduler {
    private Executor jobScheduleExecutor = Executors.newSingleThreadExecutor();
    private Executor jobsPoolExecutor;
    private PriorityBlockingQueue<Job> priorityBlockingQueue;


    public JobScheduler(Integer poolSize, Integer queueSize){
        jobsPoolExecutor = Executors.newFixedThreadPool(poolSize);
        priorityBlockingQueue = new PriorityBlockingQueue<Job>(queueSize, Comparator.comparing(Job::getJobPriority));

        jobScheduleExecutor.execute(() -> {
            while(true){
                System.out.println("Inside whilte Loop");
                try {
                    jobsPoolExecutor.execute(priorityBlockingQueue.take());
                }catch(Exception e){
                    break;
                }
            }
        });

    }
    public void scheduleJob(Job j){
        priorityBlockingQueue.add(j);
    }


}
