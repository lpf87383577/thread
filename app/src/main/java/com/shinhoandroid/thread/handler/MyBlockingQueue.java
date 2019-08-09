package com.shinhoandroid.thread.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class MyBlockingQueue implements IMyMessageQueue {
    private BlockingQueue<MyMessage> mQueue;

    public MyBlockingQueue(int init) {
        this.mQueue = new LinkedBlockingDeque<>(init);
    }

    @Override
    public MyMessage next() throws InterruptedException {
        return mQueue.take();
    }

    @Override
    public void enqueueMsg(MyMessage msg) throws InterruptedException {
        mQueue.put(msg);
    }

}
