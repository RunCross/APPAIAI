package edu.ahjzu.app.notice.view.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.WeiBo;
import edu.ahjzu.app.notice.view.widget.NetImageView;

public class IndexAdapter extends BaseAdapter {
	// private List<newsData> data;
	private int listviewitem;
	private String cachepath;
	private LayoutInflater layoutInflater;
	public List<WeiBo> weibos;
	private Context context;
	private final String LOGTAG = "indexAdapter";

	public IndexAdapter(Context context, List<WeiBo> weibos,
			int listviewitem, String cache) {
		this.context = context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.weibos = weibos;
		this.listviewitem = listviewitem;
		this.cachepath = cache;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return weibos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return weibos.get(weibos.size() - arg0 - 1);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		NetImageView icon = null;
		TextView name = null;
		NetImageView image = null;
		TextView content = null;
		ImageView good = null;
		ImageView discuss = null;
		// º”‘ÿview
		if (convertView == null) {
			convertView = layoutInflater.inflate(listviewitem, null);
			icon = (NetImageView) convertView
					.findViewById(R.id.weibo_listview_item_icon);
			name = (TextView) convertView
					.findViewById(R.id.weibo_listview_item_name);
			image = (NetImageView) convertView
					.findViewById(R.id.weibo_listview_item_image);
			content = (TextView) convertView
					.findViewById(R.id.weibo_listview_item_content);
			good = (ImageView) convertView
					.findViewById(R.id.weibo_listview_item_good);
			discuss = (ImageView) convertView
					.findViewById(R.id.weibo_listview_item_discuss);
			convertView.setTag(new DataWrapper(icon, name, image, content,
					good, discuss));

		} else {
			DataWrapper dataWrappe = (DataWrapper) convertView.getTag();
			icon = dataWrappe.icon;
			name = dataWrappe.name;
			image = dataWrappe.image;
			content = dataWrappe.content;
			good = dataWrappe.good;
			discuss = dataWrappe.discuss;
		}

		WeiBo weibo = weibos.get(position);
		icon.setNetImage(weibo.getIcon(), OverallData.USRICONPATH,
				"" + weibo.getUsrid());
		name.setText(weibo.getUsrname());
		image.setNetImage(weibo.getPicpath(), cachepath, weibo.getId());
		content.setText(weibo.getContent());
		return convertView;
	}

	private final class DataWrapper {
		NetImageView icon = null;
		TextView name = null;
		NetImageView image = null;
		TextView content = null;
		ImageView good = null;
		ImageView discuss = null;

		public DataWrapper(NetImageView icon, TextView name,
				NetImageView image, TextView content, ImageView good,
				ImageView discuss) {
			super();
			this.icon = icon;
			this.name = name;
			this.image = image;
			this.content = content;
			this.good = good;
			this.discuss = discuss;
		}

	}
}
