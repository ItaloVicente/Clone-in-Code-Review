
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class NBTest {
	@Test
	public void testCompareUInt32() {
		assertTrue(NB.compareUInt32(0
		assertTrue(NB.compareUInt32(1
		assertTrue(NB.compareUInt32(0
		assertTrue(NB.compareUInt32(-1
		assertTrue(NB.compareUInt32(0
		assertTrue(NB.compareUInt32(-1
		assertTrue(NB.compareUInt32(1
	}

	@Test
	public void testCompareUInt64() {
		assertTrue(NB.compareUInt64(0
		assertTrue(NB.compareUInt64(1
		assertTrue(NB.compareUInt64(0
		assertTrue(NB.compareUInt64(-1
		assertTrue(NB.compareUInt64(0
		assertTrue(NB.compareUInt64(-1
		assertTrue(NB.compareUInt64(1
	}

	@Test
	public void testDecodeUInt16() {
		assertEquals(0
		assertEquals(0

		assertEquals(3
		assertEquals(3

		assertEquals(0xde03
		assertEquals(0xde03

		assertEquals(0x03de
		assertEquals(0x03de

		assertEquals(0xffff
		assertEquals(0xffff
	}

	@Test
	public void testDecodeUInt24() {
		assertEquals(0
		assertEquals(0

		assertEquals(3
		assertEquals(3

		assertEquals(0xcede03
		assertEquals(0xbade03

		assertEquals(0x03bade
		assertEquals(0x03bade

		assertEquals(0xffffff
		assertEquals(0xffffff
	}

	@Test
	public void testDecodeInt32() {
		assertEquals(0
		assertEquals(0

		assertEquals(3
		assertEquals(3

		assertEquals(0xdeadbeef
		assertEquals(0xdeadbeef
				padb(3

		assertEquals(0x0310adef
		assertEquals(0x0310adef
				padb(3

		assertEquals(0xffffffff
		assertEquals(0xffffffff
				padb(3
	}

	@Test
	public void testDecodeUInt32() {
		assertEquals(0L
		assertEquals(0L

		assertEquals(3L
		assertEquals(3L

		assertEquals(0xdeadbeefL
		assertEquals(0xdeadbeefL
				0xef)

		assertEquals(0x0310adefL
		assertEquals(0x0310adefL
				0xef)

		assertEquals(0xffffffffL
		assertEquals(0xffffffffL
				0xff)
	}

	@Test
	public void testDecodeUInt64() {
		assertEquals(0L
		assertEquals(0L

		assertEquals(3L
		assertEquals(3L

		assertEquals(0xdeadbeefL
				0xbe
		assertEquals(0xdeadbeefL
				0xad

		assertEquals(0x0310adefL
				0xad
		assertEquals(0x0310adefL
				0x10

		assertEquals(0xc0ffee78deadbeefL
				0x78
		assertEquals(0xc0ffee78deadbeefL
				0xee

		assertEquals(0x00000000ffffffffL
				0xff
		assertEquals(0x00000000ffffffffL
				0xff
		assertEquals(0xffffffffffffffffL
				0xff
		assertEquals(0xffffffffffffffffL
				0xff
	}

	@Test
	public void testEncodeInt16() {
		final byte[] out = new byte[16];

		prepareOutput(out);
		NB.encodeInt16(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt16(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt16(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt16(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt16(out
		assertOutput(b(0xde

		prepareOutput(out);
		NB.encodeInt16(out
		assertOutput(b(0xde

		prepareOutput(out);
		NB.encodeInt16(out
		assertOutput(b(0xff
	}

	@Test
	public void testEncodeInt24() {
		byte[] out = new byte[16];

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0xc0

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0xba

		prepareOutput(out);
		NB.encodeInt24(out
		assertOutput(b(0xff
	}

	@Test
	public void testEncodeInt32() {
		final byte[] out = new byte[16];

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0xde

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0xde

		prepareOutput(out);
		NB.encodeInt32(out
		assertOutput(b(0xff
	}

	@Test
	public void testEncodeInt64() {
		final byte[] out = new byte[16];

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0xac

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0xac

		prepareOutput(out);
		NB.encodeInt64(out
		assertOutput(b(0xff
	}

	private static void prepareOutput(byte[] buf) {
		for (int i = 0; i < buf.length; i++)
			buf[i] = (byte) (0x77 + i);
	}

	private static void assertOutput(final byte[] expect
			final int offset) {
		for (int i = 0; i < offset; i++)
			assertEquals((byte) (0x77 + i)
		for (int i = 0; i < expect.length; i++)
			assertEquals(expect[i]
		for (int i = offset + expect.length; i < buf.length; i++)
			assertEquals((byte) (0x77 + i)
	}

	private static byte[] b(int a
		return new byte[] { (byte) a
	}

	private static byte[] padb(int len
		final byte[] r = new byte[len + 2];
		for (int i = 0; i < len; i++)
			r[i] = (byte) 0xaf;
		r[len] = (byte) a;
		r[len + 1] = (byte) b;
		return r;
	}

	private static byte[] b(int a
		return new byte[] { (byte) a
	}

	private static byte[] b(int a
		return new byte[] { (byte) a
	}

	private static byte[] padb(int len
		final byte[] r = new byte[len + 4];
		for (int i = 0; i < len; i++)
			r[i] = (byte) 0xaf;
		r[len] = (byte) a;
		r[len + 1] = (byte) b;
		r[len + 2] = (byte) c;
		return r;
	}

	private static byte[] padb(final int len
			final int c
		final byte[] r = new byte[len + 4];
		for (int i = 0; i < len; i++)
			r[i] = (byte) 0xaf;
		r[len] = (byte) a;
		r[len + 1] = (byte) b;
		r[len + 2] = (byte) c;
		r[len + 3] = (byte) d;
		return r;
	}

	private static byte[] b(final int a
			final int e
		return new byte[] { (byte) a
				(byte) f
	}

	private static byte[] padb(final int len
			final int c
			final int h) {
		final byte[] r = new byte[len + 8];
		for (int i = 0; i < len; i++)
			r[i] = (byte) 0xaf;
		r[len] = (byte) a;
		r[len + 1] = (byte) b;
		r[len + 2] = (byte) c;
		r[len + 3] = (byte) d;
		r[len + 4] = (byte) e;
		r[len + 5] = (byte) f;
		r[len + 6] = (byte) g;
		r[len + 7] = (byte) h;
		return r;
	}
}
