
package org.eclipse.jgit.transport;

import static java.lang.Integer.valueOf;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_DATA;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_ERROR;
import static org.eclipse.jgit.transport.SideBandOutputStream.CH_PROGRESS;
import static org.eclipse.jgit.transport.SideBandOutputStream.HDR_SIZE;
import static org.eclipse.jgit.transport.SideBandOutputStream.MAX_BUF;
import static org.eclipse.jgit.transport.SideBandOutputStream.SMALL_BUF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

import org.eclipse.jgit.internal.JGitText;
import org.junit.Before;
import org.junit.Test;


public class SideBandOutputStreamTest {
	private ByteArrayOutputStream rawOut;

	@Before
	public void setUp() throws Exception {
		rawOut = new ByteArrayOutputStream();
	}

	@Test
	public void testWrite_CH_DATA() throws IOException {
		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				SMALL_BUF
			out.write(new byte[] { 'a'
			out.flush();
		}
		assertBuffer("0008\001abc");
	}

	@Test
	public void testWrite_CH_PROGRESS() throws IOException {
		try (SideBandOutputStream out = new SideBandOutputStream(CH_PROGRESS
				SMALL_BUF
			out.write(new byte[] { 'a'
			out.flush();
		}
		assertBuffer("0008\002abc");
	}

	@Test
	public void testWrite_CH_ERROR() throws IOException {
		try (SideBandOutputStream out = new SideBandOutputStream(CH_ERROR
				SMALL_BUF
			out.write(new byte[] { 'a'
			out.flush();
		}
		assertBuffer("0008\003abc");
	}

	@Test
	public void testWrite_Small() throws IOException {
		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				SMALL_BUF
			out.write('a');
			out.write('b');
			out.write('c');
			out.flush();
		}
		assertBuffer("0008\001abc");
	}

	@Test
	public void testWrite_SmallBlocks1() throws IOException {
		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				rawOut)) {
			out.write('a');
			out.write('b');
			out.write('c');
			out.flush();
		}
		assertBuffer("0006\001a0006\001b0006\001c");
	}

	@Test
	public void testWrite_SmallBlocks2() throws IOException {
		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				rawOut)) {
			out.write(new byte[] { 'a'
			out.flush();
		}
		assertBuffer("0006\001a0006\001b0006\001c");
	}

	@Test
	public void testWrite_SmallBlocks3() throws IOException {
		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				rawOut)) {
			out.write('a');
			out.write(new byte[] { 'b'
			out.flush();
		}
		assertBuffer("0007\001ab0006\001c");
	}

	@Test
	public void testWrite_Large() throws IOException {
		final int buflen = MAX_BUF - HDR_SIZE;
		final byte[] buf = new byte[buflen];
		for (int i = 0; i < buf.length; i++) {
			buf[i] = (byte) i;
		}

		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				MAX_BUF
			out.write(buf);
			out.flush();
		}

		final byte[] act = rawOut.toByteArray();
		final String explen = Integer.toString(buf.length + HDR_SIZE
		assertEquals(HDR_SIZE + buf.length
		assertEquals(new String(act
		assertEquals(1
		for (int i = 0
			assertEquals(buf[i]
		}
	}

	@Test
	public void testFlush() throws IOException {
		final int[] flushCnt = new int[1];
		final OutputStream mockout = new OutputStream() {
			@Override
			public void write(int arg0) throws IOException {
				fail("should not write");
			}

			@Override
			public void flush() throws IOException {
				flushCnt[0]++;
			}
		};

		try (SideBandOutputStream out = new SideBandOutputStream(CH_DATA
				SMALL_BUF
			out.flush();
		}
		assertEquals(1
	}

	private void createSideBandOutputStream(int chan
			throws Exception {
		try (SideBandOutputStream s = new SideBandOutputStream(chan
		}
	}

	@Test
	public void testConstructor_RejectsBadChannel() throws Exception {
		try {
			createSideBandOutputStream(-1
			fail("Accepted -1 channel number");
		} catch (IllegalArgumentException e) {
			assertEquals("channel -1 must be in range [1
		}

		try {
			createSideBandOutputStream(0
			fail("Accepted 0 channel number");
		} catch (IllegalArgumentException e) {
			assertEquals("channel 0 must be in range [1
		}

		try {
			createSideBandOutputStream(256
			fail("Accepted 256 channel number");
		} catch (IllegalArgumentException e) {
			assertEquals("channel 256 must be in range [1
					.getMessage());
		}
	}

	@Test
	public void testConstructor_RejectsBadBufferSize() throws Exception {
		try {
			createSideBandOutputStream(CH_DATA
			fail("Accepted -1 for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals("packet size -1 must be >= 5"
		}

		try {
			createSideBandOutputStream(CH_DATA
			fail("Accepted 0 for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals("packet size 0 must be >= 5"
		}

		try {
			createSideBandOutputStream(CH_DATA
			fail("Accepted 1 for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals("packet size 1 must be >= 5"
		}

		try {
			createSideBandOutputStream(CH_DATA
			fail("Accepted " + Integer.MAX_VALUE + " for buffer size");
		} catch (IllegalArgumentException e) {
			assertEquals(MessageFormat.format(
					JGitText.get().packetSizeMustBeAtMost
					valueOf(Integer.MAX_VALUE)
		}
	}

	private void assertBuffer(String exp) {
		assertEquals(exp
	}
}
