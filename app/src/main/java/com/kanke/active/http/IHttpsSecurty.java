package com.kanke.active.http;

import android.content.Context;

import org.apache.http.client.HttpClient;

/**
 * 导入HTTPS安全协议接口，空实现代表：普通HTTP连接实现
 * 
 * @author <a href="mailto:heiantiankongxia@163.com">sherly.wen</a>
 * @version 1.0
 * @since 2014-2-3
 */
public interface IHttpsSecurty {
    HttpClient initClient(Context context);

}
