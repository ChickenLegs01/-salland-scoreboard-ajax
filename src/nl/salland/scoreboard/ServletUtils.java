package nl.salland.scoreboard;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ServletUtils {

	static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	
	public static String getCurrentDate() {
		return DATE_FORMAT.format(new Date());
	}
	
	public static String getCurrentTimeStamp() {
		return TIME_FORMAT.format(new Date());
	}
}
