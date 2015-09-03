package com.kanke.active.http;

import android.content.Context;

import com.kanke.active.util.NetworkCheckUitl;

/**
 * HTTP请求代理类
 * 
 * @author <a href="mailto:heiantiankongxia@163.com">sherly.wen</a>
 * @version 1.0
 * @since 2014-2-2
 */
public final class HttpProxy {
    private static final IHttpsSecurty securty = new HttpSecurtyImpl();

    /**
     * 银行项目适配
     * 
     * @param context
     * @param absUri
     * @param msg
     * @return
     */
    public static HttpRes postHasHeader(Context context, String absUri, AbsRequst msg) {
        return postHasHeader(context, absUri, msg, Configurations.MAX_RESEND_COUNT);
    }

    /**
     * 银行项目适配
     * 
     * @param context
     * @param absUri
     * @param msg
     * @return
     */
    public static HttpRes postHasHeader(Context context, String absUri, AbsRequst msg, int maxSendCount) {
        return postHasHeader(context, absUri, msg, 1, maxSendCount);
    }

    /**
     * 银行项目适配
     * 
     * @param context
     * @param absUri
     * @param msg
     * @return
     */
    public static HttpRes postHasHeader(Context context, String absUri, AbsRequst msg, int sendCount, int maxSendCount) {
        if (NetworkCheckUitl.isOnline(context)) {
            return HttpSendHasHeader.sendPostMsg(securty.initClient(context), absUri, msg, sendCount, maxSendCount);
        } else {
            HttpRes httpRes = new HttpRes();
            httpRes.setException(true);
            return httpRes;
        }
    }

    public static HttpRes post(Context context, String absUri, AbsRequst msg) {
        return post(context, absUri, msg, Configurations.MAX_RESEND_COUNT);
    }

    public static HttpRes post(Context context, String absUri, AbsRequst msg, int maxSendCount) {
        return post(context, absUri, msg, 1, maxSendCount);
    }

    public static HttpRes post(Context context, String absUri, AbsRequst msg, int sendCount, int maxSendCount) {
        if (NetworkCheckUitl.isOnline(context)) {
            return HttpSend.sendPostMsg(securty.initClient(context), absUri, msg, sendCount, maxSendCount);
        } else {
            HttpRes httpRes = new HttpRes();
            httpRes.setException(true);
            return httpRes;
        }
    }

    public static HttpRes get(Context context, AbsRequst msg) {
        return get(context, msg, Configurations.MAX_RESEND_COUNT);
    }

    public static HttpRes get(Context context, AbsRequst msg, int maxSendCount) {
        return get(context, msg, 1, maxSendCount);
    }
    public static HttpRes get(Context context, AbsRequst msg, int sendCount, int maxSendCount) {
        if (NetworkCheckUitl.isOnline(context)) {
            return HttpSend.sendGetMsg(securty.initClient(context), msg, sendCount, maxSendCount);
        } else {
            HttpRes httpRes = new HttpRes();
            httpRes.setException(true);
            return httpRes;
        }
    }

    public static HttpRes delete(Context context, AbsRequst msg) {
        return delete(context, msg, Configurations.MAX_RESEND_COUNT);
    }

    public static HttpRes delete(Context context, AbsRequst msg, int maxSendCount) {
        return delete(context, msg, 1, maxSendCount);
    }

    public static HttpRes delete(Context context, AbsRequst msg, int sendCount, int maxSendCount) {
        if (NetworkCheckUitl.isOnline(context)) {
            return HttpSend.sendDeleteMsg(securty.initClient(context), msg, sendCount, maxSendCount);
        } else {
            HttpRes httpRes = new HttpRes();
            httpRes.setException(true);
            return httpRes;
        }
    }

    public static HttpRes put(Context context, AbsRequst msg) {
        return put(context, msg, Configurations.MAX_RESEND_COUNT);
    }

    public static HttpRes put(Context context, AbsRequst msg, int maxSendCount) {
        return put(context, msg, 1, maxSendCount);
    }

    public static HttpRes put(Context context, AbsRequst msg, int sendCount, int maxSendCount) {
        if (NetworkCheckUitl.isOnline(context)) {
            return HttpSend.sendPutMsg(securty.initClient(context), msg, sendCount, maxSendCount);
        } else {
            HttpRes httpRes = new HttpRes();
            httpRes.setException(true);
            return httpRes;
        }
    }

}
