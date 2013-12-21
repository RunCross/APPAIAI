package edu.ahjzu.app.notice.util;

import com.APP.aiainotice.R;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;

public class myUIWarn {
	public static void show(Context context, String msg) {
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.icon, msg,
				System.currentTimeMillis());
		notification.setLatestEventInfo(context.getApplicationContext(),
				"提示信息", msg, null);
		notification.flags |= Notification.FLAG_AUTO_CANCEL; // 自动终止
		notification.defaults |= Notification.DEFAULT_SOUND; // 默认声音
		manager.notify(0, notification);// 发起通知
	}
	public static ProgressDialog wait(Context context, String msg) {
		ProgressDialog dialog = new ProgressDialog(context);
		// 设置进度条风格，风格为圆形，旋转�?
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置ProgressDialog 标题
		dialog.setTitle(msg);
		// 设置ProgressDialog 提示信息
		// dialog.setMessage("圆形进度�?);
		// 设置ProgressDialog 标题图标
		dialog.setIcon(android.R.drawable.ic_dialog_map);
		// 设置ProgressDialog 的一个Button
		/*
		 * dialog.setButton("确定", new ProgressDialog.OnClickListener(){
		 * 
		 * @Override public void onClick(DialogInterface dialog, int which) {
		 * 
		 * } });
		 */
		// 设置ProgressDialog 的进度条是否不明�?
		dialog.setIndeterminate(false);
		// 设置ProgressDialog 是否可以按�?回按键取�?
		dialog.setCancelable(true);
		// 显示
		dialog.show();
		return dialog;
	}
	public  static void myToast(Context context,String msg) {
		/*
		Toast toast = Toast.makeText(getApplicationContext(), msg,
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();*/
		new AlertDialog.Builder(context).setMessage(msg)  
		
		                .setPositiveButton("确定", null)  
		
		                .show();  
	}

}
