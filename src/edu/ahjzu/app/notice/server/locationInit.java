package edu.ahjzu.app.notice.server;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

import edu.ahjzu.app.notice.pojo.Chat;
import edu.ahjzu.app.notice.server.location.baiduLocation;

public class locationInit {

	public TextView locationdata = null;
	public LocationClient mLocClient = null;
	public Vibrator mVibrator01 = null;
	public Chat chartdata = null;
	Activity activity = null;

	public locationInit(Activity activity) {
		this.activity = activity;
	}

	public void init() {
		mLocClient = ((baiduLocation) activity.getApplication()).mLocationClient;
		((baiduLocation) activity.getApplication()).chartdata = chartdata;
		((baiduLocation) activity.getApplication()).mTv = locationdata;
		mVibrator01 = (Vibrator) activity.getApplication().getSystemService(
				Context.VIBRATOR_SERVICE);
		((baiduLocation) activity.getApplication()).mVibrator01 = mVibrator01;
	}

	// ������ز���
	public void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false); // ��gps
		option.setCoorType("bd09ll"); // ������������
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(3000); // ���ö�λģʽ��С��1����һ�ζ�λ;���ڵ���1����ʱ��λ
		option.setPriority(LocationClientOption.NetWorkFirst); // ������������
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocClient.setLocOption(option);
	}
}
