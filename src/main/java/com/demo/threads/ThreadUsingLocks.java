package com.demo.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadUsingLocks {
    public static void main(String[] args) {
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
            System.out.println("Final Garlic Count is " + Shopper.garlicCount + ",  Onion Count is " + Shopper.onionCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Finished Shopping");
    }


    static class Shopper implements Runnable {
        static int garlicCount = 0, onionCount = 0;
        static Lock pencil = new ReentrantLock();

        private void addOnionCount() {
            pencil.lock();
            onionCount++;
            pencil.unlock();
        }

        //NOTE: constraint is with garlic you have to buy 1 onion also
        private void addGarlicCount() {
            pencil.lock();
            garlicCount++;
            addOnionCount();
            pencil.unlock();
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                System.out.println("Thread " + Thread.currentThread().getName() + "is incrementing the garlic count" + garlicCount);
                addOnionCount();
                addGarlicCount();
            }
        }
    }
}
