
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class ConstantsEncodingTest {
	@Test
	public void testEncodeASCII_SimpleASCII()
			throws UnsupportedEncodingException {
		final String src = "abc";
		final byte[] exp = { 'a'
		final byte[] res = Constants.encodeASCII(src);
		assertArrayEquals(exp
		assertEquals(src
	}

	@Test
	public void testEncodeASCII_FailOnNonASCII() {
		final String src = "ÅªnÄ­cÅdeÌ½";
		try {
			Constants.encodeASCII(src);
			fail("Incorrectly accepted a Unicode character");
		} catch (IllegalArgumentException err) {
			assertEquals("Not ASCII string: " + src
		}
	}

	@Test
	public void testEncodeASCII_Number13() {
		final long src = 13;
		final byte[] exp = { '1'
		final byte[] res = Constants.encodeASCII(src);
		assertArrayEquals(exp
	}

	@Test
	public void testEncode_SimpleASCII() throws UnsupportedEncodingException {
		final String src = "abc";
		final byte[] exp = { 'a'
		final byte[] res = Constants.encode(src);
		assertArrayEquals(exp
		assertEquals(src
	}

	@Test
	public void testEncode_Unicode() throws UnsupportedEncodingException {
		final String src = "ÅªnÄ­cÅdeÌ½";
		final byte[] exp = { (byte) 0xC5
				(byte) 0xAD
				(byte) 0xCC
		final byte[] res = Constants.encode(src);
		assertArrayEquals(exp
		assertEquals(src
	}
}
