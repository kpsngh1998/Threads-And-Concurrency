package com.demo;

public class ProducerConsumerApp {

    public static void main(String[] args) {
            Resource r = new Resource(0 , false);
            Producer p = new Producer(r);
            Consumer c = new Consumer(r);
            p.run();
            c.run();
    }

    static class Resource{
        int x;
        boolean isSet;

        public Resource(int x, boolean isSet){
            this.x = x;
            this.isSet = isSet;
        }
        boolean isSet(){
            return isSet;
        }

        public void setIsSet(boolean flag){
            this.isSet = flag;
        }

        public void set(int x){
            this.x = x;
            System.out.println("Set: " + this.x);
        }
        public void get(){
            System.out.println("Get: "  + x);
        }

    }
    static class Producer implements Runnable{

        Resource resource;
        int i=0;
        boolean isSet = false;
        public Producer(Resource r){
            this.resource = r;
        }

        @Override
        public synchronized void run() {
            while(true) {

                while (resource.isSet() == true) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                resource.set(i++);
                resource.setIsSet(true);
                notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
        }
    }

    static class Consumer implements Runnable{

        Resource resource;
        public Consumer(Resource r){
            this.resource = r;
        }

        @Override
        public synchronized void run() {
            while(true) {
                while (resource.isSet() == false) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                resource.get();
                resource.setIsSet(false);
                notify();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
