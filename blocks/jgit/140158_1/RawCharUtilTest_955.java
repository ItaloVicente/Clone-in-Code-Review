
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.eclipse.jgit.util.QuotedString.GIT_PATH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class QuotedStringGitPathStyleTest {
	private static void assertQuote(String exp
		final String r = GIT_PATH.quote(in);
		assertNotSame(in
		assertFalse(in.equals(r));
		assertEquals('"' + exp + '"'
	}

	private static void assertDequote(String exp
		final byte[] b = ('"' + in + '"').getBytes(ISO_8859_1);
		final String r = GIT_PATH.dequote(b
		assertEquals(exp
	}

	@Test
	public void testQuote_Empty() {
		assertEquals("\"\""
	}

	@Test
	public void testDequote_Empty1() {
		assertEquals(""
	}

	@Test
	public void testDequote_Empty2() {
		assertEquals(""
	}

	@Test
	public void testDequote_SoleDq() {
		assertEquals("\""
	}

	@Test
	public void testQuote_BareA() {
		final String in = "a";
		assertSame(in
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
		assertQuote("\\a"
		assertQuote("\\b"
		assertQuote("\\f"
		assertQuote("\\n"
		assertQuote("\\r"
		assertQuote("\\t"
		assertQuote("\\v"
		assertQuote("\\\\"
		assertQuote("\\\""
	}

	@Test
	public void testDequote_NamedEscapes() {
		assertDequote("\u0007"
		assertDequote("\b"
		assertDequote("\f"
		assertDequote("\n"
		assertDequote("\r"
		assertDequote("\t"
		assertDequote("\u000B"
		assertDequote("\\"
		assertDequote("\""
	}

	@Test
	public void testDequote_OctalAll() {
		for (int i = 0; i < 127; i++) {
			assertDequote("" + (char) i
		}
		for (int i = 128; i < 256; i++) {
			int f = 0xC0 | (i >> 6);
			int s = 0x80 | (i & 0x3f);
			assertDequote("" + (char) i
		}
	}

	private static String octalEscape(int i) {
		String s = Integer.toOctalString(i);
		while (s.length() < 3) {
			s = "0" + s;
		}
		return "\\"+s;
	}

	@Test
	public void testQuote_OctalAll() {
		assertQuote("\\001"
		assertQuote("\\177"
		assertQuote("\\303\\277"
	}

	@Test
	public void testDequote_UnknownEscapeQ() {
		assertDequote("\\q"
	}

	@Test
	public void testDequote_FooTabBar() {
		assertDequote("foo\tbar"
	}

	@Test
	public void testDequote_Latin1() {
		assertDequote("\u00c5ngstr\u00f6m"
	}

	@Test
	public void testDequote_UTF8() {
		assertDequote("\u00c5ngstr\u00f6m"
	}

	@Test
	public void testDequote_RawUTF8() {
		assertDequote("\u00c5ngstr\u00f6m"
	}

	@Test
	public void testDequote_RawLatin1() {
		assertDequote("\u00c5ngstr\u00f6m"
	}

	@Test
	public void testQuote_Ang() {
		assertQuote("\\303\\205ngstr\\303\\266m"
	}

	@Test
	public void testQuoteAtAndNumber() {
		assertSame("abc@2x.png"
		assertDequote("abc@2x.png"
	}
}
