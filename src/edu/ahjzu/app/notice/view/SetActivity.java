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
		findViewById(); // ����findViewById()����
	}

	private void findViewById() {
		Button indexbtn = (Button) findViewById(R.id.button1); // ʵ������ҳ��ť
		Button newsbtn = (Button) findViewById(R.id.button2); // ʵ�������Ű�ť
		Button charbtn = (Button) findViewById(R.id.button3); // ʵ�������찴ť
		Button setbtn = (Button) findViewById(R.id.button4); // ʵ�������ð�ť
		RelativeLayout accountRL = (RelativeLayout) findViewById(R.id.relativeLayout1); // ʵ�����ҵ��ʺŰ�ť
		// RelativeLayout registerRL = (RelativeLayout)
		// findViewById(R.id.relativeLayout2); //ʵ����ע�ᰴť
		RelativeLayout passwordRL = (RelativeLayout) findViewById(R.id.relativeLayout3); // ʵ�����޸����밴ť
		RelativeLayout clearAccountRL = (RelativeLayout) findViewById(R.id.relativeLayout4);// ʵ����ɾ���ʺŰ�ť
		RelativeLayout backgroundRL = (RelativeLayout) findViewById(R.id.relativeLayout5); // ʵ�����������ð�ť
		RelativeLayout normalSetting = (RelativeLayout) findViewById(R.id.relativeLayout6); // ʵ����ͨ�����ð�ť
		RelativeLayout clearHistory = (RelativeLayout) findViewById(R.id.relativeLayout7); // ʵ���������¼��ť
		RelativeLayout helpRL = (RelativeLayout) findViewById(R.id.relativeLayout8); // ʵ����������ť
		RelativeLayout updateRL = (RelativeLayout) findViewById(R.id.relativeLayout9); // ʵ�������°�ť
		RelativeLayout aboutRL = (RelativeLayout) findViewById(R.id.relativeLayout10); // ʵ�������ڰ�ť
		RelativeLayout sharedRL = (RelativeLayout) findViewById(R.id.relativeLayout11); // ʵ��������ť
		Button exitButton = (Button) findViewById(R.id.exitButton); // ʵ�����˳���ť

		indexbtn.setOnClickListener(this); // ����ҳ��ť������
		newsbtn.setOnClickListener(this); // �����Ű�ť������
		charbtn.setOnClickListener(this); // �����찴ť������
		setbtn.setOnClickListener(this); // �����ð�ť������
		exitButton.setOnClickListener(this); // ���˳���ť������
		accountRL.setOnClickListener(RLlistener); // ��ע�������
		// registerRL.setOnClickListener(RLlistener); //��ע�������
		passwordRL.setOnClickListener(RLlistener); // ���޸����������
		clearAccountRL.setOnClickListener(RLlistener); // ������ʺż�����
		backgroundRL.setOnClickListener(RLlistener); // �󶨱������ü�����
		normalSetting.setOnClickListener(RLlistener); // ��ͨ�ü�����
		clearHistory.setOnClickListener(RLlistener); // �������¼������
		helpRL.setOnClickListener(RLlistener); // �󶨰���������
		updateRL.setOnClickListener(RLlistener); // �󶨸��¼�����
		aboutRL.setOnClickListener(RLlistener); // �󶨹��ڼ�����
		sharedRL.setOnClickListener(RLlistener); // �󶨷��������
	}

	RelativeLayout.OnClickListener RLlistener = new RelativeLayout.OnClickListener() { // RelativeLayout��ÿ������ļ�����

		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			RelativeLayout rl = (RelativeLayout) v; // ��RelativeLayoutת��ΪView
			Intent mIntent = new Intent(); // ����һ����mIntent��ͼ����
			if (rl.getTag().equals("account")) { // �õ��ؼ��ı�ǩ
				Toast.makeText(SetActivity.this, "�ҵ��ʺ�", Toast.LENGTH_SHORT)
						.show();
			} else if (rl.getTag().equals("register")) { // //�õ��ؼ��ı�ǩ
				mIntent.setClass(SetActivity.this, RegisterActivity.class); // ת�Ƶ�registerActivity,��ʾע���ҳ��
				startActivity(mIntent);
			} else if (rl.getTag().equals("password")) { // �õ��ؼ��ı�ǩ
				RelativeLayout passwordRL = (RelativeLayout) findViewById(R.id.relativeLayout3); // ʵ����ralativeLayout3��ť
				LayoutInflater inflater = LayoutInflater.from(SetActivity.this); // ��setActivity��������LayoutInflater
				View view = inflater.inflate(R.layout.popupwindow, null); // ͨ��inflater�õ�һ�������ļ�,�õ��Ĳ����ļ�Ϊһ��View����
				View surebtn = (View) view
						.findViewById(R.id.popupWindowButton1); // ʵ����popupWindow�е�������ť
				View canclebtn = (View) view
						.findViewById(R.id.popupWindowButton2); // ȷ���ͷ��ذ�ť
				final EditText editText1 = (EditText) view
						.findViewById(R.id.popupWindowEditText1); // ʵ����popupWindow�е�����EditText�������������
				final EditText editText2 = (EditText) view
						.findViewById(R.id.popupWindowEditText2); // �����������
				final EditText editText3 = (EditText) view
						.findViewById(R.id.popupWindowEditText3); // ������ȷ�������
				final PopupWindow popupWindow = new PopupWindow(view,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
						true); // ����һ��PopupWindow�ĵ���,��һ��������View���ͣ��м������ǵ����Ŀ�Ⱥ͸߶ȣ����һ��������focutouch(ֵΪtrue��false)
				popupWindow.setOutsideTouchable(true); // ���õ����������رյ���(������Ч)
				popupWindow.showAsDropDown(passwordRL); // ���õ����ĵ���λ��Ϊ���水ť
				// ����һ��Button�ļ�����
				Button.OnClickListener btnListener = new Button.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (v.getId() == R.id.popupWindowButton2) { // ȡ�ò��ֿؼ���id
							popupWindow.dismiss(); // ���ȡ����ť��doSomeThing���ر�popupWindow
						} else if (v.getId() == R.id.popupWindowButton1) { // ȡ�ò��ֿؼ���id
							if ((editText2.getText().toString()
									.equals(editText3.getText().toString()))) { // У���û�����������������Ƿ���ͬ
								Toast.makeText(
										SetActivity.this,
										"��:" + editText1.getText() + "\n"
												+ "��:" + editText2.getText(),
										Toast.LENGTH_LONG).show();
								popupWindow.dismiss(); // �ر�popupWindow����
							} else {
								SetAddition.alertDialog(SetActivity.this,
										"errorPassword"); // ���û���������������벻��ȷʱ������alertDialog����������������������ʾ�û���������벻ƥ��
							}
						}
					}
				};
				canclebtn.setOnClickListener(btnListener); // ��popupWindow�е�ȷ����ť�ļ�����
				surebtn.setOnClickListener(btnListener); // ��popupWindow�е�ȡ����ť�ļ�����
			} else if (rl.getTag().equals("clearAccount")) {
				SetAddition.alertDialog(SetActivity.this, "clearAccount"); // ����alertDialog������ת��Activity��String(clearAccount),����Dialog���û�ȷ��ɾ��
			} else if (rl.getTag().equals("background")) {
				mIntent.setClass(SetActivity.this, backgroundActivity.class); // ת�Ƶ�backGroundActivity
				startActivity(mIntent);
			} else if (rl.getTag().equals("normalSetting")) {
				mIntent.setClass(SetActivity.this, setSettingActivity.class); // ת�Ƶ�setSettingActivity
				startActivity(mIntent);
			} else if (rl.getTag().equals("clearHistory")) {
				File sd = Environment.getExternalStorageDirectory(); // ȡ��SD��·��
				String path = sd.getPath() + "/AIAINotice/history"; // ȡ���ļ�·��
				File file = new File(path); // �����ļ��Ķ����ļ���ʷΪ·��Ϊ����
				final SetAddition setAddition = new SetAddition(); // ����SetAddition��Ķ���setAddition
				setAddition.deleteHistory(file); // ����deleteFile����ɾ����¼,���ݲ�����file
				SetAddition.updateAndHistoryProgressDialog(SetActivity.this,
						"clearHistory");// ����updateAndHistoryProgressDialog����,������������
			} else if (rl.getTag().equals("help")) {
				mIntent.setAction(Intent.ACTION_VIEW); // �����û������Ѿ���װ�������(ϵͳĬ�������)���з����趨�İ�����ַ
				mIntent.setData(Uri.parse("http://m.baidu.com")); // ͨ��Uri.parseȡ�õ�ַ,ͨ����ͼ��setData����ע���ַ��mIntent
				startActivity(mIntent);
			} else if (rl.getTag().equals("update")) {
				ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);// ��ȡ����ͨѶ���ʵ��
				NetworkInfo netWorkInfo = connectivityManager
						.getActiveNetworkInfo(); // �õ��ֻ���ǰ�õ���������
				if (netWorkInfo != null && netWorkInfo.isAvailable()) { // �ж������Ƿ����ӻ�isAvailable�Ƿ�Ϊ��
					SetAddition.updateAndHistoryProgressDialog(
							SetActivity.this, "update"); // ����updateAndHistoryProgressDialog����,������������
					final Handler handler = new Handler(); // ����Handler����handler
					final Runnable cb = new Runnable() { // ����Runnable����cb
						@Override
						public void run() {
							SetAddition.alertDialog(SetActivity.this, "update"); // ����alertDialog����,������������
						}
					};
					Thread thread = new Thread() { // ����Thread�̶߳���thread
						@Override
						public void run() {
							try {
								Thread.sleep(4000); // �ȴ�4�����Runnable��Run����
							} catch (Exception e) {
								e.printStackTrace();
							} // try{}catch{}�쳣�׳�
							handler.post(cb);
						}
					}; // ��Handler��handler.post��������UI
					thread.start(); // Thread�߳�����
				} else {
					SetAddition.alertDialog(SetActivity.this, "network"); // ����alertDialog����,������������
				}
			} else if (rl.getTag().equals("about")) {
				SetAddition.aboutDialog(SetActivity.this); // ����aboutDialog��������Dialog����ʽ��ʾ������Ϣ
			} else if (rl.getTag().equals("shared")) {
				mIntent.setClass(SetActivity.this, sharedActivity.class); // ͨ��Intent��setclass����ת��Activity��sharedActivity
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
			Intent intent = new Intent(Intent.ACTION_MAIN); // �˳�����Ӧ�ã���ת��������
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // ������������Ϊһ���µĿ�ʼ
			intent.addCategory("android.intent.category.HOME");
			startActivity(intent);
			System.exit(0); // �˳�����
			break;
		}
	}

}
