package edu.ahjzu.app.notice.pojo;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatContent {
	public String fromId;
	public String toId;
	public String content;
	public String time;

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String toJson() {
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("toId", getToId());
			jsonObject.put("fromId", getFromId());
			jsonObject.put("content", getContent());
			jsonObject.put("time", getTime());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject.toString();

	}

	public static ChatContent fromJson(String jsonString) {
		ChatContent chatContent = new ChatContent();
		try {
			JSONObject object = new JSONObject(jsonString);
			chatContent.setToId(object.getString("toId"));
			chatContent.setFromId(object.getString("fromId"));
			chatContent.setContent(object.getString("content"));
			chatContent.setTime(object.getString("time"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return chatContent;
	}
}
