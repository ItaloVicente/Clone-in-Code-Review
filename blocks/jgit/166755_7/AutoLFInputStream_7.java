
package org.eclipse.jgit.util.io;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

public class AutoLFOutputStreamTest {

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
	public void testCRLFNoDetect() throws IOException {
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

	@Test
	public void testCrLfDetect() throws IOException {
		byte[] bytes = asBytes("1\r\n2\n3\r\n\r");
		test(bytes
	}

	private static void test(byte[] input
			boolean detectBinary) throws IOException {
		try (ByteArrayOutputStream result = new ByteArrayOutputStream();
				OutputStream out = new AutoLFOutputStream(result
						detectBinary)) {
			out.write(input);
			out.close();
			assertArrayEquals(expected
		}
	}

	private static byte[] asBytes(String in) {
		return in.getBytes(UTF_8);
	}
}
