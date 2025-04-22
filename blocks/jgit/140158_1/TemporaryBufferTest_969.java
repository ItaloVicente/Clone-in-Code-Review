
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringUtilsTest {
	@Test
	public void testToLowerCaseChar() {
		assertEquals('a'
		assertEquals('z'

		assertEquals('a'
		assertEquals('z'

		assertEquals((char) 0
		assertEquals((char) 0xffff
	}

	@Test
	public void testToLowerCaseString() {
		assertEquals("\n abcdefghijklmnopqrstuvwxyz\n"
				.toLowerCase("\n ABCDEFGHIJKLMNOPQRSTUVWXYZ\n"));
	}

	@Test
	public void testEqualsIgnoreCase1() {
		final String a = "FOO";
		assertTrue(StringUtils.equalsIgnoreCase(a
	}

	@Test
	public void testEqualsIgnoreCase2() {
		assertFalse(StringUtils.equalsIgnoreCase("a"
	}

	@Test
	public void testEqualsIgnoreCase3() {
		assertFalse(StringUtils.equalsIgnoreCase("a"
		assertFalse(StringUtils.equalsIgnoreCase("ac"
	}

	@Test
	public void testEqualsIgnoreCase4() {
		assertTrue(StringUtils.equalsIgnoreCase("a"
		assertTrue(StringUtils.equalsIgnoreCase("A"
		assertTrue(StringUtils.equalsIgnoreCase("a"
	}

	@Test
	public void testReplaceLineBreaks() {
		assertEquals("a b c "
				StringUtils.replaceLineBreaksWithSpace("a b\nc\r"));
		assertEquals("a b c "
				StringUtils.replaceLineBreaksWithSpace("a b\nc\n"));
		assertEquals("a b c "
				StringUtils.replaceLineBreaksWithSpace("a b\nc\r\n"));
		assertEquals("a b c d"
				StringUtils.replaceLineBreaksWithSpace("a\r\nb\nc d"));
	}
}
