
package org.eclipse.jgit.util;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.jgit.lib.PersonIdent;

import junit.framework.TestCase;

public class RawParseUtils_ParsePersonIdentTest extends TestCase {

	public void testParsePersonIdent_legalCases()
			throws UnsupportedEncodingException {
		final Date when = new Date(1234567890000l);
		final TimeZone tz = TimeZone.getTimeZone("GMT-7");

		assertPersonIdent("Me <me@example.com> 1234567890 -0700"
				new PersonIdent("Me"

		assertPersonIdent(" Me <me@example.com> 1234567890 -0700"
				new PersonIdent("Me"

		assertPersonIdent("Me <> 1234567890 -0700"
				""

		assertPersonIdent(" <me@example.com> 1234567890 -0700"
				new PersonIdent(""

		assertPersonIdent(" <> 1234567890 -0700"
				when
	}

	public void testParsePersonIdent_malformedCases()
			throws UnsupportedEncodingException {
		assertPersonIdent("Me me@example.com> 1234567890 -0700"
		assertPersonIdent("Me <me@example.com 1234567890 -0700"

		assertPersonIdent("<>"
		assertPersonIdent("<me@example.com>"
		assertPersonIdent(" <>"
		assertPersonIdent(" <me@example.com>"
		assertPersonIdent("Me <>"
		assertPersonIdent("Me <me@example.com>"

		assertPersonIdent("Me <me@example.com> 1234567890"
		assertPersonIdent("<me@example.com> 1234567890 -0700"
		assertPersonIdent("<> 1234567890 -0700"
	}

	private void assertPersonIdent(String line
			throws UnsupportedEncodingException {
		PersonIdent actual = RawParseUtils.parsePersonIdent(line
				.getBytes("UTF-8")
		assertEquals(expected
	}
}
