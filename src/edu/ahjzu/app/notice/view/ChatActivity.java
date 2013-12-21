package edu.ahjzu.app.notice.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.APP.aiainotice.R;

public class ChatActivity extends FragmentActivity implements OnClickListener {
	// 百度key:965009CFA632A5654CDC73CEF958DB2CE103592C
	private final String LOGTAG = "ChatActivity";
	private ViewPager mViewPager;
	private LayoutInflater inflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		initView();

	}

	/**
	 * 初始化界面
	 */
	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mViewPager.setAdapter(new SectionsPagerAdapter(
				getSupportFragmentManager()));
		// 底部栏设置
		Button indexbtn = (Button) findViewById(R.id.button1);
		Button newsbtn = (Button) findViewById(R.id.button2);
		Button charbtn = (Button) findViewById(R.id.button3);
		Button setbtn = (Button) findViewById(R.id.button4);
		indexbtn.setOnClickListener(this);
		newsbtn.setOnClickListener(this);
		charbtn.setOnClickListener(this);
		setbtn.setOnClickListener(this);
		// 标题栏设置
		TextView title = (TextView) findViewById(R.id.title);
		title.setText("聊天");
		View editbtn = findViewById(R.id.editbtn);

		editbtn.setOnClickListener(this);
		// 刷新按钮
		View refreshbtn = findViewById(R.id.refreshbtn);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			return false;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.button1:
			i = new Intent(ChatActivity.this, IndexActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
			break;
		case R.id.button2:
			i = new Intent(ChatActivity.this, NewsActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
			break;
		case R.id.button3:

			break;
		case R.id.button4:
			i = new Intent(ChatActivity.this, SetActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
			break;
		case R.id.refreshbtn:
			break;
		case R.id.editbtn:
			i = new Intent(ChatActivity.this, EditChatActivity.class);
			// i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
			break;
		}
	}

	private class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0) {
				return "在线同学";
			} else {
				return "最近联系人";
			}
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = null;
			if (arg0 == 0) {
				fragment = new OnlineUsrFragment();
			} else {
				fragment = new ChatingUsrFragment();
			}
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}
}
