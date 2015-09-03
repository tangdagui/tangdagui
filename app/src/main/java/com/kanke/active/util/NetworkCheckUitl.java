package com.kanke.active.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络检查工具类
 * 
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-15 上午11:19:39
 */
public final class NetworkCheckUitl {

    private NetworkCheckUitl() {
    }

    /**
     * 判断设备是否网络在线
     * 
     * @return true:在线,false：不在线
     */
    public static boolean isOnline(Context context) {
        if (context != null) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        } else {
            return false;
        }
    }

    /**
     * 判断设备是否网络在线
     * 
     * @return true:在线,false：不在线
     */
    public static boolean is3GOnline(Context context) {
        if (context != null) {
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected() && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE);
        } else {
            return false;
        }
    }
}
