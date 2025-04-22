package org.eclipse.jgit.util;

import static org.eclipse.jgit.util.RelativeDateFormatter.DAY_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.HOUR_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.MINUTE_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.SECOND_IN_MILLIS;
import static org.eclipse.jgit.util.RelativeDateFormatter.YEAR_IN_MILLIS;
import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.eclipse.jgit.junit.MockSystemReader;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RelativeDateFormatterTest {

	@Before
	public void setUp() {
		SystemReader.setInstance(new MockSystemReader());
	}

	@After
	public void tearDown() {
		SystemReader.setInstance(null);
	}

	private static void assertFormat(long ageFromNow
			String expectedFormat) {
		Date d = new Date(SystemReader.getInstance().getCurrentTime()
				- ageFromNow * timeUnit);
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
	}

	@Test
	public void testFullYearMissingSomeDays() {
		assertFormat(5 * 365 + 1
		assertFormat(2 * 365 - 10
	}

	@Test
	public void testFormatYears() {
		assertFormat(5
		assertFormat(60
	}
}
