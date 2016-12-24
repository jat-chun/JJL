package com.xy.jjl.service;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
 
import com.xy.jjl.R;
import com.xy.jjl.activity.MainActivity;
 
/**
 * ����Ӧ�÷���
 * @author Administrator
 *
 */
public class UpdateService extends Service {

	private int titleId=0;//����
	//����״̬:������   �������   ����ʧ��
	private final static int DOWNLOADING=0; 
	private final static int DOWNLOAD_COMPLETE=1;
	private final static int DOWNLOAD_FAIL=-1;
	
	public static String downloadUrl = null;//HttpUtil.UploadVersion;//��������
	
	//�ļ��洢
	private File updateDir =null;
	private File updateFile=null;
	
	//֪ͨ��
	private NotificationManager updateNotificationManager=null;
	private Notification updateNotification=null;
	
	//���֪ͨ��֪ͨ�� ��תIntent
	private Intent updateIntent =null;
	private PendingIntent updatePendingIntent=null;
	
	private static String notificationTitle="���岿��";
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
//		Log.i("TAG", " ��������onCreate()...");
//		Toast.makeText(this, "��������onCreate()...", Toast.LENGTH_SHORT)
//		.show();
		super.onCreate();	 
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
//		Log.i("TAG", " �󶨷���onBind()...");
//		Toast.makeText(this, "�󶨷���onBind()...", Toast.LENGTH_SHORT)
//		.show();
		return null;
	}	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
//		Log.i("TAG", "���ٷ���onDestroy()...");
//		Toast.makeText(this, "���ٷ���onDestroy()...", Toast.LENGTH_SHORT)
//		.show();
		super.onDestroy();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
//		Log.i("TAT", "�������ط���onStartCommand...");
//		Toast.makeText(this, "�������ط���onStartCommand...", Toast.LENGTH_SHORT)
//		.show();
		//��ȡ��ֵ
		titleId =intent.getIntExtra("titleId",0);
		//�����ļ�
		if(android.os.Environment.MEDIA_MOUNTED.equals(
				android.os.Environment.getExternalStorageState())){
			updateDir=new File(Environment.getExternalStorageDirectory(),
					"app/download/");
			updateFile=new File(updateDir.getPath(),
					getResources().getString(titleId)+ ".apk");
		}
		
		this.updateNotificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		this.updateNotification=new Notification();
		
		//�������ع����� ���֪ͨ��  �ص�ĳ������
		updateIntent =new Intent(this, MainActivity.class );
		updatePendingIntent=PendingIntent.getActivity(this,0, updateIntent,0);
		//����֪ͨ�� ��ʾ����
		updateNotification.icon=R.drawable.ic_launcher;
		updateNotification.tickerText="��ʼ����";
		updateNotification.setLatestEventInfo(this, notificationTitle , "0%", updatePendingIntent);
		//����֪ͨ
		updateNotificationManager.notify(0, updateNotification);
		
		//����һ���µ��߳����� 
		new Thread(new updateRunnable()).start();
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private Handler updateHandler=new Handler(){
		
		public void handleMessage(Message msg) {
			switch (msg.what) {
			//������    ����֪ͨ���Ľ�����ʾ
			case DOWNLOADING:
//				updateNotification.setLatestEventInfo(UpdateService.this,
//						 notificationTitle, "������   "+ msg.arg1 +"%", updatePendingIntent);
				updateNotification.setLatestEventInfo(UpdateService.this,
						 notificationTitle, "������...   ", null);
//				updateNotification.setLatestEventInfo(UpdateService.this,
//						notificationTitle, "������   "+ msg.arg1 +"%", null);
				updateNotificationManager.notify(0, updateNotification);
				break;
				
			//���سɹ�  ��ʾ��װ
			case DOWNLOAD_COMPLETE:
				//������ֱ�Ӱ�װ
				updateNotificationManager.cancel(0);
				
				Intent installIntent=new Intent(Intent.ACTION_VIEW);
				Uri uri=Uri.fromFile(updateFile);
				installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				installIntent.setDataAndType(
						uri, "application/vnd.android.package-archive");
				getApplicationContext().startActivity(installIntent);
//				updateNotification.flags|=updateNotification.FLAG_AUTO_CANCEL;
//				//�����װ
//				Intent installIntent=new Intent(Intent.ACTION_VIEW);
//				Uri uri=Uri.fromFile(updateFile);
//				installIntent.setDataAndType(
//						uri, "application/vnd.android.package-archive");
//				updatePendingIntent=PendingIntent.getActivity(UpdateService.this,
//						0, installIntent, 0);
//				updateNotification.defaults=Notification.DEFAULT_LIGHTS;
//				updateNotification.setLatestEventInfo(UpdateService.this,
//						notificationTitle, "������ɣ������װ", updatePendingIntent);
//				updateNotificationManager.notify(0, updateNotification);
				stopSelf();
//				stopService(new Intent("com.qb.drawingartifact.serviec.UpdateService"));
//				Log.i("TAG", " ������ɣ��ر� ����stopService()...");
				 
				break;
				
			//����ʧ��
			case DOWNLOAD_FAIL:
				updateNotification.setLatestEventInfo(UpdateService.this,
						notificationTitle, "����ʧ��", updatePendingIntent);
				updateNotificationManager.notify(0, updateNotification);
				stopService(updateIntent);
				break;
				
			default:
				stopSelf();
				break;
			}
		};
	};
	
	class updateRunnable implements Runnable {

		Message message= updateHandler.obtainMessage();
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
			//����Ȩ��<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE">;  
				if (!updateDir.exists()) {
					updateDir.mkdirs();
				}
				if (updateFile.exists()) {
					updateFile.delete();
				}
				updateFile.createNewFile();
				
                //�õ��������ʱ�ӷ����ȡ����APK���ص�ַ				
				long downloadSize= downloadUpdateFile(downloadUrl , updateFile);
				if(downloadSize>0){
					//���سɹ�
					message.what=DOWNLOAD_COMPLETE;
					updateHandler.sendMessage(message);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//����ʧ��
				message.what=DOWNLOAD_FAIL;
				updateHandler.sendMessage(message);
			}			
		}	
	} 
	
	//�����ļ�
	public long downloadUpdateFile(String downloadUri,File saveFile) throws Exception{
		
		int downloadCount=0;//�Ѿ����ذٷֱ���  ����֪ͨ����ʾ���ذٷֱ�
		int currentSize=0;
		long totalSize=0;//�ļ����ع�����  �Ѿ����صĴ�С
		int updateTotalSize=0;//�������ϵ�Ҫ�����ļ��Ĵ�С
		
		HttpURLConnection httpConnection=null;
		InputStream is=null;
		FileOutputStream fos =null;
		
		try {
			URL url = new URL(downloadUri);
			httpConnection=(HttpURLConnection)url.openConnection();
			
			httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
			if(currentSize >0){
				httpConnection.setRequestProperty("RANGE", 
						"bytes="+ currentSize + "-");
			}
			
			httpConnection.setConnectTimeout(10000);
			httpConnection.setReadTimeout(20000);
			updateTotalSize=httpConnection.getContentLength();
			
			if(httpConnection.getResponseCode() == 404){
				throw new Exception("fail!");			
			}
			
			is=httpConnection.getInputStream();
			fos=new FileOutputStream(saveFile, false);
			byte buffer[] =new byte[4096];
			int readSize = 0;
			while((readSize=is.read(buffer)) >0){
				fos.write(buffer, 0, readSize);
				totalSize +=readSize;
				//Ϊ�˷�ֹƵ����֪ͨ����Ӧ�óԽ����ٷֱ�����5��֪ͨһ��
				if(downloadCount == 0 || 
					 (int) (totalSize*100/updateTotalSize)-5 >downloadCount){
					downloadCount+=5;
					Message message=updateHandler.obtainMessage();
					message.what=DOWNLOADING;
					message.arg1=downloadCount;
					updateHandler.sendMessage(message);
				}
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(httpConnection != null){
				httpConnection.disconnect();
			}
			if(is != null){
				is.close();
			}
			if(fos != null) {
				fos.close();
			}
		}
				
		return totalSize;
	}

}
