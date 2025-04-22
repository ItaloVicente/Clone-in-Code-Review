
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

public class T0001_PersonIdentTest {

	@Test
	public void test001_NewIdent() {
		final PersonIdent p = new PersonIdent("A U Thor"
				new Date(1142878501000L)
		assertEquals("A U Thor"
		assertEquals("author@example.com"
		assertEquals(1142878501000L
		assertEquals("A U Thor <author@example.com> 1142878501 -0500"
				p.toExternalString());
	}

	@Test
	public void test002_NewIdent() {
		final PersonIdent p = new PersonIdent("A U Thor"
				new Date(1142878501000L)
		assertEquals("A U Thor"
		assertEquals("author@example.com"
		assertEquals(1142878501000L
		assertEquals("A U Thor <author@example.com> 1142878501 +0230"
				p.toExternalString());
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void nullForNameShouldThrowIllegalArgumentException() {
		new PersonIdent(null
	}

	@SuppressWarnings("unused")
	@Test(expected = IllegalArgumentException.class)
	public void nullForEmailShouldThrowIllegalArgumentException() {
		new PersonIdent("A U Thor"
	}

	@Test
	public void testToExternalStringTrimsNameAndEmail() throws Exception {
		PersonIdent personIdent = new PersonIdent(" \u0010A U Thor  "
				"  author@example.com \u0009");

		assertEquals(" \u0010A U Thor  "
		assertEquals("  author@example.com \u0009"

		String externalString = personIdent.toExternalString();
		assertTrue(externalString.startsWith("A U Thor <author@example.com>"));
	}

	@Test
	public void testToExternalStringTrimsAllWhitespace() {
		String ws = "  \u0001 \n ";
		PersonIdent personIdent = new PersonIdent(ws
		assertEquals(ws
		assertEquals(ws

		String externalString = personIdent.toExternalString();
		assertTrue(externalString.startsWith(" <>"));
	}

	@Test
	public void testToExternalStringTrimsOtherBadCharacters() {
		String name = " Foo\r\n<Bar> ";
		String email = " Baz>\n\u1234<Quux ";
		PersonIdent personIdent = new PersonIdent(name
		assertEquals(name
		assertEquals(email

		String externalString = personIdent.toExternalString();
		assertTrue(externalString.startsWith("Foo\rBar <Baz\u1234Quux>"));
	}

	@Test
	public void testEmptyNameAndEmail() {
		PersonIdent personIdent = new PersonIdent(""
		assertEquals(""
		assertEquals(""

		String externalString = personIdent.toExternalString();
		assertTrue(externalString.startsWith(" <>"));
	}

	@Test
	public void testAppendSanitized() {
		StringBuilder r = new StringBuilder();
		PersonIdent.appendSanitized(r
		assertEquals("Baz\u1234Quux"
	}
}

