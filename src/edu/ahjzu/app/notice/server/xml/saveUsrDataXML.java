package edu.ahjzu.app.notice.server.xml;

public class saveUsrDataXML {
	private final String LOGTAG = "saveUsrDataXML";

	// public void saveSimpleUsr(UsrPro usr, File file) {
	// ArrayList<UsrPro> usrs = new ArrayList<UsrPro>();
	// usrs.add(usr);
	// saveUsrs(usrs, file);
	// }
	//
	// public void saveUsrs(ArrayList<UsrPro> usrs, File file) {
	// StringBuilder sb = new StringBuilder(
	// "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	// sb.append("<usrs>").append("\n");
	// for (UsrPro usr : usrs) {
	// sb.append("<usr>").append("<id>").append(usr.getId())
	// .append("</id>").append("<name>").append(usr.getName())
	// .append("</name>").append("<icon>").append(usr.getIcon())
	// .append("</icon>").append("<sex>").append(usr.getSex())
	// .append("</sex>").append("<age>").append(usr.getAge())
	// .append("</age>").append("<status>")
	// .append(usr.getStatus()).append("</status>")
	// .append("<place>").append(usr.getPlace())
	// .append("</place>").append("<latitude>")
	// .append(usr.getLatitude()).append("</latitude>")
	// .append("<longitiude>").append(usr.getLongitiude())
	// .append("</longitiude>").append("<addr>")
	// .append(usr.getAddr()).append("</addr>").append("<ip>")
	// .append(usr.getIp()).append("</ip>").append("<truename>")
	// .append(usr.getTruename()).append("</truename>")
	// .append("<college>").append(usr.getCollege())
	// .append("</college>").append("<specialty>")
	// .append(usr.getSpecialty()).append("</specialty>")
	// .append("<qq>").append(usr.getQq()).append("</qq>")
	// .append("</usr>");
	// }
	// sb.append("</usrs>").append("\n");
	// try {
	// BufferedOutputStream bos = new BufferedOutputStream(
	// new FileOutputStream(file));
	// bos.write(sb.toString().getBytes());
	// bos.flush();
	// bos.close();
	// } catch (FileNotFoundException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IOException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	/*
	 * //public void save(ArrayList<usrData> usrs) { StringBuilder sb = new
	 * StringBuilder( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	 * sb.append("<usrs>").append("\n"); for (usrData usr : usrs) {
	 * sb.append("<usr>").append("<id>").append(usr.getId())
	 * .append("</id>").append("<name>").append(usr.getName())
	 * .append("</name>").append("<icon>").append(usr.getIcon())
	 * .append("</icon>").append("<sex>").append(usr.getSex())
	 * .append("</sex>").append("<age>").append(usr.getAge())
	 * .append("</age>").append("<status>")
	 * .append(usr.getStatus()).append("</status>")
	 * .append("<place>").append(usr.getPlace())
	 * .append("</place>").append("<latitude>")
	 * .append(usr.getLatitude()).append("</latitude>")
	 * .append("<longitiude>").append(usr.getLongitiude())
	 * .append("</longitiude>").append("<addr>")
	 * .append(usr.getAddr()).append("</addr>").append("<ip>")
	 * .append(usr.getIp()).append("</ip>").append("</usr>"); }
	 * sb.append("</usrs>").append("\n");
	 * System.out.println("chatingList的cache:" + sb.toString());
	 * wirte(sb.toString()); }
	 * 
	 * private void wirte(String str) { File cachePath = new
	 * File(Environment.getExternalStorageDirectory(),
	 * OverallData.chatingUsrList); System.out .println("chtinglistUsr列表保存路径：" +
	 * cachePath.getAbsolutePath()); if (!cachePath.exists()) {
	 * cachePath.mkdirs(); } File Path = new File(cachePath, "chatingUsr.xml");
	 * if (!Path.exists()) { try { Path.createNewFile(); } catch (IOException e)
	 * { // TODO Auto-generated catch block e.printStackTrace(); } }
	 * BufferedWriter bw = null; try { bw = new BufferedWriter(new
	 * OutputStreamWriter( new FileOutputStream(Path))); } catch
	 * (FileNotFoundException e) { // TODO Auto-generated catch block
	 * System.out.println(e.getMessage()); } try { bw.write(str); bw.close(); }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * System.out.println(e.getMessage()); } Log.v(LOGTAG,
	 * "chatingUsrList保存成功！"); }
	 */
}
/*
 * <usrs> <usr> <id>2</id> <name>hua</name> <icon>usricon/001.jpg</icon>
 * <sex>2</sex> <age>22</age> <mode>1</mode> <status>ffff</status>
 * <place>adf</place> <latitude>162.33</latitude>
 * <longitiude>777555.33</longitiude> <addr>erwe</addr> <ip>192.168.1.101</ip>
 * </usr> </usrs>
 */