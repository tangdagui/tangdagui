/*******************************************************************************
 * @(#)ErrorInfo.java 2013-7-22
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.kanke.active.http;

/**
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-22 下午2:34:09
 */
public class ErrorInfo {
    /** 网络异常提示 */
    public static final String NETWORK_EXCEPTION = "网络连接异常，请检查您的网络";
    public static final String NETWORK_EXCEPTION_TEXT = "网络错误，点击刷新";
    public static final String LODING_FAIL= "加载失败，点击刷新";
    /** 未知错误 */
    public static final String UNKNOWN_ERROR = "未知错误";
    /** 服务器内部错误 */
    public static final String SERVER_EXCEPTION = "亲，服务器偷懒了，请稍后重试";
    public static final String LOGIN_OUT_TIME = "你的账号在其他设备登陆";
    public static final String HAVE_BEEN_TREAT= "该导师已在诊疗该项目";
}
