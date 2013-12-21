package edu.ahjzu.app.notice.view.settingview;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import com.APP.aiainotice.R;
public class SetAddition {
	public static  ProgressDialog updateAndHistoryProgressDialog(final Activity activity,String string){//设置函数,接受两个参数
    	final ProgressDialog dialog = new ProgressDialog(activity);								//生成一个ProgressDialog的对象
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);								//设置ProgressDialog的显示类型为圆形
			if(string.equals("clearHistory")){													//判断参数值
			dialog.setMessage("正在清除记录...");													//设置清除历史记录dialog的内容
			}else if(string.equals("weibo")){
				dialog.setMessage("正在发送...");													//设置发送微博的dialog的内容
			}else if(string.equals("clearAccount")){
				dialog.setMessage("正在删除帐号...");												//设置正在删除帐号的dialog的内容
			}
			else if(string.equals("sendweibo")){
				dialog.setMessage("请在同步到微博...");
			}else{
				dialog.setMessage("正在检测更新...");												//设置检测更新的dialog的内容
			}
			dialog.show();																		//显示dialog
				Timer timer = new Timer();														//生成一个新TimerTask对象
				timer.schedule(new TimerTask() {												//用timer.schedule方法设置新任务
					@Override
					public void run() {
						Looper.prepare();														//调用looper。pripare()方法同步UI线程
						dialog.dismiss();														//4000毫秒关闭dialog
					}
				}, 4000);																		//设置延迟时间为4000毫秒
			return dialog;																		//返回dialog到UI
		}
    public static  Dialog aboutDialog(final Activity activity){									//创建aboutDialog的函数,设置接受参数为activity
  	  Dialog dialog = new AlertDialog.Builder(activity).setIcon(R.drawable.ic_launcher)			//设置Dialog的图标,标题,内容
  			  .setTitle("AIAINotice").setMessage("版本:13.05.20\n适用于android2.3-4.2版本\n" +
  			  		"支持邮箱:AIAINotice@163.com\n客服QQ:88888888\n安建大软件工作室@版权所有")
  			  .setPositiveButton("使用协议", new OnClickListener() {								//生成Dialog的第一按钮的监听器
  				@Override
  				public void onClick(DialogInterface dialog, int which) {						//Dialog第一按钮的触发事件				
  					dialog.dismiss();
  				}
  			}).setNegativeButton("关闭返回", new OnClickListener() {								//生成Dialog的第二按钮的监听器
  				@Override
  				public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}		//Dialog的第二按钮,触发时间是关闭Dialog
  			}).setNeutralButton("反馈建议", new OnClickListener() {								//设置第三Dialog按钮,并生成监听器
  				@Override
  				public void onClick(DialogInterface dialog, int which) {						//生成Dialog第三按钮的监听器
  					Intent mIntent = new Intent();												//生成一个新mIntent意图对象
  					mIntent.setAction(Intent.ACTION_VIEW);                                  //设置用机器上已经安装的浏览器(系统默认浏览器)进行访问设定的帮助网址
  					mIntent.setData(Uri.parse("http://m.baidu.com"));						 //通过Uri.parse取得地址,通过意图的setData方法注册地址到mIntent
  					activity.startActivity(mIntent);
  				}
  			}).create();
  	  dialog.show();																			//显示dialog
  	  return dialog;																			//返回dialog到UI
  	  }
    public static Builder alertDialog(final Activity activity,String string){
    	final Builder builder = new Builder(activity);						//生成一个Buidler对象builder
		builder.setIcon(R.drawable.clear_acount_icon);						//设置Builder的图标
		if(string.equals("update")){
		builder.setMessage("没有可用的更新");									//设置Buidler的显示的内容
		}else if(string.equals("errorPassword")){
			builder.setMessage("输入的新密码不匹配");
		}else if(string.equals("clearAccount")){
			builder.setMessage("你确定要删除帐号吗？");
			builder.setPositiveButton("确定", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					updateAndHistoryProgressDialog(activity,"clearAccount");
				}
			});
		}else if(string.equals("sendSuccess")){
			builder.setMessage("同步成功");
		}else
			builder.setMessage("无法连接网络");
		builder.setNegativeButton("取消", new OnClickListener() {				//第三Button的设置与监听
			@Override
			public void onClick(DialogInterface dialog, int which) {		//点击按钮触发
				dialog.dismiss();											//关闭builder弹窗
			}
		}).create();
		builder.show();														//显示builder弹窗
		return builder;														//返回builder到UI
    }
    public void deleteHistory(File file) {									//删除文件夹所有内容
    		if (file.exists()) { 											// 判断文件是否存在
  			if (file.isFile()) { 											// 判断是否是文件
  				file.delete(); 												// delete()方法 你应该知道 是删除的意思;
  			} else if (file.isDirectory()) { 								// 否则如果它是一个目录
  				File files[] = file.listFiles(); 							// 声明目录下所有的文件 files[];
  				for (int i = 0; i < files.length; i++) { 					// 遍历目录下所有的文件
  					this.deleteHistory(files[i]); 							// 把每个文件 用这个方法进行迭代
  				}
  			}
  			file.delete();													//删除文件(历史记录)
  		}
  	}
    
}


