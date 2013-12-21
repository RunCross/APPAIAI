package edu.ahjzu.app.notice.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.News;
import edu.ahjzu.app.notice.server.NewsGet;
import edu.ahjzu.app.notice.view.adapter.NewsActivityAdapter;

public class NewsActivity extends Activity {

	private ImageButton newsrefreshbtn;

	private ListView listView;

	// private ImageView imageView;
	File imagecache;
	List<News> newses = new ArrayList<News>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);
		
		listView = (ListView) findViewById(R.id.newslist);
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("ÐÂÎÅ");
		newsrefreshbtn = (ImageButton) findViewById(R.id.refeshbtn);
		newsrefreshbtn.setOnClickListener(refreshbtnlister);
		// imageView = (ImageView) findViewById(R.id.ItemImage);

		imagecache = new File(Environment.getExternalStorageDirectory(),
				OverallData.newsImagePath);
		init();
		refreshData();

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			newses = (List<News>) msg.obj;
			NewsActivityAdapter newsadapter = new NewsActivityAdapter(
					NewsActivity.this, newses, R.layout.newslistview_item,
					imagecache);
			listView.setAdapter(newsadapter);
		}
	};

	private void refreshData() {
		if (!imagecache.exists()) {
			imagecache.mkdirs();
		}
		new Thread(new Runnable() {
			@Override
			public void run() {

				handler.sendMessage(handler.obtainMessage(21,
						NewsGet.getnewsData()));

			}
		}).start();

	}

	private void init() {
		Button indexbtn = (Button) findViewById(R.id.button1);
		Button newsbtn = (Button) findViewById(R.id.button2);
		Button charbtn = (Button) findViewById(R.id.button3);
		Button setbtn = (Button) findViewById(R.id.button4);
		indexbtn.setOnClickListener(btnlistener);
		newsbtn.setOnClickListener(btnlistener);
		charbtn.setOnClickListener(btnlistener);
		setbtn.setOnClickListener(btnlistener);
	}

	Button.OnClickListener refreshbtnlister = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			refreshData();
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	Button.OnClickListener btnlistener = new Button.OnClickListener() {
		@SuppressLint("InlinedApi")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Button btn = (Button) v;
			String btnText = (String) btn.getText();
			Log.v("btnText", btnText);
			Intent i = null;
			switch (v.getId()) {
			case R.id.button1:
				i = new Intent(NewsActivity.this, IndexActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
				break;
			case R.id.button2:
				refreshData();
				break;
			case R.id.button3:
				i = new Intent(NewsActivity.this, ChatActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
				break;
			case R.id.button4:
				i = new Intent(NewsActivity.this, SetActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
				break;
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

}
