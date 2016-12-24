package com.xy.jjl.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.xy.jjl.R;
import com.xy.jjl.R.id;
import com.xy.jjl.R.layout;
import com.xy.jjl.application.ActivityManager;
import com.xy.jjl.common.APPConfig;
import com.xy.jjl.utils.OkHttpUtils;
import com.xy.jjl.utils.OkHttpUtils.Param;
import com.xy.jjl.utils.OkHttpUtils.ResultCallback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View.OnClickListener;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	Button btn_login;
	Button btn_register;
	EditText et_name;
	EditText et_pwd;
	EditText et;
	ProgressDialog myDialog;
	ActivityManager exitM=null;
	ImageView myImage;
	
	Handler myHandler=new Handler(){
		public void handleMessage(Message msg){
			Bundle b=null;
			String str=null;
			super.handleMessage(msg);
			switch(msg.what){	
			case 0:
				b=new Bundle();
				b=msg.getData();
				str=b.getString("string");
				et.setText(str);
				break;
			case 1:
				b=new Bundle();
				b=msg.getData();
				str=b.getString("string");
				et.setText(str);
				break;				
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Full screen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.login);
		
		exitM=ActivityManager.getInstance();
		exitM.addActivity(LoginActivity.this);
	
		btn_login=(Button)findViewById(R.id.btn_login);
		btn_register=(Button)findViewById(R.id.btn_register);
		et_name=(EditText)findViewById(R.id.et_name);
		et_pwd=(EditText)findViewById(R.id.et_pwd);
		
		//----------
		//test
		et_name.setText("star");
		et_pwd.setText("star");
		//----------
		et=(EditText)findViewById(R.id.et);
		btn_login.setOnClickListener(this);
		btn_register.setOnClickListener(this);	
	} 	
	public void onClick(View v){   
		if(v==btn_login){
			String name=et_name.getText().toString();
			String pwd=et_pwd.getText().toString();
			if(name.trim().equals("")){
				Toast.makeText(this, "Please enter User name!", Toast.LENGTH_SHORT).show();
				return;
			}
			if(pwd.trim().equals("")){
				Toast.makeText(this, "Please enter Password!", Toast.LENGTH_SHORT).show();
				return;
			}
			myDialog=ProgressDialog.show(this, "进度", "正在加载...");
			new Thread(){
				public void run(){
					Socket socket=null;
					DataOutputStream dout=null;
					DataInputStream din=null;
					try{
						//socket=new Socket("112.74.38.240",8888);//192.168.0.112
						socket=new Socket("192.168.0.112",8888);//
						dout=new DataOutputStream(socket.getOutputStream());
						din=new DataInputStream(socket.getInputStream());
						dout.writeUTF("<#LOGIN#>"+et_name.getText().toString()+"|"+et_pwd.getText().toString());
						String msg=din.readUTF();
						if(msg.startsWith("<#LOGINOK#>")){
							Message message=new Message();
							Bundle b=new Bundle();
							b.putString("string", msg);
							message.what=0;
							message.setData(b);
							myHandler.sendMessage(message);
							//------------------------
							msg=msg.substring(11);
							Intent intent=new Intent();
							//intent.setClass(LoginActivity.this, MainActivity.class);
							intent.setClass(LoginActivity.this, testActivity.class);
							Bundle bundle=new Bundle();
							bundle.putString("uid", msg);
							bundle.putString("u_name", et_name.getText().toString());
							intent.putExtras(bundle);
							startActivity(intent);
							LoginActivity.this.finish();
							//------------------------
							
						}else if(msg.startsWith("<#LOGINERROR#>")){
							Message message=new Message();
							Bundle b=new Bundle();
							b.putString("string", msg);
							message.what=1;
							message.setData(b);
							myHandler.sendMessage(message);
						}
						
					}catch(Exception e){
						e.printStackTrace();
					}finally{						
						try{
							if(din!=null){
								din.close();
								din=null;
							}	
						}catch(Exception e){
							e.printStackTrace();
						}
						try{
							if(dout!=null){
								dout.close();
								dout=null;
							}	
						}catch(Exception e){
							e.printStackTrace();
						}
						try{
							if(socket!=null){
								socket.close();
								socket=null;
							}	
						}catch(Exception e){
							e.printStackTrace();
						}
						myDialog.dismiss();
					}
				}
			}.start();
		}
		else if(v==btn_register){
			Intent intent=new Intent();
			intent.setClass(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
			
	}
	
	public void login(){
		new Thread(){
			public void run() {
				List<Param> list = new ArrayList<OkHttpUtils.Param>();
				list.add(new Param("user_name", "star"));
				list.add(new Param("password", "star"));
				OkHttpUtils.post(APPConfig.Login, new ResultCallback<String>() {
					public void onSuccess(String response) {
						
					};
					public void onFailure(Exception e) {
						
					};
				}, list);
			};
		}.start();
	}
}
