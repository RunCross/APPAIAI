package edu.ahjzu.app.notice.view;

import java.io.File;

import android.R.integer;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.service.ChatService.ServiceBinder;
import edu.ahjzu.app.notice.service.ChatService.UsrListChangeListener;
import edu.ahjzu.app.notice.view.adapter.onlineUsrListAdapter;

public class OnlineUsrFragment extends Fragment implements
		OnItemLongClickListener {
	private final String LOGTAG = "chart_view_Adapter";
	private ServiceBinder binder = null;

	private onlineUsrListAdapter adapter = null;
	// �û�ͷ�񱣴�·��
	private File ImageCache = new File(OverallData.USRICONPATH);

	private ListView listview = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���������
		Intent service = new Intent();
		service.setAction("com.aiai.chat.service");
		getActivity()
				.bindService(service, connection, Context.BIND_AUTO_CREATE);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().unbindService(connection);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_view_stulist, null);
		listview = (ListView) view.findViewById(R.id.studentsList);
		listview.setOnItemLongClickListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// �õ����������
				Usr usr = binder.getOnlineUsr().get(position);
				// ��ת���������
				Intent intent = new Intent(getActivity(), ChatingActivity.class);
				intent.putExtra("usr", usr);
				getActivity().startActivity(intent);
			}
		});
		return view;
	};

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		System.err.println("OnlineUsrFragment   onDestroyView");
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final Usr usr = binder.getOnlineUsr().get(position);
		new AlertDialog.Builder(getActivity()).setTitle(usr.getName())
				.setItems(new String[] { "�鿴����", "ɾ��" }, new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int i) {
						switch (i) {
						case 0:
							Intent intent = new Intent(getActivity(),
									UsrInfoActivity.class);
							intent.putExtra("usr", usr);
							getActivity().startActivity(intent);
							break;
						case 1:
							System.out.println("ɾ��");
							break;
						}
					}
				}).setNegativeButton("ȡ��", null).show();
		return true;
	}

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			System.err.println("onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (ServiceBinder) service;
			adapter = new onlineUsrListAdapter(getActivity(),
					binder.getOnlineUsr(), R.layout.chatactivity_listview_item,
					ImageCache);
			listview.setAdapter(adapter);
			// ���������û��仯�ļ�����
			binder.setOnOnlineUsrListChangeListener(new UsrListChangeListener() {
				@Override
				public void handler(int id) {
					// �������û��仯ʱˢ��listview
					if (adapter != null) {
						adapter.setData(binder.getOnlineUsr());
						adapter.notifyDataSetChanged();
					}
				}
			});
		}
	};
}
