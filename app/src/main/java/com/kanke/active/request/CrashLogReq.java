package com.kanke.active.request;

import com.kanke.active.http.AbsRequst;
import com.kanke.active.model.CrashLogModel;
import com.kanke.active.util.StringUtil;

import org.json.JSONException;

/**
 * Created by Ganker on 2015/7/11.
 */
public class CrashLogReq extends AbsRequst {

    public CrashLogModel model;
    @Override
    protected String packetMsgBody() throws JSONException {
        return StringUtil.parseStr(model.toJson());
    }

    @Override
    public String getUri() {
        return "api/AppLog/PostAddAppLog";
    }

}
