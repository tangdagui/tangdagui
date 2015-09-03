package com.kanke.active.http;

import com.kanke.active.base.Constants;
import com.kanke.active.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 请求消息抽象类<br>
 * usage:<br>
 * 1.所有消息交互实体均需实现该抽象类，提供抽象方法packetMsgBody()，对消息内容进行打包<br>
 * 2.对于delete请求和get请求可根据实际情况实现setReuqestUri()方法和空的packetMsgBody()方法;<br>
 * 3.对于post和put请求，实现方式一致，需实现setReuqestUri()和packetMsgBody()；<br>
 * 4.实现setReuqestUri方法设置访问资源变量reuqestUri
 * 
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-15 上午11:21:21
 */

public abstract class AbsRequst {

    /** 服务端分配终端用户的最新access_token信息，在HTTP协议的Header中出现 */
    public String mAccessToken = null;
    /** 请求消息组装对象 */
    protected JSONObject mRequestJson = null;

    /**
     * 获取请求的资源
     * 
     * @return Returns the reuqestUri.
     */
    public String getReuqestUri() {
        return getUri();
    }

    /**
     * 获取请求体
     * 
     * @return
     */
    public String getMsgBody() {
        mRequestJson = new JSONObject();
        try {
            return packetMsgBody();
        } catch (JSONException e) {
            Logger.w(getClass().getName(), e);
        }
        return Constants.EMPTY;
    }

    /**
     * 打包请求消息体
     * 
     * @return
     */
    protected abstract String packetMsgBody() throws JSONException;

    /**
     * 设置请求的资源
     */
    public abstract String getUri();
}
