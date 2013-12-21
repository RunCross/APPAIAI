package edu.ahjzu.app.notice.view.settingview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.weibo.oauthv2.OAuthV2;
import com.tencent.weibo.oauthv2.OAuthV2Client;
import com.tencent.weibo.webview.OAuthV2AuthorizeWebView;

/**
 * OAuth Version 2.a授权示例（使用WebView方式请求用户授权）
 */
public class OAuthV2ImplicitGrantActivity extends Activity {

	private static String TAG = "OAuthV2ImplicitGrantActivity.class";

	/*
	 * 申请APP KEY的具体介绍，可参见 http://wiki.open.t.qq.com/index.php/应用接入指引
	 * http://wiki.
	 * open.t.qq.com/index.php/腾讯微博移动应用接入规范#.E6.8E.A5.E5.85.A5.E6.B5.81.E7.A8.8B
	 */
	// !!!请根据您的实际情况修改!!! 认证成功后浏览器会被重定向到这个url中 必须与注册时填写的一致
	private String redirectUri = "http://dearfamilyyuan.blog.163.com/profile/?target=note&fromIndex#m=1";
	// !!!请根据您的实际情况修改!!! 换为您为自己的应用申请到的APP KEY
	private String clientId = "801361002";
	// !!!请根据您的实际情况修改!!! 换为您为自己的应用申请到的APP SECRET
	private String clientSecret = "bfc95084cf930c455163715fb83473bf";

	private OAuthV2 oAuth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		oAuth = new OAuthV2(redirectUri);
		oAuth.setClientId(clientId);
		oAuth.setClientSecret(clientSecret);

		// 关闭OAuthV2Client中的默认开启的QHttpClient。
		OAuthV2Client.getQHttpClient().shutdownConnection();

		Intent intent;
		intent = new Intent(OAuthV2ImplicitGrantActivity.this,
				OAuthV2AuthorizeWebView.class);// 鍒涘缓Intent锛屼娇鐢╓ebView璁╃敤鎴锋巿鏉�
		intent.putExtra("oauth", oAuth);
		startActivityForResult(intent, 2);

	};

	// Button btnImplicitGrant=(Button)findViewById(R.id.btnImplicitGrant);
	// Button btnAPItest3=(Button)findViewById(R.id.btnAPItest3);
	// btnImplicitGrant.setOnClickListener(listener);
	// btnAPItest3.setOnClickListener(listener);

	@Override
	public void onBackPressed() {
		finish();
	}

	/*
	 * 通过读取OAuthV2AuthorizeWebView返回的Intent，获取用户授权信息
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 2) {
			if (resultCode == OAuthV2AuthorizeWebView.RESULT_CODE) {
				oAuth = (OAuthV2) data.getExtras().getSerializable("oauth");
				if (oAuth.getStatus() == 0) {
					Toast.makeText(getApplicationContext(), "登陆成功",
							Toast.LENGTH_SHORT).show();
					Intent mIntent = new Intent();
					mIntent.putExtra("oauth", oAuth);
					mIntent.setClass(OAuthV2ImplicitGrantActivity.this,
							WeiBoAPIV2Activity.class);
					startActivity(mIntent);
					finish();
				}
			}
		}
	}
}
