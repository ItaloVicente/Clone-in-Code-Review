
package org.eclipse.jgit.util.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import junit.framework.TestCase;

public class EolCanonicalizingInputStreamTest extends TestCase {

	public void testLF() throws IOException {
		final byte[] bytes = new byte[] { (byte)1
		test(bytes
	}

	public void testCR() throws IOException {
		final byte[] input = new byte[] { (byte)1
		final byte[] expected = new byte[] { (byte)1
		test(input
	}

	public void testCRLF() throws IOException {
		final byte[] input = new byte[] { (byte)1
		final byte[] expected = new byte[] { (byte)1
		test(input
	}

	public void testLFCR() throws IOException {
		final byte[] input = new byte[] { (byte)1
		final byte[] expected = new byte[] { (byte)1
		test(input
	}

	private void test(byte[] input
		final ByteArrayInputStream bis1 = new ByteArrayInputStream(input);
		final EolCanonicalizingInputStream cis1 = new EolCanonicalizingInputStream(bis1);
		int index1 = 0;
		for (int b = cis1.read(); b != -1; b = cis1.read()) {
			assertEquals(expected[index1]
			index1++;
		}

		assertEquals(expected.length

		for (int bufferSize = 1; bufferSize < 10; bufferSize++) {
			final byte[] buffer = new byte[bufferSize];
			final ByteArrayInputStream bis2 = new ByteArrayInputStream(input);
			final EolCanonicalizingInputStream cis2 = new EolCanonicalizingInputStream(bis2);

			int read = 0;
			for (int readNow = cis2.read(buffer
				for (int index2 = 0; index2 < readNow; index2++) {
					assertEquals(expected[read + index2]
				}
				read += readNow;
			}

			assertEquals(expected.length
		}
	}
}
