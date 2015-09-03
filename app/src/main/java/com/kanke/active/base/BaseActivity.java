package com.kanke.active.base;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kanke.active.activity.R;
import com.kanke.active.http.ErrorInfo;
import com.kanke.active.util.ContextUtil;
import com.kanke.active.util.StringUtil;
import com.kanke.active.view.GifMovieView;


public abstract class BaseActivity extends FragmentActivity implements Handler.Callback {
    public boolean IS_FIRST = true;
    public boolean mHasMore = false;
    public LayoutInflater mInflater;
    public int page = 1;

    public abstract void LoadingData();

    public abstract int getLayoutId();

    public void onBack(View v) {
       // finish();
        onBackPressed();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        KankeApplication.mInstance.addActivity(this);
        setContentView(getLayoutId());
        this.mInflater = LayoutInflater.from(getApplicationContext());
        LoadingData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
             //透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public void reLoadingData(Object obj) {
        if (Constants.TOCKEN_INVALIED_FLAG.equals(StringUtil.parseStr(obj))) {
            KankeApplication.mInstance.tockenInvalied(this);
        } else {
            View loadingView = findViewById(R.id.moreView);
            if (loadingView != null) {
                loadingView.setVisibility(View.VISIBLE);
                final TextView tipsView = (TextView) loadingView.findViewById(R.id.moreTx);
                final GifMovieView gifview = (GifMovieView)loadingView.findViewById(R.id.gifView);
                tipsView.setText(StringUtil.parseStr(obj));
                gifview.setPaused(true);
                loadingView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (StringUtil.parseStr(tipsView.getText()).equals(ErrorInfo.NETWORK_EXCEPTION)) {
                            gifview.setPaused(false);
                            LoadingData();
                            tipsView.setText(R.string.loadingTips);
                        }
                    }
                });
            }
        }
    }
    public void showToast(Object obj){
        if (Constants.TOCKEN_INVALIED_FLAG.equals(StringUtil.parseStr(obj))) {
            KankeApplication.mInstance.tockenInvalied(this);
        }else {
            ContextUtil.showToast(StringUtil.parseObj2Str(obj), getApplicationContext());
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    /**
     * 显示提示窗口
     *
     * @param msg
     */
    protected BaseProgressDialog mBaseProDialog = null;

    public void showProgressDialog(String msg) {
        dismissProgressDialog();
        mBaseProDialog = new BaseProgressDialog(this, msg);
        mBaseProDialog.setCanceledOnTouchOutside(true);
        mBaseProDialog.show();
    }

    //点击外部不消失
    public void showProgressDialog2(String msg) {
        dismissProgressDialog();
        mBaseProDialog = new BaseProgressDialog(this, msg);
        mBaseProDialog.setCanceledOnTouchOutside(false);
        mBaseProDialog.show();
    }

    public void dismissProgressDialog() {
        if (mBaseProDialog != null && mBaseProDialog.isShowing()) {
            mBaseProDialog.dismiss();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }
}
