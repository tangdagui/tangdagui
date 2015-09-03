package com.kanke.active.util;


import android.content.Context;
import android.content.SharedPreferences;

import com.kanke.active.base.KankeApplication;

/**
 * Created by dagui on 2015/8/20.
 */
public class PreferenceUtils {

    /**
     * 存储基本信息
     */

    //public  static  void  savaBaseInfo(BaseInfo mBaseInfo) {
     //   SharedPreferences prefs = KankeApplication.getInstance().getApplicationContext()
            //    .getSharedPreferences("baseInfo", Context.MODE_PRIVATE);
      //  editor.commit();
   // }

    public  static SharedPreferences getBaseInfo(){
        SharedPreferences prefs = KankeApplication.getInstance().getApplicationContext()
                .getSharedPreferences("baseInfo", Context.MODE_PRIVATE);
        return prefs;
    }
}
