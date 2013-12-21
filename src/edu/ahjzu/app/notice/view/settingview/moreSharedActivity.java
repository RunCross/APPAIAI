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
		setContentView(R.layout.moreshared);														//ȡ�ò��ֿؼ�moreshared
		
		LinearLayout tencent_weibo = (LinearLayout) findViewById(R.id.tencent_weibo);				//ʵ������Ѷ΢���İ�ť
		LinearLayout sina_weibo = (LinearLayout) findViewById(R.id.sina_weibo);						//ʵ��������΢���İ�ť
		LinearLayout renrenwang = (LinearLayout) findViewById(R.id.renrenwang);						//ʵ�����������İ�ť
		LinearLayout tencent_weixin = (LinearLayout) findViewById(R.id.tencent_weixin);				//ʵ������Ѷ΢�ŵİ�ť

		LinearLayout.OnClickListener moreshared = new LinearLayout.OnClickListener(){				//����һ���������������û�����������ŷ�ʽ�İ�ť
			@Override
			public void onClick(View v) {
				switch(v.getId()){																	//ȡ�ò����ļ��е�id
				case R.id.tencent_weibo:															//���û������Ѷ΢����ť
					Intent mIntent = new Intent();													//������һ����ͼ����
					mIntent.setClass(moreSharedActivity.this, OAuthV2ImplicitGrantActivity.class);	//ת�Ƶ�OAuthV2ImplicitGrantActivityȡ��Oauth
					startActivity(mIntent);
					finish();																		//�رյ�ǰActivity
					break;
				case R.id.sina_weibo:																//���û��������΢��
					Toast.makeText(moreSharedActivity.this, "sina_weibo", Toast.LENGTH_SHORT).show();
					finish();
					break;
				case R.id.renrenwang:
					Toast.makeText(moreSharedActivity.this, "renrenwang", Toast.LENGTH_SHORT).show();
					finish();
					break;
				case R.id.tencent_weixin:
					Toast.makeText(moreSharedActivity.this, "��Ѷ΢��", Toast.LENGTH_LONG).show();
				}
			}
		};
		tencent_weibo.setOnClickListener(moreshared);												//����Ѷ΢���İ�ť������
		sina_weibo.setOnClickListener(moreshared);													//������΢���İ�ť������
		renrenwang.setOnClickListener(moreshared);													//���������İ�ť������
		tencent_weixin.setOnClickListener(moreshared);												//����Ѷ΢�ŵİ�ť������
	}
	/**
	 * �����û������Ļ�Ĵ����¼�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		return super.onTouchEvent(event);
	}
	
}
