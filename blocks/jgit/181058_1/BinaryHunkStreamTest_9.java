package org.eclipse.jgit.util.io;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;

import org.junit.Test;

public class BinaryDeltaInputStreamTest {

	private InputStream getBinaryHunk(String name) {
		return this.getClass().getResourceAsStream(name);
	}

	@Test
	public void testBinaryDelta() throws Exception {
		byte[] data = new byte[8192];
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (255 - (i % 256));
		}
		int middle = data.length / 2;
		byte[] newData = new byte[data.length + 5];
		System.arraycopy(data
		for (int i = 0; i < 5; i++) {
			newData[middle + i] = 'x';
		}
		System.arraycopy(data
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				BinaryDeltaInputStream input = new BinaryDeltaInputStream(data
						new InflaterInputStream(new BinaryHunkInputStream(
								getBinaryHunk("delta1.forward"))))) {
			byte[] buf = new byte[1024];
			int n;
			while ((n = input.read(buf)) >= 0) {
				out.write(buf
			}
			assertArrayEquals(newData
			assertTrue(input.isFullyConsumed());
		}
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				BinaryDeltaInputStream input = new BinaryDeltaInputStream(
						newData
						new InflaterInputStream(new BinaryHunkInputStream(
								getBinaryHunk("delta1.reverse"))))) {
			long expectedSize = input.getExpectedResultSize();
			assertEquals(data.length
			byte[] buf = new byte[1024];
			int n;
			while ((n = input.read(buf)) >= 0) {
				out.write(buf
			}
			assertArrayEquals(data
			assertTrue(input.isFullyConsumed());
		}
	}
}
