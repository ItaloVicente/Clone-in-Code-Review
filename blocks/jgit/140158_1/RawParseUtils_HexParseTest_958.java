
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class RawParseUtils_FormatTest {
	@Test
	public void testFormatBase10() throws UnsupportedEncodingException {
		byte[] b = new byte[64];
		int p;

		p = RawParseUtils.formatBase10(b
		assertEquals("0"

		p = RawParseUtils.formatBase10(b
		assertEquals("42"

		p = RawParseUtils.formatBase10(b
		assertEquals("1234"

		p = RawParseUtils.formatBase10(b
		assertEquals("-9876"

		p = RawParseUtils.formatBase10(b
		assertEquals("123456789"
	}
}
