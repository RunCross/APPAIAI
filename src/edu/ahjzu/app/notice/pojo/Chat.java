package edu.ahjzu.app.notice.pojo;

public class Chat {
	private String id = "";// id
	private double addr_Longitude = 0;// 经度
	private double addr_Latitude = 0;// 纬度
	private String addr = "";// 地址，可以为空
	private String content = "";// 信息内容
	private String picpath = "";// 图片地址，可以为空
	private int goodop = 0;// 好评（赞）数目
	private int badop = 0;// 差评数目

	public void setaddr_Longitude(double addr_Longitude) {
		this.addr_Longitude = addr_Longitude;
	}

	public void setaddr_Latitude(double addr_Latitude) {
		this.addr_Latitude = addr_Latitude;
	}

	public void setaddr(String addr) {
		this.addr = addr;
	}

	public void setcontent(String content) {
		this.content = content;
	}

	public void setpicpath(String picpath) {
		this.picpath = picpath;
	}

	public double getaddr_Longitude() {
		return addr_Longitude;
	}

	public double getaddr_Latitude() {
		return addr_Latitude;
	}

	public int getgoodop() {
		return goodop;
	}

	public int getbadop() {
		return badop;
	}

	public String getaddr() {
		return addr;
	}

	public String getcontent() {
		return content;
	}

	public String getpicpath() {
		return picpath;
	}

	public void setgoodop(int goodop) {
		this.goodop = goodop;
	}

	public void setbadop(int badop) {
		this.badop = badop;
	}

	public void setid(String id) {
		this.id = id;
	}

	public String getid() {
		return id;
	}
}
/*
 * <id></id> <addr_Longitude></addr_Longitude> <addr_Latitude></addr_Latitude>
 * <addr></addr> <content></content> <picpath></picpath> <goodop></goodop>
 * <badop></badop>
 */
