package com.shinhoandroid;

import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.shinhoandroid.thread.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("Thread started!");
                Looper.prepare();
                Looper.loop();
                System.out.println("Thread stop!");

            }
        };
        thread.start();

    }
}
