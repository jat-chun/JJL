package com.xy.jjl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.xy.jjl.R;
import com.xy.jjl.application.ActivityManager;


public class AboutUsActivity  extends Activity {

	private ImageButton back;// ����
	private TextView versionCodeTV, versionNameTV;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_us);
		ActivityManager.activityList.add(this);
		initView();
	}

	public void initView() {
		back = (ImageButton) findViewById(R.id.aboutUsBackImageButton);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
/*		
//		versionCodeTV=(TextView)findViewById(R.id.aboutVersionsCodeTV);
		versionNameTV=(TextView)findViewById(R.id.aboutVersionsNameTV);
		int versionCode;
		String versionName;
		try {
//			versionCode=getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
			versionName=getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//			versionCodeTV.setText("��ǰ�汾�ţ�" + versionCode);
			versionNameTV.setText("��ǰ�汾��"+ versionName);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}
}
