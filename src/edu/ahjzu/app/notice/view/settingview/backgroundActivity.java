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
		findViewById();//����findViewById����
		TextView title = (TextView)findViewById(R.id.title);
		title.setText("��������");
	}
	private void findViewById() {
		/**
		 * ʵ����titlebasis�еİ�ť��TextView
		 */
		Button backbtn = (Button)findViewById(R.id.backbtn);
		/**
		 * ʵ��������RelativeLayout
		 */
		RelativeLayout RL1 = (RelativeLayout) findViewById(R.id.background_from_exit_image);
		RelativeLayout RL2 = (RelativeLayout) findViewById(R.id.background_from_photos);
		RelativeLayout RL3 = (RelativeLayout) findViewById(R.id.background_from_camera);
		/**
		 * ��titlebasis��ť�󶨼�����
		 */
		backbtn.setOnClickListener(btnlistener);
		//refeshbtn.setOnClickListener(btnlistener);
		//refeshbtn.setText("����");
		/**
		 * �ֱ�����RelagiveLayout�󶨵�RLlistener������
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
			else if(btn.getText().equals("����")){finish();}
		}
	};//����һ��RLlistener������
	RelativeLayout.OnClickListener RLlistener = new RelativeLayout.OnClickListener(){
		@Override
		public void onClick(View v) {
			RelativeLayout rl = (RelativeLayout)v;
			Intent mIntent = new Intent();
			if(rl.getTag().equals("background_from_exit_image")){
				imageDialog();//����imageDialog����
			}else if(rl.getTag().equals("background_from_photos")){
				mIntent.setType("image/*");//����Pictures����Type�趨Ϊimage
				mIntent.setAction(Intent.ACTION_GET_CONTENT);//ʹ��Intent.ACTION_GET_CONTENT���Action
				startActivityForResult(mIntent,1);//ȡ����Ƭ�󷵻ر�����
			}else if(rl.getTag().equals("background_from_camera")){
				//ʹ��MediaStore.ACTION_IMAGE_CAPTRUE���Action
				mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(mIntent, 2);//ȡ����Ƭ�󷵻ر�����
			}
		}
	};
	/**
	 * ȡ�ñ���ͼƬ��ͨ��onActivityResult����������Ӧ�Ĵ���
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
                //��Bitmap�趨��ImageView   
                imageView.setImageBitmap(bitmap);  
            } catch (FileNotFoundException e) {  
                Log.e("Exception", e.getMessage(),e);  
            }  
        }  
*/		super.onActivityResult(requestCode, resultCode, data);
	}

	private void imageDialog() {//����Dialog��ʾ�ĵ����ݷŵ�String[]��������
		final String[] items = { "��ɫ����ͼ", "��ɫ����ͼ", "ǳ��ɫ����ͼ"};
		//����һ��AlertDialog���󣬲����öԻ����ͼ��ͱ��⣬������ʹ�õ�ѡ���������ü�����
		AlertDialog.Builder builder = new Builder(this).setTitle("ѡ�񱳾���ɫ")
				.setSingleChoiceItems(items, 0, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				case 0:
					Toast.makeText(backgroundActivity.this, "�ɹ�������ɫ����ͼ", Toast.LENGTH_SHORT).show();
					break;
				case 1:
					Toast.makeText(backgroundActivity.this, "�ɹ����û�ɫ����ͼ", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(backgroundActivity.this, "�ɹ�����ǳ��ɫ����ͼ", Toast.LENGTH_SHORT).show();
					break;
				}
				dialog.dismiss();//�ر�AlertDialog
			}
		});
		builder.setNegativeButton("ȡ��", null);
     	builder.create().show();
	}
}
