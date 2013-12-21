package edu.ahjzu.app.notice.view.settingview;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.APP.aiainotice.R;
import com.tencent.weibo.api.TAPI;
import com.tencent.weibo.api.UserAPI;
import com.tencent.weibo.constants.OAuthConstants;
import com.tencent.weibo.oauthv2.OAuthV2;

/**
 * 娴嬭瘯OAuth Version
 * 2.a鐨凙PI灏佽鎺ュ彛锛屾湰绫讳腑鍙紨绀轰笁涓唬琛ㄦ�鎺ュ彛璋冪敤锛堝垎鍒噰鐢℅et鏂规硶鍙戦�娑堟伅锛宲ost鏂规硶鍙戦
 * �娑堟伅鍜宲ost鏂规硶鍙戦�鏂囦欢锛�?
 */
public class WeiBoAPIV2Activity extends Activity {

	private OAuthV2 oAuthV2;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		/*
		 * 璁剧疆鐣岄潰鍏冪礌锛屽苟娣诲姞�?瑰悇鎸夐挳鐨勭洃鍚�
		 */
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.weibo);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebasis);
		Intent intent = this.getIntent();

		Button backbtn = (Button) findViewById(R.id.backbtn);
		View refeshbtn = (ImageButton) findViewById(R.id.refeshbtn);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		TextView title = (TextView) findViewById(R.id.title);

		// refeshbtn.setText("");
		textView1
				.setText("鎴戞鍦ㄤ娇鐢ˋIAINotice,闈炲父濂界敤!!!瀹夊缓澶х殑鍚屽浠�浣犱滑涔熻刀绱т笅杞戒娇鐢ㄥ惂!涓嬭浇鍦板潃:http://dearfamilyyuan.blog.163.com/profile/?target=note&fromIndex#m=1");
		title.setText("");

		final AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				String response;
				UserAPI userAPI;
				TAPI tAPI;

				tAPI = new TAPI(OAuthConstants.OAUTH_VERSION_2_A);
				try {
					File fileDir = new File("/sdcard/AIAI/qqweibo");
					if (!fileDir.exists())
						fileDir.mkdirs();
					File file = new File("/sdcard/AIAI/qqweibo/ic_launcher.png");
					if (!file.exists()) {
						file.createNewFile();
						InputStream inputStream = WeiBoAPIV2Activity.class
								.getResourceAsStream("/res/drawable-hdpi/ic_launcher.png");
						FileOutputStream fileOutputStream = new FileOutputStream(
								file);
						byte[] buf = new byte[1024];
						int ins;
						while ((ins = inputStream.read(buf)) != -1) {
							fileOutputStream.write(buf, 0, ins);
						}
						inputStream.close();
						fileOutputStream.close();
					}
					String picPath = "/sdcard/qweibosdk2/ic_launcher.png";
					response = tAPI
							.addPic(oAuthV2,
									"json",
									"鎴戞鍦ㄤ娇鐢ˋIAINotice,闈炲父濂界敤!!!瀹夊缓澶х殑鍚屽浠�浣犱滑涔熻刀绱т笅杞戒娇鐢ㄥ惂!涓嬭浇鍦板潃:http://dearfamilyyuan.blog.163.com/profile/?target=note&fromIndex#m=1",
									"127.0.0.1", picPath);
					// textResponse.append(response+"\n");
				} catch (Exception e) {
					e.printStackTrace();
				}
				tAPI.shutdownConnection();
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				try {
					Thread.sleep(2 * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				SetAddition.alertDialog(WeiBoAPIV2Activity.this, "sendSuccess");
				super.onPostExecute(result);
			}

			@Override
			protected void onPreExecute() {
				SetAddition.updateAndHistoryProgressDialog(
						WeiBoAPIV2Activity.this, "sendweibo");
				super.onPreExecute();
			}
		};

		backbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		refeshbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				asyncTask.execute();
			}
		});
		// 鎺ユ敹鐢↖ntent浼犳潵鐨凙pp淇℃伅鍙婁箣鍓嶉�杩囦簡Oauth閴存潈鐨勪俊鎭�
		oAuthV2 = (OAuthV2) intent.getExtras().getSerializable("oauth");
	}

	@Override
	public void onBackPressed() {
		finish();
	}

}
