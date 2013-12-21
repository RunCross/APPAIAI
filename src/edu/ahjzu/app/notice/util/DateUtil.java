package edu.ahjzu.app.notice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	/**
	 * 得到当前日期和时间
	 * 
	 * @return
	 */
	public static String getDate() {
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");   
		// String a2=dateformat.format(new Date());
		// Calendar c = Calendar.getInstance();
		// String year = String.valueOf(c.get(Calendar.YEAR));
		// String month = String.valueOf(c.get(Calendar.MONTH));
		// String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		// String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		// String mins = String.valueOf(c.get(Calendar.MINUTE));
		// StringBuffer sbBuffer = new StringBuffer();
		// sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
		// + mins);

		return dateformat.format(new Date());
	}
}
