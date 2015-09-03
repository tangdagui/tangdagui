package com.kanke.active.http;

public final class Configurations {
    public static String SERVER_URL;
    /* 是否打印日志 */
    public static boolean DEBUG;
    /* 是否显示支付宝功能 */
    public static boolean SHOW_ZFB_FUNC;
    public static int MAX_RESEND_COUNT;
    /* 建立HTTP连接超时时间 */
    public static int HTTP_CONN_TIMEOUT;
    /* 接收HTTP响应超时时间 */
    public static int HTTP_RECV_TIMEOUT;
    /* 发起请求编码格式 */
    public static String CHAR_SET;

    /* 服务端HTTPS安全连接密码 */
    public static String SERVER_PASSWORD;
    
    public static boolean COME_CONSERLOR = false;
    
    public static int SOCRE = 1;
}
