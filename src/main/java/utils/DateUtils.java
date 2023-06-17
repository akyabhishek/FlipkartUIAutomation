package utils;

import java.util.Date;

public class DateUtils {
	// returns the timestamp
	public static String getTimeStamp() {
		Date date=new Date();
		return date.toString().replaceAll(":", "_").replaceAll(" ","_");
	}



}
