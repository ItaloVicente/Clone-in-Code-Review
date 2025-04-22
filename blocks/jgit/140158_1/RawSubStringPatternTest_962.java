
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.TimeZone;

import org.eclipse.jgit.lib.PersonIdent;
import org.junit.Test;

public class RawParseUtils_ParsePersonIdentTest {

	@Test
	public void testParsePersonIdent_legalCases() {
		final Date when = new Date(1234567890000l);
		final TimeZone tz = TimeZone.getTimeZone("GMT-7");

		assertPersonIdent("Me <me@example.com> 1234567890 -0700"
				new PersonIdent("Me"

		assertPersonIdent(" Me <me@example.com> 1234567890 -0700"
				new PersonIdent(" Me"

		assertPersonIdent("A U Thor <author@example.com> 1234567890 -0700"
				new PersonIdent("A U Thor"

		assertPersonIdent("A U Thor<author@example.com> 1234567890 -0700"
				new PersonIdent("A U Thor"

		assertPersonIdent("A U Thor<author@example.com>1234567890 -0700"
				new PersonIdent("A U Thor"

		assertPersonIdent(
				" A U Thor   < author@example.com > 1234567890 -0700"
				new PersonIdent(" A U Thor  "

		assertPersonIdent("A U Thor<author@example.com>1234567890 -0700"
				new PersonIdent("A U Thor"
	}

	@Test
	public void testParsePersonIdent_fuzzyCases() {
		final Date when = new Date(1234567890000l);
		final TimeZone tz = TimeZone.getTimeZone("GMT-7");

		assertPersonIdent(
				"A U Thor <author@example.com>
				new PersonIdent("A U Thor"

		assertPersonIdent(
				"A U Thor <author@example.com> and others 1234567890 -0700"
				new PersonIdent("A U Thor"
	}

	@Test
	public void testParsePersonIdent_incompleteCases() {
		final Date when = new Date(1234567890000l);
		final TimeZone tz = TimeZone.getTimeZone("GMT-7");

		assertPersonIdent("Me <> 1234567890 -0700"
				when

		assertPersonIdent(" <me@example.com> 1234567890 -0700"
				new PersonIdent(""

		assertPersonIdent(" <> 1234567890 -0700"
				tz));

		assertPersonIdent("<>"

		assertPersonIdent(" <>"

		assertPersonIdent("<me@example.com>"
				"me@example.com"

		assertPersonIdent(" <me@example.com>"
				"me@example.com"

		assertPersonIdent("Me <>"

		assertPersonIdent("Me <me@example.com>"
				"me@example.com"

		assertPersonIdent("Me <me@example.com> 1234567890"
				"Me"

		assertPersonIdent("Me <me@example.com> 1234567890 "
				"Me"
	}

	@Test
	public void testParsePersonIdent_malformedCases() {
		assertPersonIdent("Me me@example.com> 1234567890 -0700"
		assertPersonIdent("Me <me@example.com 1234567890 -0700"
	}

	private static void assertPersonIdent(String line
		PersonIdent actual = RawParseUtils.parsePersonIdent(line);
		assertEquals(expected
	}
}
