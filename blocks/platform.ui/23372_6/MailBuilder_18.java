package org.eclipse.e4.demo.cssbridge.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
	private static final String UNKNOWN_DATE = "unknown";

	private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm", Locale.ENGLISH);

	public static Date parse(String dateAsString) {
		try {
			return DATE_FORMATTER.parse(dateAsString);
		} catch (ParseException exc) {
			return null;
		}
	}

	public static String toString(Date date) {
		return date == null ? UNKNOWN_DATE : DATE_FORMATTER.format(date);
	}
}
