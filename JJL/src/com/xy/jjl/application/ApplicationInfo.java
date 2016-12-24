package com.xy.jjl.application;


import io.vov.vitamio.Vitamio;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.wlf.filedownloader.FileDownloadConfiguration;
import org.wlf.filedownloader.FileDownloadConfiguration.Builder;
import org.wlf.filedownloader.FileDownloader;

import android.app.Activity;
import android.app.Application;
import android.os.Environment;

import com.xy.jjl.common.APPConfig;
import com.xy.jjl.utils.RsSharedUtil;
import com.xy.jjl.utils.UncaughtException;

public class ApplicationInfo extends Application {

	private List<Activity> mList = new LinkedList<Activity>();
	private static ApplicationInfo info;

	public ApplicationInfo() {

	}

	@Override
	public void onCreate() {

		UncaughtException mUncaughtException = UncaughtException.getInstance();
		mUncaughtException.init();

		super.onCreate();
		
		//��ʼ��Vitomio
		Vitamio.isInitialized(ApplicationInfo.this);
		
		String user_name = RsSharedUtil.getString(this, APPConfig.USER_NAME);
		
		RsSharedUtil.putString(this, APPConfig.USER_NAME, user_name);
		
		
		
		//---------------------------------��ʼ��FileDownloader---��ʼ---------------------------------------//
		// 1������Builder
		Builder builder = new FileDownloadConfiguration.Builder(ApplicationInfo.this);
		// 2.����Builder
		// ���������ļ�������ļ���
		builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
		        "FileDownloader");
		// ����ͬʱ�����������������������Ĭ��Ϊ2
		builder.configDownloadTaskSize(3);
		// ����ʧ��ʱ�������ԵĴ��������������Ĭ��Ϊ0������
		builder.configRetryDownloadTimes(5);
		// ��������ģʽ������鿴��־�ȵ�����أ����������Ĭ�ϲ�����
		builder.configDebugMode(true);
		// �����������糬ʱʱ�䣬���������Ĭ��Ϊ15��
		builder.configConnectTimeout(25000);// 25��

		// 3��ʹ�������ļ���ʼ��FileDownloader
		FileDownloadConfiguration configuration = builder.build();
		FileDownloader.init(configuration);
		//---------------------------------��ʼ��FileDownloader---����---------------------------------------//
	}

	public synchronized static ApplicationInfo getInstance() {
		if (null == info) {
			info = new ApplicationInfo();
		}
		return info;
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);

		UncaughtException.getInstance().setContext(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}

}