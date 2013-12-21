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

	// 设置相关参数
	public void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(false); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setPoiExtraInfo(true);
		option.setAddrType("all");
		option.setScanSpan(3000); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
		option.setPriority(LocationClientOption.NetWorkFirst); // 设置网络优先
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocClient.setLocOption(option);
	}
}
