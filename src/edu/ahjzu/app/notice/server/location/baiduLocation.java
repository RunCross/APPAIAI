package edu.ahjzu.app.notice.server.location;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;

import edu.ahjzu.app.notice.pojo.Chat;

import android.app.Application;
import android.util.Log;
import android.widget.TextView;
import android.os.Process;
import android.os.Vibrator;

public class baiduLocation extends Application {

	public LocationClient mLocationClient = null;
	private String mData;

	public TextView mTv;
	public NotifyLister mNotifyer = null;
	public Vibrator mVibrator01;
	public static String TAG = "myLocation";
	public Chat chartdata = null;

	@Override
	public void onCreate() {
		mLocationClient = new LocationClient(this);
		MyLocationListenner myListener = new MyLocationListenner();
		mLocationClient.registerLocationListener(myListener);
		super.onCreate();
		Log.d(TAG, "... Application onCreate... pid=" + Process.myPid());
	}

	/**
	 * 显示字符串
	 * 
	 * @param str
	 */
	public void logMsg(String str) {
		try {
			mData = str;
			if (mTv != null)
				mTv.setText(mData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			/*
			 * sb.append("\nerror code : "); sb.append(location.getLocType());
			 * sb.append("\nlatitude : "); sb.append(location.getLatitude());
			 * chartdata.setaddr_Latitude(location.getLatitude());
			 * sb.append("\nlontitude : "); sb.append(location.getLongitude());
			 * chartdata.setaddr_Longitude(location.getLongitude());
			 * sb.append("\nradius : "); sb.append(location.getRadius()); if
			 * (location.getLocType() == BDLocation.TypeGpsLocation){
			 * sb.append("\nspeed : "); sb.append(location.getSpeed());
			 * sb.append("\nsatellite : ");
			 * sb.append(location.getSatelliteNumber()); } else
			 */
			if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				// sb.append("\n省：");
				// sb.append(location.getProvince());
				// sb.append("\n市：");
				// sb.append(location.getCity());
				// sb.append("\n区/县：");
				// sb.append(location.getDistrict());
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				chartdata.setaddr(location.getAddrStr());
			}
			/*
			 * sb.append("\nsdk version : ");
			 * sb.append(mLocationClient.getVersion());
			 * sb.append("\nisCellChangeFlag : ");
			 * sb.append(location.isCellChangeFlag());
			 */
			logMsg(sb.toString());
			Log.i(TAG, sb.toString());

		}

		@Override
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
			StringBuffer sb = new StringBuffer(256);
			sb.append("Poi time : ");
			sb.append(poiLocation.getTime());
			sb.append("\nerror code : ");
			sb.append(poiLocation.getLocType());
			sb.append("\nlatitude : ");
			sb.append(poiLocation.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(poiLocation.getLongitude());
			sb.append("\nradius : ");
			sb.append(poiLocation.getRadius());
			if (poiLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(poiLocation.getAddrStr());
			}
			if (poiLocation.hasPoi()) {
				sb.append("\nPoi:");
				sb.append(poiLocation.getPoi());
			} else {
				sb.append("noPoi information");
			}
			logMsg(sb.toString());
		}
	}

	public class NotifyLister extends BDNotifyListener {
		@Override
		public void onNotify(BDLocation mlocation, float distance) {
			mVibrator01.vibrate(1000);
		}
	}
}