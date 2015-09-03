package com.kanke.active.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/9/2.
 */
public class ContextUtil {
    private static Toast mCurrentToast=null;//toast类
    /**
     * 获得当前context屏幕参数
     *
     * @param context
     * @return DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetric = context.getResources().getDisplayMetrics();
        return displayMetric;
    }

    public static void alterActivity(Activity from, Class<?> to) {
        alterActivity(from, to, null);
    }

    public static void alterActivity(Activity from, Class<?> to, Bundle extras) {
        Intent intent = new Intent(from, to);
        if (extras != null)
            intent.putExtras(extras);
        from.startActivity(intent);
    }
    public static void alterActivityForResult(Activity from, Class<?> to, Bundle extras,int resultCode) {
        Intent intent = new Intent(from, to);
        if (extras != null)
            intent.putExtras(extras);
        from.startActivityForResult(intent, resultCode);
    }
    public static void showToast(CharSequence text,Context context) {
        if (null != mCurrentToast) {
            mCurrentToast.cancel();
        }
        mCurrentToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mCurrentToast.show();
    }

    /**
     * 转换DIP单位为像素PX
     *
     * @param context
     *            当前上下�?
     * @param dp
     *            待转换的DP单位
     * @return int
     */
    public static int dp2px(Context context, float dp) {
        DisplayMetrics metics = getDisplayMetrics(context);
        float density = metics.density;
        return (int) (density * dp + 0.5f);
    }

    /**
     * 转换PX像素单位为DIP
     *
     * @param context
     *            当前上下�?
     * @param px
     *            待转换的像素PX单位
     * @return int
     */
    public static int px2dp(Context context, float px) {
        DisplayMetrics metics = getDisplayMetrics(context);
        float density = metics.density;
        return (int) (px / density + 0.5f);
    }
}
