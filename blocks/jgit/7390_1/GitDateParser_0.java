
package org.eclipse.jgit.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GitDateParserTest {
	SimpleDateFormat df;
	private Date refDate;

	private Date refDateMidnight;

	@Before
	public void setUp() throws ParseException {
		df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		refDate = df.parse("2007-02-21 15:35:00");
		refDateMidnight = df.parse("2007-02-21 00:00:00");
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
	public void yesterday() throws ParseException {
		Date parse = GitDateParser.parse("yesterday"
		Assert.assertEquals(df.parse("2007-02-20 00:00:00")
	}

	@Test
	public void now() {
		Date parse = GitDateParser.parse("now"
		Assert.assertEquals(refDate
		long t1 = System.currentTimeMillis();
		parse = GitDateParser.parse("now"
		long t2 = System.currentTimeMillis();
		Assert.assertTrue(t2 >= parse.getTime() && parse.getTime() >= t1);
	}

	@Test
	public void weeksAgo() throws ParseException {
		Date parse = GitDateParser.parse("2 weeks ago"
		Assert.assertEquals(df.parse("2007-02-07 15:35:00")
	}

	@Test
	public void daysAndWeeksAgo() throws ParseException {
		Date parse = GitDateParser.parse("3 days 2 weeks ago"
		Assert.assertEquals(df.parse("2007-02-04 15:35:00")
		parse = GitDateParser.parse("3.day.2.week.ago"
		Assert.assertEquals(df.parse("2007-02-04 15:35:00")
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
