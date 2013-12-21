package edu.ahjzu.app.notice.pojo;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;

/**
 * 
 * @author Administrator
 * 
 */
public class Usr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String truename;
	public String name;
	public String icon;
	public String sex;// 男/女
	public int age;
	public int mode;// 1在线/0不在线
	public String status;
	public String place;
	public double latitude;
	public double longitiude;
	public String addr;
	public String ip;
	public String college;
	public String specialty;
	public int qq;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitiude() {
		return longitiude;
	}

	public void setLongitiude(double longitiude) {
		this.longitiude = longitiude;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public int getQq() {
		return qq;
	}

	public void setQq(int qq) {
		this.qq = qq;
	}

	@Override
	public String toString() {
		return "id:" + getId() + "\n name:" + getName() + "\n icon" + getIcon();
	}

	public String toJson() {
		JSONObject object = new JSONObject();
		try {
			object.put("id", getId());
			object.put("name", getName());
			object.put("truename", getTruename());
			object.put("icon", getIcon());
			object.put("sex", getSex());
			object.put("age", getAge());
			object.put("mode", getMode());
			object.put("status", getStatus());
			object.put("place", getPlace());
			object.put("latitude", getLatitude());
			object.put("longitiude", getLongitiude());
			object.put("addr", getAddr());
			object.put("ip", getIp());
			object.put("college", getCollege());
			object.put("specialty", getSpecialty());
			object.put("qq", getQq());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object.toString();
	}

	public static Usr fromJson(String jsonString) {
		Usr usr = null;
		try {
			usr = new Usr();
			JSONObject object = new JSONObject(jsonString);
			usr.setId(object.getInt("id"));
			usr.setName(object.getString("name"));
			usr.setTruename(object.getString("truename"));
			usr.setIcon(object.getString("icon"));
			usr.setSex(object.getString("sex"));
			usr.setAge(object.getInt("age"));
			usr.setMode(object.getInt("mode"));
			usr.setStatus(object.getString("status"));
			usr.setPlace(object.getString("place"));
			usr.setLatitude(object.getDouble("latitude"));
			usr.setLongitiude(object.getDouble("longitiude"));
			usr.setAddr(object.getString("addr"));
			usr.setIp(object.getString("ip"));
			usr.setCollege(object.getString("college"));
			usr.setQq(object.getInt("qq"));
			usr.setSpecialty(object.getString("specialty"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return usr;

	}
}
