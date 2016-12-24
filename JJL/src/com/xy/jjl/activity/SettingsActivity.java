package com.xy.jjl.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.xy.jjl.R;
import com.xy.jjl.R.anim;
import com.xy.jjl.R.id;
import com.xy.jjl.R.layout;
import com.xy.jjl.application.ActivityManager;
import com.xy.jjl.common.APPConfig;
import com.xy.jjl.image.ImageShowActivity;
import com.xy.jjl.utils.FolderUtil;
import com.xy.jjl.utils.OkHttpUtils;
import com.xy.jjl.utils.OkHttpUtils.Param;
import com.xy.jjl.utils.OkHttpUtils.ResultCallback;
import com.xy.jjl.utils.UpdateManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener{

	private TextView img_upload;
	private ImageButton settingBack;
	private TextView settingAboutUsTextView;
	private TextView settingDisclaimerTextView;
	private TextView settingVideo;
	private TextView UpdateVersion;
	
	
	private String u_name;
	private int uid;
	ClientNetThread clientNetThread = null;//网络处理线程
	private final int IMAGE_CODE=0;
	private String ImagePath=null;
	public ProgressDialog myDialog;
	
	public Handler myHandler=new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			switch(msg.what){	
			case 0:
				//发送完成提示
				//====================		
				new AlertDialog.Builder(SettingsActivity.this).setTitle("Update").setMessage("更新完成!")
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				}).show();	
				//====================
				break;	
			}
		}
	};
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		ActivityManager.activityList.add(this);
		
		img_upload=(TextView)findViewById(R.id.settingImage);
		img_upload.setOnClickListener(this);
		//========
		settingBack=(ImageButton) findViewById(R.id.settingBack);
		settingBack.setOnClickListener(this);
		//=======
		
		settingVideo=(TextView)findViewById(R.id.settingVideo);
		settingVideo.setOnClickListener(this);
		
		UpdateVersion=(TextView)findViewById(R.id.settingUpdateVersion);
		UpdateVersion.setOnClickListener(this);
		
		settingAboutUsTextView=(TextView)findViewById(R.id.settingAboutUsTextView);
		settingAboutUsTextView.setOnClickListener(this);
		
		settingDisclaimerTextView=(TextView)findViewById(R.id.settingDisclaimerTextView);
		settingDisclaimerTextView.setOnClickListener(this);
	}
	 
	
	public void openActivity(Context context, Class<?> cls, int flag,boolean isfinish){
		Intent intent=new Intent();
		intent.setClass(context, cls);
		startActivity(intent);
		if(flag==0){
			overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);	
		}
		else if(flag==1){
			overridePendingTransition(R.anim.slide_right_in,R.anim.slide_left_out);	
		}
		if(isfinish){
			finish();
		}
	}
	public void onClick(View v){
		int id = v.getId();
		switch (id) {
		
		case R.id.settingBack:
			openActivity(SettingsActivity.this,MainActivity.class,0,true);
			break;
			
		case R.id.settingImage:
			openActivity(SettingsActivity.this,ImageShowActivity.class,1,true);
			break;
			
		case R.id.settingVideo:
			StringBuffer sb = new StringBuffer();
			sb.append("视频上传下载功能尚未完善！");
			Dialog dialog = new AlertDialog.Builder(SettingsActivity.this).setTitle("视频")
					.setMessage(sb.toString())// 提示内容
					.setPositiveButton("确定", null)// 设置确定按钮
					.create();// 创建
			dialog.show();
			break;
		
		case R.id.settingUpdateVersion:
			UpdateManager updateManager=new UpdateManager(SettingsActivity.this,true);
			updateManager.showNoNewVersionDialog();
			break;
			
		case R.id.settingAboutUsTextView:
			openActivity(SettingsActivity.this,AboutUsActivity.class,1,false);				
			break;
			
		case R.id.settingDisclaimerTextView:
			openActivity(SettingsActivity.this,DisclaimerActivity.class,1,false);
			break;
		default:
			break;
		}
	}
 
	
	private void creatImageFolder(){
		
		File file=null;
		file=Environment.getExternalStorageDirectory();
		ImagePath=file.getAbsolutePath()+File.separator+"JJL";
		file=new File(ImagePath);
		if(!file.exists()){
			file.mkdirs();
		}
	}
	 
	
	@Override
	protected void onDestroy() {//Activity被摧毁时被调用
		super.onDestroy();
	}
	
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Intent intent=new Intent();
			intent.setClass(SettingsActivity.this, MainActivity.class);
			startActivity(intent);					
			overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);						
			SettingsActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
