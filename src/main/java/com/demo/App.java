package com.demo;

import java.util.concurrent.locks.ReentrantLock;

public class App {

    private static void print(){
        System.out.println("From Thread : " + Thread.currentThread().getId());
    }

    private static void runMethod(Runnable r){
        System.out.print("Inside runMethod");
        r.run();
    }

    private static ReentrantLock lock = new ReentrantLock();

    private static void accessResource(){
        lock.lock();
        try {
            System.out.println("Accessed Resource by " + Thread.currentThread().getId());
        } finally {
            lock.unlock();
        }
     }

    public static void main(String[] args) {
        String s = "caat";
        String pattern = "c*t";
        System.out.print("Matching ? : " + isMatch(s, pattern));
        //runMethod(() -> print());
        /*Thread t1 = new Thread(() -> accessResource()); t1.run();
        Thread t2 = new Thread(() -> accessResource()); t2.run();
        Thread t3 = new Thread(() -> accessResource()); t3.run();
        Thread t4 = new Thread(() -> accessResource()); t4.run();
        */
    }

    public static boolean isMatch(String s, String p) {
        boolean[][] match=new boolean[s.length()+1][p.length()+1];
        match[s.length()][p.length()]=true;
        for(int i=p.length()-1;i>=0;i--){
            if(p.charAt(i)!='*')
                break;
            else
                match[s.length()][i]=true;
        }

        for(int i=0; i<=s.length(); i++){
            for(int j=0; j<=p.length(); j++){
                System.out.print(match[i][j] + " ");
            }
            System.out.println();
        }

        for(int i=s.length()-1;i>=0;i--){
            for(int j=p.length()-1;j>=0;j--){
                if(s.charAt(i)==p.charAt(j)||p.charAt(j)=='?')
                    match[i][j]=match[i+1][j+1];
                else if(p.charAt(j)=='*')
                    match[i][j]=match[i+1][j]||match[i][j+1];
                else
                    match[i][j]=false;
            }
        }
        return match[0][0];
    }

    static class Temp{

        public static void print(){
            System.out.println("This is Static Method");
        }
    }
}
