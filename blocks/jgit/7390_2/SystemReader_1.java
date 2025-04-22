package org.eclipse.jgit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class GitDateParser {
	private static ThreadLocal<Map<ParseableSimpleDateFormat
		protected Map<ParseableSimpleDateFormat
			return new HashMap<ParseableSimpleDateFormat
		}
	};

	private static SimpleDateFormat getDateFormat(ParseableSimpleDateFormat f) {
		Map<ParseableSimpleDateFormat
				.get();
		SimpleDateFormat dateFormat = map.get(f);
		if (dateFormat != null)
			return dateFormat;
		SimpleDateFormat df = SystemReader.getInstance().getSimpleDateFormat(
				f.formatStr);
		map.put(f
		return df;
	}

	enum ParseableSimpleDateFormat {
		ISO("yyyy-MM-dd HH:mm:ss Z")
		RFC("EEE
		SHORT("yyyy-MM-dd")
		SHORT_WITH_DOTS_REVERSE("dd.MM.yyyy")
		SHORT_WITH_DOTS("yyyy.MM.dd")
		SHORT_WITH_SLASH("MM/dd/yyyy")
		DEFAULT("EEE MMM dd HH:mm:ss yyyy Z")
		LOCAL("EEE MMM dd HH:mm:ss yyyy");

		String formatStr;

		private ParseableSimpleDateFormat(String formatStr) {
			this.formatStr = formatStr;
		}
	}

	public static Date parse(String dateStr
		dateStr = dateStr.trim();
		Date ret;
		ret = parse_relative(dateStr
		if (ret != null)
			return ret;
		for (ParseableSimpleDateFormat f : ParseableSimpleDateFormat.values()) {
			ret = parse_simple(dateStr
			if (ret != null)
				return ret;
		}
		return null;
	}

	private static Date parse_simple(String dateStr
		SimpleDateFormat dateFormat = getDateFormat(f);
		try {
			dateFormat.setLenient(false);
			return dateFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	private static Date parse_relative(String dateStr
		GregorianCalendar cal;
		SystemReader sysRead = SystemReader.getInstance();

		if ("now".equals(dateStr)) {
			return ((now == null) ? new Date(sysRead.getCurrentTime()) : now);
		}

		if ("yesterday".equals(dateStr)) {
			cal = new GregorianCalendar(sysRead.getTimeZone()
					sysRead.getLocale());
			cal.setTimeInMillis(now == null ? sysRead.getCurrentTime() : now
					.getTime());
			cal.add(Calendar.DATE
			cal.set(Calendar.HOUR_OF_DAY
			cal.set(Calendar.MINUTE
			cal.set(Calendar.SECOND
			cal.set(Calendar.MILLISECOND
			cal.set(Calendar.MILLISECOND
			return cal.getTime();
		}

		String[] parts = dateStr.split("\\.| ");
		int partsLength = parts.length;
		if (partsLength < 3 || (partsLength & 1) == 0
				|| !"ago".equals(parts[parts.length - 1]))
			return null;
		cal = new GregorianCalendar(sysRead.getTimeZone()
		cal.setTime(now == null ? new Date(sysRead.getCurrentTime()) : now);
		int number;
		for (int i = 0; i < parts.length - 2; i += 2) {
			try {
				number = Integer.parseInt(parts[i]);
			} catch (NumberFormatException e) {
				return null;
			}
			if ("year".equals(parts[i + 1]) || "years".equals(parts[i + 1]))
				cal.add(Calendar.YEAR
			else if ("month".equals(parts[i + 1])
					|| "months".equals(parts[i + 1]))
				cal.add(Calendar.MONTH
			else if ("week".equals(parts[i + 1])
					|| "weeks".equals(parts[i + 1]))
				cal.add(Calendar.WEEK_OF_YEAR
			else if ("day".equals(parts[i + 1]) || "days".equals(parts[i + 1]))
				cal.add(Calendar.DATE
			else if ("hour".equals(parts[i + 1])
					|| "hours".equals(parts[i + 1]))
				cal.add(Calendar.HOUR_OF_DAY
			else if ("minute".equals(parts[i + 1])
					|| "minutes".equals(parts[i + 1]))
				cal.add(Calendar.MINUTE
			else if ("second".equals(parts[i + 1])
					|| "seconds".equals(parts[i + 1]))
				cal.add(Calendar.SECOND
			else
				return null;
		}
		return cal.getTime();
	}
}
