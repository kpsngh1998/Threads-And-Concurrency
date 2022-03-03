package com.demo;

public class JobSchedulerTest {
    public static void main(String[] args){
        Job j1 = new Job("Job1", JobPriority.HIGH);
        Job j2 = new Job("Job2", JobPriority.LOW);
        Job j3 = new Job("Job3", JobPriority.HIGH);
        Job j4 = new Job("Job4", JobPriority.HIGH);
        Job j5 = new Job("Job5", JobPriority.LOW);
        Job j6 = new Job("Job6", JobPriority.MEDIUM);
        Job j7 = new Job("Job7", JobPriority.MEDIUM);
        Job j8 = new Job("Job8", JobPriority.LOW);
        Job j9 = new Job("Job9", JobPriority.MEDIUM);

        JobScheduler jobScheduler = new JobScheduler(10, 3);

        jobScheduler.scheduleJob(j1);
        jobScheduler.scheduleJob(j2);
        jobScheduler.scheduleJob(j3);
        jobScheduler.scheduleJob(j4);
        jobScheduler.scheduleJob(j5);
        jobScheduler.scheduleJob(j6);
        jobScheduler.scheduleJob(j7);
        jobScheduler.scheduleJob(j8);
        jobScheduler.scheduleJob(j7);
    }
}
