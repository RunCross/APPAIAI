package edu.ahjzu.app.notice.view.settingview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.APP.aiainotice.R;

public class sharedActivity extends Activity {
	private TextView textView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.settingshared);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebasis);
		/**
		 * 实例化Button和TextView
		 */
		// Button backbtn = (Button)findViewById(R.id.backbtn);
		ImageButton refeshbtn = (ImageButton) findViewById(R.id.refeshbtn);
		TextView title = (TextView) findViewById(R.id.title);
		textView = (TextView) findViewById(R.id.textView5);
		// refeshbtn.setText("更多");//把refeshbtn按钮的文字重写为：更多分享方式
		title.setText("分享应用");// 把title文本框的内容重写为:分享应用
		/**
		 * 绑定textView的监听器，并设置titlebasis按钮的触发事件
		 */
		textView.setOnClickListener(TVlistener);
		/*
		 * backbtn.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { finish(); } });
		 */
		refeshbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.setClass(sharedActivity.this, moreSharedActivity.class);
				startActivity(mIntent);
			}
		});
	}

	// textView的监听器
	TextView.OnClickListener TVlistener = new TextView.OnClickListener() {
		@Override
		// 调用发短信功能
		public void onClick(View v) {
			Uri uri = Uri.parse("smsto:"); // 同过Uri获得短信目的地号码
			String downloadUrl = (String) textView.getText();// 取得textView的内容(应用下载地址)
			Intent mIntent = new Intent(Intent.ACTION_SENDTO, uri);// 通过Intent调用系统短信功能
			mIntent.putExtra("sms_body", downloadUrl);// 内容到信息的内容框
			startActivity(mIntent);
		}
	};

}
