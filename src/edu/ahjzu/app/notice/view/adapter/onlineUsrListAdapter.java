package edu.ahjzu.app.notice.view.adapter;

import java.io.File;
import java.util.List;

import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.server.net.HTTPServer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.APP.aiainotice.R;

public class onlineUsrListAdapter extends BaseAdapter {
	private List<Usr> data;
	private int listviewitem;
	private File cache;
	private LayoutInflater layoutInflater;

	public onlineUsrListAdapter(Context context, List<Usr> data,
			int listviewitem, File cache) {

		layoutInflater = LayoutInflater.from(context);
		this.data = data;
		this.listviewitem = listviewitem;
		this.cache = cache;
	}

	public void setData(List<Usr> mdata) {
		this.data = mdata;
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int postion, View converView, ViewGroup parent) {
		ViewHolder holder = null;
		if (converView == null) {
			holder = new ViewHolder();
			converView = layoutInflater.inflate(listviewitem, null);
			holder.iconView = (ImageView) converView
					.findViewById(R.id.receive_icon);
			holder.nameView = (TextView) converView
					.findViewById(R.id.receive_name);
			holder.placeView = (TextView) converView
					.findViewById(R.id.receive_place);
			holder.statusView = (TextView) converView
					.findViewById(R.id.receive_status);
			converView.setTag(holder);
		} else {
			holder = (ViewHolder) converView.getTag();
		}
		Usr usr = data.get(postion);
		// System.out.println("dddusr:"+usr);
		// System.out.println("name:"+usr.name);
		// System.out.println("nameView:"+nameView);
		holder.nameView.setText(usr.name);
		holder.placeView.setText(usr.place);
		holder.statusView.setText(usr.status);

		asynsImageLoad(holder.iconView, usr.icon, postion);

		return converView;

	}

	private void asynsImageLoad(final ImageView imageview, final String path,
			final int postion) {
		AsyncImageTask asyncImageTask = new AsyncImageTask(imageview, postion);
		asyncImageTask.execute(path);

	}

	@SuppressLint("NewApi")
	private final class AsyncImageTask extends AsyncTask<String, Integer, Uri> {
		private ImageView imageview;
		private int postion;

		public AsyncImageTask(ImageView imageview, int postion) {
			this.imageview = imageview;
			this.postion = postion;
		}

		// 在子线程里运行
		@Override
		protected Uri doInBackground(String... params) {

			return HTTPServer.getBufferImage(params[0], cache);

		}

		// 回调函数， 运行在UI主线程中
		@Override
		protected void onPostExecute(Uri result) {
			if (result != null && imageview != null) {
				System.out.println("Uri result::" + result);
				imageview.setImageURI(result);
			} else {
				imageview.setImageResource(R.drawable.ic_launcher);
			}

		}

	}

	private final class ViewHolder {
		public ImageView iconView;
		public TextView nameView;
		public TextView placeView;
		public TextView statusView;
	}

}
