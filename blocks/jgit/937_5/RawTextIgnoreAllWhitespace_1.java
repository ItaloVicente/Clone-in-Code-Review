
package org.eclipse.jgit.util;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;
import static org.eclipse.jgit.util.RawCharUtil.isWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimLeadingWhitespace;

public class RawCharUtilTest extends TestCase {

	public void testIsWhitespace() {
		for (byte c = -128; c < 127; c++) {
			switch (c) {
			case (byte) '\r':
			case (byte) '\n':
			case (byte) '\t':
			case (byte) ' ':
				assertTrue(isWhitespace(c));
				break;
			default:
				assertFalse(isWhitespace(c));
			}
		}
	}

	public void testTrimTrailingWhitespace()
			throws UnsupportedEncodingException {
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(2
				trimTrailingWhitespace(" a ".getBytes("US-ASCII")
		assertEquals(3
				trimTrailingWhitespace("  a".getBytes("US-ASCII")
		assertEquals(6
				"  test   ".getBytes("US-ASCII")
	}

	public void testTrimLeadingWhitespace() throws UnsupportedEncodingException {
		assertEquals(0
		assertEquals(1
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(2
				2
	}

}
