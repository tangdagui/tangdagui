package com.kanke.active.asyn;

import android.os.Handler;
import android.os.Message;

import com.kanke.active.base.KankeApplication;
import com.kanke.active.base.KankeAsyncTask;
import com.kanke.active.base.StateCodes;
import com.kanke.active.http.AbsResponseParse;
import com.kanke.active.http.HttpProxy;
import com.kanke.active.model.CrashLogModel;
import com.kanke.active.request.CrashLogReq;
import com.kanke.active.response.CrashLogRes;

/**
 * Created by Ganker on 2015/7/11.
 */
public class CrashLogTask extends KankeAsyncTask {

    public Handler mHandler;
    public CrashLogModel model;
    @Override
    protected void doInBackground() {
        CrashLogReq surportReq = new CrashLogReq();
        surportReq.model = model;

        new AbsResponseParse<CrashLogRes>(HttpProxy.post(KankeApplication.mInstance.getApplicationContext(),
                KankeApplication.mInstance.getServerUrl(), surportReq), StateCodes.CRASH_FAILED, mHandler) {

            @Override
            public void doRightLogic(CrashLogRes absRes) {
                Message msg = handler.obtainMessage();
                msg.what = StateCodes.CRASH_SUCCESS;
                msg.sendToTarget();
            }
        }.parseRes(new CrashLogRes());
    }
}