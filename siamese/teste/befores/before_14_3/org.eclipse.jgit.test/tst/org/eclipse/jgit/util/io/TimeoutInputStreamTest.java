/*
 * Copyright (C) 2009, Google Inc.
 * and other copyright owners as documented in the project's IP log.
 *
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Distribution License v1.0 which
 * accompanies this distribution, is reproduced below, and is
 * available at http://www.eclipse.org/org/documents/edl-v10.php
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or
 * without modification, are permitted provided that the following
 * conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the following
 *   disclaimer in the documentation and/or other materials provided
 *   with the distribution.
 *
 * - Neither the name of the Eclipse Foundation, Inc. nor the
 *   names of its contributors may be used to endorse or promote
 *   products derived from this software without specific prior
 *   written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
 * CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eclipse.jgit.util.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.io.InterruptTimer;
import org.eclipse.jgit.util.io.TimeoutInputStream;

import junit.framework.TestCase;

public class TimeoutInputStreamTest extends TestCase {
	private static final int timeout = 250;

	private PipedOutputStream out;

	private PipedInputStream in;

	private InterruptTimer timer;

	private TimeoutInputStream is;

	private long start;

	protected void setUp() throws Exception {
		super.setUp();
		out = new PipedOutputStream();
		in = new PipedInputStream(out);
		timer = new InterruptTimer();
		is = new TimeoutInputStream(in, timer);
		is.setTimeout(timeout);
	}

	protected void tearDown() throws Exception {
		timer.terminate();
		for (Thread t : active())
			assertFalse(t instanceof InterruptTimer.AlarmThread);
		super.tearDown();
	}

	public void testTimeout_readByte_Success1() throws IOException {
		out.write('a');
		assertEquals('a', is.read());
	}

	public void testTimeout_readByte_Success2() throws IOException {
		final byte[] exp = new byte[] { 'a', 'b', 'c' };
		out.write(exp);
		assertEquals(exp[0], is.read());
		assertEquals(exp[1], is.read());
		assertEquals(exp[2], is.read());
		out.close();
		assertEquals(-1, is.read());
	}

	public void testTimeout_readByte_Timeout() throws IOException {
		beginRead();
		try {
			is.read();
			fail("incorrectly read a byte");
		} catch (InterruptedIOException e) {
			// expected
		}
		assertTimeout();
	}

	public void testTimeout_readBuffer_Success1() throws IOException {
		final byte[] exp = new byte[] { 'a', 'b', 'c' };
		final byte[] act = new byte[exp.length];
		out.write(exp);
		IO.readFully(is, act, 0, act.length);
		assertTrue(Arrays.equals(exp, act));
	}

	public void testTimeout_readBuffer_Success2() throws IOException {
		final byte[] exp = new byte[] { 'a', 'b', 'c' };
		final byte[] act = new byte[exp.length];
		out.write(exp);
		IO.readFully(is, act, 0, 1);
		IO.readFully(is, act, 1, 1);
		IO.readFully(is, act, 2, 1);
		assertTrue(Arrays.equals(exp, act));
	}

	public void testTimeout_readBuffer_Timeout() throws IOException {
		beginRead();
		try {
			IO.readFully(is, new byte[512], 0, 512);
			fail("incorrectly read bytes");
		} catch (InterruptedIOException e) {
			// expected
		}
		assertTimeout();
	}

	public void testTimeout_skip_Success() throws IOException {
		final byte[] exp = new byte[] { 'a', 'b', 'c' };
		out.write(exp);
		assertEquals(2, is.skip(2));
		assertEquals('c', is.read());
	}

	public void testTimeout_skip_Timeout() throws IOException {
		beginRead();
		try {
			is.skip(1024);
			fail("incorrectly skipped bytes");
		} catch (InterruptedIOException e) {
			// expected
		}
		assertTimeout();
	}

	private void beginRead() {
		start = now();
	}

	private void assertTimeout() {
		// Our timeout was supposed to be ~250 ms. Since this is a timing
		// test we can't assume we spent *exactly* the timeout period, as
		// there may be other activity going on in the system. Instead we
		// look for the delta between the start and end times to be within
		// 50 ms of the expected timeout.
		//
		final long wait = now() - start;
		assertTrue(Math.abs(wait - timeout) < 50);
	}

	private static List<Thread> active() {
		Thread[] all = new Thread[16];
		int n = Thread.currentThread().getThreadGroup().enumerate(all);
		while (n == all.length) {
			all = new Thread[all.length * 2];
			n = Thread.currentThread().getThreadGroup().enumerate(all);
		}
		return Arrays.asList(all).subList(0, n);
	}

	private static long now() {
		return System.currentTimeMillis();
	}
}
