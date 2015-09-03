package com.kanke.active.base;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.kanke.active.activity.LoginActivity;
import com.kanke.active.activity.R;
import com.kanke.active.http.Configurations;
import com.kanke.active.util.ContextUtil;
import com.kanke.active.util.Logger;
import com.kanke.active.util.PreferenceUtils;
import com.kanke.active.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2015/9/2.
 */
public class KankeApplication extends Application{
    private String mServerUrl = Constants.EMPTY;
    public static KankeApplication mInstance;
    public int width = 0;
    public int height = 0;
    private static final List<WeakReference<FragmentActivity>> mActivitys =
            new ArrayList<WeakReference<FragmentActivity>>();
    public boolean mIsShowing = false;//已经弹出了dialog
    private View mMoreView = null;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        readConfiguritions();
        KankeCrashHandler handler = KankeCrashHandler.getInstance();
        handler.init(getApplicationContext());
        width = ContextUtil.getDisplayMetrics(getApplicationContext()).widthPixels;
        height = ContextUtil.getDisplayMetrics(getApplicationContext()).heightPixels;
    }
    public static KankeApplication getInstance() {
        return mInstance;
    }
    /**
     * 退出系统
     */
    public void exitSystem() {

        for (WeakReference<FragmentActivity> activity : mActivitys) {
            FragmentActivity ac = activity.get();
            if (ac != null)
                ac.finish();
        }
        mActivitys.clear();
    }

    /**
     * 添加activity到list里面
     *
     * @param activity
     */
    public void addActivity(FragmentActivity activity) {
        mActivitys.add(new WeakReference<FragmentActivity>(activity));
    }
    /**
     * 获取最后一个没有推出的activity
     *
     * @return
     */
    public Activity getLastActivity() {
        if (mActivitys != null && mActivitys.size() > 0) {
            return mActivitys.get(mActivitys.size() - 1).get();
        }
        return null;
    }
    /**
     * 获取请求域名
     *
     * @return
     */
    public String getServerUrl() {
        if (StringUtil.isNull(mServerUrl)) {
            mServerUrl = PreferenceUtils.getBaseInfo().getString(Constants.Url,Constants.EMPTY);
        }
        return mServerUrl;
    }

    /**
     * 临时存储请求域名
     *
     * @param url
     */
    public void setServerUrl(String url) {
        mServerUrl = url;
    }
    /**
     * 读取系统配置信息
     */
    private void readConfiguritions() {
        InputStream configureSteam = null;
        Properties properties = null;
        try {
            configureSteam = getAssets().open("configure.properties");
            properties = new Properties();
            properties.load(configureSteam);
            Configurations.DEBUG = Boolean.parseBoolean(properties.getProperty("debug"));
            if (Configurations.DEBUG) {
                Configurations.SERVER_URL = properties.getProperty("server_url_test");
                KankeApplication.mInstance.setServerUrl(Configurations.SERVER_URL);
                PreferenceUtils.getBaseInfo().edit().putString(Constants.Url,Constants.EMPTY).commit();
            } else {
                Configurations.SERVER_URL = properties.getProperty("server_url_product");
                KankeApplication.mInstance.setServerUrl(Configurations.SERVER_URL);
                PreferenceUtils.getBaseInfo().edit().putString(Constants.Url,Constants.EMPTY).commit();
            }
            Configurations.MAX_RESEND_COUNT = Integer.parseInt(properties.getProperty("max_resend_count"));
            Configurations.HTTP_RECV_TIMEOUT = Integer.parseInt(properties.getProperty("http_recv_timeout"));
            Configurations.HTTP_CONN_TIMEOUT = Integer.parseInt(properties.getProperty("http_conn_timeout"));
            Configurations.CHAR_SET = properties.getProperty("char_set");
            Configurations.SERVER_PASSWORD = properties.getProperty("server_password");
        } catch (IOException e) {
            Logger.w(getClass(), e);
        } finally {
            if (configureSteam != null) {
                try {
                    configureSteam.close();
                } catch (IOException e) {
                    Logger.w(getClass(), e);
                }
            }
        }
    }

    public synchronized void tockenInvalied(final BaseActivity activity) {
        if (mIsShowing) {
            return;
        }
        if (activity != null) {
            mIsShowing = true;
            BaseDialog mTipDialog = new BaseDialog(activity, "退出提示", getString(R.string.tocken_invalied_tip), false) {
                @Override
                public void dismiss() {
                    super.dismiss();
                    mIsShowing = false;
                    KankeApplication.mInstance.exitSystem();
                    ContextUtil.alterActivity(activity, LoginActivity.class);
                }

                @Override
                public void okListener() {
                    dismiss();
                }

                @Override
                public void cancelListener() {

                }

                @Override
                public boolean hasOkBt() {
                    return true;
                }

                @Override
                public boolean hasCancelBt() {
                    return false;
                }

            };
            if (activity != null) {
                try {
                    mTipDialog.show();
                } catch (Throwable e) {
                    ContextUtil.showToast(getString(R.string.tocken_invalied_tip), getApplicationContext());
                    mIsShowing = false;
                    KankeApplication.mInstance.exitSystem();
                    ContextUtil.alterActivity(activity, LoginActivity.class);
                    Logger.w(getClass(), e);
                }
            }
        }
    }
    public View getMoreView() {
        if (mMoreView == null) {
            LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
            mMoreView = inflater.inflate(R.layout.moreview, null);
        } else {
            ((TextView) mMoreView.findViewById(R.id.moreTx)).setText(R.string.p2refresh_head_load_more);
            //((TextView) mMoreView.findViewById(R.id.moreTx))
        }
        return mMoreView;
    }
}
