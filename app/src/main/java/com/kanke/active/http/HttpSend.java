package com.kanke.active.http;

import com.kanke.active.base.Constants;
import com.kanke.active.util.Logger;
import com.kanke.active.util.PreferenceUtils;
import com.kanke.active.util.StringUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

/**
 * HTTP REST消息发送工具类<br>
 * usage:<br>
 * 1.发 送post消息HttpMsgSendUtil.sendPostMsg(msg);<br>
 * 2.发送get消息HttpMsgSendUtil.sendGetMsg(msg);<br>
 * 3.发送put消息HttpMsgSendUtil.sendPutMsg(msg);<br>
 * 4.发送delete消息HttpMsgSendUtil.sendDeleteMsg(msg);<br>
 * 
 * @author <a href="mailto:heiantiankongxia@163.com">sherly.wen</a>
 * @version 1.0
 * @since 2014-2-2
 */
public final class HttpSend {

    private HttpSend() {
    }

    /**
     * 向服务器发起消息
     * 
     * @param msg
     *            消息内容
     * @param sendCount
     *            发送次数， 第一次为1
     * @param maxSendCount
     *            最大重发次数
     * @return HttpRes
     */
    static HttpRes sendPostMsg(HttpClient httpClient, String absUri, AbsRequst msg, int sendCount, int maxSendCount) {
        HttpRes httpRes = new HttpRes();
        // 设置连接超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Configurations.HTTP_CONN_TIMEOUT);
        // 设置接收超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Configurations.HTTP_RECV_TIMEOUT);
        HttpPost httpPost = null;
        try {
            String uri = Constants.EMPTY;
            uri = absUri + msg.getReuqestUri();
            httpPost = new HttpPost(uri);
            setBizHeader(msg, httpPost);
            String msgBody = msg.getMsgBody();
            if (msgBody != null) {
                httpPost.setEntity(new StringEntity(msgBody, Configurations.CHAR_SET));
            }
            if (Configurations.DEBUG) {
                Logger.e(HttpSend.class, "[HTTP REST消息发送工具类]：POST消息:第", sendCount, "次发送，Request URI：", uri, "Tocken:",
                        msg.mAccessToken, "，ETag:", "，MsgBody:", msgBody);
            }
            HttpResponse response = httpClient.execute(httpPost);

            httpRes = new HttpRes();
            httpRes.setStatusCode(response.getStatusLine().getStatusCode());
            if (response.getEntity() != null) {
                httpRes.setContent(EntityUtils.toString((response.getEntity()), Configurations.CHAR_SET));
            }
            if (Configurations.DEBUG) {
                Logger.e(HttpSend.class, "[HTTP REST消息发送工具类]：POST消息响应，statusCode：", httpRes.getStatusCode(),
                        "，content:", httpRes.getContent(), "，requestUri:", msg.getReuqestUri());
            }
            return httpRes;
        } catch (Exception e) {
            Logger.w(HttpSend.class, "[HTTP REST消息发送工具类]：发送POST消息异常，详细信息：", e);
            int tempSendCount = sendCount;
            tempSendCount++;
            if (tempSendCount <= maxSendCount) {
                return sendPostMsg(httpClient, absUri, msg, tempSendCount, maxSendCount);
            } else {
                httpRes.setException(true);
                return httpRes;
            }

        } finally {
            if (httpPost != null) {
                httpPost.abort();
            }
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }

    }

    /**
     * 向服务器发起GET消息
     * 
     * @param msg
     *            消息内容
     * @param sendCount
     *            发起次数， 第一次为1
     * @param maxSendCount
     *            最大发起次数
     * @return HttpRes
     */
    static HttpRes sendGetMsg(HttpClient httpClient, AbsRequst msg, int sendCount, int maxSendCount) {
        HttpRes httpRes = new HttpRes();
        // 设置连接超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Configurations.HTTP_CONN_TIMEOUT);
        // 设置接收超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Configurations.HTTP_RECV_TIMEOUT);

        HttpGet httpGet = null;
        try {
            String uri = Configurations.SERVER_URL + msg.getReuqestUri();
            httpGet = new HttpGet(uri);
            setBizHeader(msg, httpGet);
            if (Configurations.DEBUG) {
                Logger.e(HttpSend.class, "[HTTP REST消息发送工具类]：GET消息：第", sendCount, "次发送，Request URI:", uri, "，Token:",
                        msg.mAccessToken, "，ETag:");
            }
            HttpResponse response = httpClient.execute(httpGet);

            httpRes.setStatusCode(response.getStatusLine().getStatusCode());
            if (response.getEntity() != null) {
                httpRes.setContent(EntityUtils.toString((response.getEntity()), Configurations.CHAR_SET));
            }
            if (Configurations.DEBUG) {
                Logger.e(HttpSend.class, "[HTTP REST消息发送工具类]：GET消息响应，statusCode：", httpRes.getStatusCode(), "，content:",
                        httpRes.getContent(), "，requestUri:", msg.getReuqestUri());   
            }
            return httpRes;
        } catch (Exception e) {
            Logger.w(HttpSend.class, "[HTTP REST消息发送工具类]：发送Get消息异常，详细信息：", e);
            int tempSendCount = sendCount;
            tempSendCount++;
            if (tempSendCount <= maxSendCount) {
                return sendGetMsg(httpClient, msg, tempSendCount, maxSendCount);
            } else {
                httpRes.setException(true);
                return httpRes;
            }

        } finally {
            if (httpGet != null) {
                httpGet.abort();
            }
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * 向服务器发起PUT消息
     * 
     * @param msg
     *            消息内容
     * @param sendCount
     *            本次发送次数，次数 第一次为1
     * @param maxSendCount
     *            最大可发送次数
     * @return HttpRes
     */
    static HttpRes sendPutMsg(HttpClient httpClient, AbsRequst msg, int sendCount, int maxSendCount) {
        HttpRes httpRes = new HttpRes();
        // 设置连接超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Configurations.HTTP_CONN_TIMEOUT);
        // 设置接收超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Configurations.HTTP_RECV_TIMEOUT);

        HttpPut httpPut = null;
        try {
            String uri = Configurations.SERVER_URL + msg.getReuqestUri();
            httpPut = new HttpPut(uri);
            setBizHeader(msg, httpPut);
            String msgBody = msg.getMsgBody();
            httpPut.setEntity(new StringEntity(msgBody, Configurations.CHAR_SET));
            if (Configurations.DEBUG)
                Logger.d(HttpSend.class, "[HTTP REST消息发送工具类]：PUT消息：", sendCount, "次发送，Request URI:", uri, "，Token:",
                        msg.mAccessToken, "，MsgBody:", msgBody);
            HttpResponse response = httpClient.execute(httpPut);

            httpRes.setStatusCode(response.getStatusLine().getStatusCode());
            if (response.getEntity() != null) {
                httpRes.setContent(EntityUtils.toString((response.getEntity()), Configurations.CHAR_SET));
            }
            if (Configurations.DEBUG)
                Logger.d(HttpSend.class, "[HTTP REST消息发送工具类]：PUT消息响应，statusCode：", httpRes.getStatusCode(),
                        "，content:", httpRes.getContent(), "，requestUri:", msg.getReuqestUri());
            return httpRes;
        } catch (Exception e) {
            Logger.w(HttpSend.class, "[HTTP REST消息发送工具类]：发送PUT消息异常，详细信息：", e);
            int tempSendCount = sendCount;
            tempSendCount++;
            if (tempSendCount <= maxSendCount) {
                return sendPutMsg(httpClient, msg, tempSendCount, maxSendCount);
            } else {
                httpRes.setException(true);
                return httpRes;
            }

        } finally {
            if (httpPut != null) {
                httpPut.abort();
            }
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }

    }

    /**
     * 向服务器发起DELETE消息
     * 
     * @param msg
     *            消息内容
     * @param sendCount
     *            发送次数， 第一次为1
     * @param maxSendCount
     *            最大可发送次数
     * @return HttpRes
     */
    static HttpRes sendDeleteMsg(HttpClient httpClient, AbsRequst msg, int sendCount, int maxSendCount) {
        HttpRes httpRes = new HttpRes();

        // 设置连接超时
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Configurations.HTTP_CONN_TIMEOUT);
        // 设置接收超时
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Configurations.HTTP_RECV_TIMEOUT);

        HttpDelete httpDelete = null;
        try {
            String uri = Configurations.SERVER_URL + msg.getReuqestUri();
            httpDelete = new HttpDelete(uri);
            setBizHeader(msg, httpDelete);
            if (Configurations.DEBUG)
                Logger.d(HttpSend.class, "[HTTP REST消息发送工具类]：DELETE消息：", sendCount, "次发送，Request URI:", uri,
                        "，Tocken:", msg.mAccessToken);
            HttpResponse response = httpClient.execute(httpDelete);

            httpRes.setStatusCode(response.getStatusLine().getStatusCode());
            if (response.getEntity() != null) {
                httpRes.setContent(EntityUtils.toString((response.getEntity()), Configurations.CHAR_SET));
            }
            Logger.d(HttpSend.class, "[HTTP REST消息发送工具类]：DELETE消息响应，statusCode:", httpRes.getStatusCode(), "，content:",
                    httpRes.getContent(), "，requestUri:", msg.getReuqestUri());
            return httpRes;
        } catch (Exception e) {
            Logger.w(HttpSend.class, "[HTTP REST消息发送工具类]：发送DELETE消息异常，详细信息：", e);
            int tempSendCount = sendCount;
            tempSendCount++;
            if (tempSendCount <= maxSendCount) {
                return sendDeleteMsg(httpClient, msg, tempSendCount, maxSendCount);
            } else {
                httpRes.setException(true);
                return httpRes;
            }

        } finally {
            if (httpDelete != null) {
                httpDelete.abort();
            }
            if (httpClient != null) {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    /**
     * 设置报文头业务内容
     * 
     * @param msg
     * @param httpClient
     */
    private static void setBizHeader(AbsRequst msg, HttpRequestBase httpClient) {
        httpClient.setHeader("Content-Type", "application/json;charset=UTF-8");
        /*
         * if (msg.mAccessToken != null) { httpClient.setHeader("access_token",
         * msg.mAccessToken); }
         */
        String accessToken = "";//PreferenceUtils.getBaseInfo().getString("Token", Constants.EMPTY);//KankeApplication.getInstance().getAccessToken();
        if (!StringUtil.isNull(accessToken.trim()))
            msg.mAccessToken = accessToken;
            httpClient.setHeader("token",accessToken);
    }
}
