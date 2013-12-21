package edu.ahjzu.app.notice.view.adapter;

import java.io.File;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.APP.aiainotice.R;

import edu.ahjzu.app.notice.init.OverallData;
import edu.ahjzu.app.notice.pojo.ChatContent;
import edu.ahjzu.app.notice.pojo.Usr;
import edu.ahjzu.app.notice.util.ImageUtil;
import edu.ahjzu.app.notice.util.MD5;

public class chatingWindowAdapter extends BaseAdapter {

	private final String LOGTAG = "chatingWindowAdapter";

	private List<ChatContent> chatContents;

	private LayoutInflater mInflater;
	private Usr otherUsr;
	private Bitmap otherIconBitmap = null;
	private Bitmap meIconBitmap = null;
	private Usr ownUsr = null;

	public chatingWindowAdapter(Context context,
			List<ChatContent> chatContents, Usr ownUsr, Usr otherUsr) {
		this.chatContents = chatContents;
		this.otherUsr = otherUsr;
		this.ownUsr = ownUsr;
		mInflater = LayoutInflater.from(context);
		// 初始化用户头像图片缓存
		int minSideLength = 40;
		int maxNumOfPixels = 40;

		File cache = new File(Environment.getExternalStorageDirectory(),
				OverallData.USRICONPATH);
		// 对方头像
		if (otherUsr.getIcon() != null) {
			String imagepath = otherUsr.getIcon();
			File OtherIconFile = new File(cache, MD5.getMD5(imagepath)
					+ imagepath.substring(imagepath.lastIndexOf(".")));
			if (OtherIconFile.exists()) {
				otherIconBitmap = ImageUtil.getBitmap(
						OtherIconFile.getAbsolutePath(), minSideLength,
						maxNumOfPixels);
			}
		}
		// 自己头像
		cache = new File(Environment.getExternalStorageDirectory(),
				OverallData.ownDataPATH);
		String imagepath = ownUsr.getIcon();
		File MeIconFile = new File(cache, imagepath);
		if (MeIconFile.exists()) {
			meIconBitmap = ImageUtil.getBitmap(MeIconFile.getAbsolutePath(),
					minSideLength, maxNumOfPixels);
		}

	}

	public void setData(List<ChatContent> chatContents) {
		this.chatContents = chatContents;
	}

	@Override
	public int getCount() {
		return chatContents.size();
	}

	@Override
	public Object getItem(int position) {
		return chatContents.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ChatContent chatContent = chatContents.get(position);
		if (chatContent.getToId().equals(ownUsr.getId() + "")) {
			convertView = mInflater.inflate(
					R.layout.chatting_item_msg_text_left, null);
		} else {
			convertView = mInflater.inflate(
					R.layout.chatting_item_msg_text_right, null);
		}
		TextView tvUserName = (TextView) convertView
				.findViewById(R.id.tv_username);
		TextView tvContent = (TextView) convertView
				.findViewById(R.id.tv_chatcontent);
		ImageView iv_userhead = (ImageView) convertView
				.findViewById(R.id.iv_userhead);

		// viewHolder.tvSendTime.setText(entity.getTime());
		tvContent.setText(chatContent.getContent());
		// 加载头像图片
		if (chatContent.getToId().equals(ownUsr.getId() + "")) {
			if (otherIconBitmap != null) {
				iv_userhead.setImageBitmap(otherIconBitmap);
			} else {
				iv_userhead.setImageResource(R.drawable.ic_launcher);
			}
		} else {
			if (meIconBitmap != null) {
				iv_userhead.setImageBitmap(meIconBitmap);
			} else {
				iv_userhead.setImageResource(R.drawable.ic_launcher);
			}
		}

		return convertView;
	}

	// static class ViewHolder {
	// public TextView tvSendTime;
	// public TextView tvUserName;
	// public TextView tvContent;
	// public ImageView iv_userhead;
	// public String isComMsg;
	// }

}
