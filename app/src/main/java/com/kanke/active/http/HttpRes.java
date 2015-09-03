package com.kanke.active.http;

/**
 * http 响应包装类
 * 
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-15 上午11:21:39
 */
public class HttpRes {

    public static final String NETWORK_NOT_STABLE_PROMPT = "服务不稳定，请稍后重试";

    public static final String EXPIRED_TOKEN_PROMPT = "登录已失效，请您重新登录";

    public static final String NOT_MODIFIED_PROMPT = "服务端内容无变化";

    public static final String NO_CONTENT_PROMPT = "无查询结果";

    public static final String NETWORK_EXCEPTION_PROPMT = "网络连接异常，请检查您的网络";

    /** 状态码 */
    private int statusCode;

    /** 返回内容 */
    private String content;

    /** 是否异常 */
    private boolean isException = false;

    /**
     * 是否返回成功<br>
     * 条件：<br>
     * 1.没有异常；<br>
     * 2.返回状态码为200或204或304或content不为空<br>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    public boolean isSuccess() {
        if (!isException
                && (statusCode == 200 || statusCode == 204 || statusCode == 304 || content != null
                        && content.length() > 0)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean needCached() {
        if (!isException && statusCode == 200) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否token过期
     * 
     * @return true:过期，false:未过期
     */
    public boolean isTokenExpire() {
        return statusCode == 298;
    }

    /**
     * 获取 statusCode
     * 
     * @return 返回 statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * 获取失败信息
     * 
     * @return 失败信息
     */
    public String getFailInfo() {
        if (isException) {
            return NETWORK_EXCEPTION_PROPMT;
        } else if (statusCode == 204) {
            return NO_CONTENT_PROMPT;
        } else if (statusCode == 304) {
            return NOT_MODIFIED_PROMPT;
        } else if (statusCode == 401) {
            return EXPIRED_TOKEN_PROMPT;
        } else {
            return NETWORK_NOT_STABLE_PROMPT;
        }
    }

    /**
     * 设置 statusCode
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * 获取 content
     * 
     * @return 返回 content
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置 content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 设置 isException
     */
    public void setException(boolean isException) {
        this.isException = isException;
    }

    /**
     * 获取 isException
     * 
     * @return 返回 isException
     */
    public boolean isException() {
        return isException;
    }

}
