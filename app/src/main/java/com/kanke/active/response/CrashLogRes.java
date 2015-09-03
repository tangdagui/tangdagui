package com.kanke.active.response;


import com.kanke.active.http.AbsResponse;
import com.kanke.active.util.JsonUtil;
import com.kanke.active.util.Logger;

import org.json.JSONException;

/**
 * Created by Ganker on 2015/7/11.
 */
public class CrashLogRes extends AbsResponse {
    @Override
    public boolean parseCorrectMsg(String jsonObject) {
        try {
            JsonUtil.parseBaseResponse(this,jsonObject);
        } catch (JSONException e) {
            Logger.e(this,e);
        }
        return true;
    }
}
