package com.kanke.active.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidateUtil {
    private ValidateUtil() {

    }

    /**
     * 验证邮箱格式是否正确
     * 
     * @param address
     * @return Boolean
     */
    public static boolean v_email(String address) {
        String check = "^[0-9a-z][a-z0-9\\._-]{1,}@[a-z0-9-]{0,}[a-z0-9]\\.[a-z\\.]{1,}[a-z]$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(address);
        return matcher.matches();
    }

    /**
     * 验证电话号码
     * 
     * @param number
     * @return
     */
    public static boolean v_call(String number) {
        // 电话号码与手机号码同时验证
        String regexRule = "^((\\d{3,4}-)?\\d{7,8})$|^(1(3|4|5|6|7|8)?[0-9]{9})$";
        Pattern regex = Pattern.compile(regexRule);
        Matcher matcher = regex.matcher(number);
        // matcher = regex.matcher(number);
        return matcher.matches();
    }

    /**
     * 验证手机号码
     * 
     * @param number
     * @return
     */
    public static boolean v_callPhone(String number) {
        // 电话号码与手机号码同时验证
        String regexRule = "[1][34578]\\d{9}";
        Pattern regex = Pattern.compile(regexRule);
        Matcher matcher = regex.matcher(number);
        // matcher = regex.matcher(number);
        return matcher.matches();
    }

    /**
     * 检查SD卡的当前状态 MEDIA_MOUNTED:可读可写，MEDIA_MOUNTED_READ_ONLY：可读不可写
     * 
     * @return boolean
     */
    public static boolean v_sdcard_avaliable() {
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        // 如果外部媒体mounted且writable则可用，否则不可用
        return mExternalStorageAvailable && mExternalStorageWriteable;
    }

    /**
     * 检测当前是否有活动网络
     * 
     * @return
     */
    public static boolean v_network_avaliable(Context context) {
        boolean available = false;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
            NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
            if (networkInfo != null) {
                available = true;
            }
        }
        return available;
    }

    /**
     * 验证字符串是否包含数字以外的字符
     * 
     * @param value
     * @return
     */
    public static boolean v_Numberic(String value) {
        if (StringUtil.isNull(value) || value.contains(" ")) {
            return false;
        }
        char[] chr = value.toCharArray();
        for (char c : chr) {
            if (c != '.' && (c < '0' || c > '9')) {
                return false;
            }
        }
        return true;
    }
}
