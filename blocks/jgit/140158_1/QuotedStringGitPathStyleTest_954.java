
package org.eclipse.jgit.util;

import static org.eclipse.jgit.util.QuotedString.BOURNE_USER_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class QuotedStringBourneUserPathStyleTest {
	private static void assertQuote(String in
		final String r = BOURNE_USER_PATH.quote(in);
		assertNotSame(in
		assertFalse(in.equals(r));
		assertEquals('\'' + exp + '\''
	}

	private static void assertDequote(String exp
		final byte[] b = Constants.encode('\'' + in + '\'');
		final String r = BOURNE_USER_PATH.dequote(b
		assertEquals(exp
	}

	@Test
	public void testQuote_Empty() {
		assertEquals("''"
	}

	@Test
	public void testDequote_Empty1() {
		assertEquals(""
	}

	@Test
	public void testDequote_Empty2() {
		assertEquals(""
				2));
	}

	@Test
	public void testDequote_SoleSq() {
		assertEquals(""
	}

	@Test
	public void testQuote_BareA() {
		assertQuote("a"
	}

	@Test
	public void testDequote_BareA() {
		final String in = "a";
		final byte[] b = Constants.encode(in);
		assertEquals(in
	}

	@Test
	public void testDequote_BareABCZ_OnlyBC() {
		final String in = "abcz";
		final byte[] b = Constants.encode(in);
		final int p = in.indexOf('b');
		assertEquals("bc"
	}

	@Test
	public void testDequote_LoneBackslash() {
		assertDequote("\\"
	}

	@Test
	public void testQuote_NamedEscapes() {
		assertQuote("'"
		assertQuote("!"

		assertQuote("a'b"
		assertQuote("a!b"
	}

	@Test
	public void testDequote_NamedEscapes() {
		assertDequote("'"
		assertDequote("!"

		assertDequote("a'b"
		assertDequote("a!b"
	}

	@Test
	public void testQuote_User() {
		assertEquals("~foo/"
		assertEquals("~foo/"
		assertEquals("~/"

		assertEquals("~foo/'a'"
		assertEquals("~/'a'"
	}

	@Test
	public void testDequote_User() {
		assertEquals("~foo"
		assertEquals("~foo/"
		assertEquals("~/"

		assertEquals("~foo/a"
		assertEquals("~/a"
	}
}
