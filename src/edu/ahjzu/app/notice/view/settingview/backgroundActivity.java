package edu.ahjzu.app.notice.view.settingview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.APP.aiainotice.R;
public class backgroundActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.backgroundsetting);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.titlebasis); 
		findViewById();//调用findViewById函数
		TextView title = (TextView)findViewById(R.id.title);
		title.setText("背景设置");
	}
	private void findViewById() {
		/**
		 * 实例化titlebasis中的按钮和TextView
		 */
		Button backbtn = (Button)findViewById(R.id.backbtn);
		/**
		 * 实例化三个RelativeLayout
		 */
		RelativeLayout RL1 = (RelativeLayout) findViewById(R.id.background_from_exit_image);
		RelativeLayout RL2 = (RelativeLayout) findViewById(R.id.background_from_photos);
		RelativeLayout RL3 = (RelativeLayout) findViewById(R.id.background_from_camera);
		/**
		 * 对titlebasis按钮绑定监听器
		 */
		backbtn.setOnClickListener(btnlistener);
		//refeshbtn.setOnClickListener(btnlistener);
		//refeshbtn.setText("放弃");
		/**
		 * 分别三个RelagiveLayout绑定到RLlistener监听器
		 */
		RL1.setOnClickListener(RLlistener);
		RL2.setOnClickListener(RLlistener);
		RL3.setOnClickListener(RLlistener);
	}
	RelativeLayout.OnClickListener btnlistener = new RelativeLayout.OnClickListener(){
		@Override
		public void onClick(View v) {
			Button btn = (Button)v;
			if(btn.getText().equals("back")){finish();}
			else if(btn.getText().equals("放弃")){finish();}
		}
	};//创建一个RLlistener监听器
	RelativeLayout.OnClickListener RLlistener = new RelativeLayout.OnClickListener(){
		@Override
		public void onClick(View v) {
			RelativeLayout rl = (RelativeLayout)v;
			Intent mIntent = new Intent();
			if(rl.getTag().equals("background_from_exit_image")){
				imageDialog();//调用imageDialog函数
			}else if(rl.getTag().equals("background_from_photos")){
				mIntent.setType("image/*");//开启Pictures画面Type设定为image
				mIntent.setAction(Intent.ACTION_GET_CONTENT);//使用Intent.ACTION_GET_CONTENT这个Action
				startActivityForResult(mIntent,1);//取得相片后返回本画面
			}else if(rl.getTag().equals("background_from_camera")){
				//使用MediaStore.ACTION_IMAGE_CAPTRUE这个Action
				mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(mIntent, 2);//取得相片后返回本画面
			}
		}
	};
	/**
	 * 取得背景图片后，通过onActivityResult函数进行相应的处理
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
/*		if (resultCode == RESULT_OK) {  
            Uri uri = data.getData();  
            Log.e("uri", uri.toString());  
            ContentResolver cr = this.getContentResolver();  
            try {  
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
                ImageView imageView = (ImageView) findViewById(R.id.iv01);  
                //将Bitmap设定到ImageView   
                imageView.setImageBitmap(bitmap);  
            } catch (FileNotFoundException e) {  
                Log.e("Exception", e.getMessage(),e);  
            }  
        }  
*/		super.onActivityResult(requestCode, resultCode, data);
	}

	private void imageDialog() {//设置Dialog显示的的内容放到String[]数组里面
		final String[] items = { "蓝色背景图", "灰色背景图", "浅红色背景图"};
		//生成一个AlertDialog对象，并设置对话框的图标和标题，且设置使用单选方法并设置监听器
		AlertDialog.Builder builder = new Builder(this).setTitle("选择背景颜色")
				.setSingleChoiceItems(items, 0, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					Toast.makeText(backgroundActivity.this, "成功设置蓝色背景图", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(backgroundActivity.this, "成功设置灰色背景图", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(backgroundActivity.this, "成功设置浅红色背景图", Toast.LENGTH_SHORT).show();
					break;
				}
				dialog.dismiss();//关闭AlertDialog
			}
		});
		builder.setNegativeButton("取消", null);
     	builder.create().show();
	}
}
