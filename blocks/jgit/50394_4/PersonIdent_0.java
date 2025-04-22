
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.PushCertificateIdent.parse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.TimeZone;

import org.eclipse.jgit.lib.PersonIdent;
import org.junit.Test;

public class PushCertificateIdentTest {
	@Test
	public void parseValid() throws Exception {
		String raw = "A U. Thor <a_u_thor@example.com> 1218123387 +0700";
		PushCertificateIdent ident = parse(raw);
		assertEquals(raw
		assertEquals("A U. Thor <a_u_thor@example.com>"
		assertEquals("A U. Thor"
		assertEquals("a_u_thor@example.com"
		assertEquals(1218123387000L
		assertEquals(TimeZone.getTimeZone("GMT+0700")
		assertEquals(7 * 60
	}

	@Test
	public void trimName() throws Exception {
		String name = "A U. Thor";
		String email = "a_u_thor@example.com";
		String rest = "<a_u_thor@example.com> 1218123387 +0700";

		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
	}

	@Test
	public void noEmail() throws Exception {
		String name = "A U. Thor";
		String rest = " 1218123387 +0700";

		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
		checkNameEmail(name
	}

	@Test
	public void exoticUserId() throws Exception {
		String rest = " 218123387 +0700";
		assertEquals(""

		String id = "foo\n\0bar\uabcd\n ";
		assertEquals(id
	}

	@Test
	public void fuzzyCasesMatchPersonIdent() throws Exception {
		Date when = new Date(1234567890000l);
		TimeZone tz = TimeZone.getTimeZone("GMT-7");

		assertMatchesPersonIdent(
				"A U Thor <author@example.com>
				new PersonIdent("A U Thor"
				"A U Thor <author@example.com>
		assertMatchesPersonIdent(
				"A U Thor <author@example.com> and others 1234567890 -0700"
				new PersonIdent("A U Thor"
				"A U Thor <author@example.com> and others");
	}

	@Test
	public void incompleteCasesMatchPersonIdent() throws Exception {
		Date when = new Date(1234567890000l);
		TimeZone tz = TimeZone.getTimeZone("GMT-7");

		assertMatchesPersonIdent(
				"Me <> 1234567890 -0700"
				new PersonIdent("Me"
				"Me <>");
		assertMatchesPersonIdent(
				" <me@example.com> 1234567890 -0700"
				new PersonIdent(""
				" <me@example.com>");
		assertMatchesPersonIdent(
				" <> 1234567890 -0700"
				new PersonIdent(""
				" <>");
		assertMatchesPersonIdent(
				"<>"
				new PersonIdent(""
				"<>");
		assertMatchesPersonIdent(
				" <>"
				new PersonIdent(""
				" <>");
		assertMatchesPersonIdent(
				"<me@example.com>"
				new PersonIdent(""
				"<me@example.com>");
		assertMatchesPersonIdent(
				" <me@example.com>"
				new PersonIdent(""
				" <me@example.com>");
		assertMatchesPersonIdent(
				"Me <>"
				new PersonIdent("Me"
				"Me <>");
		assertMatchesPersonIdent(
				"Me <me@example.com>"
				new PersonIdent("Me"
				"Me <me@example.com>");
		assertMatchesPersonIdent(
				"Me <me@example.com> 1234567890"
				new PersonIdent("Me"
				"Me <me@example.com>");
		assertMatchesPersonIdent(
				"Me <me@example.com> 1234567890 "
				new PersonIdent("Me"
				"Me <me@example.com>");
	}

	private static void assertMatchesPersonIdent(String raw
			PersonIdent expectedPersonIdent
		PushCertificateIdent certIdent = PushCertificateIdent.parse(raw);
		assertNotNull(raw);
		assertEquals(raw
		assertEquals(expectedPersonIdent.getName()
		assertEquals(expectedPersonIdent.getEmailAddress()
				certIdent.getEmailAddress());
		assertEquals(expectedPersonIdent.getWhen()
		assertEquals(expectedPersonIdent.getTimeZoneOffset()
				certIdent.getTimeZoneOffset());
		assertEquals(expectedUserId
	}

	private static void checkNameEmail(String expectedName
			String raw) {
		PushCertificateIdent ident = parse(raw);
		assertNotNull(ident);
		assertEquals(raw
		assertEquals(expectedName
		assertEquals(expectedEmail
	}
}
