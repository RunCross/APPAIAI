package edu.ahjzu.app.notice.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.WeiBo;
import edu.ahjzu.app.notice.server.weiboDataGet;
import edu.ahjzu.app.notice.server.io.IOServer;
import edu.ahjzu.app.notice.server.xml.XMLSwitch;
import edu.ahjzu.app.notice.view.adapter.IndexAdapter;

public class IndexActivity extends Activity {
	List<WeiBo> weibos = new ArrayList<WeiBo>();
	ListView listView = null;
	private String LOG = "indexActivity";
	IndexAdapter adapter = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		initView();
		initData();
		refreshData();
	}

	/**
	 * 加载缓存的数据
	 */
	private void initData() {
		IOServer io = new IOServer();
		File f = new File(OverallData.WEIBOCACHEFILEPATH);
		if (f.exists()) {
			byte[] data = io.IORead(f);
			if (data != null) {
				List<WeiBo> weiboList = XMLSwitch.getWeibo(new String(data));
				if (weiboList != null) {
					weibos = weiboList;
				}
			}

		}
	}

	private void initView() {
		Button indexbtn = (Button) findViewById(R.id.button1);
		Button newsbtn = (Button) findViewById(R.id.button2);
		Button charbtn = (Button) findViewById(R.id.button3);
		Button setbtn = (Button) findViewById(R.id.button4);
		indexbtn.setOnClickListener(btnlistener);
		newsbtn.setOnClickListener(btnlistener);
		charbtn.setOnClickListener(btnlistener);
		setbtn.setOnClickListener(btnlistener);

		File f = new File(OverallData.WEIBOIMAGEPATH);
		if (!f.exists()) {
			f.mkdirs();
		}
		f = new File(OverallData.USRICONPATH);
		if (!f.exists()) {
			f.mkdirs();
		}
		listView = (ListView) findViewById(R.id.weibolist);
		adapter = new IndexAdapter(IndexActivity.this, weibos,
				R.layout.weibo_listview_item, OverallData.WEIBOIMAGEPATH);
		listView.setAdapter(adapter);
		// 添加监听

	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			weibos = (List<WeiBo>) msg.obj;
			if (weibos != null) {
				adapter.weibos = weibos;
				adapter.notifyDataSetChanged();
			} else {
				Log.v(LOG, "weibos为空");
			}
		}
	};

	private void refreshData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				handler.sendMessage(handler.obtainMessage(21,
						weiboDataGet.getnewsData()));
			}
		}).start();
	}

	Button.OnClickListener btnlistener = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button btn = (Button) v;
			String btnText = (String) btn.getText();
			Log.v("btnText", btnText);
			if (v.getId() == R.id.button2) {
				Intent i = new Intent(IndexActivity.this, NewsActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			} else if (v.getId() == R.id.button3) {
				Intent i = new Intent(IndexActivity.this, ChatActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			} else if (v.getId() == R.id.button4) {
				Intent i = new Intent(IndexActivity.this, SetActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return false;
	}

	/**
	 * 保存数据
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		File f = new File(OverallData.WEIBOCACHEPATH);
		if (!f.exists()) {
			f.mkdir();
		}
		f = new File(OverallData.WEIBOCACHEFILEPATH);
		XMLSwitch.saveWeiboList(weibos, f);
	}

}
