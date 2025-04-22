
package org.eclipse.jgit.util.io;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class AutoLFInputStreamTest {

	@Test
	public void testLF() throws IOException {
		final byte[] bytes = asBytes("1\n2\n3");
		test(bytes
	}

	@Test
	public void testCR() throws IOException {
		final byte[] bytes = asBytes("1\r2\r3");
		test(bytes
	}

	@Test
	public void testCRLF() throws IOException {
		test(asBytes("1\r\n2\r\n3")
	}

	@Test
	public void testLFCR() throws IOException {
		final byte[] bytes = asBytes("1\n\r2\n\r3");
		test(bytes
	}

	@Test
	public void testEmpty() throws IOException {
		final byte[] bytes = asBytes("");
		test(bytes
	}

	@Test
	public void testBinaryDetect() throws IOException {
		final byte[] bytes = asBytes("1\r\n2\r\n3\0");
		test(bytes
	}

	@Test
	public void testBinaryDontDetect() throws IOException {
		test(asBytes("1\r\n2\r\n3\0")
	}

	private static void test(byte[] input
			boolean detectBinary) throws IOException {
		try (InputStream bis1 = new ByteArrayInputStream(input);
				InputStream cis1 = new AutoLFInputStream(bis1
			int index1 = 0;
			for (int b = cis1.read(); b != -1; b = cis1.read()) {
				assertEquals(expected[index1]
				index1++;
			}

			assertEquals(expected.length

			for (int bufferSize = 1; bufferSize < 10; bufferSize++) {
				final byte[] buffer = new byte[bufferSize];
				try (InputStream bis2 = new ByteArrayInputStream(input);
						InputStream cis2 = new AutoLFInputStream(bis2
								detectBinary)) {

					int read = 0;
					for (int readNow = cis2.read(buffer
							buffer.length); readNow != -1
									&& read < expected.length; readNow = cis2
											.read(buffer
						for (int index2 = 0; index2 < readNow; index2++) {
							assertEquals(expected[read + index2]
									buffer[index2]);
						}
						read += readNow;
					}

					assertEquals(expected.length
				}
			}
		}
	}

	private static byte[] asBytes(String in) {
		return in.getBytes(UTF_8);
	}
}
