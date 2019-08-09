package com.shinhoandroid.thread;

public class Synchronized1Demo implements TestDemo {

    private int x = 0;
    private int y = 0;

    private void count(int newValue) {
        x = newValue;
        y = newValue;
        if (x != y) {
            System.out.println("x: " + x + ", y:" + y);
        }
    }

    @Override
    public void runTest() {
        new Thread() {
            @Override
            public void run() {
                System.out.println("线程一开始了"+System.currentTimeMillis());
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
                System.out.println("线程一结束了"+System.currentTimeMillis());
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                System.out.println("线程二开始了"+System.currentTimeMillis());
                for (int i = 0; i < 1_000_000_000; i++) {
                    count(i);
                }
                System.out.println("线程二结束了"+System.currentTimeMillis());
            }
        }.start();
    }
}
