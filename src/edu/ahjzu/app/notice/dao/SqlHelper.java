package edu.ahjzu.app.notice.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelper extends SQLiteOpenHelper {

	public SqlHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建用户表
		db.execSQL("create table if not exists usr(usr_id integer primary key ,me integer NOT NULL,truename varchar(20),name varchar(20),icon varchar(512),sex integer,age integer,mode integer,status varchar(512),place varchar(128),latitude double,longitiude double,addr varchar(128),ip varchar(16),college varchar(128),specialty varchar(128),qq integer);");
		// 创建聊天内容缓存表
		db.execSQL("create table if not exists ChatContent(_id integer primary key,fromid varchar(20),toid  varchar(20),content varchar(512),time varchar(20));");
		// 创建最近聊天用户表
		db.execSQL("create table if not exists RecentUsr(usr_id integer primary key,time varchar(20) NOT NULL);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public static SqlHelper instanceSqlHelper(Context context) {
		SqlHelper helper = new SqlHelper(context, "app.db", null, 1);
		return helper;
	}
}
