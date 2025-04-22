
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
	@Before
	public void setUp() {
		MockSystemReader mockSystemReader = new MockSystemReader();
		SystemReader.setInstance(mockSystemReader);
	}

	@Test
	public void badlyFormatted() {
		Calendar ref = new GregorianCalendar(SystemReader.getInstance()
				.getTimeZone()
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
		cal.add(Calendar.DATE
		cal.set(Calendar.HOUR_OF_DAY
		cal.set(Calendar.MINUTE
		cal.set(Calendar.SECOND
		cal.set(Calendar.MILLISECOND
		cal.set(Calendar.MILLISECOND
		Assert.assertEquals(cal.getTime()
	}

	@Test
	public void now() throws ParseException {
		String dateStr = "2007-02-21 15:35:00 +0100";
		Date refDate = SystemReader.getInstance()
				.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(dateStr);

		GregorianCalendar cal = new GregorianCalendar(SystemReader
				.getInstance().getTimeZone()
				.getLocale());
		cal.setTime(refDate);

		Date parse = GitDateParser.parse("now"
		Assert.assertEquals(refDate
		long t1 = SystemReader.getInstance().getCurrentTime();
		parse = GitDateParser.parse("now"
		long t2 = SystemReader.getInstance().getCurrentTime();
		Assert.assertTrue(t2 >= parse.getTime() && parse.getTime() >= t1);
	}

	@Test
	public void weeksAgo() throws ParseException {
		String dateStr = "2007-02-21 15:35:00 +0100";
		SimpleDateFormat df = SystemReader.getInstance()
				.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		Date refDate = df.parse(dateStr);
		GregorianCalendar cal = new GregorianCalendar(SystemReader
				.getInstance().getTimeZone()
				.getLocale());
		cal.setTime(refDate);

		Date parse = GitDateParser.parse("2 weeks ago"
		Assert.assertEquals(df.parse("2007-02-07 15:35:00 +0100")
	}

	@Test
	public void daysAndWeeksAgo() throws ParseException {
		String dateStr = "2007-02-21 15:35:00 +0100";
		SimpleDateFormat df = SystemReader.getInstance().getSimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss Z");
		Date refDate = df.parse(dateStr);
		GregorianCalendar cal = new GregorianCalendar(SystemReader
				.getInstance().getTimeZone()
				.getLocale());
		cal.setTime(refDate);

		Date parse = GitDateParser.parse("2 weeks ago"
		Assert.assertEquals(df.parse("2007-02-07 15:35:00 +0100")
		parse = GitDateParser.parse("3 days 2 weeks ago"
		Assert.assertEquals(df.parse("2007-02-04 15:35:00 +0100")
		parse = GitDateParser.parse("3.day.2.week.ago"
		Assert.assertEquals(df.parse("2007-02-04 15:35:00 +0100")
	}

	@Test
	public void iso() throws ParseException {
		String dateStr = "2007-02-21 15:35:00 +0100";
		Date exp = SystemReader.getInstance()
				.getSimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}

	@Test
	public void rfc() throws ParseException {
		String dateStr = "Wed
		Date exp = SystemReader.getInstance()
				.getSimpleDateFormat("EEE
				.parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}

	@Test
	public void shortFmt() throws ParseException {
		String dateStr = "2007-02-21";
		Date exp = SystemReader.getInstance().getSimpleDateFormat("yyyy-MM-dd")
				.parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}

	@Test
	public void shortWithDots() throws ParseException {
		String dateStr = "2007.02.21";
		Date exp = SystemReader.getInstance().getSimpleDateFormat("yyyy.MM.dd")
				.parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}

	@Test
	public void shortWithSlash() throws ParseException {
		String dateStr = "02/21/2007";
		Date exp = SystemReader.getInstance().getSimpleDateFormat("MM/dd/yyyy")
				.parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}

	@Test
	public void shortWithDotsReverse() throws ParseException {
		String dateStr = "21.02.2007";
		Date exp = SystemReader.getInstance().getSimpleDateFormat("dd.MM.yyyy")
				.parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}

	@Test
	public void defaultFmt() throws ParseException {
		String dateStr = "Wed Feb 21 15:35:00 2007 +0100";
		Date exp = SystemReader.getInstance()
				.getSimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z")
				.parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}

	@Test
	public void local() throws ParseException {
		String dateStr = "Wed Feb 21 15:35:00 2007";
		Date exp = SystemReader.getInstance()
				.getSimpleDateFormat("EEE MMM dd HH:mm:ss yyyy").parse(dateStr);
		Date parse = GitDateParser.parse(dateStr
		Assert.assertEquals(exp
	}
}
