
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class RawParseUtils_HexParseTest {
	@Test
	public void testInt4_1() {
		assertEquals(0
		assertEquals(1
		assertEquals(2
		assertEquals(3
		assertEquals(4
		assertEquals(5
		assertEquals(6
		assertEquals(7
		assertEquals(8
		assertEquals(9
		assertEquals(10
		assertEquals(11
		assertEquals(12
		assertEquals(13
		assertEquals(14
		assertEquals(15

		assertEquals(10
		assertEquals(11
		assertEquals(12
		assertEquals(13
		assertEquals(14
		assertEquals(15

		assertNotHex('q');
		assertNotHex(' ');
		assertNotHex('.');
	}

	private static void assertNotHex(char c) {
		try {
			RawParseUtils.parseHexInt4((byte) c);
			fail("Incorrectly acccepted " + c);
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	@Test
	public void testInt16() {
		assertEquals(0x0000
		assertEquals(0x0001
		assertEquals(0x1234
		assertEquals(0xdead
		assertEquals(0xBEEF
		assertEquals(0x4321
		assertEquals(0xffff

		try {
			parse16("noth");
			fail("Incorrectly acccepted \"noth\"");
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		try {
			parse16("01");
			fail("Incorrectly acccepted \"01\"");
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		try {
			parse16("000.");
			fail("Incorrectly acccepted \"000.\"");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	private static int parse16(final String str) {
		return RawParseUtils.parseHexInt16(Constants.encodeASCII(str)
	}

	@Test
	public void testInt32() {
		assertEquals(0x00000000
		assertEquals(0x00000001
		assertEquals(0xc0ffEE42
		assertEquals(0xffffffff
		assertEquals(-1

		try {
			parse32("noth");
			fail("Incorrectly acccepted \"noth\"");
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		try {
			parse32("notahexs");
			fail("Incorrectly acccepted \"notahexs\"");
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		try {
			parse32("01");
			fail("Incorrectly acccepted \"01\"");
		} catch (ArrayIndexOutOfBoundsException e) {
		}

		try {
			parse32("0000000.");
			fail("Incorrectly acccepted \"0000000.\"");
		} catch (ArrayIndexOutOfBoundsException e) {
		}
	}

	private static int parse32(final String str) {
		return RawParseUtils.parseHexInt32(Constants.encodeASCII(str)
	}
}
