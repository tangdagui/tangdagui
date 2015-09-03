package com.kanke.active.model;

import org.json.JSONException;
import org.json.JSONObject;

/** 崩溃日志 实体
 * Created by Ganker on 2015/7/11.
 */
public class CrashLogModel {
    public String Version;//版本号
    public String LogMsg;//日志

    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Version", Version);
        jsonObject.put("LogMsg", LogMsg);
        return jsonObject;
    }

}




