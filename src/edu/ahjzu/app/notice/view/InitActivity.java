package edu.ahjzu.app.notice.view;

import java.io.File;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.server.initAll;

public class InitActivity extends Activity {
	private File appDir = Environment.getExternalStorageDirectory();
	private final String LOGTAG = "initActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initactivity);
		// initUsr();
		// ����Ƿ�����
		// �����˻��ͳ�ʼ��mainActivity

		initAll init = new initAll();
		init.isSDCard();
		if (!init.isWeb(this)) {
			new AlertDialog.Builder(InitActivity.this).setMessage("����ֻ�δ����")
					.show();// ���Ի�����ʾ��������û��b1�����Ч�������Ե�ʱ�������д��

		}

		/*
		 * File usrdatafile = new
		 * File(Environment.getExternalStorageDirectory(), OverallData.ownData);
		 * if (!usrdatafile.exists()) { // ��ת��ע����� Intent i = new
		 * Intent(initActivity.this, regeisternameActivity.class);
		 * startActivity(i); finish(); } else { Intent i = new
		 * Intent(initActivity.this, indexActivity.class); startActivity(i);
		 * finish(); }
		 */

	}

	// Handler handler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// String strMsg = msg.obj.toString();
	// System.out.println(LOGTAG + ":" + strMsg);
	// if (strMsg.equals("have")) {
	// Intent i = new Intent(InitActivity.this, IndexActivity.class);
	// startActivity(i);
	// finish();
	// } else if (strMsg.equals("nothave")) {
	// // ��ת��ע�����
	// Intent i = new Intent(InitActivity.this,
	// RegeisterNameActivity.class);
	// startActivity(i);
	// finish();
	// }
	// }
	// };
	//
	// private void initUsr() {
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	//
	// if (OverallData.isSD) {
	// File file = new File(OverallData.APPFilepath);
	// file.mkdirs();
	// }
	// File weibopicdir = new File(appDir, OverallData.WEIBOIMAGEPATH);
	// if (!weibopicdir.exists()) {
	// weibopicdir.mkdirs();// �����ļ���
	// }
	//
	// File usrdatafile = new File(appDir, OverallData.ownData);
	// if (!usrdatafile.exists()) {
	// handler.sendMessage(handler.obtainMessage(0, "nothave"));
	// } else {
	// // �����û���������
	// Globalvariables.getUsr();
	// handler.sendMessage(handler.obtainMessage(0, "have"));
	// }
	// }
	// }).start();
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
