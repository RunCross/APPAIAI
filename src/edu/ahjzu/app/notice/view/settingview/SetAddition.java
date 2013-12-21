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
	public static  ProgressDialog updateAndHistoryProgressDialog(final Activity activity,String string){//���ú���,������������
    	final ProgressDialog dialog = new ProgressDialog(activity);								//����һ��ProgressDialog�Ķ���
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);								//����ProgressDialog����ʾ����ΪԲ��
			if(string.equals("clearHistory")){													//�жϲ���ֵ
			dialog.setMessage("���������¼...");													//���������ʷ��¼dialog������
			}else if(string.equals("weibo")){
				dialog.setMessage("���ڷ���...");													//���÷���΢����dialog������
			}else if(string.equals("clearAccount")){
				dialog.setMessage("����ɾ���ʺ�...");												//��������ɾ���ʺŵ�dialog������
			}
			else if(string.equals("sendweibo")){
				dialog.setMessage("����ͬ����΢��...");
			}else{
				dialog.setMessage("���ڼ�����...");												//���ü����µ�dialog������
			}
			dialog.show();																		//��ʾdialog
				Timer timer = new Timer();														//����һ����TimerTask����
				timer.schedule(new TimerTask() {												//��timer.schedule��������������
					@Override
					public void run() {
						Looper.prepare();														//����looper��pripare()����ͬ��UI�߳�
						dialog.dismiss();														//4000����ر�dialog
					}
				}, 4000);																		//�����ӳ�ʱ��Ϊ4000����
			return dialog;																		//����dialog��UI
		}
    public static  Dialog aboutDialog(final Activity activity){									//����aboutDialog�ĺ���,���ý��ܲ���Ϊactivity
  	  Dialog dialog = new AlertDialog.Builder(activity).setIcon(R.drawable.ic_launcher)			//����Dialog��ͼ��,����,����
  			  .setTitle("AIAINotice").setMessage("�汾:13.05.20\n������android2.3-4.2�汾\n" +
  			  		"֧������:AIAINotice@163.com\n�ͷ�QQ:88888888\n���������������@��Ȩ����")
  			  .setPositiveButton("ʹ��Э��", new OnClickListener() {								//����Dialog�ĵ�һ��ť�ļ�����
  				@Override
  				public void onClick(DialogInterface dialog, int which) {						//Dialog��һ��ť�Ĵ����¼�				
  					dialog.dismiss();
  				}
  			}).setNegativeButton("�رշ���", new OnClickListener() {								//����Dialog�ĵڶ���ť�ļ�����
  				@Override
  				public void onClick(DialogInterface dialog, int which) {dialog.dismiss();}		//Dialog�ĵڶ���ť,����ʱ���ǹر�Dialog
  			}).setNeutralButton("��������", new OnClickListener() {								//���õ���Dialog��ť,�����ɼ�����
  				@Override
  				public void onClick(DialogInterface dialog, int which) {						//����Dialog������ť�ļ�����
  					Intent mIntent = new Intent();												//����һ����mIntent��ͼ����
  					mIntent.setAction(Intent.ACTION_VIEW);                                  //�����û������Ѿ���װ�������(ϵͳĬ�������)���з����趨�İ�����ַ
  					mIntent.setData(Uri.parse("http://m.baidu.com"));						 //ͨ��Uri.parseȡ�õ�ַ,ͨ����ͼ��setData����ע���ַ��mIntent
  					activity.startActivity(mIntent);
  				}
  			}).create();
  	  dialog.show();																			//��ʾdialog
  	  return dialog;																			//����dialog��UI
  	  }
    public static Builder alertDialog(final Activity activity,String string){
    	final Builder builder = new Builder(activity);						//����һ��Buidler����builder
		builder.setIcon(R.drawable.clear_acount_icon);						//����Builder��ͼ��
		if(string.equals("update")){
		builder.setMessage("û�п��õĸ���");									//����Buidler����ʾ������
		}else if(string.equals("errorPassword")){
			builder.setMessage("����������벻ƥ��");
		}else if(string.equals("clearAccount")){
			builder.setMessage("��ȷ��Ҫɾ���ʺ���");
			builder.setPositiveButton("ȷ��", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					updateAndHistoryProgressDialog(activity,"clearAccount");
				}
			});
		}else if(string.equals("sendSuccess")){
			builder.setMessage("ͬ���ɹ�");
		}else
			builder.setMessage("�޷���������");
		builder.setNegativeButton("ȡ��", new OnClickListener() {				//����Button�����������
			@Override
			public void onClick(DialogInterface dialog, int which) {		//�����ť����
				dialog.dismiss();											//�ر�builder����
			}
		}).create();
		builder.show();														//��ʾbuilder����
		return builder;														//����builder��UI
    }
    public void deleteHistory(File file) {									//ɾ���ļ�����������
    		if (file.exists()) { 											// �ж��ļ��Ƿ����
  			if (file.isFile()) { 											// �ж��Ƿ����ļ�
  				file.delete(); 												// delete()���� ��Ӧ��֪�� ��ɾ������˼;
  			} else if (file.isDirectory()) { 								// �����������һ��Ŀ¼
  				File files[] = file.listFiles(); 							// ����Ŀ¼�����е��ļ� files[];
  				for (int i = 0; i < files.length; i++) { 					// ����Ŀ¼�����е��ļ�
  					this.deleteHistory(files[i]); 							// ��ÿ���ļ� ������������е���
  				}
  			}
  			file.delete();													//ɾ���ļ�(��ʷ��¼)
  		}
  	}
    
}


