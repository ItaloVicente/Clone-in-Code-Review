package org.eclipse.jgit.util;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.eclipse.jgit.internal.JGitText;

public class GitDateParser {
	public static final Date NEVER = new Date(Long.MAX_VALUE);

	private static ThreadLocal<Map<Locale
			new ThreadLocal<Map<Locale

		@Override
		protected Map<Locale
			return new HashMap<>();
		}
	};

	private static SimpleDateFormat getDateFormat(ParseableSimpleDateFormat f
			Locale locale) {
		Map<Locale
				.get();
		Map<ParseableSimpleDateFormat
				.get(locale);
		if (map == null) {
			map = new HashMap<>();
			cache.put(locale
			return getNewSimpleDateFormat(f
		}
		SimpleDateFormat dateFormat = map.get(f);
		if (dateFormat != null)
			return dateFormat;
		SimpleDateFormat df = getNewSimpleDateFormat(f
		return df;
	}

	private static SimpleDateFormat getNewSimpleDateFormat(
			ParseableSimpleDateFormat f
			Map<ParseableSimpleDateFormat
		SimpleDateFormat df = SystemReader.getInstance().getSimpleDateFormat(
				f.formatStr
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

		String formatStr;

		private ParseableSimpleDateFormat(String formatStr) {
			this.formatStr = formatStr;
		}
	}

	public static Date parse(String dateStr
			throws ParseException {
		return parse(dateStr
	}

	public static Date parse(String dateStr
			throws ParseException {
		dateStr = dateStr.trim();
		Date ret;

			return NEVER;
		ret = parse_relative(dateStr
		if (ret != null)
			return ret;
		for (ParseableSimpleDateFormat f : ParseableSimpleDateFormat.values()) {
			try {
				return parse_simple(dateStr
			} catch (ParseException e) {
			}
		}
		ParseableSimpleDateFormat[] values = ParseableSimpleDateFormat.values();
				.append(values[0].formatStr);
		for (int i = 1; i < values.length; i++)
			allFormats.append("\"
		throw new ParseException(MessageFormat.format(
				JGitText.get().cannotParseDate
	}

	private static Date parse_simple(String dateStr
			ParseableSimpleDateFormat f
			throws ParseException {
		SimpleDateFormat dateFormat = getDateFormat(f
		dateFormat.setLenient(false);
		return dateFormat.parse(dateStr);
	}

	private static Date parse_relative(String dateStr
		Calendar cal;
		SystemReader sysRead = SystemReader.getInstance();

			return ((now == null) ? new Date(sysRead.getCurrentTime()) : now
					.getTime());
		}

		if (now == null) {
			cal = new GregorianCalendar(sysRead.getTimeZone()
					sysRead.getLocale());
			cal.setTimeInMillis(sysRead.getCurrentTime());
		} else
			cal = (Calendar) now.clone();

			cal.add(Calendar.DATE
			cal.set(Calendar.HOUR_OF_DAY
			cal.set(Calendar.MINUTE
			cal.set(Calendar.SECOND
			cal.set(Calendar.MILLISECOND
			cal.set(Calendar.MILLISECOND
			return cal.getTime();
		}

		int partsLength = parts.length;
		if (partsLength < 3 || (partsLength & 1) == 0
			return null;
		int number;
		for (int i = 0; i < parts.length - 2; i += 2) {
			try {
				number = Integer.parseInt(parts[i]);
			} catch (NumberFormatException e) {
				return null;
			}
			if (null == parts[i + 1]) 
                            return null;
			else switch (parts[i + 1]) {
                        case "year":
                        case "years":
                            cal.add(Calendar.YEAR
                            break;
                        case "month":
                        case "months":
                            cal.add(Calendar.MONTH
                            break;
                        case "week":
                        case "weeks":
                            cal.add(Calendar.WEEK_OF_YEAR
                            break;
                        case "day":
                        case "days":
                            cal.add(Calendar.DATE
                            break;
                        case "hour":
                        case "hours":
                            cal.add(Calendar.HOUR_OF_DAY
                            break;
                        case "minute":
                        case "minutes":
                            cal.add(Calendar.MINUTE
                            break;
                        case "second":
                        case "seconds":
                            cal.add(Calendar.SECOND
                            break;
                        default:
                            return null;
                    }
		}
		return cal.getTime();
	}
}
