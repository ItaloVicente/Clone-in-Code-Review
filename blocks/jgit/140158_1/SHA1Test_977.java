
package org.eclipse.jgit.util.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

public class UnionInputStreamTest {
	@Test
	public void testEmptyStream() throws IOException {
		try (UnionInputStream u = new UnionInputStream()) {
			assertTrue(u.isEmpty());
			assertEquals(-1
			assertEquals(-1
			assertEquals(0
			assertEquals(0
		}
	}

	@Test
	public void testReadSingleBytes() throws IOException {
		try (UnionInputStream u = new UnionInputStream()) {
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
	}

	@Test
	public void testReadByteBlocks() throws IOException {
		try (UnionInputStream u = new UnionInputStream()) {
			u.add(new ByteArrayInputStream(new byte[] { 1
			u.add(new ByteArrayInputStream(new byte[] { 3 }));
			u.add(new ByteArrayInputStream(new byte[] { 4

			final byte[] r = new byte[5];
			assertEquals(3
			assertTrue(Arrays.equals(new byte[] { 1
			assertEquals(1
			assertEquals(3
			assertEquals(2
			assertTrue(Arrays.equals(new byte[] { 4
			assertEquals(-1
		}
	}

	private static byte[] slice(byte[] in
		byte[] r = new byte[len];
		System.arraycopy(in
		return r;
	}

	@Test
	public void testArrayConstructor() throws IOException {
		try (UnionInputStream u = new UnionInputStream(
				new ByteArrayInputStream(new byte[] { 1
				new ByteArrayInputStream(new byte[] { 3 })
				new ByteArrayInputStream(new byte[] { 4
			final byte[] r = new byte[5];
			assertEquals(3
			assertTrue(Arrays.equals(new byte[] { 1
			assertEquals(1
			assertEquals(3
			assertEquals(2
			assertTrue(Arrays.equals(new byte[] { 4
			assertEquals(-1
		}
	}

	@Test
	public void testMarkSupported() throws IOException {
		try (UnionInputStream u = new UnionInputStream()) {
			assertFalse(u.markSupported());
			u.add(new ByteArrayInputStream(new byte[] { 1
			assertFalse(u.markSupported());
		}
	}

	@Test
	public void testSkip() throws IOException {
		try (UnionInputStream u = new UnionInputStream()) {
			u.add(new ByteArrayInputStream(new byte[] { 1
			u.add(new ByteArrayInputStream(new byte[] { 3 }));
			u.add(new ByteArrayInputStream(new byte[] { 4
			assertEquals(0
			assertEquals(3
			assertEquals(3
			assertEquals(2
			assertEquals(0
			assertEquals(-1

			u.add(new ByteArrayInputStream(new byte[] { 20
				@Override
				@SuppressWarnings("UnsynchronizedOverridesSynchronized")
				public long skip(long n) {
					return 0;
				}
			});
			assertEquals(2
			assertEquals(-1
		}
	}

	@Test
	public void testAutoCloseDuringRead() throws IOException {
		try (UnionInputStream u = new UnionInputStream()) {
			final boolean closed[] = new boolean[2];
			u.add(new ByteArrayInputStream(new byte[] { 1 }) {
				@Override
				public void close() {
					closed[0] = true;
				}
			});
			u.add(new ByteArrayInputStream(new byte[] { 2 }) {
				@Override
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
	}

	@Test
	public void testCloseDuringClose() throws IOException {
		final boolean closed[] = new boolean[2];
		try (UnionInputStream u = new UnionInputStream()) {
			u.add(new ByteArrayInputStream(new byte[] { 1 }) {
				@Override
				public void close() {
					closed[0] = true;
				}
			});
			u.add(new ByteArrayInputStream(new byte[] { 2 }) {
				@Override
				public void close() {
					closed[1] = true;
				}
			});

			assertFalse(closed[0]);
			assertFalse(closed[1]);
		}

		assertTrue(closed[0]);
		assertTrue(closed[1]);
	}

	@Test
	public void testExceptionDuringClose() {
		final UnionInputStream u = new UnionInputStream();
		u.add(new ByteArrayInputStream(new byte[] { 1 }) {
			@Override
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

	@Test
	public void testNonBlockingPartialRead() throws Exception {
		InputStream errorReadStream = new InputStream() {
			@Override
			public int read() throws IOException {
				throw new IOException("Expected");
			}

			@Override
			public int read(byte b[]
				throw new IOException("Expected");
			}
		};
		try (UnionInputStream u = new UnionInputStream(
				new ByteArrayInputStream(new byte[] { 1
				errorReadStream)) {
			byte buf[] = new byte[10];
			assertEquals(3
			assertTrue(Arrays.equals(new byte[] { 1
			try {
				u.read(buf
				fail("Expected exception from errorReadStream");
			} catch (IOException e) {
				assertEquals("Expected"
			}
		}
	}
}
