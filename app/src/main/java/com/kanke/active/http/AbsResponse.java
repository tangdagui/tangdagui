/*******************************************************************************
 * @(#)AbstractRes.java 2013-3-11
 *
 * Copyright 2013 Neusoft Group Ltd. All rights reserved.
 * Neusoft PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *******************************************************************************/
package com.kanke.active.http;

import com.kanke.active.util.Logger;
import com.kanke.active.util.StringUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 响应消息抽象类
 * 
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-22 下午2:23:25
 */
public abstract class AbsResponse {
    /** 错误返回结果码 */
    public String mErrorCode;

    /** 错误描述 */
    public String mErrorDes;
    public String mStr;
    public boolean mIsSuccess;
    /** 是否有错 */
    public boolean mIsError = false;
    /**
     * 总条数
     */
    public int mCount;
    /**
     * 为0或者1，0表示没数据了，-1表示还有数据
     */
    public int mStartIndex;

    /**
     * 请求消息解析，完成请求消息内容的解析
     * 
     * @param msg
     * @return true:解析成功，false:解析失败
     * @throws JSONException
     */
    public boolean parse(String msg) {
        if (StringUtil.isNull(msg)) {
            return false;
        }
        try {
            JSONTokener jsonTokener = new JSONTokener(msg);
            JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
            if (jsonObject != null && parseErrorMsg(jsonObject)) {
                if (mIsSuccess) {
                    return parseCorrectMsg(msg);
                } else {
                    mIsError = true;
                    return true;
                }
            } else {
                mIsError = false;
                return false;
            }
        } catch (Exception e) {
            Logger.e(getClass(), "【响应消息抽象类】：响应消息解析异常：", e);
            mIsError = false;
            return false;
        }
    }

    /**
     * 解析错误消息
     * 
     * @param jObject
     * @return
     */
    private boolean parseErrorMsg(JSONObject jObject) {
        try {
            mErrorCode = jObject.optString("Code");
            mErrorDes = jObject.optString("Msg");
            mIsSuccess = jObject.optBoolean("IsSuccess");
            mStr = jObject.optString("Str");
            return true;
        } catch (Exception e) {
            Logger.w(getClass(), "[响应消息抽象类]：ERROR响应消息解析异常：", e);
        }
        return false;
    }

    /**
     * 解析正确消息，即正常的响应消息
     * 
     * @param jsonObject
     * @return
     */
    public abstract boolean parseCorrectMsg(String jsonObject);

    /**
     * 请求信息中是否无access_token信息
     * 
     * @return true：请求信息中无access_token信息，false:否
     */
    // public boolean isNoTokenInfo() {
    // return StringUtils.equals("20000", errorCode);
    // }

    /**
     * 是否令牌异常
     * 
     * @return true：是，false:否
     */
    public boolean isOauthFailed() {
        return "401".equals(mErrorCode);
    }

}
