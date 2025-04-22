
package org.eclipse.jgit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.eclipse.jgit.junit.MockSystemReader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GitDateParserTest {
	SimpleDateFormat df;
	private Date refDate;
	private Date refDateMidnight;

	@Before
	public void setUp() throws ParseException {
		MockSystemReader mockSystemReader = new MockSystemReader() {
			@Override
			public long getCurrentTime() {
				return 1318125997291L;
			}
		};
		SystemReader.setInstance(mockSystemReader);
		df = SystemReader.getInstance().getSimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss Z");
		refDate = df.parse("2007-02-21 15:35:00 +0100");
		refDateMidnight = df.parse("2007-02-21 00:00:00 +0100");
	}

	@Test
	public void badlyFormatted() {
		Assert.assertNull(GitDateParser.parse("foo"
		Assert.assertNull(GitDateParser.parse(""
		Assert.assertNull(GitDateParser.parse(""
		Assert.assertNull(GitDateParser.parse("1970"
		Assert.assertNull(GitDateParser.parse("3000.3000.3000"
		Assert.assertNull(GitDateParser.parse("3 yesterday ago"
		Assert.assertNull(GitDateParser.parse("now yesterday ago"
		Assert.assertNull(GitDateParser.parse("yesterdays"
		Assert.assertNull(GitDateParser.parse("3.day. 2.week.ago"
		Assert.assertNull(GitDateParser.parse("day ago"
		Assert.assertNull(GitDateParser.parse("Gra Feb 21 15:35:00 2007 +0100"
				null));
		Assert.assertNull(GitDateParser.parse("Sun Feb 21 15:35:00 2007 +0100"
				null));
		Assert.assertNull(GitDateParser.parse(
				"Wed Feb 21 15:35:00 Grand +0100"
				null));
	}

	@Test
	public void yesterday() {
		GregorianCalendar cal = new GregorianCalendar(SystemReader
				.getInstance().getTimeZone()
				.getLocale());
		Date parse = GitDateParser.parse("yesterday"
		cal.setTime(refDate);
		cal.add(Calendar.DATE
		cal.set(Calendar.HOUR_OF_DAY
		cal.set(Calendar.MINUTE
		cal.set(Calendar.SECOND
		cal.set(Calendar.MILLISECOND
		cal.set(Calendar.MILLISECOND
		Assert.assertEquals(cal.getTime()
	}

	@Test
	public void now() {
		Date parse = GitDateParser.parse("now"
		Assert.assertEquals(refDate
		long t1 = SystemReader.getInstance().getCurrentTime();
		parse = GitDateParser.parse("now"
		long t2 = SystemReader.getInstance().getCurrentTime();
		Assert.assertTrue(t2 >= parse.getTime() && parse.getTime() >= t1);
	}

	@Test
	public void weeksAgo() throws ParseException {
		Date parse = GitDateParser.parse("2 weeks ago"
		Assert.assertEquals(df.parse("2007-02-07 15:35:00 +0100")
	}

	@Test
	public void daysAndWeeksAgo() throws ParseException {
		Date parse = GitDateParser.parse("3 days 2 weeks ago"
		Assert.assertEquals(df.parse("2007-02-04 15:35:00 +0100")
		parse = GitDateParser.parse("3.day.2.week.ago"
		Assert.assertEquals(df.parse("2007-02-04 15:35:00 +0100")
	}

	@Test
	public void iso() {
		Date parse = GitDateParser.parse("2007-02-21 15:35:00 +0100"
		Assert.assertEquals(refDate
	}

	@Test
	public void rfc() {
		Date parse = GitDateParser.parse("Wed
				null);
		Assert.assertEquals(refDate
	}

	@Test
	public void shortFmt() {
		Date parse = GitDateParser.parse("2007-02-21"
		Assert.assertEquals(refDateMidnight
	}

	@Test
	public void shortWithDots() {
		Date parse = GitDateParser.parse("2007.02.21"
		Assert.assertEquals(refDateMidnight
	}

	@Test
	public void shortWithSlash() {
		Date parse = GitDateParser.parse("02/21/2007"
		Assert.assertEquals(refDateMidnight
	}

	@Test
	public void shortWithDotsReverse() {
		Date parse = GitDateParser.parse("21.02.2007"
		Assert.assertEquals(refDateMidnight
	}

	@Test
	public void defaultFmt() {
		Date parse = GitDateParser
				.parse("Wed Feb 21 15:35:00 2007 +0100"
		Assert.assertEquals(refDate
	}

	@Test
	public void local() {
		Date parse = GitDateParser.parse("Wed Feb 21 15:35:00 2007"
		Assert.assertEquals(refDate
	}
}
