package edu.ahjzu.app.notice.server;

import edu.ahjzu.app.notice.init.OverallData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

public class initAll {// extends Activity{
	// ����Ƿ�����
	public boolean isWeb(Context c) {

		ConnectivityManager cwjManager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			// ������
			OverallData.isWEB = true;
			return true;
		} else {
			// δ����
			OverallData.isWEB = false;
			return false;
		}
	}

	public boolean isSDCard() {
		String sdStatus = Environment.getExternalStorageState();
		// ���sd�Ƿ����
		if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
			Log.v("TestFile", "SD card is not avaiable/writeable right now.");
			OverallData.isSD = false;
			return false;
		} else {
			OverallData.isSD = true;
			return true;
		}
	}
}
