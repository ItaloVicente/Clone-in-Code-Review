package org.eclipse.jgit.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.Test;

public class IOTest {

	private static final byte[] DATA = "abcdefghijklmnopqrstuvwxyz"
			.getBytes(StandardCharsets.US_ASCII);

	private byte[] initBuffer(int size) {
		byte[] buffer = new byte[size];
		for (int i = 0; i < size; i++) {
			buffer[i] = (byte) ('0' + (i % 10));
		}
		return buffer;
	}

	private int read(byte[] buffer
		try (InputStream in = new ByteArrayInputStream(DATA)) {
			return IO.readFully(in
		}
	}

	@Test
	public void readFullyBufferShorter() throws Exception {
		byte[] buffer = initBuffer(9);
		int length = read(buffer
		assertEquals(buffer.length
		assertArrayEquals(buffer
	}

	@Test
	public void readFullyBufferLonger() throws Exception {
		byte[] buffer = initBuffer(50);
		byte[] initial = Arrays.copyOf(buffer
		int length = read(buffer
		assertEquals(DATA.length
		assertArrayEquals(Arrays.copyOfRange(buffer
		assertArrayEquals(Arrays.copyOfRange(buffer
				Arrays.copyOfRange(initial
	}

	@Test
	public void readFullyBufferShorterOffset() throws Exception {
		byte[] buffer = initBuffer(9);
		byte[] initial = Arrays.copyOf(buffer
		int length = read(buffer
		assertEquals(3
		assertArrayEquals(Arrays.copyOfRange(buffer
				Arrays.copyOfRange(initial
		assertArrayEquals(Arrays.copyOfRange(buffer
				Arrays.copyOfRange(DATA
	}

	@Test
	public void readFullyBufferLongerOffset() throws Exception {
		byte[] buffer = initBuffer(50);
		byte[] initial = Arrays.copyOf(buffer
		int length = read(buffer
		assertEquals(10
		assertArrayEquals(Arrays.copyOfRange(buffer
				Arrays.copyOfRange(initial
		assertArrayEquals(Arrays.copyOfRange(buffer
				Arrays.copyOfRange(DATA
	}
}
