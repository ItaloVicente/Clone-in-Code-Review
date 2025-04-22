
package org.eclipse.jgit.util;

import static org.eclipse.jgit.junit.JGitTestUtil.getName;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.util.TemporaryBuffer.Block;
import org.junit.Test;

public class TemporaryBufferTest {
	@Test
	public void testEmpty() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		try {
			b.close();
			assertEquals(0
			final byte[] r = b.toByteArray();
			assertNotNull(r);
			assertEquals(0
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testOneByte() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte test = (byte) new TestRng(getName()).nextInt();
		try {
			b.write(test);
			b.close();
			assertEquals(1
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(1
				assertEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(1
				assertEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testOneBlock_BulkWrite() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.Block.SZ);
		try {
			b.write(test
			b.write(test
			b.write(test
			b.write(test
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testOneBlockAndHalf_BulkWrite() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.Block.SZ * 3 / 2);
		try {
			b.write(test
			b.write(test
			b.write(test
			b.write(test
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testOneBlockAndHalf_SingleWrite() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.Block.SZ * 3 / 2);
		try {
			for (int i = 0; i < test.length; i++)
				b.write(test[i]);
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testOneBlockAndHalf_Copy() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.Block.SZ * 3 / 2);
		try {
			final ByteArrayInputStream in = new ByteArrayInputStream(test);
			b.write(in.read());
			b.copy(in);
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testLarge_SingleWrite() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.DEFAULT_IN_CORE_LIMIT * 3);
		try {
			b.write(test);
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testInCoreInputStream() throws IOException {
		final int cnt = 256;
		final byte[] test = new TestRng(getName()).nextBytes(cnt);
		try (TemporaryBuffer.Heap b = new TemporaryBuffer.Heap(cnt + 4)) {
			b.write(test);
			InputStream in = b.openInputStream();
			byte[] act = new byte[cnt];
			IO.readFully(in
			assertArrayEquals(test
		}
	}

	@Test
	public void testInCoreLimit_SwitchOnAppendByte() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.DEFAULT_IN_CORE_LIMIT + 1);
		try {
			b.write(test
			b.write(test[test.length - 1]);
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testInCoreLimit_SwitchBeforeAppendByte() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.DEFAULT_IN_CORE_LIMIT * 3);
		try {
			b.write(test
			b.write(test[test.length - 1]);
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testInCoreLimit_SwitchOnCopy() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final byte[] test = new TestRng(getName())
				.nextBytes(TemporaryBuffer.DEFAULT_IN_CORE_LIMIT * 2);
		try {
			final ByteArrayInputStream in = new ByteArrayInputStream(test
					TemporaryBuffer.DEFAULT_IN_CORE_LIMIT
							- TemporaryBuffer.DEFAULT_IN_CORE_LIMIT);
			b.write(test
			b.copy(in);
			b.close();
			assertEquals(test.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(test.length
				assertArrayEquals(test
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(test.length
				assertArrayEquals(test
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testDestroyWhileOpen() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		try {
			b.write(new TestRng(getName())
					.nextBytes(TemporaryBuffer.DEFAULT_IN_CORE_LIMIT * 2));
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testRandomWrites() throws IOException {
		final TemporaryBuffer b = new TemporaryBuffer.LocalFile(null);
		final TestRng rng = new TestRng(getName());
		final int max = TemporaryBuffer.DEFAULT_IN_CORE_LIMIT * 2;
		final byte[] expect = new byte[max];
		try {
			int written = 0;
			boolean onebyte = true;
			while (written < max) {
				if (onebyte) {
					final byte v = (byte) rng.nextInt();
					b.write(v);
					expect[written++] = v;
				} else {
					final int len = Math
							.min(rng.nextInt() & 127
					final byte[] tmp = rng.nextBytes(len);
					b.write(tmp
					System.arraycopy(tmp
					written += len;
				}
				onebyte = !onebyte;
			}
			assertEquals(expect.length
			b.close();

			assertEquals(expect.length
			{
				final byte[] r = b.toByteArray();
				assertNotNull(r);
				assertEquals(expect.length
				assertArrayEquals(expect
			}
			try (ByteArrayOutputStream o = new ByteArrayOutputStream()) {
				b.writeTo(o
				final byte[] r = o.toByteArray();
				assertEquals(expect.length
				assertArrayEquals(expect
			}
		} finally {
			b.destroy();
		}
	}

	@Test
	public void testHeap() throws IOException {
		try (TemporaryBuffer b = new TemporaryBuffer.Heap(2 * 8 * 1024)) {
			final byte[] r = new byte[8 * 1024];
			b.write(r);
			b.write(r);
			try {
				b.write(1);
				fail("accepted too many bytes of data");
			} catch (IOException e) {
				assertEquals("In-memory buffer limit exceeded"
			}
		}
	}

	@Test
	public void testHeapWithEstimatedSize() throws IOException {
		int sz = 2 * Block.SZ;
		try (TemporaryBuffer b = new TemporaryBuffer.Heap(sz / 2
			for (int i = 0; i < sz; i++) {
				b.write('x');
			}
			try {
				b.write(1);
				fail("accepted too many bytes of data");
			} catch (IOException e) {
				assertEquals("In-memory buffer limit exceeded"
			}

			try (InputStream in = b.openInputStream()) {
				for (int i = 0; i < sz; i++) {
					assertEquals('x'
				}
				assertEquals(-1
			}
		}
	}
}
