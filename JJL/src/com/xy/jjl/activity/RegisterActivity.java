package com.xy.jjl.activity;

import com.xy.jjl.R;
import com.xy.jjl.R.anim;
import com.xy.jjl.R.id;
import com.xy.jjl.R.layout;
import com.xy.jjl.image.ImageShowActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class RegisterActivity extends Activity implements OnClickListener {
	
	EditText et_user;
	EditText et_password;
	EditText et_password_checked;
	Button btn_register;
	Button btn_cancel;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Full screen
		/*requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
							 WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		setContentView(R.layout.register);
		et_user=(EditText)findViewById(R.id.et_user);
		et_password=(EditText)findViewById(R.id.et_password);
		et_password_checked=(EditText)findViewById(R.id.et_password_checked);
		btn_register=(Button)findViewById(R.id.btn_register);
		btn_cancel=(Button)findViewById(R.id.btn_cancel);
		btn_register.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		et_user.requestFocus();
	}
	public boolean onKeyDown(int keyCode,KeyEvent event){
		if(keyCode==KeyEvent.KEYCODE_BACK){
			RegisterActivity.this.finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v==btn_register){
			String name=et_user.getText().toString();
			String pwd1=et_password.getText().toString();
			String pwd2=et_password_checked.getText().toString();
			
			if(name.trim().equals("")){
				Toast.makeText(this, "请输入用户名!", Toast.LENGTH_SHORT).show();
				return;
			}
			if(pwd1.trim().equals("")){
				Toast.makeText(this, "请输入密码!", Toast.LENGTH_SHORT).show();
				return;
			}
			if(pwd2.trim().equals("")){
				Toast.makeText(this, "请输入确认密码!", Toast.LENGTH_SHORT).show();
				return;
			}
			
			if(pwd1.equals(pwd2)){
				new AlertDialog.Builder(RegisterActivity.this).setTitle("提示").setMessage("注册成功!")
				.setPositiveButton("OK",new DialogInterface.OnClickListener() {					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent=new Intent();
						//intent.setClass(RegisterActivity.this, MainActivity.class);
						intent.setClass(RegisterActivity.this, testActivity.class);
						startActivity(intent);
						RegisterActivity.this.finish();
						return;
					}
				}).show();	
			}
			else{
				Toast.makeText(this, "两次输入密码不匹配，请重新输入!", Toast.LENGTH_SHORT).show();
				return;
			}
			
		}
		else if(v==btn_cancel){
			Intent intent=new Intent();
			intent.setClass(RegisterActivity.this, LoginActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.slide_left_in,R.anim.slide_right_out);	
			finish();
		}
	}
}
