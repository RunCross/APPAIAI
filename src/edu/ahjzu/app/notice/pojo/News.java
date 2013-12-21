package edu.ahjzu.app.notice.pojo;

public class News {
	public News(String id, String title, String content, String picpath,
			String url, String time) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.picpath = picpath;
		this.url = url;
		this.time = time;
	}

	public News() {
	}

	private String id = "";
	private String title = "";
	private String content = "";
	private String picpath = "";
	private String url = "";
	private String time = "";

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;

	}

	public String getPicpath() {
		return picpath;
	}

	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
