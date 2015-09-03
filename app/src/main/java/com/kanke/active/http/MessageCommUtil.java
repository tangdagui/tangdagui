/*******************************************************************************
 * @(#)ThreadCommUtil.java 2013-3-12
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.kanke.active.http;

import android.os.Handler;
import android.os.Message;

import com.kanke.active.util.Logger;

import java.util.List;
import java.util.Map;

/**
 * 线程通讯工具类，通过此工具类向UI发送状态码
 * 
 * @author <a href="mailto:fengzht@neusoft.com">Jason Feng</a>
 * @version $Revision 1.1 $ 2013-3-12 下午1:14:01
 */
public class MessageCommUtil {
    private MessageCommUtil() {
    }

    /**
     * 向UI发送消息
     * 
     * @param handler
     *            Handler对象，用于线程间交互
     * @param msgWhat
     *            消息What值
     */
    public static void sendMsgToUI(Handler handler, int msgWhat) {
        if (handler != null) {
            if (Configurations.DEBUG)
                Logger.i(MessageCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",threadid:", Thread.currentThread()
                        .getId());
            Message message = handler.obtainMessage();
            message.what = msgWhat;
            handler.sendMessage(message);
        }
    }

    /**
     * 向UI发送消息
     * 
     * @param handler
     *            Handler对象，用于线程间交互
     * @param msgWhat
     *            消息What值
     * @param msgObj
     *            消息Obj值
     */
    public static void sendMsgToUI(Handler handler, int msgWhat, Object msgObj) {
        if (handler != null) {
            if (msgObj instanceof String) {
                if (Configurations.DEBUG)
                    Logger.i(MessageCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",obj:", msgObj);
            } else if (msgObj instanceof List) {
                if (Configurations.DEBUG)
                    Logger.i(MessageCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",list size:",
                            ((List<?>) msgObj).size());
            } else if (msgObj instanceof Map) {
                if (Configurations.DEBUG)
                    Logger.i(MessageCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",map size:",
                            ((Map<?, ?>) msgObj).size());
            } else {
                if (Configurations.DEBUG)
                    Logger.i(MessageCommUtil.class, "[线程通讯工具类]:msgwhat:", msgWhat, ",obj:", msgObj);
            }

            Message message = handler.obtainMessage();
            message.what = msgWhat;
            message.obj = msgObj;
            handler.sendMessage(message);
        }
    }
}
