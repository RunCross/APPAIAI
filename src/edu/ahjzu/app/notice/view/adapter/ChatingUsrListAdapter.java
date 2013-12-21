package edu.ahjzu.app.notice.view.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.util.MD5;

public class ChatingUsrListAdapter extends BaseAdapter {
	private int listviewitem;
	private LayoutInflater layoutInflater;
	private List<Usr> chatingusrs;
	private File cache;

	public ChatingUsrListAdapter(Context context, List<Usr> usrs,
			int listviewitem, File cache) {
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.listviewitem = listviewitem;
		this.cache = cache;
		this.chatingusrs = usrs;
		System.out.println("chatingusrsµÄ³¤¶È£º" + chatingusrs.size());
	}

	public void setData(List<Usr> mchatingusrs) {
		this.chatingusrs = mchatingusrs;
	}

	@Override
	public int getCount() {
		return chatingusrs.size();
	}

	@Override
	public Object getItem(int position) {
		return chatingusrs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = layoutInflater.inflate(listviewitem, null);
			holder.iconView = (ImageView) convertView
					.findViewById(R.id.receive_icon);
			holder.nameView = (TextView) convertView
					.findViewById(R.id.receive_name);
			holder.placeView = (TextView) convertView
					.findViewById(R.id.receive_place);
			holder.statusView = (TextView) convertView
					.findViewById(R.id.receive_status);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		Usr usr = chatingusrs.get(position);
		System.out.println(usr.toJson());
		holder.nameView.setText(usr.name);
		holder.placeView.setText(usr.place);
		holder.statusView.setText(usr.status);
		String imagepath = usr.icon;
		if (imagepath != null && !imagepath.isEmpty()) {
			File locadFile = new File(cache, MD5.getMD5(imagepath)
					+ imagepath.substring(imagepath.lastIndexOf(".")));
			if (locadFile.exists()) {
				holder.iconView.setImageURI(Uri.fromFile(locadFile));
			} else {
				holder.iconView.setImageResource(R.drawable.ic_launcher);
			}
		} else {
			holder.iconView.setImageResource(R.drawable.ic_launcher);
		}
		return convertView;

	}

	private final class ViewHolder {
		public ImageView iconView;
		public TextView nameView;
		public TextView placeView;
		public TextView statusView;

	}
}
