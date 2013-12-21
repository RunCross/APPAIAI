package edu.ahjzu.app.notice.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tencent.open.cgireport.reportItem;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import edu.ahjzu.app.notice.pojo.Usr;

public class UsrDao {
	private SqlHelper helper = null;

	public UsrDao(Context context) {
		helper = SqlHelper.instanceSqlHelper(context);
	}

	/**
	 * 保存用户
	 * 
	 * @param usrList
	 * @return
	 */
	public boolean addUsr(Usr usr) {
		ContentValues values = new ContentValues();
		values.put("usr_id", usr.getId());
		values.put("me", 1);
		values.put("name", usr.getName());
		values.put("truename", usr.getTruename());
		values.put("sex", usr.getSex());
		values.put("age", usr.getAge());
		values.put("icon", usr.getIcon());
		values.put("college", usr.getCollege());
		values.put("specialty", usr.getSpecialty());
		values.put("status", usr.getStatus());
		values.put("mode", usr.getMode());
		values.put("place", usr.getPlace());
		values.put("latitude", usr.getLatitude());
		values.put("longitiude", usr.getLongitiude());
		values.put("addr", usr.getAddr());
		values.put("ip", usr.getIp());
		values.put("qq", usr.getQq());

		long id = helper.getWritableDatabase().insert("Usr", null, values);
		if (id != -1) {
			return true;
		}
		return true;
	}

	public boolean addMeInfo(Usr usr) {
		ContentValues values = new ContentValues();
		values.put("usr_id", usr.getId());
		values.put("me", 0);
		values.put("name", usr.getName());
		values.put("truename", usr.getTruename());
		values.put("sex", usr.getSex());
		values.put("age", usr.getAge());
		values.put("icon", usr.getIcon());
		values.put("college", usr.getCollege());
		values.put("specialty", usr.getSpecialty());
		values.put("status", usr.getStatus());
		values.put("mode", usr.getMode());
		values.put("place", usr.getPlace());
		values.put("latitude", usr.getLatitude());
		values.put("longitiude", usr.getLongitiude());
		values.put("addr", usr.getAddr());
		values.put("ip", usr.getIp());
		values.put("qq", usr.getQq());
		if (isMeSaved()) {
			int row = helper.getWritableDatabase().update("Usr", values,
					"usr_id", new String[] { usr.getId() + "" });
			if (row > 0) {
				return true;
			}
		} else {
			long id = helper.getWritableDatabase().insert("Usr", null, values);
			if (id != -1) {
				return true;
			}
		}
		return false;
	}

	public boolean addUsr(List<Usr> usrs) {
		// 清空数据
		// helper.getWritableDatabase().delete("usr", "me = 1 ", null);
		helper.getWritableDatabase().delete("usr", null, null);
		// 添加用户
		for (Usr usr : usrs) {
			addUsr(usr);
		}
		return true;
	}

	/**
	 * 得到用户
	 * 
	 * @param usr_id
	 * @return
	 */
	public Usr getUsr(int usr_id) {
		Usr usr = null;
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select * from Usr where usr_id = ?",
				new String[] { usr_id + "" });
		if (cursor.moveToNext()) {
			usr = new Usr();
			usr.setId(cursor.getInt(cursor.getColumnIndex("usr_id")));
			usr.setName(cursor.getString(cursor.getColumnIndex("name")));
			usr.setTruename(cursor.getString(cursor.getColumnIndex("truename")));
			usr.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
			usr.setAge(cursor.getInt(cursor.getColumnIndex("age")));
			usr.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			usr.setStatus(cursor.getString(cursor.getColumnIndex("status")));
			usr.setCollege(cursor.getString(cursor.getColumnIndex("college")));
			usr.setSpecialty(cursor.getString(cursor
					.getColumnIndex("specialty")));
			usr.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
			usr.setLongitiude(cursor.getDouble(cursor
					.getColumnIndex("longitiude")));
			usr.setMode(cursor.getInt(cursor.getColumnIndex("mode")));
			usr.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
			usr.setIp(cursor.getString(cursor.getColumnIndex("ip")));
			usr.setPlace(cursor.getString(cursor.getColumnIndex("place")));
			usr.setQq(cursor.getInt(cursor.getColumnIndex("qq")));

		}
		return usr;
	}

	public List<Usr> getOnLineUsrs() {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select * from Usr where me = 1 ", null);
		return getUsrs(cursor);
	}

	public List<Usr> getAllUsr() {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select * from Usr", null);
		return getUsrs(cursor);
	}

	private List<Usr> getUsrs(Cursor cursor) {
		List<Usr> list = new ArrayList<Usr>();
		while (cursor.moveToNext()) {
			Usr usr = new Usr();
			usr.setId(cursor.getInt(cursor.getColumnIndex("usr_id")));
			usr.setName(cursor.getString(cursor.getColumnIndex("name")));
			usr.setTruename(cursor.getString(cursor.getColumnIndex("truename")));
			usr.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
			usr.setAge(cursor.getInt(cursor.getColumnIndex("age")));
			usr.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			usr.setStatus(cursor.getString(cursor.getColumnIndex("status")));
			usr.setCollege(cursor.getString(cursor.getColumnIndex("college")));
			usr.setSpecialty(cursor.getString(cursor
					.getColumnIndex("specialty")));
			usr.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
			usr.setLongitiude(cursor.getDouble(cursor
					.getColumnIndex("longitiude")));
			usr.setMode(cursor.getInt(cursor.getColumnIndex("mode")));
			usr.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
			usr.setIp(cursor.getString(cursor.getColumnIndex("ip")));
			usr.setPlace(cursor.getString(cursor.getColumnIndex("place")));
			usr.setQq(cursor.getInt(cursor.getColumnIndex("qq")));
			list.add(usr);
		}
		return list;
	}

	public List<Integer> getAllRecentUsr() {
		List<Integer> list = new ArrayList<Integer>();
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select usr_id from RecentUsr ORDER BY time DESC", null);
		while (cursor.moveToNext()) {
			list.add(cursor.getInt(cursor.getColumnIndex("usr_id")));
		}
		return list;
	}

	private boolean isMeSaved() {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select usr_id from Usr where me = 0", null);
		if (cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 得到自己的信息
	 * 
	 * @return
	 */
	public Usr getMeInfo() {
		Usr usr = null;
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select * from Usr where me = 0", null);
		if (cursor.moveToLast()) {
			usr = new Usr();
			usr.setId(cursor.getInt(cursor.getColumnIndex("usr_id")));
			usr.setName(cursor.getString(cursor.getColumnIndex("name")));
			usr.setTruename(cursor.getString(cursor.getColumnIndex("truename")));
			usr.setIcon(cursor.getString(cursor.getColumnIndex("icon")));
			usr.setAge(cursor.getInt(cursor.getColumnIndex("age")));
			usr.setSex(cursor.getString(cursor.getColumnIndex("sex")));
			usr.setStatus(cursor.getString(cursor.getColumnIndex("status")));
			usr.setCollege(cursor.getString(cursor.getColumnIndex("college")));
			usr.setSpecialty(cursor.getString(cursor
					.getColumnIndex("specialty")));
			usr.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
			usr.setLongitiude(cursor.getDouble(cursor
					.getColumnIndex("longitiude")));
			usr.setMode(cursor.getInt(cursor.getColumnIndex("mode")));
			usr.setAddr(cursor.getString(cursor.getColumnIndex("addr")));
			usr.setIp(cursor.getString(cursor.getColumnIndex("ip")));
			usr.setPlace(cursor.getString(cursor.getColumnIndex("place")));
			usr.setQq(cursor.getInt(cursor.getColumnIndex("qq")));
		}
		return usr;
	}

	/**
	 * 删除最近聊天用户
	 * 
	 * @param usr
	 * @return
	 */
	public boolean deleteRecentUsr(int id) {
		int i = helper.getWritableDatabase().delete("RecentUsr", "usr_id = ?",
				new String[] { id + "" });
		if (i > 0) {
			return true;
		}
		return false;

	}

	/**
	 * 清空最近聊天用户表
	 * 
	 * @return
	 */
	public boolean clearRecentUsr() {
		helper.getWritableDatabase().execSQL("DELETE * FROM RecentUsr");
		return true;

	}

	/**
	 * 添加用户到最近聊天用户表
	 * 
	 * @param usr
	 * @return
	 */
	public boolean addRecentUsr(int usr_id) {
		// 如果最近联系人表中存在就删除
		if (isRecentSaved(usr_id)) {
			ContentValues values = new ContentValues();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			values.put("time", dateformat.format(new Date()));
			int row = helper.getWritableDatabase().update("RecentUsr", values,
					"usr_id = ?", new String[] { usr_id + "" });
			if (row > 0) {
				return true;
			}
		} else {
			ContentValues values = new ContentValues();
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			values.put("usr_id", usr_id);
			values.put("time", dateformat.format(new Date()));
			long id = helper.getWritableDatabase().insert("RecentUsr", null,
					values);
			if (id != -1) {
				return true;
			}
		}
		return false;
	}

	public boolean isRecentSaved(int id) {
		Cursor cursor = helper.getReadableDatabase().rawQuery(
				"select usr_id from RecentUsr where usr_id = ?",
				new String[] { id + "" });
		if (cursor.getCount() > 0) {
			return true;
		} else {
			return false;
		}

	}
	// public boolean updateUsr(List<Usr> usrs) {
	// for (Usr usr : usrs) {
	// ContentValues values = new ContentValues();
	// values.put("", value);
	// SqlHelper
	// .getSqlHelper()
	// .getWritableDatabase()
	// .update("RecentUsr", values, "usr_id = ?",
	// new String[] { usr.getId() + "" });
	// }
	// return false;
	// }
}
