package org.eclipse.jgit.util;

import java.text.MessageFormat;
import java.util.Date;

import org.eclipse.jgit.JGitText;

public class RelativeDateFormatter {
	private final static long SECOND_IN_MILLIS = 1000;

	private final static long MINUTE_IN_MILLIS = 60 * SECOND_IN_MILLIS;

	private final static long HOUR_IN_MILLIS = 60 * MINUTE_IN_MILLIS;

	private final static long DAY_IN_MILLIS = 24 * HOUR_IN_MILLIS;

	private final static long WEEK_IN_MILLIS = 7 * DAY_IN_MILLIS;

	private final static long MONTH_IN_MILLIS = 30 * DAY_IN_MILLIS;

	private final static long YEAR_IN_MILLIS = 365 * DAY_IN_MILLIS;

	@SuppressWarnings("boxing")
	public static String format(Date when) {

		long ageMillis = (System.currentTimeMillis() - when.getTime());

		if (ageMillis < 0)
			return JGitText.get().inTheFuture;

		if (ageMillis < upperLimit(MINUTE_IN_MILLIS))
			return MessageFormat.format(JGitText.get().secondsAgo
					round(ageMillis

		if (ageMillis < upperLimit(HOUR_IN_MILLIS))
			return MessageFormat.format(JGitText.get().minutesAgo
					round(ageMillis

		if (ageMillis < upperLimit(DAY_IN_MILLIS))
			return MessageFormat.format(JGitText.get().hoursAgo
					round(ageMillis

		if (ageMillis < 14 * DAY_IN_MILLIS)
			return MessageFormat.format(JGitText.get().daysAgo
					round(ageMillis

		if (ageMillis < 10 * WEEK_IN_MILLIS)
			return MessageFormat.format(JGitText.get().weeksAgo
					round(ageMillis

		if (ageMillis < YEAR_IN_MILLIS)
			return MessageFormat.format(JGitText.get().monthsAgo
					round(ageMillis

		if (ageMillis < 5 * YEAR_IN_MILLIS) {
			long years = ageMillis / YEAR_IN_MILLIS;
					JGitText.get().year;
			long months = round(ageMillis % YEAR_IN_MILLIS
					JGitText.get().month;
			return MessageFormat.format(JGitText.get().yearsMonthsAgo
					new Object[] { years
		}

		return MessageFormat.format(JGitText.get().yearsAgo
				round(ageMillis
	}

	private static long upperLimit(long unit) {
		long limit = unit + unit / 2;
		return limit;
	}

	private static long round(long n
		long rounded = (n + unit / 2) / unit;
		return rounded;
	}
}
