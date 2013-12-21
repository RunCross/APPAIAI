package edu.ahjzu.app.notice.view;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.server.net.HTTPServer;

public class RegeisterNameActivity extends Activity {
	EditText text = null;
	String strname = null;
	ProgressDialog dialog = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regeistername);

		Button btn = (Button) findViewById(R.id.submitbtn);
		text = (EditText) findViewById(R.id.name);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strname = text.getText().toString();

				System.out.println("regeisternameActivity_strname:" + strname);
				if (!strname.equals("")) {
					if (strname.contains(";") || strname.contains("//")) {
						myToast("用户名中有非法字符！请修改");
					} else {
						myWait();
						TestData();
					}
				} else {
					myToast("用户名不能为空！");
				}
			}

		});
	}

	/**
	 * 监测登录
	 */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dialog.cancel();
			// 测试代码
			// {
			// Intent intent = new Intent(RegeisterNameActivity.this,
			// IndexActivity.class);
			// startActivity(intent);
			// finish();
			// }
			Boolean ishave = (Boolean) msg.obj;
			if (ishave) {
				Intent i = new Intent(RegeisterNameActivity.this,
						RegisterActivity.class);
				i.putExtra("name", strname);
				startActivity(i);
			} else {
				myToast("用户名已存在！请再换一个");
			}
		}
	};

	private void TestData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				map.put("Oper", "loginname");
				map.put("name", strname);
				String ishave = new HTTPServer().HttpGet(OverallData.actionUrl
						+ "initServlet", map);
				Message message = new Message();
				message.obj = Boolean.valueOf(ishave);
				handler.sendMessage(message);
			}
		}).start();
	}

	private void myWait() {
		dialog = new ProgressDialog(this);
		// 设置进度条风格，风格为圆形，旋转的
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置ProgressDialog 标题
		dialog.setTitle("正在验证");
		// 设置ProgressDialog 提示信息
		// dialog.setMessage("圆形进度条");
		// 设置ProgressDialog 标题图标
		dialog.setIcon(android.R.drawable.ic_dialog_map);
		// 设置ProgressDialog 的一个Button
		/*
		 * dialog.setButton("确定", new ProgressDialog.OnClickListener(){
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * 
		 * } });
		 */
		// 设置ProgressDialog 的进度条是否不明确
		dialog.setIndeterminate(false);
		// 设置ProgressDialog 是否可以按退回按键取消
		dialog.setCancelable(true);
		// 显示
		dialog.show();
	}

	private void myToast(String msg) {
		/*
		 * Toast toast = Toast.makeText(getApplicationContext(), msg,
		 * Toast.LENGTH_LONG); toast.setGravity(Gravity.CENTER, 0, 0);
		 * toast.show();
		 */
		new AlertDialog.Builder(this).setMessage(msg)

		.setPositiveButton("确定", null)

		.show();
	}
}
