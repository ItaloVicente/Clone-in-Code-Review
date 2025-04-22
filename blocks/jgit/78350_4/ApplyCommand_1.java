
package org.eclipse.jgit.util;

import org.junit.Test;

import static org.eclipse.jgit.util.Base85.decode85;
import static org.eclipse.jgit.util.Base85.encode85;
import static org.junit.Assert.*;

public class Base85Test {

	private static char[] ILLEGAL_CHARS = {
			'['
	};

	@Test
	public void testFailOnIllegalChars() {
		for (char c : ILLEGAL_CHARS) {
			try {
				String s = c + "2345";
				byte[] bytes = s.getBytes();
				decode85(bytes
				fail("Accepted bad string in decode");
			} catch (IllegalArgumentException fail) {
			}
		}
	}

	@Test
	public void failOnBadLength() {
		try {
			byte[] bytes = "hi".getBytes();
			decode85(bytes
			fail("Accepted string with length not multiple of 5");
		} catch (IllegalArgumentException fail) {
		}
	}

	@Test
	public void testEncodeDecode() {
		byte[] buffer = "joke".getBytes();
		byte[] encoded = encode85(buffer

		assertEquals(5

		byte[] decoded = decode85(encoded
		assertArrayEquals("joke".getBytes()
	}

	@Test
	public void testEncodePadding() {
		byte[] buffer = "hello".getBytes();
		byte[] encoded = encode85(buffer

		assertEquals(10

		byte[] decoded = decode85(encoded
		assertArrayEquals("hello".getBytes()
	}
}
