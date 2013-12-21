package edu.ahjzu.app.notice.server;

import edu.ahjzu.app.notice.init.OverallData;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

public class initAll {// extends Activity{
	// 检查是否联网
	public boolean isWeb(Context c) {

		ConnectivityManager cwjManager = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cwjManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			// 已联网
			OverallData.isWEB = true;
			return true;
		} else {
			// 未联网
			OverallData.isWEB = false;
			return false;
		}
	}

	public boolean isSDCard() {
		String sdStatus = Environment.getExternalStorageState();
		// 检测sd是否可用
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
