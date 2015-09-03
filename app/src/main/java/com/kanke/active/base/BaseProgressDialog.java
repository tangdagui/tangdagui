package com.kanke.active.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.TextView;

import com.kanke.active.activity.R;
import com.kanke.active.util.ContextUtil;

/**
 * 显示进度窗口
 * 
 * @author <a href="mailto:342777803@qq.com">sherly.wen </a>
 * @version $Revision 1.1 $ 2013-7-15 上午11:18:31
 */
public class BaseProgressDialog extends Dialog {

    public BaseProgressDialog(Context context, String msg) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setContentView(R.layout.dialog_progress_temp);
        TextView messageTx = (TextView) findViewById(R.id.messageTx);
        messageTx.setText(msg);
        //getWindow().setBackgroundDrawable("#ffffff");
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_shap);
        getWindow().setLayout(KankeApplication.mInstance.width - ContextUtil.dp2px(getContext(), 60),
                ContextUtil.dp2px(getContext(), 80));
    }
}
