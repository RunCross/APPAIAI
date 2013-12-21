package edu.ahjzu.app.notice.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.ahjzu.app.notice.pojo.ChatContent;

public class ChatContentDao {
	private SqlHelper helper = null;

	public ChatContentDao(Context context) {
		helper = SqlHelper.instanceSqlHelper(context);
	}

	/**
	 * 添加聊天记录
	 * 
	 * @param contentList
	 * @return
	 */
	public boolean save(ChatContent content) {
		if (content != null) {
			ContentValues values = new ContentValues();
			values.put("fromid", content.getFromId());
			values.put("toid", content.getToId());
			values.put("content ", content.getContent());
			values.put("time ", content.getTime());

			long id = helper.getWritableDatabase().insert("ChatContent", null,
					values);
			if (id != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到聊天记录集
	 * 
	 * @param id
	 * @return
	 */
	public List<ChatContent> getContent(String otherId, String meId) {
		List<ChatContent> list = new ArrayList<ChatContent>();
		Cursor cursor = helper
				.getReadableDatabase()
				.rawQuery(
						"select * from ChatContent where (fromid = ? AND toid = ?)OR(fromid = ? AND toid = ?) ORDER BY time",
						new String[] { otherId, meId, meId, otherId });
		while (cursor.moveToNext()) {
			ChatContent chatContent = new ChatContent();
			chatContent
					.setToId(cursor.getString(cursor.getColumnIndex("toid")));
			chatContent.setFromId(cursor.getString(cursor
					.getColumnIndex("fromid")));
			chatContent.setContent(cursor.getString(cursor
					.getColumnIndex("content")));
			chatContent
					.setTime(cursor.getString(cursor.getColumnIndex("time")));
			list.add(chatContent);
		}
		System.out.println("content size:"+list.size());
		return list;
	}

	public boolean clear(String otherId, String meId) {
		helper.getWritableDatabase().delete("ChatContent", "fromid = ?",
				new String[] { otherId });
		helper.getWritableDatabase().delete("ChatContent", "toid = ?",
				new String[] { meId });
		return true;
	}

	/**
	 * 清空所有
	 * 
	 * @return
	 */
	public boolean clearAll() {
		helper.getWritableDatabase()
				.rawQuery("delete * from ChatContent", null);
		return true;
	}
}
