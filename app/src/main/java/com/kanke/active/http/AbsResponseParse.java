/**
 * ****************************************************************************
 *
 * @(#)HttpMshParseUtil.java 2013-7-29
 * <p/>
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * *****************************************************************************
 */
package com.kanke.active.http;

import android.os.Handler;

import com.kanke.active.util.StringUtil;

/**
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-29 上午9:58:53
 */
public abstract class AbsResponseParse<T extends AbsResponse> {
    public static final int FLAG_0 = 0;
    public static final int FLAG_1 = 1;
    protected HttpRes httpRes;

    protected int wrongCode;

    protected Handler handler;

    public AbsResponseParse(HttpRes res, int wrongCode, Handler handler) {
        this.httpRes = res;
        this.wrongCode = wrongCode;
        this.handler = handler;

    }

    /**
     * 适配银行项目协议
     *
     * @param res
     * @param errorCode
     */
    public void parseRes(T res, int errorCode) {

        if (httpRes.isSuccess()) {
            if (res.parse(httpRes.getContent()) && !res.mIsError) {
                if (!res.mIsSuccess) {
                    MessageCommUtil.sendMsgToUI(handler, wrongCode, res.mErrorDes);
                } else {
                    doRightLogic(res);
                }
            } else {
                MessageCommUtil.sendMsgToUI(handler, wrongCode, ErrorInfo.SERVER_EXCEPTION);
            }

        } else if (httpRes.isException()) {
            MessageCommUtil.sendMsgToUI(handler, wrongCode, ErrorInfo.NETWORK_EXCEPTION);
        } else {
            MessageCommUtil.sendMsgToUI(handler, wrongCode, ErrorInfo.UNKNOWN_ERROR);
        }

    }
    public void parseRes(T res) {
        if (httpRes.isSuccess()) {
            if (res.parse(httpRes.getContent()) && !res.mIsError) {
                doRightLogic(res);
            } else {
                if ("9999".equals(res.mErrorCode)) {
                    MessageCommUtil.sendMsgToUI(handler, wrongCode, "-1");
                } else if (!StringUtil.isNull(res.mErrorCode)) {
                    MessageCommUtil.sendMsgToUI(handler, wrongCode, res.mErrorDes);
                } else {
                    MessageCommUtil.sendMsgToUI(handler, wrongCode, ErrorInfo.SERVER_EXCEPTION);
                }
            }
        } else if (httpRes.isException()) {
            MessageCommUtil.sendMsgToUI(handler, wrongCode, ErrorInfo.NETWORK_EXCEPTION);
        } else {
            MessageCommUtil.sendMsgToUI(handler, wrongCode, ErrorInfo.UNKNOWN_ERROR);
        }
    }

    /**
     * 处理消息发送成功相关逻辑
     */
    public abstract void doRightLogic(T absRes);

}
