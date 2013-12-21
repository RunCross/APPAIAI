package edu.ahjzu.app.notice.view;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.view.settingview.SetAddition;
import edu.ahjzu.app.notice.view.settingview.backgroundActivity;
import edu.ahjzu.app.notice.view.settingview.setSettingActivity;
import edu.ahjzu.app.notice.view.settingview.sharedActivity;

public class SetActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		findViewById(); // 调用findViewById()方法
	}

	private void findViewById() {
		Button indexbtn = (Button) findViewById(R.id.button1); // 实例化主页按钮
		Button newsbtn = (Button) findViewById(R.id.button2); // 实例化新闻按钮
		Button charbtn = (Button) findViewById(R.id.button3); // 实例化聊天按钮
		Button setbtn = (Button) findViewById(R.id.button4); // 实例化设置按钮
		RelativeLayout accountRL = (RelativeLayout) findViewById(R.id.relativeLayout1); // 实例化我的帐号按钮
		// RelativeLayout registerRL = (RelativeLayout)
		// findViewById(R.id.relativeLayout2); //实例化注册按钮
		RelativeLayout passwordRL = (RelativeLayout) findViewById(R.id.relativeLayout3); // 实例化修改密码按钮
		RelativeLayout clearAccountRL = (RelativeLayout) findViewById(R.id.relativeLayout4);// 实例化删除帐号按钮
		RelativeLayout backgroundRL = (RelativeLayout) findViewById(R.id.relativeLayout5); // 实例化背景设置按钮
		RelativeLayout normalSetting = (RelativeLayout) findViewById(R.id.relativeLayout6); // 实例化通用设置按钮
		RelativeLayout clearHistory = (RelativeLayout) findViewById(R.id.relativeLayout7); // 实例化清除记录按钮
		RelativeLayout helpRL = (RelativeLayout) findViewById(R.id.relativeLayout8); // 实例化帮助按钮
		RelativeLayout updateRL = (RelativeLayout) findViewById(R.id.relativeLayout9); // 实例化更新按钮
		RelativeLayout aboutRL = (RelativeLayout) findViewById(R.id.relativeLayout10); // 实例化关于按钮
		RelativeLayout sharedRL = (RelativeLayout) findViewById(R.id.relativeLayout11); // 实例化分享按钮
		Button exitButton = (Button) findViewById(R.id.exitButton); // 实例化退出按钮

		indexbtn.setOnClickListener(this); // 绑定主页按钮监听器
		newsbtn.setOnClickListener(this); // 绑定新闻按钮监听器
		charbtn.setOnClickListener(this); // 绑定聊天按钮监听器
		setbtn.setOnClickListener(this); // 绑定设置按钮监听器
		exitButton.setOnClickListener(this); // 绑定退出按钮监听器
		accountRL.setOnClickListener(RLlistener); // 绑定注册监听器
		// registerRL.setOnClickListener(RLlistener); //绑定注册监听器
		passwordRL.setOnClickListener(RLlistener); // 绑定修改密码监听器
		clearAccountRL.setOnClickListener(RLlistener); // 绑定清除帐号监听器
		backgroundRL.setOnClickListener(RLlistener); // 绑定背景设置监听器
		normalSetting.setOnClickListener(RLlistener); // 绑定通用监听器
		clearHistory.setOnClickListener(RLlistener); // 绑定清除记录监听器
		helpRL.setOnClickListener(RLlistener); // 绑定帮助监听器
		updateRL.setOnClickListener(RLlistener); // 绑定更新监听器
		aboutRL.setOnClickListener(RLlistener); // 绑定关于监听器
		sharedRL.setOnClickListener(RLlistener); // 绑定分享监听器
	}

	RelativeLayout.OnClickListener RLlistener = new RelativeLayout.OnClickListener() { // RelativeLayout中每个单项的监听器

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			RelativeLayout rl = (RelativeLayout) v; // 把RelativeLayout转换为View
			Intent mIntent = new Intent(); // 生成一个新mIntent意图对象
			if (rl.getTag().equals("account")) { // 得到控件的标签
				Toast.makeText(SetActivity.this, "我的帐号", Toast.LENGTH_SHORT)
						.show();
			} else if (rl.getTag().equals("register")) { // //得到控件的标签
				mIntent.setClass(SetActivity.this, RegisterActivity.class); // 转移到registerActivity,显示注册的页面
				startActivity(mIntent);
			} else if (rl.getTag().equals("password")) { // 得到控件的标签
				RelativeLayout passwordRL = (RelativeLayout) findViewById(R.id.relativeLayout3); // 实例化ralativeLayout3按钮
				LayoutInflater inflater = LayoutInflater.from(SetActivity.this); // 在setActivity当中引入LayoutInflater
				View view = inflater.inflate(R.layout.popupwindow, null); // 通过inflater得到一个布局文件,得到的布局文件为一个View类型
				View surebtn = (View) view
						.findViewById(R.id.popupWindowButton1); // 实例化popupWindow中的两个按钮
				View canclebtn = (View) view
						.findViewById(R.id.popupWindowButton2); // 确定和返回按钮
				final EditText editText1 = (EditText) view
						.findViewById(R.id.popupWindowEditText1); // 实例化popupWindow中的三个EditText，旧密码输入框
				final EditText editText2 = (EditText) view
						.findViewById(R.id.popupWindowEditText2); // 新密码输入框
				final EditText editText3 = (EditText) view
						.findViewById(R.id.popupWindowEditText3); // 新密码确认输入框
				final PopupWindow popupWindow = new PopupWindow(view,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
						true); // 生成一个PopupWindow的弹窗,第一个参数是View类型，中间两个是弹窗的宽度和高度，最后一个参数是focutouch(值为true或false)
				popupWindow.setOutsideTouchable(true); // 设置点击弹窗外面关闭弹窗(这里无效)
				popupWindow.showAsDropDown(passwordRL); // 设置弹窗的弹出位置为跟随按钮
				// 生成一个Button的监听器
				Button.OnClickListener btnListener = new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (v.getId() == R.id.popupWindowButton2) { // 取得布局控件的id
							popupWindow.dismiss(); // 点击取消按钮的doSomeThing，关闭popupWindow
						} else if (v.getId() == R.id.popupWindowButton1) { // 取得布局控件的id
							if ((editText2.getText().toString()
									.equals(editText3.getText().toString()))) { // 校验用户两次输入的新密码是否相同
								Toast.makeText(
										SetActivity.this,
										"旧:" + editText1.getText() + "\n"
												+ "新:" + editText2.getText(),
										Toast.LENGTH_LONG).show();
								popupWindow.dismiss(); // 关闭popupWindow弹窗
							} else {
								SetAddition.alertDialog(SetActivity.this,
										"errorPassword"); // 当用户输入的两次新密码不正确时，调用alertDialog函数，传递两个参数，提示用户输入的密码不匹配
							}
						}
					}
				};
				canclebtn.setOnClickListener(btnListener); // 绑定popupWindow中的确定按钮的监听器
				surebtn.setOnClickListener(btnListener); // 绑定popupWindow中的取消按钮的监听器
			} else if (rl.getTag().equals("clearAccount")) {
				SetAddition.alertDialog(SetActivity.this, "clearAccount"); // 调用alertDialog函数，转递Activity和String(clearAccount),调出Dialog让用户确认删除
			} else if (rl.getTag().equals("background")) {
				mIntent.setClass(SetActivity.this, backgroundActivity.class); // 转移到backGroundActivity
				startActivity(mIntent);
			} else if (rl.getTag().equals("normalSetting")) {
				mIntent.setClass(SetActivity.this, setSettingActivity.class); // 转移到setSettingActivity
				startActivity(mIntent);
			} else if (rl.getTag().equals("clearHistory")) {
				File sd = Environment.getExternalStorageDirectory(); // 取得SD卡路径
				String path = sd.getPath() + "/AIAINotice/history"; // 取得文件路径
				File file = new File(path); // 生成文件的对象，文件历史为路径为参数
				final SetAddition setAddition = new SetAddition(); // 生成SetAddition类的对象setAddition
				setAddition.deleteHistory(file); // 调用deleteFile函数删除记录,传递参数是file
				SetAddition.updateAndHistoryProgressDialog(SetActivity.this,
						"clearHistory");// 调用updateAndHistoryProgressDialog函数,传递两个参数
			} else if (rl.getTag().equals("help")) {
				mIntent.setAction(Intent.ACTION_VIEW); // 设置用机器上已经安装的浏览器(系统默认浏览器)进行访问设定的帮助网址
				mIntent.setData(Uri.parse("http://m.baidu.com")); // 通过Uri.parse取得地址,通过意图的setData方法注册地址到mIntent
				startActivity(mIntent);
			} else if (rl.getTag().equals("update")) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);// 获取网络通讯类的实例
				NetworkInfo netWorkInfo = connectivityManager
						.getActiveNetworkInfo(); // 得到手机当前用的网络类型
				if (netWorkInfo != null && netWorkInfo.isAvailable()) { // 判断网络是否连接或isAvailable是否为真
					SetAddition.updateAndHistoryProgressDialog(
							SetActivity.this, "update"); // 调用updateAndHistoryProgressDialog函数,传递两个参数
					final Handler handler = new Handler(); // 生成Handler对象handler
					final Runnable cb = new Runnable() { // 生成Runnable对象cb
						@Override
						public void run() {
							SetAddition.alertDialog(SetActivity.this, "update"); // 调用alertDialog对象,传递两个参数
						}
					};
					Thread thread = new Thread() { // 生成Thread线程对象thread
						@Override
						public void run() {
							try {
								Thread.sleep(4000); // 等待4秒调用Runnable的Run方法
							} catch (Exception e) {
								e.printStackTrace();
							} // try{}catch{}异常抛出
							handler.post(cb);
						}
					}; // 用Handler的handler.post方法更新UI
					thread.start(); // Thread线程启动
				} else {
					SetAddition.alertDialog(SetActivity.this, "network"); // 调用alertDialog对象,传递两个参数
				}
			} else if (rl.getTag().equals("about")) {
				SetAddition.aboutDialog(SetActivity.this); // 调用aboutDialog函数，以Dialog的形式显示关于信息
			} else if (rl.getTag().equals("shared")) {
				mIntent.setClass(SetActivity.this, sharedActivity.class); // 通过Intent的setclass方法转移Activity到sharedActivity
				startActivity(mIntent);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			i = new Intent(SetActivity.this, IndexActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
			break;
		case R.id.button2:
			i = new Intent(SetActivity.this, NewsActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
			break;
		case R.id.button3:
			i = new Intent(SetActivity.this, ChatActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(i);
			break;
		case R.id.button4:
			break;
		case R.id.exitButton:
			Intent intent = new Intent(Intent.ACTION_MAIN); // 退出整个应用，跳转到主界面
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // 设置这项活动将成为一个新的开始
			intent.addCategory("android.intent.category.HOME");
			startActivity(intent);
			System.exit(0); // 退出程序
			break;
		}
	}

}
