package edu.ahjzu.app.notice.view.settingview;

import com.tencent.mm.sdk.openapi.WXTextObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.APP.aiainotice.R;
public class moreSharedActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.moreshared);														//取得布局控件moreshared
		
		LinearLayout tencent_weibo = (LinearLayout) findViewById(R.id.tencent_weibo);				//实例化腾讯微博的按钮
		LinearLayout sina_weibo = (LinearLayout) findViewById(R.id.sina_weibo);						//实例化新浪微博的按钮
		LinearLayout renrenwang = (LinearLayout) findViewById(R.id.renrenwang);						//实例化人人网的按钮
		LinearLayout tencent_weixin = (LinearLayout) findViewById(R.id.tencent_weixin);				//实例化腾讯微信的按钮

		LinearLayout.OnClickListener moreshared = new LinearLayout.OnClickListener(){				//生成一个监听器，监听用户点击更多分享放方式的按钮
			@Override
			public void onClick(View v) {
				switch(v.getId()){																	//取得布局文件中的id
				case R.id.tencent_weibo:															//当用户点击腾讯微博按钮
					Intent mIntent = new Intent();													//新生成一个意图对象
					mIntent.setClass(moreSharedActivity.this, OAuthV2ImplicitGrantActivity.class);	//转移到OAuthV2ImplicitGrantActivity取得Oauth
					startActivity(mIntent);
					finish();																		//关闭当前Activity
					break;
				case R.id.sina_weibo:																//当用户点击新浪微博
					Toast.makeText(moreSharedActivity.this, "sina_weibo", Toast.LENGTH_SHORT).show();
					finish();
					break;
				case R.id.renrenwang:
					Toast.makeText(moreSharedActivity.this, "renrenwang", Toast.LENGTH_SHORT).show();
					finish();
					break;
				case R.id.tencent_weixin:
					Toast.makeText(moreSharedActivity.this, "腾讯微信", Toast.LENGTH_LONG).show();
				}
			}
		};
		tencent_weibo.setOnClickListener(moreshared);												//绑定腾讯微博的按钮监听器
		sina_weibo.setOnClickListener(moreshared);													//绑定新浪微博的按钮监听器
		renrenwang.setOnClickListener(moreshared);													//绑定人人网的按钮监听器
		tencent_weixin.setOnClickListener(moreshared);												//绑定腾讯微信的按钮监听器
	}
	/**
	 * 设置用户点击屏幕的触发事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
	
}
