package com.kanke.active.base;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kanke.active.activity.R;
import com.kanke.active.util.ContextUtil;

public abstract class BaseDialog extends Dialog {
    public EditText enroll_name;
    public EditText enroll_info;
    private Button mCancel;
    private Button mOk;
    public String name;
    public String info;


    public BaseDialog(Context paramContext, String title, String content, boolean cancable) {
        super(paramContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(cancable);
        setContentView(R.layout.base_dialog_score);
        ((TextView) findViewById(R.id.title)).setText(title);
        TextView view = (TextView) findViewById(R.id.content);
        mOk = ((Button) findViewById(R.id.secondBt));
        view.setText(content);

        if (hasOkBt()) {
            mOk.setVisibility(View.VISIBLE);
        } else {
            mOk.setVisibility(View.GONE);
        }

        mOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                okListener();
            }
        });
        getWindow().setLayout(KankeApplication.mInstance.width - ContextUtil.dp2px(getContext(), 30.0F),
                ContextUtil.dp2px(getContext(), 200.0F));
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public BaseDialog(Context paramContext, String title, String hint) {
        super(paramContext);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.add_active_enroll_dialog);
        ((TextView) findViewById(R.id.title)).setText(title);
        enroll_name = (EditText) findViewById(R.id.enroll_name);
        enroll_name.setVisibility(View.GONE);
        enroll_info = (EditText) findViewById(R.id.enroll_info);
        enroll_info.setHint(hint);
        mOk = ((Button) findViewById(R.id.secondBt));
        mOk.setText("修改");
        mCancel = ((Button) findViewById(R.id.firstBt));

        if (hasOkBt()) {
            mOk.setVisibility(View.VISIBLE);
        } else {
            mOk.setVisibility(View.GONE);
        }
        if (hasCancelBt()) {
            mCancel.setVisibility(View.VISIBLE);
        } else {
            mCancel.setVisibility(View.GONE);
        }
        mOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                    okListener();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                cancelListener();
            }
        });
        getWindow().setLayout(KankeApplication.mInstance.width - ContextUtil.dp2px(getContext(), 30.0F),
                ContextUtil.dp2px(getContext(), 200.0F));
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
    //监听cancle点击按钮
    public abstract void cancelListener();

    //是否显示cancle按钮
    public abstract boolean hasCancelBt();

    //是否显示ok按钮
    public abstract boolean hasOkBt();

    //监听ok点击按钮
    public abstract void okListener();
}

