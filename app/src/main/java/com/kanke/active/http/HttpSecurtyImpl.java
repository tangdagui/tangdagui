package com.kanke.active.http;

import android.content.Context;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 普通HTTP连接实现
 * 
 * @author <a href="mailto:heiantiankongxia@163.com">sherly.wen</a>
 * @version 1.0
 * @since 2014-2-10
 */
public class HttpSecurtyImpl implements IHttpsSecurty {

    @Override
    public HttpClient initClient(Context context) {
        return new DefaultHttpClient();
    }

}
