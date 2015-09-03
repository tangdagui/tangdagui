package com.kanke.active.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class Utils {
	public static final String TAG = "PushDemoActivity";
	public static final String RESPONSE_METHOD = "method";
	public static final String RESPONSE_CONTENT = "content";
	public static final String RESPONSE_ERRCODE = "errcode";
	protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
	public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
	public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
	public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
	protected static final String EXTRA_ACCESS_TOKEN = "access_token";
	public static final String EXTRA_MESSAGE = "message";

	public static String logStringCache = "";
	// 获取ApiKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (PackageManager.NameNotFoundException e) {

		}
		return apiKey;
	}

	public static List<String> getTagsList(String originalText) {
		if (originalText == null || originalText.equals("")) {
			return null;
		}
		List<String> tags = new ArrayList<String>();
		int indexOfComma = originalText.indexOf(',');
		String tag;
		while (indexOfComma != -1) {
			tag = originalText.substring(0, indexOfComma);
			tags.add(tag);

			originalText = originalText.substring(indexOfComma + 1);
			indexOfComma = originalText.indexOf(',');
		}

		tags.add(originalText);
		return tags;
	}

	public static String getLogText(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getString("log_text", "");
	}

	public static void setLogText(Context context, String text) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = sp.edit();
		editor.putString("log_text", text);
		editor.commit();
	}
  //控制滚动条滚动到底部
  	public static void scrollToBottom(final ScrollView scroll) {

  		Handler mHandler = new Handler();

  		mHandler.post(new Runnable() {
  			public void run() {
  				scroll.fullScroll(ScrollView.FOCUS_DOWN);
  			}
  		});
  	}
	//读取db文件
	public static String GetDataBasePath(Context context) {
		  String DATABASE_PATH = android.os.Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/address";
		  String DATABASE_FILENAME = "dictionary.db3";
		String packageName = context.getPackageName();
		 String DB_PATH = String.format("/data/data/%1$s/databases/", packageName);

		if ((new File(DB_PATH+"address.db")).exists() == false) {
			try {
				// 如 SQLite 数据库文件不存在，再检查一下 database 目录是否存在
				File f = new File(DB_PATH+"address.db");
				// 如 database 目录不存在，新建该目录
				if (!f.exists()) {
					f.mkdir();
				}
				// 得到 assets 目录下我们实现准备好的 SQLite 数据库作为输入流
				InputStream is = context.getAssets().open("address.db");
				// 输出流
				OutputStream os = new FileOutputStream(DB_PATH+"address.db");
				// 文件写入
				byte[] buffer = new byte[1024];
				int length;
				while ((length = is.read(buffer)) > 0) {
					os.write(buffer, 0, length);
				}
				// 关闭文件流
				os.flush();
				os.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return DB_PATH+"address.db";
	}
}
