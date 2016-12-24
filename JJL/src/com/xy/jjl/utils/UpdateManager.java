package com.xy.jjl.utils;


import org.json.JSONException;
import org.json.JSONObject;

import com.xy.jjl.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.xy.jjl.service.*;
//����Ӧ�ù�����
public class UpdateManager {

	private static Context mContext;
	private static String newVerName = "";
	private static int newVerCode =0;
	//�Ƿ�  �������ø��¶Ի���    �������������ʱ   ����Ҫ����ʱ�Ͳ������Ի���
	private static boolean isShowNotUpdateDialog=true;

	public UpdateManager(Context context,boolean isShowNotUpdateDialog) {
		this.mContext = context;
		this.isShowNotUpdateDialog=isShowNotUpdateDialog;
	}

	// ��ȡ��ǰ�汾����
	private static int getCurrentVerCode() {
		int verCode = -1;
		try {
			// ��ȡ��ǰ�汾����
			verCode = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			Log.i("TAG", e.getMessage());
		}
		return verCode;
	}

	// ��ȡ��ǰ�汾������
	private static String getCurrentVerName() {
		String verName = "";
		try {
			verName = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			Log.i("TAG", e.getMessage());
		}
		return verName;
	}

	// ��ȡ Ӧ������
	private static String getCurrentAppName() {
		String appName = mContext.getResources().getText(R.string.app_name)
				.toString();
		return appName;
	}
	
	// ����Ƿ�Ҫ���� �����ǱȽϵ�ǰ�汾 ��������汾��С��
	public void checkUpdate() {
		CheckServerVersionCodeTask check = new CheckServerVersionCodeTask();
		check.execute();
	}

	class CheckServerVersionCodeTask extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			//������м����²�����ʾ���ȿ�
			if(isShowNotUpdateDialog){
				ProgressDialogUtil.showProgressDialog(mContext, "", "���ڼ��汾...");
			}			
		}

		@Override
		protected String doInBackground(String... params) {
			try {
				String resultJson = null;//HttpUtil.httpGet(HttpUtil.CheckServerCode);
				return resultJson;
			} catch (Exception e) {
				return "error";
			}
		}

		@Override
		protected void onPostExecute(String result) {
			if(isShowNotUpdateDialog){
				ProgressDialogUtil.dismissDialog();
			}			
			if (result.isEmpty()) {
				Toast.makeText(mContext, "��ȡ�������汾��Ϣʧ��", Toast.LENGTH_SHORT)
						.show();
				newVerCode = -1;
				newVerName = "";
			} else if (result.equals("error")) {
				Toast.makeText(mContext, "��ȡ�������汾��Ϣʧ��", Toast.LENGTH_SHORT)
						.show();
				newVerCode = -1;
				newVerName = "";
			} else {
				try {
					JSONObject jsonObject = new JSONObject(result);
					newVerCode = Integer.parseInt(jsonObject.getString("serverVersion"));
//					Log.i("TAG", "�������汾��"+newVerCode);
					
					int curVerCode = getCurrentVerCode();// ��ȡ��ǰ�汾
//					String curVerName = getCurrentVerName();// ��ȡ��ǰ�汾��
//					Log.i("TAG", "��ǰ�汾��"+curVerCode);					 
					 
					if ( newVerCode > curVerCode) {
						// ��ʾҪ����
						showNewVersionUpdateDialog();
					} else if(isShowNotUpdateDialog){						 
						// ��ʾ������߰汾 ���ø���
						showNoNewVersionDialog();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			super.onPostExecute(result);
		}
	}

	// ���ø�����ʾ�Ի���
	public static void showNoNewVersionDialog() {
		StringBuffer sb = new StringBuffer();
		sb.append("�������°棬������£�");
		Dialog dialog = new AlertDialog.Builder(mContext).setTitle("�������")
				.setMessage(sb.toString())// ��ʾ����
				.setPositiveButton("ȷ��", null)// ����ȷ����ť
				.create();// ����
		dialog.show();
	}

	private static void showNewVersionUpdateDialog() {
//		int verCode = getCurrentVerCode();
//		String verName = getCurrentVerName();
		StringBuffer sb = new StringBuffer();
//		sb.append("��ǰ�汾��");
//		sb.append(verName);
		sb.append("�����°汾���Ƿ����?");
		Dialog dialog = new AlertDialog.Builder(mContext).setTitle("�������")
				.setMessage(sb.toString())
				.setPositiveButton("����", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						// �����¶��� ���ز�����֪ͨ����ʾ���ؽ���
						Intent intent = new Intent(mContext,
								UpdateService.class);
						intent.putExtra("titleId", R.string.app_name);
						mContext.startService(intent);
					}
				}).setNegativeButton("�ݲ�����", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).create();

		dialog.show();
	}

}

