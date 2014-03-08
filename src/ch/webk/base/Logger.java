package ch.webk.base;

import android.util.Log;

public class Logger {

	public static final boolean logEnabled = true;
	public static final String prefix = "WEBK | ";
	
	public static void i(String tag, String msg) {
		if (logEnabled) {
			Log.i(prefix+tag, msg);
		}	
	}
	
	public static void e(String tag, String msg) {
		if (logEnabled) {
			Log.e(tag, msg);
		}	
	}
	
}