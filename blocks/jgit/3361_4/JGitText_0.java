package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.eclipse.jgit.util.RelativeDateFormatter.YEAR_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.SECOND_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.MINUTE_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.HOUR_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.DAY_IN_MILLIS;

import java.util.Date;

import org.eclipse.jgit.util.RelativeDateFormatter;
import org.junit.Test;

public class RelativeDateFormatterTest {

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
