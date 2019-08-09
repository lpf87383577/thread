package com.shinhoandroid.thread;

public class WaitDemo implements TestDemo {
    private String sharedString;

    private synchronized void initString() {
        sharedString = "rengwuxian";
        notifyAll();
        notify();
    }

    private synchronized void printString() {
        while (sharedString == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("String: " + sharedString);
    }

    @Override
    public void runTest() {
        final Thread thread1 = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread1--"+System.currentTimeMillis());
                printString();
            }
        };
        thread1.start();
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                //用了yield方法后，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是说本来自己执行的，现在大家公平竞争）
                Thread.yield();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread2--"+System.currentTimeMillis());
                initString();
            }
        };
        thread2.start();
    }
}