package org.eclipse.jgit.util;

import java.text.MessageFormat;
import java.util.Date;

import org.eclipse.jgit.JGitText;

public class RelativeDateFormatter {

	@SuppressWarnings("boxing")
	public static String format(Date when) {
		long age = (System.currentTimeMillis() - when.getTime()) / 1000;

		if (age < 0)
			return JGitText.get().inTheFuture;

		if (age < 90)
			return MessageFormat.format(JGitText.get().secondsAgo

		age = (age + 30) / 60;
		if (age < 90)
			return MessageFormat.format(JGitText.get().minutesAgo

		age = (age + 30) / 60;
		if (age < 36)
			return MessageFormat.format(JGitText.get().hoursAgo

		age = (age + 12) / 24;
		if (age < 14)
			return MessageFormat.format(JGitText.get().daysAgo

		if (age < 70)
			return MessageFormat.format(JGitText.get().weeksAgo

		if (age < 365)
			return MessageFormat.format(JGitText.get().monthsAgo
					(age + 15) / 30);

		if (age < 1825) {
			long years = age / 365;
					JGitText.get().year;
			long months = (age % 365 + 15) / 30;
					JGitText.get().month;
			return MessageFormat.format(JGitText.get().yearsMonthsAgo
					new Object[] { years
		}

		return MessageFormat.format(JGitText.get().yearsAgo
	}
}
