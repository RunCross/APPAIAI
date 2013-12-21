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
		//ȡ��PreferenceScreen����
		addPreferencesFromResource(R.xml.settings);
		//����findViewByKey����
		findViewByKey();
		//����setListener����
		setListener();
	}

	private void findViewByKey() {
		/**
		 * ͨ��Keyʵ����Preference��CheckBoxPreference
		 */
		location = findPreference("location");
		shake = (CheckBoxPreference)findPreference("shake");
		voice = (CheckBoxPreference)findPreference("voice");
	}

	private void setListener() {
		/**
		 * �ֱ��location,shake��voice�󶨼�����
		 */
		location.setOnPreferenceClickListener(this);
		shake.setOnPreferenceClickListener(this);
		voice.setOnPreferenceClickListener(this);
	}
	/**
	 * ��Preference��CheckBoxPreference���ü�����,���ز���ֵ
	 */
	@Override
	public boolean onPreferenceClick(Preference preference) {
		if(preference == location){//��λ��ʽ�ļ�����
			RefreshDialog();
		}else if(preference == shake){//�񶯵ļ�����
			
		}else if(preference == voice){//�����ļ�����
			
		}
		return true;
	}
	 /**
	  * �����λ��ʽʱ,��AlertDialog�ĵ������û�����ѡ��
	  */
	private void RefreshDialog() {
		//����һ������,���û�ѡ���AlertDialog��������ʾ��������
		final String[] items = { "GPS��λ(���ڿ����޷���λ)", "GPRS(����)��λ", "�ر�"};
		//����һ��AlertDialog����
		AlertDialog.Builder builder = new Builder(this);
		//����AlertDialog�ı���
		builder.setTitle("��λ��ʽ");
		//��AlertDialog�������ü�����,��Single�ķ�ʽ��ʾ��������
		builder.setSingleChoiceItems(items, 0, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				//���û����ѡ��ʱ,��Preference����ʾ�û������"����"
				location.setSummary(items[which]);
				//�ر�AlertDialog
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
