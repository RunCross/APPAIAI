package edu.ahjzu.app.notice.view;

import java.util.List;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.ChatContent;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.server.net.UDPClient;
import edu.ahjzu.app.notice.service.ChatService.ContentChangeListener;
import edu.ahjzu.app.notice.service.ChatService.ServiceBinder;
import edu.ahjzu.app.notice.util.DateUtil;
import edu.ahjzu.app.notice.view.adapter.chatingWindowAdapter;

/**
 * 聊天中界面
 * 
 * @author Administrator
 * 
 */
public class ChatingActivity extends Activity implements OnClickListener {
	private final String LOGTAG = "ChatingActivity";

	private EditText editView;
	private ListView listView;
	private chatingWindowAdapter adapter;
	private ServiceBinder binder = null;
	private Usr otherUsr = null;
	private List<ChatContent> contents = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatwindow);
		// 得到聊天对象的用户信息
		otherUsr = (Usr) getIntent().getSerializableExtra("usr");
		initView();
		// 绑定服务
		Intent service = new Intent();
		service.setAction("com.aiai.chat.service");
		bindService(service, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		System.out.println("ChatingActivity  onDestroy");
		unbindService(connection);
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listview);
		findViewById(R.id.btn_send).setOnClickListener(this);
		findViewById(R.id.btn_back).setOnClickListener(this);
		editView = (EditText) findViewById(R.id.et_sendmessage);
		TextView titleView = (TextView) findViewById(R.id.chatwindowtitile);
		titleView.setText(otherUsr.getName());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_send:
			sendMsg();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}

	private void sendMsg() {
		String contString = editView.getText().toString();
		if (contString.length() > 0) {
			ChatContent content = new ChatContent();
			content.setFromId(binder.getMyUsr().getId() + "");
			content.setToId(otherUsr.getId() + "");
			content.setTime(DateUtil.getDate());
			content.setContent(contString);
			// 转化为json格式
			final String jsonString = content.toJson();
			// 发送数据
			new Thread(new Runnable() {
				@Override
				public void run() {
					Log.v(LOGTAG, "发送消息:" + jsonString);
					UDPClient.sendMsg(otherUsr.getIp(),
							OverallData.RECEIVEPORT, jsonString);
					Log.v(LOGTAG, "发送完毕");
				}
			}).start();
			// 添加到数据库
			if (binder != null) {
				binder.addChatContent(content);
				contents = binder.getContents(otherUsr.getId());
				System.out.println("content size:" + contents.size());
				adapter.setData(contents);
				adapter.notifyDataSetChanged();
			} else {
				throw new RuntimeException("ChatService服务未连接");
			}
			listView.setSelection(listView.getCount());
			// 清空输入框
			editView.setText("");
		}
	}

	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (ServiceBinder) service;
			contents = binder.getContents(otherUsr.getId());
			Usr ownUsr = binder.getMyUsr();
			adapter = new chatingWindowAdapter(ChatingActivity.this, contents,
					ownUsr, otherUsr);
			listView.setAdapter(adapter);
			listView.setSelection(listView.getCount());
			//
			binder.setChatContentChangeListener(new ContentChangeListener() {
				@Override
				public void handler(int content_usrid) {
					if (content_usrid == otherUsr.getId()) {
						contents = binder.getContents(otherUsr.getId());
						adapter.setData(contents);
						adapter.notifyDataSetChanged();
						listView.setSelection(listView.getCount());
					}
				}
			});

		}
	};
}