package com.demo.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadCourse {

    public static void main(String[] args) {
        System.out.print("XYZ");
        Shopper shopper = new Shopper();
        // same runnable can be used by multiple threads
        Thread user1 = new Thread(shopper, "User1");
        Thread user2 = new Thread(shopper, "User2");

        user1.start();
        user2.start();

        try {
            // main thread will wait till user1 & user2 thread are not terminated
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished Shopping");
    }


    static class Shopper implements Runnable{
        static int garlicCount;
        static Lock pencil = new ReentrantLock();

        // note : here synchronize method is static -> both threads will call incrementGarlic() associated with the CLASS;
        // if static not present -> both threads will call method associated with their objects -> inconsistent result;
        public static synchronized  void incrementGarlic(){
            garlicCount++;
        }

        @Override
        public void run() {
            pencil.lock();
            for(int i=0; i<10; i++) {
                System.out.println("Thread " + Thread.currentThread().getName() + "is incrementing the garlic count" + garlicCount);
                garlicCount++;
                // or instead of locks, use synchronised method incrementGarlic()
            }
            pencil.unlock();
        }

        // another way to solve the above issue is to user AtmoicVaribales in java(as for now we have only one variable/resource )
    }
}
