package com.shinhoandroid.thread.handler;

import android.os.Handler;
import android.os.Message;

/**
 * @author Liupengfei
 * @describe TODO
 * @date on 2019/8/8 16:32
 */
public class TestHandler {



    public void test() {
        MainThread mainThread = new MainThread();
        mainThread.start();

        while (null == MyLooper.getMainLooper()) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        final MyHandler handler = new MyHandler(MyLooper.getMainLooper()) {
            @Override
            public void handleMessage(MyMessage msg) {
                switch (msg.getCode()) {
                    case 1:
                        System.out.print(msg.getMsg() + "在" + Thread.currentThread().getName() + "上执行！\r\n");
                        break;
                    case 2:
                        System.out.print(msg.getMsg() + "在" + Thread.currentThread().getName() + "上执行！\r\n");
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyMessage myMessage = new MyMessage(1, "子线程消息1");
                handler.sendMessage(myMessage);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                MyMessage myMessage2 = new MyMessage(2, "子线程消息2");
                handler.sendMessage(myMessage2);
            }
        }).start();
    }

    /**
     * 主线程
     */
    public class MainThread extends Thread {
        public MainThread() {
            setName("MainThread");
        }

        @Override
        public void run() {
            MyLooper.prepareMainLooper();
            System.out.print(getName() + "has been prepared.\r\n");
            MyLooper.loop();
        }
    }

}
