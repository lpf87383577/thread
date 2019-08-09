package com.shinhoandroid.thread;



import android.os.Looper;

import com.shinhoandroid.thread.handler.TestHandler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/8/8 11:14
 */
public class TestThread {

    public static void main(String[] args) {

        thread();
        //runnable();
        //executor();
        //带回调的线程
        //callable();
        //runSynchronized1Demo();
        //runSynchronized2Demo();
        //runSynchronized3Demo();
        //读写锁
        //runReadWriteLockDemo();

        //线程中断
        //runThreadInteractionDemo();
        //线程等待
        //runWaitDemo();
        //
        //runCustomizableThreadDemo();

        //new TestHandler().test();

    }

    private static void runSynchronized1Demo() {

        new Synchronized1Demo().runTest();
    }

    static void runSynchronized2Demo() {
        new Synchronized2Demo().runTest();
    }

    static void runSynchronized3Demo() {
        new Synchronized3Demo().runTest();
    }

    static void runReadWriteLockDemo() {
        new ReadWriteLockDemo().runTest();
    }


    //new一个线程执行
    private static void thread() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!");
            }
        };
        thread.start();
    }

    //new Runnable代替执行
    private static void runnable() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    //线程池执行
    private static void executor() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Thread with Runnable started!");
            }
        };


        Executor executor = Executors.newCachedThreadPool();
        executor.execute(runnable);
        executor.execute(runnable);
        executor.execute(runnable);



    }

    static void callable() {
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "Done!";
            }
        };
        System.out.println("---00000----"+System.currentTimeMillis());
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);
        try {
            String result = future.get();
            System.out.println("result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("---11111----"+System.currentTimeMillis());
    }

    static void runThreadInteractionDemo() {
        new ThreadInteractionDemo().runTest();
    }

    static void runWaitDemo() {
        new WaitDemo().runTest();

    }

    static void runCustomizableThreadDemo() {
        new CustomizableThreadDemo().runTest();
    }
}
