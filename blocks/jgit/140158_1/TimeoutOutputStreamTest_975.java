
package org.eclipse.jgit.util.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.util.IO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeoutInputStreamTest {
	private static final int timeout = 250;

	private PipedOutputStream out;

	private PipedInputStream in;

	private InterruptTimer timer;

	private TimeoutInputStream is;

	private long start;

	@Before
	public void setUp() throws Exception {
		out = new PipedOutputStream();
		in = new PipedInputStream(out);
		timer = new InterruptTimer();
		is = new TimeoutInputStream(in
		is.setTimeout(timeout);
	}

	@After
	public void tearDown() throws Exception {
		timer.terminate();
		for (Thread t : active())
			assertFalse(t instanceof InterruptTimer.AlarmThread);
	}

	@Test
	public void testTimeout_readByte_Success1() throws IOException {
		out.write('a');
		assertEquals('a'
	}

	@Test
	public void testTimeout_readByte_Success2() throws IOException {
		final byte[] exp = new byte[] { 'a'
		out.write(exp);
		assertEquals(exp[0]
		assertEquals(exp[1]
		assertEquals(exp[2]
		out.close();
		assertEquals(-1
	}

	@Test
	public void testTimeout_readByte_Timeout() throws IOException {
		beginRead();
		try {
			is.read();
			fail("incorrectly read a byte");
		} catch (InterruptedIOException e) {
		}
		assertTimeout();
	}

	@Test
	public void testTimeout_readBuffer_Success1() throws IOException {
		final byte[] exp = new byte[] { 'a'
		final byte[] act = new byte[exp.length];
		out.write(exp);
		IO.readFully(is
		assertArrayEquals(exp
	}

	@Test
	public void testTimeout_readBuffer_Success2() throws IOException {
		final byte[] exp = new byte[] { 'a'
		final byte[] act = new byte[exp.length];
		out.write(exp);
		IO.readFully(is
		IO.readFully(is
		IO.readFully(is
		assertArrayEquals(exp
	}

	@Test
	public void testTimeout_readBuffer_Timeout() throws IOException {
		beginRead();
		try {
			IO.readFully(is
			fail("incorrectly read bytes");
		} catch (InterruptedIOException e) {
		}
		assertTimeout();
	}

	@Test
	public void testTimeout_skip_Success() throws IOException {
		final byte[] exp = new byte[] { 'a'
		out.write(exp);
		assertEquals(2
		assertEquals('c'
	}

	@Test
	public void testTimeout_skip_Timeout() throws IOException {
		beginRead();
		try {
			is.skip(1024);
			fail("incorrectly skipped bytes");
		} catch (InterruptedIOException e) {
		}
		assertTimeout();
	}

	private void beginRead() {
		start = now();
	}

	private void assertTimeout() {
		final long wait = now() - start;
		assertTrue("waited only " + wait + " ms"
	}

	private static List<Thread> active() {
		Thread[] all = new Thread[16];
		int n = Thread.currentThread().getThreadGroup().enumerate(all);
		while (n == all.length) {
			all = new Thread[all.length * 2];
			n = Thread.currentThread().getThreadGroup().enumerate(all);
		}
		return Arrays.asList(all).subList(0
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}
