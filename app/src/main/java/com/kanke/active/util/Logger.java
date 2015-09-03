/*******************************************************************************
 * @(#)Logger.java 2012-2-21
 *
 * Copyright 2012 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.kanke.active.util;

import android.os.Environment;
import android.util.Log;

import com.kanke.active.activity.BuildConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 日志记录类
 * 
 * @author <a href="mailto:wenxw@neusoft.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2012-12-27 上午10:51:49
 */
public final class Logger {
    private static final String DEFAULT_TAG = "COM.KANKE";

    private static final String NULL = "NULL";

    /* 日志级别描述定义 */
    private static final String VERBOSE = "VERBOSE", DEBUG = "DEBUG", INFO = "INFO", WARN = "WARN", ERROR = "ERROR";

    /* 日志级别 */
    private static final int L_VERBOSE = 1, L_DEBUG = 2, L_INFO = 3, L_WARN = 4, L_ERROR = 5, L_DEFAULT = 1,
            L_NO_WRITE_FILE = 6;

    private static String save_path, log_tag;

    private static int consol_level, save_level;
    static {
        try {
            InputStream inputStream = Logger.class.getResourceAsStream("/assets/logger.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
            consol_level = formatLogL(Integer.parseInt(properties.getProperty("consol_level")));
            save_level = formatLogL(Integer.parseInt(properties.getProperty("save_level")));
            save_path = properties.getProperty("save_path");
            log_tag = properties.getProperty("log_tag");
        } catch (FileNotFoundException e) {
            setDefaultValue();
            Log.e(DEFAULT_TAG, "未找到配置文件异常", e);
        } catch (IOException e) {
            setDefaultValue();
            Log.e(DEFAULT_TAG, "输入输出文件异常", e);
        } catch (Exception e) {
            setDefaultValue();
            Log.e(DEFAULT_TAG, "其他异常", e);
        }
    }

    private Logger() {

    }

    /**
     * 设置參數默认值
     */
    private static void setDefaultValue() {
        consol_level = L_DEFAULT;
        save_level = L_NO_WRITE_FILE;
        save_path = Environment.getExternalStorageState() + "/default.log";
        log_tag = DEFAULT_TAG;
    }

    /**
     * 输出日志 ERROR
     * 
     * @param logTag
     *            日志标签
     * @param objects
     *            日志详细信息
     */
    public static void e(String logTag, Object... objects) {
        String _logTag = (logTag == null ? log_tag : logTag);
        if (consol_level <= L_ERROR && BuildConfig.DEBUG)
            Log.e(_logTag, composeMsgs(objects));
        if (save_level <= L_ERROR)
            saveFile(ERROR, _logTag, composeMsgs(objects));
    }

    public static <T> void e(Class<T> cls, Object... objects) {
        e(cls.getName(), objects);
    }

    public static void e(Object... objects) {
        e(NULL, objects);
    }

    /**
     * 输出日志 WARN
     * 
     * @param logTag
     *            日志标签
     * @param objects
     *            日志详细信息
     */
    public static void w(String logTag, Object... objects) {
        String _logTag = (logTag == null ? log_tag : logTag);
        if (consol_level <= L_WARN && BuildConfig.DEBUG)
            Log.w(_logTag, composeMsgs(objects));
        if (save_level <= L_WARN)
            saveFile(WARN, _logTag, composeMsgs(objects));
    }

    public static <T> void w(Class<T> cls, Object... objects) {
        w(cls.getName(), objects);
    }

    /**
     * 输出日志 WARN
     * 
     * @param objects
     *            日志详细信息
     */
    public static void w(Object... objects) {
        w(NULL, objects);
    }

    /**
     * 输出日志 INFO
     * 
     * @param logTag
     *            日志标签
     * @param objects
     *            日志详细信息
     */
    public static void i(String logTag, Object... objects) {
        String _logTag = (logTag == null ? log_tag : logTag);
        if (consol_level <= L_INFO && BuildConfig.DEBUG)
            Log.i(_logTag, composeMsgs(objects));
        if (save_level <= L_INFO)
            saveFile(INFO, _logTag, composeMsgs(objects));
    }

    public static <T> void i(Class<T> cls, Object... objects) {
        i(cls.getName(), objects);
    }

    /**
     * 输出日志 INFO
     * 
     * @param objects
     *            日志详细信息
     */
    public static void i(Object... objects) {
        i(NULL, objects);
    }

    /**
     * 输出日志 DEBUG
     * 
     * @param logTag
     *            日志标签
     * @param objects
     *            日志详细信息
     */
    public static void d(String logTag, Object... objects) {
        String _logTag = (logTag == null ? NULL : logTag);
        if (consol_level <= L_DEBUG && BuildConfig.DEBUG)
            Log.d(_logTag, composeMsgs(objects));
        if (save_level <= L_DEBUG)
            saveFile(DEBUG, _logTag, composeMsgs(objects));
    }

    public static <T> void d(Class<T> cls, Object... objects) {
        d(cls.getName(), objects);
    }

    /**
     * 输出日志 DEBUG
     * 
     * @param objects
     *            日志详细信息
     */
    public static void d(Object... objects) {
        d(log_tag, objects);
    }

    /**
     * 输出日志 VERBOSE
     * 
     * @param logTag
     *            日志标签
     * @param objects
     *            日志详细信息
     */
    public static void v(String logTag, Object... objects) {
        String _logTag = (logTag == null ? log_tag : logTag);
        if (consol_level <= L_VERBOSE && BuildConfig.DEBUG)
            Log.v(_logTag, composeMsgs(objects));
        if (save_level <= L_VERBOSE)
            saveFile(VERBOSE, _logTag, composeMsgs(objects));
    }

    public static <T> void v(Class<T> cls, Object... objects) {
        v(cls.getName(), objects);
    }

    /**
     * 输出日志 VERBOSE
     * 
     * @param objects
     *            日志详细信息
     */
    public static void v(Object... objects) {
        v(NULL, objects);
    }

    /**
     * 将日志信息写入文件
     */
    private static synchronized void saveFile(String Level, String logtag, String logMsg) {
        if (ValidateUtil.v_sdcard_avaliable()) {
            FileOutputStream fos = null;
            String str = new StringBuilder().append("[")
                    .append(DateUtil.formatCurrentDate(DateUtil.YYYY_MM_DD_HH_MM_SS)).append("]").append("--")
                    .append("[").append(Level).append("]").append("--<").append(logtag).append(">:").append(logMsg)
                    .append("\n").toString();
            try {
                File file = new File(save_path);
                if (!file.exists()) {
                    boolean createSucess = file.createNewFile();
                    if (!createSucess) {
                        return;
                    }
                }
                fos = new FileOutputStream(file, true);
                fos.write(str.getBytes());
            } catch (IOException e) {
                v(log_tag, "写日志信息异常，详细信息", e);
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        v(log_tag, "关闭日志文件异常，详细信息", e);
                    }
                }
            }
        } else {
            Logger.i(Logger.class, "SD卡不可用，写日志失败");
        }
    }

    /**
     * 格式化日志打印级别
     * 
     * @param _printLevel
     * @return
     */
    private static int formatLogL(int _printLevel) {
        int result = _printLevel;
        // 如果日志显示级别小于L_VERBOSE：1，则将日志显示级别定义为默认L_DEFAULT：1
        if (_printLevel < L_VERBOSE) {
            result = L_DEFAULT;
        }
        // 如果日志记录为文件级别大于L_ERROR：5则将日志级别定义为默认L_NO_WRITE_FILE = 6
        if (_printLevel > L_ERROR) {
            result = L_NO_WRITE_FILE;
        }
        return result;
    }

    /**
     * 组装消息
     * 
     * @param objects
     * @return
     */
    private static String composeMsgs(Object... objects) {
        int currentLength = 0;
        StringBuilder builder = new StringBuilder();
        for (Object object : objects) {
            builder.append(StringUtil.parseObj2Str(object));
            if (currentLength < objects.length - 1)
                builder.append("--");
            currentLength++;
        }
        return builder.toString();
    }
}
