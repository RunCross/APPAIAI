package edu.ahjzu.app.notice.view.adapter;

import java.io.File;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.pojo.News;
import edu.ahjzu.app.notice.server.net.HTTPServer;

public class NewsActivityAdapter extends BaseAdapter {
	
	private List<News> data;
	private int listviewitem;
	private File cache;
	LayoutInflater layoutInflater;

	public NewsActivityAdapter(Context context, List<News> data, int listviewitem,
			File cache) {
		layoutInflater = LayoutInflater.from(context);
		this.data = data;
		this.listviewitem = listviewitem;
		this.cache = cache;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView pic = null;
		TextView title = null;
		;
		TextView content = null;
		TextView time = null;
		// 缓存
		if (convertView == null) {
			convertView = layoutInflater.inflate(listviewitem, null);

			pic = (ImageView) convertView.findViewById(R.id.ItemImage);
			title = (TextView) convertView.findViewById(R.id.ItemTitle);
			content = (TextView) convertView.findViewById(R.id.ItemContent);
			time = (TextView) convertView.findViewById(R.id.ItemTime);
			convertView.setTag(new DataWrapper(pic, title, content, time));

		} else {
			DataWrapper dataWrappe = (DataWrapper) convertView.getTag();
			pic = dataWrappe.pic;
			title = dataWrappe.title;
			content = dataWrappe.content;
			time = dataWrappe.time;
		}

		News news = data.get(position);
		title.setText(news.getTitle());
		content.setText(news.getContent());
		time.setText(news.getTime());
		asynsImageLoad(pic, news.getPicpath(),news.getId());// 异步加载图片
		return convertView;
	}

	private void asynsImageLoad(final ImageView imageview, final String path,String id) {
		AsyncImageTask asyncImageTask = new AsyncImageTask(imageview);
		asyncImageTask.execute(path,id);

	}

	// 异步类
	@SuppressLint("NewApi")
	private final class AsyncImageTask extends AsyncTask<String, Integer, Uri> {
		private ImageView imageview;

		public AsyncImageTask(ImageView imageview) {
			this.imageview = imageview;
		}

		// 在子线程内运行
		@Override
		protected Uri doInBackground(String...  params) {
			return HTTPServer.getBufferImage(params[0], cache,params[1]);
		}

		// 回调函数， 运行在UI主线程中
		@Override
		protected void onPostExecute(Uri result) {
			Log.e("newsAdapter","Uri："+result);
			if (result != null&&imageview != null) {
				Log.e("newsAdapter","Uri result："+result);
				imageview.setImageURI(result);
			} else {
				Log.e("newsAdapter","Uri result为空");
				imageview.setImageResource(0);
			}

		}
	}

	private final class DataWrapper {
		public ImageView pic;
		public TextView title;
		public TextView content;
		public TextView time;

		public DataWrapper(ImageView pic, TextView title, TextView content,
				TextView time) {
			this.pic = pic;
			this.title = title;
			this.content = content;
			this.time = time;
		}
	}

}
