package edu.ahjzu.app.notice.view;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.Chat;
import edu.ahjzu.app.notice.server.locationInit;
import edu.ahjzu.app.notice.server.net.HTTPServer;
import edu.ahjzu.app.notice.service.ChatService.ServiceBinder;
import edu.ahjzu.app.notice.tool.FormFile;
import edu.ahjzu.app.notice.util.myUIWarn;

public class EditChatActivity extends Activity {

	locationInit locatinit = null;// 定位功能模块
	// HTTPServer HTTPConn = null;
	Chat chatdata = new Chat();// 用户在该页面填写的数据

	public static String LOGTAG = "editchatActivity";
	private static int count = 1;

	private TextView locationdatatextview = null;
	private Button locationbtn = null;
	private EditText content = null;
	private ImageView iv = null;
	private View photobtn = null;
	private ServiceBinder binder = null;
	SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
	String fileName = formatter.format(curDate) + ".jpg";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.editchat);
		// HTTPConn = new HTTPServer();

		initView();
		// 绑定服务
		Intent service = new Intent();
		service.setAction("com.aiai.chat.service");
		bindService(service, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 相机响应
		if (requestCode == 1) {
			if (resultCode == Activity.RESULT_OK) {
				if (!OverallData.isSD) {
					return;
				}
				File imagefile = new File(
						Environment.getExternalStorageDirectory(),
						OverallData.WEIBOIMAGEPATH + fileName);
				Bitmap bmpDefaultPic = BitmapFactory.decodeFile(
						imagefile.getAbsolutePath(), null);

				/*
				 * Bundle bundle = data.getExtras(); Bitmap bitmap = (Bitmap)
				 * bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
				 * 
				 * FileOutputStream b = null; try {
				 * 
				 * b = new FileOutputStream(new File(
				 * Environment.getExternalStorageDirectory(),
				 * OverallData.weiboPICPath + fileName));
				 * bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);//
				 * 把数据写入文件
				 * 
				 * } catch (FileNotFoundException e) { Log.e(LOGTAG,
				 * e.getMessage()); } finally { try { if (b != null) {
				 * b.flush(); b.close(); } } catch (IOException e1) { // TODO
				 * Auto-generated catch block Log.e(LOGTAG, e1.getMessage()); }
				 * 
				 * }
				 */
				chatdata.setpicpath(fileName);
				iv.setImageBitmap(bmpDefaultPic);// 将图片显示在ImageView
				bmpDefaultPic = null;

			}
			// 相册响应
		} else if (requestCode == 2) {
			if (resultCode == Activity.RESULT_OK && null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumns = { MediaColumns.DATA };
				Cursor c = this.getContentResolver().query(selectedImage,
						filePathColumns, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePathColumns[0]);
				String picturePath = c.getString(columnIndex);
				chatdata.setpicpath(picturePath);
				System.out.println("picturePath::" + picturePath);
				c.close();
				Bitmap bitmap = BitmapFactory.decodeFile(picturePath);// 从本地取图片
				iv.setImageBitmap(bitmap);
			}
		}
	}

	/**
	 * 处理当得到数据
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}
	};

	private void initView() {

		iv = ((ImageView) findViewById(R.id.pic));
		locationdatatextview = (TextView) findViewById(R.id.locationdata);
		photobtn = findViewById(R.id.photobtn);
		locationbtn = (Button) findViewById(R.id.locationbtn);
		content = (EditText) findViewById(R.id.content);// 内容编辑view
		View cancelbtn = findViewById(R.id.cancelbtn); // 发送取消按钮
		View okbtn = findViewById(R.id.okbtn); // 确认发送按钮

		// 初始化启动定位功能模块
		locatinit = new locationInit(this);
		locatinit.locationdata = locationdatatextview;
		locatinit.chartdata = chatdata;
		locatinit.init();

		cancelbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(EditChatActivity.this, ChatActivity.class);
				i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				finish();
				startActivity(i);
			}
		});
		okbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				locatinit.mLocClient.stop();
				if (!content.getText().toString().equals("")) {
					if (!locationdatatextview.getText().toString().equals("")) {
						showDialog("是否将您的位置信息一起发送？");
					} else {
						sendMsg();
					}
					/*
					 * if (chartdata.getaddr_Latitude() != 0) {
					 * showDialog("是否将您的位置信息一起发送？"); }else{ sendMsg(); }
					 */
				} else {
					myUIWarn.myToast(EditChatActivity.this, "请填写文字");
				}

			}
		});

		photobtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				choosePic();
			}
		});

		// 定位开始/停止按钮
		locationbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!locatinit.mLocClient.isStarted()) {
					locatinit.setLocationOption();
					locatinit.mLocClient.start();
				}
				Log.d(LOGTAG, "... mStartBtn onClick... pid=" + Process.myPid()
						+ " count=" + count++);
			}
		});
	}

	private void choosePic() {
		String[] s = { "相机拍摄", "手机相册" };
		new AlertDialog.Builder(EditChatActivity.this) // build AlertDialog
				.setTitle("设置") // title
				.setItems(s, new DialogInterface.OnClickListener() { // content
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if (which == 0) {
									// 选择到相机
									/*
									 * Intent intent = new Intent(
									 * MediaStore.ACTION_IMAGE_CAPTURE);
									 * startActivityForResult(intent, 1);
									 */

									Intent it = new Intent(
											"android.media.action.IMAGE_CAPTURE");
									it.putExtra(
											MediaStore.EXTRA_OUTPUT,
											Uri.fromFile(new File(
													Environment
															.getExternalStorageDirectory(),
													OverallData.WEIBOIMAGEPATH
															+ fileName)));
									startActivityForResult(it, 1);
								} else {
									// 选择相册
									Intent intent = new Intent(
											Intent.ACTION_PICK,
											android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
									startActivityForResult(intent, 2);
								}
							}
						}).show();

	}

	private void showDialog(String msg) {
		new AlertDialog.Builder(EditChatActivity.this)
				// .setTitle("Message")
				.setMessage(msg)
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						// 关闭定位系统
						if (locatinit.mLocClient.isStarted()) {
							locatinit.mLocClient.stop();
						}
						sendMsg();
					}
				})
				.setNegativeButton("否定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						chatdata.setaddr_Latitude(0);
						chatdata.setaddr_Longitude(0);
						chatdata.setaddr("");
						dialog.dismiss();
						sendMsg();
					}
				}).show();
	}

	/*
	 * Handler handler = new Handler() { public void handleMessage(Message msg)
	 * { String isok = (String) msg.obj; if (isok.equals("true")) {
	 * 
	 * myUIWarn.remind(editchatActivity.this, "发送成功！"); Intent i = new
	 * Intent(editchatActivity.this, chatActivity.class); startActivity(i);
	 * finish();// 结束界面 } else { } } };
	 */

	private void sendMsg() {
		System.out.println("启动发送线程！");
		new Thread(new Runnable() {
			@Override
			public void run() {
				chatdata.setcontent(content.getText().toString());
				Map<String, String> map = new HashMap<String, String>();
				map.put("latitude", chatdata.getaddr_Latitude() + "");
				map.put("longitiude", chatdata.getaddr_Longitude() + "");
				map.put("content", chatdata.getcontent());
				map.put("name", binder.getMyUsr().getName());
				map.put("addr", chatdata.getaddr());
				map.put("Oper", "addweibo");
				String url = OverallData.actionUrl + "initServlet";
				/*
				 * String url = OverallData.actionUrl +
				 * "initServlet?Oper=addweibo";
				 */
				File imagefile = null;
				FormFile uploadfile = null;
				// 上传图片
				if (!chatdata.getpicpath().equals("")) {
					imagefile = new File(chatdata.getpicpath());
					uploadfile = new FormFile(imagefile.getName(), imagefile,
							"image", "image/jpeg");
					// uploadfile.setOper("Oper=addweibo");
					try {
						boolean isok = uploadfile.post(url, map);
						if (isok == true) {
							myUIWarn.show(EditChatActivity.this, "发送成功！");
						} else {
							myUIWarn.show(EditChatActivity.this, "发送失败！");
						}
					} catch (Exception e) {
						// Log.v(LOGTAG, e.getMessage());
						myUIWarn.show(EditChatActivity.this, "发送失败！");
					}
				} else {// 没有图片就用普通的HTTP GET
					// uploadfile = new FormFile("", imagefile, "image",
					// "image/jpeg");
					map.put("Oper", "addweibo");
					HTTPServer httpServer = new HTTPServer();
//					try {
						String isok = httpServer.HttpGet(url, map);
						if (isok.equals("true")) {
							myUIWarn.show(EditChatActivity.this, "发送成功！");
						} else {
							myUIWarn.show(EditChatActivity.this, "发送失败！");
						}
					// } catch (MalformedURLException e) {
					// System.out.println(e);
					// myUIWarn.show(EditChatActivity.this, "发送失败！");
					// } catch (IOException e) {
					// System.out.println(e);
					// myUIWarn.show(EditChatActivity.this, "发送失败！");
					// }
				}

			}
		}).start();
		Activityclose();
	}

	private void Activityclose() {
		Intent i = new Intent(EditChatActivity.this, ChatActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(i);
		finish();// 结束界面
	}

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (ServiceBinder) service;
		}
	};
}
