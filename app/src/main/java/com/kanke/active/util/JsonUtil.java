package com.kanke.active.util;


import com.kanke.active.base.Constants;
import com.kanke.active.http.AbsResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Json数据解析工具类
 * 
 * @author <a href="mailto:heiantiankongxia@163.com">sherly.wen</a>
 * @version 1.0
 * @since 2014-2-9
 */
public final class JsonUtil {
    /**
     * 解析基础响应
     * 
     * @param response
     */
    public static final JSONObject parseBaseResponse(AbsResponse response, String jObject) throws JSONException {
        JSONObject object = null;
        if (jObject != null && !Constants.EMPTY.equals(jObject)) {
            object = new JSONObject(jObject);
            response.mIsSuccess = object.optBoolean("IsSuccess");
            response.mErrorCode = object.optString("Code");
            response.mErrorDes = object.optString("Msg");
            if (object.optString("Data").contains("StartIndex")){
                response.mStartIndex = new JSONObject(object.optString("Data")).optInt("StartIndex");
            }

        } else {
            object = new JSONObject();
        }
        return object;
    }
}
