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
		 * ʵ����Button��TextView
		 */
		// Button backbtn = (Button)findViewById(R.id.backbtn);
		ImageButton refeshbtn = (ImageButton) findViewById(R.id.refeshbtn);
		TextView title = (TextView) findViewById(R.id.title);
		textView = (TextView) findViewById(R.id.textView5);
		// refeshbtn.setText("����");//��refeshbtn��ť��������дΪ���������ʽ
		title.setText("����Ӧ��");// ��title�ı����������дΪ:����Ӧ��
		/**
		 * ��textView�ļ�������������titlebasis��ť�Ĵ����¼�
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

	// textView�ļ�����
	TextView.OnClickListener TVlistener = new TextView.OnClickListener() {
		@Override
		// ���÷����Ź���
		public void onClick(View v) {
			Uri uri = Uri.parse("smsto:"); // ͬ��Uri��ö���Ŀ�ĵغ���
			String downloadUrl = (String) textView.getText();// ȡ��textView������(Ӧ�����ص�ַ)
			Intent mIntent = new Intent(Intent.ACTION_SENDTO, uri);// ͨ��Intent����ϵͳ���Ź���
			mIntent.putExtra("sms_body", downloadUrl);// ���ݵ���Ϣ�����ݿ�
			startActivity(mIntent);
		}
	};

}
