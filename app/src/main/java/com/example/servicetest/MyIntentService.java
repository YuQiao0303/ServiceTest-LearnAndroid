package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {
    /**
     * 首先要提供一个无参的构造函数
     */
    public MyIntentService() {
        super("MyIntentService"); // 调用父类的有参构造函数
    }

    /**
     * 在子线程中运行
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
    // 打印当前线程的id
        Log.d("MyIntentService", "Thread id is " + Thread.currentThread(). getId());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService", "onDestroy executed");
    }
}