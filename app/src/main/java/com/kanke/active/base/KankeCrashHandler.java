/*******************************************************************************
 * @(#)MyCrashHandler.java 2012-6-21
 *
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.kanke.active.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.kanke.active.asyn.AsyncManager;
import com.kanke.active.model.CrashLogModel;
import com.kanke.active.util.DateUtil;
import com.kanke.active.util.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;

/**
 * 自定义异常类
 * 
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-15 上午11:19:31
 */
public class KankeCrashHandler implements UncaughtExceptionHandler {
    // 需求是 整个应用程序 只有一个 MyCrash-Handler
    private static KankeCrashHandler myCrashHandler;

    private Context context;

    private UncaughtExceptionHandler ThreadEx;

    // 1.私有化构造方法
    private KankeCrashHandler() {
    }

    public static synchronized KankeCrashHandler getInstance() {
        if (myCrashHandler == null)
            myCrashHandler = new KankeCrashHandler();

        return myCrashHandler;

    }

    public void init(Context context) {
        this.context = context;
        ThreadEx = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread arg0, Throwable arg1) {
        if (!handleException(arg1) && ThreadEx != null)
            ThreadEx.uncaughtException(arg0, arg1);
        // 干掉当前的程序
        //android.os.Process.killProcess(android.os.Process.myPid());
        KankeApplication.mInstance.exitSystem();
        //ContextUtil.alterActivity(context, MainActivity.class);
        ThreadEx.uncaughtException(arg0, arg1);
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return true;
        }
        // 1.获取当前程序的版本号. 版本的id
        String versioninfo = getVersionInfo();
        // 2.获取手机的硬件信息.
        String mobileInfo = getMobileInfo();
        // 3.把错误的堆栈信息 获取出来
        String errorinfo = getErrorInfo(ex);
        // 4.把所有的信息 还有信息对应的时间 提交到服务器
        StringBuilder errorDetails = new StringBuilder();
        errorDetails.append("GIGHT TO END, 日期：").append(DateUtil.formatCurrentDate(DateUtil.YYYY_MM_DD_HH_MM_SS))
                .append("程序版本号：").append(versioninfo).append("手机硬件信息：").append(mobileInfo).append("错误信息：")
                .append(errorinfo);
        Logger.w(getClass(), errorDetails.toString());
        CrashLogModel model = new CrashLogModel();
        model.LogMsg = errorDetails.toString();
        model.Version = versioninfo;
        AsyncManager.startErrorLogTask(model);
        return true;
    }

    /**
     * 获取错误的信息
     * 
     * @param arg1
     * @return
     */
    private String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        return error;
    }

    /**
     * 获取手机的硬件信息
     * 
     * @return
     */
    private String getMobileInfo() {
        StringBuffer sb = new StringBuffer();
        // 通过反射获取系统的硬件信息
        try {
            Field[] fields = Build.class.getDeclaredFields();
            for (Field field : fields) {
                // 暴力反射 ,获取私有的信息
                field.setAccessible(true);
                String name = field.getName();
                String value = field.get(null).toString();
                sb.append(name + "=" + value);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取手机的版本信息
     * 
     * @return
     */
    private String getVersionInfo() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            return "版本号未知";
        }
    }
}
