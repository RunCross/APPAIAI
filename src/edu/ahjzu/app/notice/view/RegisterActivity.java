package edu.ahjzu.app.notice.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.server.net.HTTPServer;
import edu.ahjzu.app.notice.server.xml.XMLSwitch;
import edu.ahjzu.app.notice.tool.FormFile;

/**
 * 具体信息注册界面
 * 
 * @author zhaoxianhua
 * 
 */
public class RegisterActivity extends Activity {
	private final String LOGTAG = "registerActivity";
	EditText pwdtext = null;
	EditText pwdagaintext = null;
	EditText truenametext = null;
	EditText agetext = null;

	EditText specialtytext = null;
	EditText qqtext = null;
	RadioGroup mRadioGroup;
	RadioButton man, women;
	String college = "";
	String sex = "3";
	String name = null;
	Button surebtn = null;
	ImageView icon = null;
	File iconFile = null;
	ProgressDialog dialog = null;
	final int MANTYPE = 1;
	final int WOMENTYPE = 0;
	// private r adapter;
	private Map<String, String> map = new HashMap<String, String>();
	Usr usr = new Usr();
	private String collegeName = "";

	// private bitmap = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regeister);
		Intent i = getIntent();
		name = i.getStringExtra("name");
		initView();
	}

	public void initView() {
		icon = (ImageView) findViewById(R.id.icon);
		icon.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				chooseImage();
			}

		});
		pwdtext = (EditText) findViewById(R.id.pwd);
		pwdagaintext = (EditText) findViewById(R.id.pwdagain);
		truenametext = (EditText) findViewById(R.id.truename);
		agetext = (EditText) findViewById(R.id.age);
		// 学院
		Spinner collegetext = (Spinner) findViewById(R.id.college);
		@SuppressWarnings("rawtypes")
		final ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
				R.array.colleges, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		collegetext.setAdapter(adapter);
		collegetext.setVisibility(View.VISIBLE);
		collegetext.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				college = adapter.getItem(position).toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		// 专业
		specialtytext = (EditText) findViewById(R.id.specialty);
		qqtext = (EditText) findViewById(R.id.qq);
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		man = (RadioButton) findViewById(R.id.man);
		women = (RadioButton) findViewById(R.id.women);
		mRadioGroup.setOnCheckedChangeListener(mChangeRadio);
		RelativeLayout RErelativeLayout = (RelativeLayout) findViewById(R.id.RErelativeLayout1);
		Button backbtn = (Button) findViewById(R.id.backbtn);
		RErelativeLayout.setOnClickListener(btnListener);
		backbtn.setOnClickListener(btnListener);
		surebtn = (Button) findViewById(R.id.surebtn);
		surebtn.setOnClickListener(submitbtnListener);
	}

	Button.OnClickListener btnListener = new Button.OnClickListener() {
		@SuppressLint("NewApi")
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.RErelativeLayout1:
				InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				inputMethodManager.hideSoftInputFromWindow(
						RegisterActivity.this.getCurrentFocus()
								.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
				break;

			case R.id.backbtn:
				finish();
				break;

			}
		}
	};
	private RadioGroup.OnCheckedChangeListener mChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			if (checkedId == man.getId()) {
				sex = MANTYPE + "";
			} else if (checkedId == women.getId()) {
				sex = WOMENTYPE + "";
			}
		}
	};
	Button.OnClickListener submitbtnListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			String pwd = pwdtext.getText().toString();
			String pwdagain = pwdagaintext.getText().toString();
			if (!pwd.equals("") && !pwdagain.equals("") && pwd.equals(pwdagain)) {
				String specialty = specialtytext.getText().toString();
				if (college.equals("")) {
					myToast("请填写学院或专业");
				} else {
					// 上传图片的方法
					String qq = qqtext.getText().toString();
					String truename = truenametext.getText().toString();
					String age = agetext.getText().toString();
					if (age.equals("")) {
						age = "0";
					}
					if (qq.equals("")) {
						qq = "0";
					}
					map.put("Oper", "insertusr");
					map.put("name", name);
					map.put("truename", truename);
					if (sex.equals("3")) {
						sex = "保密";
					} else if (sex.equals(MANTYPE + "")) {
						sex = "1";
					} else if (sex.equals("" + WOMENTYPE)) {
						sex = "0";
					}
					map.put("sex", sex);
					map.put("pwd", pwd);
					map.put("age", age);
					map.put("college", collegeName);
					map.put("specialty", specialty);
					map.put("qq", qq);

					usr.setName(name);
					usr.setTruename(truename);
					usr.setAge(Integer.valueOf(age));
					usr.setCollege(college);
					usr.setSpecialty(specialty);
					usr.setQq(Integer.valueOf(qq));
					myWait();
					sendMsg(OverallData.insertusrURL, map);
				}
			} else {
				myToast("密码不一致");
			}
		}

	};

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String id = (String) msg.obj;
			Log.v(LOGTAG, "id:" + id);
			if (!id.equals("0")) {
				// 保存用户数据
				File usrdatafile = new File(
						Environment.getExternalStorageDirectory(),
						OverallData.ownData);
				if (!usrdatafile.exists()) {
					try {
						usrdatafile.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				new XMLSwitch();
				XMLSwitch.saveUsr(usr, usrdatafile);
				dialog.cancel();
				Intent i = new Intent(RegisterActivity.this,
						IndexActivity.class);
				startActivity(i);
				finish();// 结束界面
			} else {
				dialog.cancel();
				myToast("注册失败，请重新注册！");
			}
		}
	};

	private void sendMsg(final String url, final Map<String, String> map) {
		Log.v(LOGTAG, "启动注册线程！");

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (iconFile != null) {
					Log.v(LOGTAG, "传图片");
					FormFile uploadfile = new FormFile(iconFile.getName(),
							iconFile, "iconfile", "image/jpeg");
					// uploadfile.setOper("Oper=insertusr");
					try {
						boolean isok = uploadfile.post(url, map);
						handler.sendMessage(handler.obtainMessage(0, isok + ""));
					} catch (Exception e) {
						e.printStackTrace();
					}
					// map.put("icon", name + ".jpg");
				} else {
					Log.v(LOGTAG, "传文本");
					String isok = new HTTPServer().HttpGet(url, map);
					handler.sendMessage(handler.obtainMessage(0, isok));

				}
			}

		}).start();
	};

	/**
	 * 选择图片
	 */
	private void chooseImage() {

		String[] s = { "相机拍摄", "手机相册" };
		// build AlertDialog
		new AlertDialog.Builder(RegisterActivity.this).setTitle("设置")
				.setItems(s, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (which == 0) {
							// 选择到相机
							Intent intent = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							startActivityForResult(intent, 1);
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

	private Bitmap bitmap = null;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// 相机响应
		if (requestCode == 1) {
			if (resultCode == Activity.RESULT_OK) {
				if (!OverallData.isSD) {
					return;
				}
				Bundle bundle = data.getExtras();
				bitmap = null;
				// 获取相机返回的数据，并转换为Bitmap图片格式
				bitmap = (Bitmap) bundle.get("data");
				// 保存头像
				saveImage(bitmap);
				usr.setIcon(name + ".jpg");
				// 将图片显示在ImageView
				icon.setImageBitmap(bitmap);
			}
			// 相册响应
		} else if (requestCode == 2) {
			if (resultCode == Activity.RESULT_OK && null != data) {
				Uri selectedImagePath = data.getData();
				Log.v(LOGTAG, "uri:" + selectedImagePath.toString());

				Cursor cursor = getContentResolver().query(selectedImagePath,
						null, null, null, null);
				Log.v("cursor==null", (cursor == null) + "");
				if (cursor == null) {
					myToast("图片损坏，请另选一张");
				} else {
					cursor.moveToFirst();
					String picturePath = cursor.getString(1);
					bitmap = BitmapFactory.decodeFile(picturePath);
					saveImage(bitmap);
					usr.setIcon(name + ".jpg");
					icon.setImageBitmap(bitmap);
					cursor.close();
				}
			}
		}
	}

	private void saveImage(final Bitmap bitmap) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 创建文件
				File usrDataDir = new File(
						Environment.getExternalStorageDirectory(),
						OverallData.ownDataPATH);
				if (!usrDataDir.exists()) {
					usrDataDir.mkdir();
				}
				String path = usrDataDir.getAbsolutePath() + "/" + name
						+ ".jpg";
				File fileName = new File(path);
				if (!fileName.exists()) {
					try {
						fileName.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				FileOutputStream b = null;
				try {
					b = new FileOutputStream(fileName);
					// 把数据写入文件
					bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					try {
						b.flush();
						b.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}).start();

	}

	private void myToast(String msg) {
		/*
		 * Toast toast = Toast.makeText(getApplicationContext(), msg,
		 * Toast.LENGTH_LONG); toast.setGravity(Gravity.CENTER, 0, 0);
		 * toast.show();
		 */
		new AlertDialog.Builder(this).setMessage(msg)

		.setPositiveButton("确定", null)

		.show();
	}

	private void myWait() {
		dialog = new ProgressDialog(this);
		// 设置进度条风格，风格为圆形，旋转的
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		// 设置ProgressDialog 标题
		dialog.setTitle("正在注册");
		// 设置ProgressDialog 提示信息
		// dialog.setMessage("圆形进度条");
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
		// 设置ProgressDialog 的进度条是否不明确
		dialog.setIndeterminate(false);
		// 设置ProgressDialog 是否可以按退回按键取消
		dialog.setCancelable(true);
		// 显示
		dialog.show();
	}

}
