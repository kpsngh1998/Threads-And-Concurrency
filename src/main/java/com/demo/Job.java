package com.demo;

public class Job implements  Runnable{
    String jobName;
    JobPriority jobPriority;

    public Job(String jobName, JobPriority jobPriority){
        this.jobName = jobName;
        this.jobPriority = jobPriority;
    }

    @Override
    public void run() {
        System.out.println("Job: " + jobName + " Job Priority: " + jobPriority);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public JobPriority getJobPriority(){
        return this.jobPriority;
    }
}
