package org.eclipse.jgit.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

public class Base85Test {

	private static final String VALID_CHARS = "0123456789"
			+ "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
			+ "!#$%&()*+-;<=>?@^_`{|}~";

	@Test
	public void testChars() {
		for (int i = 0; i < 256; i++) {
			byte[] testData = { '1'
			if (VALID_CHARS.indexOf(i) >= 0) {
				byte[] decoded = Base85.decode(testData
				assertNotNull(decoded);
			} else {
				assertThrows(IllegalArgumentException.class
						() -> Base85.decode(testData
			}
		}
	}

	private void roundtrip(byte[] data
		byte[] encoded = Base85.encode(data);
		assertEquals(expectedLength
		assertArrayEquals(data
	}

	private void roundtrip(String data
		roundtrip(data.getBytes(StandardCharsets.US_ASCII)
	}

	@Test
	public void testPadding() {
		roundtrip(""
		roundtrip("a"
		roundtrip("ab"
		roundtrip("abc"
		roundtrip("abcd"
		roundtrip("abcde"
		roundtrip("abcdef"
		roundtrip("abcdefg"
		roundtrip("abcdefgh"
		roundtrip("abcdefghi"
	}

	@Test
	public void testBinary() {
		roundtrip(new byte[] { 1 }
		roundtrip(new byte[] { 1
		roundtrip(new byte[] { 1
		roundtrip(new byte[] { 1
		roundtrip(new byte[] { 1
		roundtrip(new byte[] { 1
		roundtrip(new byte[] { 1
	}

	@Test
	public void testOverflow() {
		IllegalArgumentException e = assertThrows(
				IllegalArgumentException.class
				() -> Base85.decode(new byte[] { '~'
		assertTrue(e.getMessage().contains("overflow"));
	}
}
