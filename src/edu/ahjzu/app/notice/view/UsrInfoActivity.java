package edu.ahjzu.app.notice.view;

import java.io.File;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.util.MD5;

public class UsrInfoActivity extends Activity {
	private Usr usr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usrinfo);
		usr = (Usr) getIntent().getSerializableExtra("usr");
		initView();
	}

	private void initView() {
		ImageView icon = (ImageView) findViewById(R.id.icon);
		TextView name = (TextView) findViewById(R.id.name);
		TextView age_sex = (TextView) findViewById(R.id.age_sex);
		TextView place = (TextView) findViewById(R.id.place);
		TextView status = (TextView) findViewById(R.id.status);
		//
		File cache = new File(Environment.getExternalStorageDirectory(),
				OverallData.USRICONPATH);
		File locadFile = new File(cache, MD5.getMD5(usr.icon)
				+ usr.icon.substring(usr.icon.lastIndexOf(".")));
		System.out.println("locadFile::" + locadFile);
		icon.setImageURI(Uri.fromFile(locadFile));
		String strsex = usr.getSex();
		name.setText(usr.getName());
		age_sex.setText(usr.getAge() + "Ëê     " + strsex);
		place.setText(usr.getPlace());
		status.setText(usr.getStatus());
	}

}