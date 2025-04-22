
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.eclipse.jgit.util.RawCharUtil.isWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimLeadingWhitespace;
import static org.eclipse.jgit.util.RawCharUtil.trimTrailingWhitespace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RawCharUtilTest {

	@Test
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

	@Test
	public void testTrimTrailingWhitespace() {
		assertEquals(0
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(3
		assertEquals(6
				trimTrailingWhitespace("  test   ".getBytes(US_ASCII)
	}

	@Test
	public void testTrimLeadingWhitespace() {
		assertEquals(0
		assertEquals(1
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(2
				trimLeadingWhitespace("  test   ".getBytes(US_ASCII)
				2
	}

}
