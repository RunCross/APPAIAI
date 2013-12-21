package edu.ahjzu.app.notice.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.server.net.HTTPServer;
import edu.ahjzu.app.notice.service.ChatService.ServiceBinder;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText nameView = null;
	private EditText pwdView = null;
	private ServiceBinder binder = null;
	private int id = -1;
	private Intent service = null;
	private CheckBox autoBox = null;
	private CheckBox pwdBox = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//
		service = new Intent();
		service.setAction("com.aiai.chat.service");
		//
		nameView = (EditText) findViewById(R.id.name);
		pwdView = (EditText) findViewById(R.id.pwd);

		findViewById(R.id.login).setOnClickListener(this);
		findViewById(R.id.reg).setOnClickListener(this);
		autoBox = (CheckBox) findViewById(R.id.autologin);
		pwdBox = (CheckBox) findViewById(R.id.checkpwd);
		initData();
	}

	private void initData() {
		// 查找是否存储了用户名和密码
		SharedPreferences preference = getSharedPreferences("loginname",
				MODE_PRIVATE);
		boolean isAuto = preference.getBoolean("auto", false);

		String name = preference.getString("name", "");
		String pwd = preference.getString("pwd", "");
		if (isAuto) {// auto login
			if (!name.isEmpty() && !pwd.isEmpty()) {
				send(name, pwd);
			}
		}
		nameView.setText(name);
		pwdView.setText(pwd);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("onDestroy");
		// 保存帐号和密码
		SharedPreferences preference = getSharedPreferences("loginname",
				MODE_PRIVATE);
		Editor editor = preference.edit();
		if (pwdBox.isChecked()) {
			editor.putString("name", nameView.getText().toString());
			editor.putString("pwd", pwdView.getText().toString());
		}
		if (autoBox.isChecked()) {
			editor.putBoolean("auto", true);
		}
		editor.commit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login:
			if (!nameView.getText().toString().isEmpty()
					&& !pwdView.getText().toString().isEmpty()) {
				send(nameView.getText().toString(), pwdView.getText()
						.toString());
			} else {
				Toast.makeText(LoginActivity.this, "用户名和密码不能为空",
						Toast.LENGTH_SHORT).show();
				// 清空
				nameView.setText("");
				pwdView.setText("");
			}
			break;
		case R.id.reg:
			Intent intent = new Intent(LoginActivity.this,
					RegeisterNameActivity.class);
			startActivity(intent);
			break;
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String state = "";
			if (msg.obj != null) {
				int usrId = Integer.valueOf(msg.obj.toString());
				System.out.println("LoginActivity  usrid:" + usrId);
				if (usrId != -1) {
					id = usrId;
					state = " 登录成功";
					// 开始服务
					startService(service);
					// 绑定服务
					bindService(service, connection, Context.BIND_AUTO_CREATE);
				} else {
					state = " 登录失败";
				}
				Toast.makeText(LoginActivity.this, state, Toast.LENGTH_SHORT)
						.show();
			}
		}
	};

	private void send(final String name, final String pwd) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("name", name));
				params.add(new BasicNameValuePair("pwd", pwd));
				String usrId = new HTTPServer().sendByPost(
						OverallData.LOGINURL, params);
				Message msg = new Message();
				msg.obj = usrId;
				handler.sendMessage(msg);
			}
		}).start();
	}

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (ServiceBinder) service;
			binder.setUsrId(id);
			unbindService(connection);
			// 跳转
			Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
			startActivity(intent);
			finish();

		}
	};
}
