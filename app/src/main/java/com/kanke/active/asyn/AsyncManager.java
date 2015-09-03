package com.kanke.active.asyn;

import android.os.Handler;

import com.kanke.active.model.CrashLogModel;

/**
 * Created by Administrator on 2015/9/2.
 */
public class AsyncManager {
    /**
     * 崩溃日志
     * Created by Ganker on 2015/7/11.
     */
    public static void startErrorLogTask(CrashLogModel model){
        CrashLogTask crashLogTask = new CrashLogTask();
        crashLogTask.mHandler = new Handler();
        crashLogTask.model = model;
        crashLogTask.execute();
    }
}
