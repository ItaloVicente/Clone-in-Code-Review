
package org.eclipse.jgit.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

public class EolCanonicalizingInputStreamTest extends TestCase {

	public void testLF() throws IOException {
		final byte[] bytes = asBytes("1\n2\n3");
		test(bytes
	}

	public void testCR() throws IOException {
		final byte[] bytes = asBytes("1\r2\r3");
		test(bytes
	}

	public void testCRLF() throws IOException {
		test(asBytes("1\r\n2\r\n3")
	}

	public void testLFCR() throws IOException {
		final byte[] bytes = asBytes("1\n\r2\n\r3");
		test(bytes
	}

	private void test(byte[] input
		final InputStream bis1 = new ByteArrayInputStream(input);
		final InputStream cis1 = new EolCanonicalizingInputStream(bis1);
		int index1 = 0;
		for (int b = cis1.read(); b != -1; b = cis1.read()) {
			assertEquals(expected[index1]
			index1++;
		}

		assertEquals(expected.length

		for (int bufferSize = 1; bufferSize < 10; bufferSize++) {
			final byte[] buffer = new byte[bufferSize];
			final InputStream bis2 = new ByteArrayInputStream(input);
			final InputStream cis2 = new EolCanonicalizingInputStream(bis2);

			int read = 0;
			for (int readNow = cis2.read(buffer
					&& read < expected.length; readNow = cis2.read(buffer
					buffer.length)) {
				for (int index2 = 0; index2 < readNow; index2++) {
					assertEquals(expected[read + index2]
				}
				read += readNow;
			}

			assertEquals(expected.length
		}
	}

	private static byte[] asBytes(String in) {
		try {
			return in.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			throw new AssertionError();
		}
	}
}
