
package org.eclipse.jgit.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import junit.framework.TestCase;

public class UnionInputStreamTest extends TestCase {
	public void testEmptyStream() throws IOException {
		final UnionInputStream u = new UnionInputStream();
		assertTrue(u.isEmpty());
		assertEquals(-1
		assertEquals(-1
		assertEquals(0
		assertEquals(0
		u.close();
	}

	public void testReadSingleBytes() throws IOException {
		final UnionInputStream u = new UnionInputStream();

		assertTrue(u.isEmpty());
		u.add(new ByteArrayInputStream(new byte[] { 1
		u.add(new ByteArrayInputStream(new byte[] { 3 }));
		u.add(new ByteArrayInputStream(new byte[] { 4

		assertFalse(u.isEmpty());
		assertEquals(3
		assertEquals(1
		assertEquals(0
		assertEquals(2
		assertEquals(0

		assertEquals(3
		assertEquals(0

		assertEquals(4
		assertEquals(1
		assertEquals(5
		assertEquals(0
		assertEquals(-1

		assertTrue(u.isEmpty());
		u.add(new ByteArrayInputStream(new byte[] { (byte) 255 }));
		assertEquals(255
		assertEquals(-1
		assertTrue(u.isEmpty());
	}

	public void testReadByteBlocks() throws IOException {
		final UnionInputStream u = new UnionInputStream();
		u.add(new ByteArrayInputStream(new byte[] { 1
		u.add(new ByteArrayInputStream(new byte[] { 3 }));
		u.add(new ByteArrayInputStream(new byte[] { 4

		final byte[] r = new byte[5];
		assertEquals(5
		assertTrue(Arrays.equals(new byte[] { 1
		assertEquals(1
		assertEquals(5
		assertEquals(-1
	}

	public void testArrayConstructor() throws IOException {
		final UnionInputStream u = new UnionInputStream(
				new ByteArrayInputStream(new byte[] { 1
				new ByteArrayInputStream(new byte[] { 3 })
				new ByteArrayInputStream(new byte[] { 4

		final byte[] r = new byte[5];
		assertEquals(5
		assertTrue(Arrays.equals(new byte[] { 1
		assertEquals(1
		assertEquals(5
		assertEquals(-1
	}

	public void testMarkSupported() {
		final UnionInputStream u = new UnionInputStream();
		assertFalse(u.markSupported());
		u.add(new ByteArrayInputStream(new byte[] { 1
		assertFalse(u.markSupported());
	}

	public void testSkip() throws IOException {
		final UnionInputStream u = new UnionInputStream();
		u.add(new ByteArrayInputStream(new byte[] { 1
		u.add(new ByteArrayInputStream(new byte[] { 3 }));
		u.add(new ByteArrayInputStream(new byte[] { 4
		assertEquals(0
		assertEquals(4
		assertEquals(4
		assertEquals(1
		assertEquals(0
		assertEquals(-1

		u.add(new ByteArrayInputStream(new byte[] { 20
			public long skip(long n) {
				return 0;
			}
		});
		assertEquals(2
		assertEquals(-1
	}

	public void testAutoCloseDuringRead() throws IOException {
		final UnionInputStream u = new UnionInputStream();
		final boolean closed[] = new boolean[2];
		u.add(new ByteArrayInputStream(new byte[] { 1 }) {
			public void close() {
				closed[0] = true;
			}
		});
		u.add(new ByteArrayInputStream(new byte[] { 2 }) {
			public void close() {
				closed[1] = true;
			}
		});

		assertFalse(closed[0]);
		assertFalse(closed[1]);

		assertEquals(1
		assertFalse(closed[0]);
		assertFalse(closed[1]);

		assertEquals(2
		assertTrue(closed[0]);
		assertFalse(closed[1]);

		assertEquals(-1
		assertTrue(closed[0]);
		assertTrue(closed[1]);
	}

	public void testCloseDuringClose() throws IOException {
		final UnionInputStream u = new UnionInputStream();
		final boolean closed[] = new boolean[2];
		u.add(new ByteArrayInputStream(new byte[] { 1 }) {
			public void close() {
				closed[0] = true;
			}
		});
		u.add(new ByteArrayInputStream(new byte[] { 2 }) {
			public void close() {
				closed[1] = true;
			}
		});

		assertFalse(closed[0]);
		assertFalse(closed[1]);

		u.close();

		assertTrue(closed[0]);
		assertTrue(closed[1]);
	}

	public void testExceptionDuringClose() {
		final UnionInputStream u = new UnionInputStream();
		u.add(new ByteArrayInputStream(new byte[] { 1 }) {
			public void close() throws IOException {
				throw new IOException("I AM A TEST");
			}
		});
		try {
			u.close();
			fail("close ignored inner stream exception");
		} catch (IOException e) {
			assertEquals("I AM A TEST"
		}
	}
}
