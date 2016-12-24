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
		
		//初始化Vitomio
		Vitamio.isInitialized(ApplicationInfo.this);
		
		String user_name = RsSharedUtil.getString(this, APPConfig.USER_NAME);
		
		RsSharedUtil.putString(this, APPConfig.USER_NAME, user_name);
		
		
		
		//---------------------------------初始化FileDownloader---开始---------------------------------------//
		// 1、创建Builder
		Builder builder = new FileDownloadConfiguration.Builder(ApplicationInfo.this);
		// 2.配置Builder
		// 配置下载文件保存的文件夹
		builder.configFileDownloadDir(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
		        "FileDownloader");
		// 配置同时下载任务数量，如果不配置默认为2
		builder.configDownloadTaskSize(3);
		// 配置失败时尝试重试的次数，如果不配置默认为0不尝试
		builder.configRetryDownloadTimes(5);
		// 开启调试模式，方便查看日志等调试相关，如果不配置默认不开启
		builder.configDebugMode(true);
		// 配置连接网络超时时间，如果不配置默认为15秒
		builder.configConnectTimeout(25000);// 25秒

		// 3、使用配置文件初始化FileDownloader
		FileDownloadConfiguration configuration = builder.build();
		FileDownloader.init(configuration);
		//---------------------------------初始化FileDownloader---结束---------------------------------------//
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