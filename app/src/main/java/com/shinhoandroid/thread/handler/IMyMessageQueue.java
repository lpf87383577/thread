package com.shinhoandroid.thread.handler;


public interface IMyMessageQueue {
    MyMessage next() throws InterruptedException;
    void enqueueMsg(MyMessage msg) throws InterruptedException;
}
