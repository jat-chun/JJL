package com.xy.jjl.utils;


import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

//import com.wc.xph.LoginMemberActivity;
import com.xy.jjl.application.ApplicationInfo;

/**
 * ����ȫ���쳣,��Ϊ�е��쳣���ǲ��񲻵�
 * 
 * @author river
 * 
 */
public class UncaughtException implements UncaughtExceptionHandler {
	private final static String TAG = "TAG";
	private static UncaughtException mUncaughtException;
	private Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	private UncaughtException() {
	}

	/**
	 * ͬ�����������ⵥ�����̻߳����³����쳣
	 * 
	 * @return
	 */
	public synchronized static UncaughtException getInstance() {
		if (mUncaughtException == null) {
			mUncaughtException = new UncaughtException();
		}
		return mUncaughtException;
	}

	/**
	 * ��ʼ�����ѵ�ǰ�������ó�UncaughtExceptionHandler������
	 */
	public void init() {
		Thread.setDefaultUncaughtExceptionHandler(mUncaughtException);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// �����쳣,���ǻ����԰��쳣��Ϣд���ļ����Թ�����������
		Log.e(TAG,
				"uncaughtException thread : " + thread + "||name="
						+ thread.getName() + "||id=" + thread.getId()
						+ "||exception=" + ex);
		showDialog();
	}

	private void showDialog() {
		new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				new AlertDialog.Builder(context).setTitle("��ʾ")
						.setCancelable(false).setMessage("δ֪����")
						.setNeutralButton("��֪����", new OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//Intent intent=new Intent(context,LoginMemberActivity.class);
								ApplicationInfo info = (ApplicationInfo)ApplicationInfo.getInstance();
//								info.setLoginFlag("Exit");
								ApplicationInfo.getInstance().exit();
							}
						}).create().show();
				Looper.loop();
			}
		}.start();
	}
}
