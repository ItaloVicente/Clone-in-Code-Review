package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.eclipse.jgit.util.RelativeDateFormatter;
import org.junit.Test;

public class RelativeDateFormatterTest {
	final int SECOND = 1000;

	final int MINUTE = 60 * SECOND;

	final int HOUR = 60 * MINUTE;

	final long DAY = 24 * HOUR;

	final long MONTH = 30 * DAY;

	final long YEAR = 365 * DAY;

	private void assertFormat(long ageFromNow
			String expectedFormat) {
		Date d = new Date(System.currentTimeMillis() - ageFromNow * timeUnit);
		String s = RelativeDateFormatter.format(d);
		assertEquals(expectedFormat
	}

	@Test
	public void testFuture() {
		assertFormat(-100
		assertFormat(-1
	}

	@Test
	public void testFormatSeconds() {
		assertFormat(1
		assertFormat(89
	}

	@Test
	public void testFormatMinutes() {
		assertFormat(90
		assertFormat(3
		assertFormat(60
		assertFormat(89
	}

	@Test
	public void testFormatHours() {
		assertFormat(90
		assertFormat(149
		assertFormat(35
	}

	@Test
	public void testFormatDays() {
		assertFormat(36
		assertFormat(13
	}

	@Test
	public void testFormatWeeks() {
		assertFormat(14
		assertFormat(69
	}

	@Test
	public void testFormatMonths() {
		assertFormat(70
		assertFormat(75
		assertFormat(364
	}

	@Test
	public void testFormatYearsMonths() {
		assertFormat(366
		assertFormat(380
		assertFormat(410
		assertFormat(2
		assertFormat(1824
	}

	@Test
	public void testFormatYears() {
		assertFormat(5
		assertFormat(60
	}
}
