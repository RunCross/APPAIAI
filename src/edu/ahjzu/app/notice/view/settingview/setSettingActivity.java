package edu.ahjzu.app.notice.view.settingview;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.widget.Toast;
import com.APP.aiainotice.R;

public class setSettingActivity extends PreferenceActivity implements
		OnPreferenceClickListener {
	private Preference location;
	private CheckBoxPreference shake;
	private CheckBoxPreference voice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取得PreferenceScreen布局
		addPreferencesFromResource(R.xml.settings);
		//调用findViewByKey函数
		findViewByKey();
		//调用setListener函数
		setListener();
	}

	private void findViewByKey() {
		/**
		 * 通过Key实例化Preference和CheckBoxPreference
		 */
		location = findPreference("location");
		shake = (CheckBoxPreference)findPreference("shake");
		voice = (CheckBoxPreference)findPreference("voice");
	}

	private void setListener() {
		/**
		 * 分别对location,shake和voice绑定监听器
		 */
		location.setOnPreferenceClickListener(this);
		shake.setOnPreferenceClickListener(this);
		voice.setOnPreferenceClickListener(this);
	}
	/**
	 * 对Preference和CheckBoxPreference设置监听器,返回布尔值
	 */
	@Override
	public boolean onPreferenceClick(Preference preference) {
		if(preference == location){//定位方式的监听器
			RefreshDialog();
		}else if(preference == shake){//振动的监听器
			
		}else if(preference == voice){//铃声的监听器
			
		}
		return true;
	}
	 /**
	  * 点击定位方式时,以AlertDialog的弹窗供用户进行选择
	  */
	private void RefreshDialog() {
		//定义一个数组,在用户选择的AlertDialog弹窗中显示数组内容
		final String[] items = { "GPS定位(室内可能无法定位)", "GPRS(网络)定位", "关闭"};
		//生成一个AlertDialog对象
		AlertDialog.Builder builder = new Builder(this);
		//设置AlertDialog的标题
		builder.setTitle("定位方式");
		//对AlertDialog弹窗设置监听器,以Single的方式显示数组内容
		builder.setSingleChoiceItems(items, 0, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//当用户点击选项时,在Preference中显示用户点击的"内容"
				location.setSummary(items[which]);
				//关闭AlertDialog
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
