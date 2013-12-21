package edu.ahjzu.app.notice.view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
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
import edu.ahjzu.app.notice.view.adapter.ChatingUsrListAdapter;

public class ChatingUsrFragment extends Fragment implements
		OnItemLongClickListener {
	// ChatService的binder
	private ServiceBinder binder = null;

	private ChatingUsrListAdapter adapter = null;
	// 用户头像保存路径
	private File ImageCache = new File(
			Environment.getExternalStorageDirectory(), OverallData.USRICONPATH);
	// 历史聊天用户列表
	private List<Usr> chatingUsrList = new ArrayList<Usr>();
	private ListView listview = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chat_view_stulist, null);
		listview = (ListView) view.findViewById(R.id.studentsList);
		listview.setOnItemLongClickListener(this);
		listview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 得到点击的数据
				Usr usr = binder.getChatingUsr().get(position);
				// 跳转到聊天界面
				Intent intent = new Intent(getActivity(), ChatingActivity.class);
				intent.putExtra("usr", usr);
				getActivity().startActivity(intent);
			}
		});
		// 绑定服务
		Intent service = new Intent();
		service.setAction("com.aiai.chat.service");
		getActivity()
				.bindService(service, connection, Context.BIND_AUTO_CREATE);
		return view;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final Usr usr = binder.getChatingUsr().get(position);
		System.out.println("onItemLongClick:" + usr.getId() + "   "
				+ usr.getName());
		new AlertDialog.Builder(getActivity()).setTitle(usr.getName())
				.setItems(new String[] { "查看资料", "删除" }, new OnClickListener() {
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
							binder.deleteRecentUsr(usr.getId());
							//
							chatingUsrList = binder.getChatingUsr();
							adapter.setData(chatingUsrList);
							adapter.notifyDataSetChanged();
							break;
						}
					}
				}).setNegativeButton("取消", null).show();
		return true;
	}

	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (ServiceBinder) service;
			//
			chatingUsrList = binder.getChatingUsr();
			System.out.println("chatingUsrList:"+chatingUsrList.size());
			for (Usr usr : chatingUsrList) {
				System.err.println(usr.toJson());
			}
			adapter = new ChatingUsrListAdapter(getActivity(), chatingUsrList,
					R.layout.chatactivity_listview_item, ImageCache);
			listview.setAdapter(adapter);

			// 当数据列表发生变化时刷新listview
			binder.setOnChatingUsrChangeListener(new UsrListChangeListener() {
				@Override
				public void handler(int id) {
					System.out
							.println("ChatingUsrFragment UsrListChangeListener handler:"
									+ id);
					for (Usr usr : chatingUsrList) {
						if (usr.getId() == id) {
							return;
						}
					}
					chatingUsrList = binder.getChatingUsr();
					adapter.setData(chatingUsrList);
					adapter.notifyDataSetChanged();
					System.out.println("notifyDataSetChanged");
				}
			});
		}
	};
}
