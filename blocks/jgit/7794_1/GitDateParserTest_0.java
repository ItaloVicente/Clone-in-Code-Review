package org.eclipse.jgit.util;

import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class GitDateParserBadlyFormattedTest {
	private String dateStr;

	public GitDateParserBadlyFormattedTest(String dateStr) {
		this.dateStr = dateStr;
	}

	@DataPoints
	static public String[] getDataPoints() {
		return new String[] { ""
				"now yesterday ago"
				"day ago"
				"Sun Feb 21 15:35:00 2007 +0100"
				"Wed Feb 21 15:35:00 Grand +0100" };
	}

	@Theory
	public void badlyFormattedWithExplicitRef() {
		Calendar ref = new GregorianCalendar(SystemReader.getInstance()
				.getTimeZone()
		try {
			GitDateParser.parse(dateStr
			fail("The expected ParseException while parsing '" + dateStr
					+ "' did not occur.");
		} catch (ParseException e) {
		}
	}

	@Theory
	public void badlyFormattedWithoutRef() {
		try {
			GitDateParser.parse(dateStr
			fail("The expected ParseException while parsing '" + dateStr
					+ "' did not occur.");
		} catch (ParseException e) {
		}
	}
}
