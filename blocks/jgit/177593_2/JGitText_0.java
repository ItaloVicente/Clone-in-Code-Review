package org.eclipse.jgit.util.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class BinaryHunkStreamTest {

	@Test
	public void testRoundtripWholeBuffer() throws IOException {
		for (int length = 1; length < 520 + 52; length++) {
			byte[] data = new byte[length];
			for (int i = 0; i < data.length; i++) {
				data[i] = (byte) (255 - (i % 256));
			}
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
					BinaryHunkOutputStream out = new BinaryHunkOutputStream(
							bos)) {
				out.write(data);
				out.flush();
				byte[] encoded = bos.toByteArray();
				assertFalse(Arrays.equals(data
				try (BinaryHunkInputStream in = new BinaryHunkInputStream(
						new ByteArrayInputStream(encoded))) {
					byte[] decoded = new byte[data.length];
					int newLength = in.read(decoded);
					assertEquals(newLength
					assertEquals(-1
					assertArrayEquals(data
				}
			}
		}
	}

	@Test
	public void testRoundtripChunks() throws IOException {
		for (int length = 1; length < 520 + 52; length++) {
			byte[] data = new byte[length];
			for (int i = 0; i < data.length; i++) {
				data[i] = (byte) (255 - (i % 256));
			}
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
					BinaryHunkOutputStream out = new BinaryHunkOutputStream(
							bos)) {
				out.write(data
				out.write(data
				out.flush();
				byte[] encoded = bos.toByteArray();
				assertFalse(Arrays.equals(data
				try (BinaryHunkInputStream in = new BinaryHunkInputStream(
						new ByteArrayInputStream(encoded))) {
					byte[] decoded = new byte[data.length];
					int p = 0;
					int n;
					while ((n = in.read(decoded
							Math.min(decoded.length - p
						p += n;
						if (p == decoded.length) {
							break;
						}
					}
					assertEquals(p
					assertEquals(-1
					assertArrayEquals(data
				}
			}
		}
	}

	@Test
	public void testRoundtripBytes() throws IOException {
		for (int length = 1; length < 520 + 52; length++) {
			byte[] data = new byte[length];
			for (int i = 0; i < data.length; i++) {
				data[i] = (byte) (255 - (i % 256));
			}
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
					BinaryHunkOutputStream out = new BinaryHunkOutputStream(
							bos)) {
				for (int i = 0; i < data.length; i++) {
					out.write(data[i]);
				}
				out.flush();
				byte[] encoded = bos.toByteArray();
				assertFalse(Arrays.equals(data
				try (BinaryHunkInputStream in = new BinaryHunkInputStream(
						new ByteArrayInputStream(encoded))) {
					byte[] decoded = new byte[data.length];
					for (int i = 0; i < decoded.length; i++) {
						int val = in.read();
						assertTrue(0 <= val && val <= 255);
						decoded[i] = (byte) val;
					}
					assertEquals(-1
					assertArrayEquals(data
				}
			}
		}
	}

	@Test
	public void testRoundtripWithClose() throws IOException {
		for (int length = 1; length < 520 + 52; length++) {
			byte[] data = new byte[length];
			for (int i = 0; i < data.length; i++) {
				data[i] = (byte) (255 - (i % 256));
			}
			try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
				try (BinaryHunkOutputStream out = new BinaryHunkOutputStream(
						bos)) {
					out.write(data);
				}
				byte[] encoded = bos.toByteArray();
				assertFalse(Arrays.equals(data
				try (BinaryHunkInputStream in = new BinaryHunkInputStream(
						new ByteArrayInputStream(encoded))) {
					byte[] decoded = new byte[data.length];
					int newLength = in.read(decoded);
					assertEquals(newLength
					assertEquals(-1
					assertArrayEquals(data
				}
			}
		}
	}
}
