package org.eclipse.jgit.util.io;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class IdentInputStreamTest {

	@Test
	public void testEmtyStream() throws IOException {
		final byte[] bytes = asBytes("");
		test(bytes
	}

	@Test
	public void testNoChange() throws IOException {
				+ " Twenty sister hearts garden limits put gay has. We hill lady will both sang room by. Desirous men exercise overcame procured speaking her followed. ";
		final byte[] bytes = asBytes(input);
		test(bytes
		test(bytes
	}

	@Test
	public void testBasicUseCase() throws IOException {
		String input = "$Id: 3.14159265358979323846264338327950288419 $";
		String expected = "$Id$";
		test(asBytes(input)
		test(asBytes(input)
	}

	@Test
	public void testEmptyPattern() throws IOException {
		final byte[] bytes = asBytes("$Id$");
		test(bytes
	}

	@Test
	public void testNonCompletePattern() throws IOException {
		final byte[] bytes = asBytes("$Id:abcdefg");
		test(bytes
	}

	@Test
	public void testSuccessivePattern() throws IOException {
		final byte[] bytes = asBytes("$Id:fake$Id:3.14159265358979323846264338327950288419716939937510582097494459230$");
		test(bytes
				asBytes("$Id$Id:3.14159265358979323846264338327950288419716939937510582097494459230$"));
		test(bytes
				asBytes("$Id$Id:3.14159265358979323846264338327950288419716939937510582097494459230$")
				8);
		test(bytes
				asBytes("$Id$Id:3.14159265358979323846264338327950288419716939937510582097494459230$")
				9);
	}

	@Test
	public void testSuccessivePattern2() throws IOException {
		final byte[] bytes = asBytes("$Id: xxfake$$Id: 3.1415926535897932384626433832795028841971693993751 $3.1415926535897932384626433832795028841971693993751");
		test(bytes
				asBytes("$Id$$Id$3.1415926535897932384626433832795028841971693993751"));
		test(bytes
				asBytes("$Id$$Id$3.1415926535897932384626433832795028841971693993751")
				11);
		test(bytes
				asBytes("$Id$$Id$3.1415926535897932384626433832795028841971693993751")
				12);
	}

	@Test
	public void testSuccessivePattern3() throws IOException {
		final byte[] bytes = asBytes("$Id: xxfake3.141592653589793$$Id: 3.1415926535897932384626433832795028841971693993751 $3.1415926535897932384626433832795028841971693993751");
		test(bytes
				asBytes("$Id$$Id$3.1415926535897932384626433832795028841971693993751"));
		test(bytes
				asBytes("$Id: xxfake3.141592653589793$$Id: 3.1415926535897932384626433832795028841971693993751 $3.1415926535897932384626433832795028841971693993751")
				11
	}

	@Test
	public void testMemoryBounderies() throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append("$Id: ");
		contentBuilder.append(Strings.repeat("x"
				IdentInputStream.DEFAULT_MEMORY_BUFFER_SIZE + 1));
		String content = contentBuilder.toString();
		test(asBytes(content)
	}

	@Test
	public void testMemoryBounderies2() throws IOException {
		StringBuilder contentBuilder = new StringBuilder();
		contentBuilder.append("$Id:");
		contentBuilder.append(Strings.repeat("x"
				IdentInputStream.DEFAULT_MEMORY_BUFFER_SIZE - 1));
		contentBuilder.append("$");
		String content = contentBuilder.toString();
		test(asBytes(content)
		test(asBytes(content)
	}

	@Test
	public void testBoundaries() throws IOException {
		for (int i = IdentInputStream.DEFAULT_BUFFER_SIZE - 10; i < IdentInputStream.DEFAULT_BUFFER_SIZE + 10; i++) {
			String s1 = Strings.repeat("a"
			test(asBytes(s1)
		}
	}

	static String toString(java.io.InputStream is) throws IOException {
		java.util.Scanner scanner = new java.util.Scanner(is
		scanner.useDelimiter("\\A");
		try {
			String result = scanner.hasNext() ? scanner.next() : "";
			return result;
		} finally {
			scanner.close();
			is.close();
		}
	}

	private static void test(byte[] input
			throws IOException {
		test(input
				IdentInputStream.DEFAULT_BUFFER_SIZE
				IdentInputStream.DEFAULT_MEMORY_BUFFER_SIZE);
	}

	private static void test(byte[] input
			throws IOException {
		test(input
				IdentInputStream.DEFAULT_MEMORY_BUFFER_SIZE);
	}

	private static void test(byte[] input
			int streamBufferSize
			int streamMemoryBufferSize)
			throws IOException {
		final InputStream inputStream = new ByteArrayInputStream(input);
		final InputStream identStream = new IdentInputStream(inputStream
				false
		assertEquals(toString(new ByteArrayInputStream(expected))
				toString(identStream));


		for (int bufferSize = 1; bufferSize < 10; bufferSize++) {
			final byte[] buffer = new byte[bufferSize];
			final InputStream inputStream2 = new ByteArrayInputStream(input);
			final InputStream identStream2 = new IdentInputStream(inputStream2
					false

			int read = 0;
			for (int readNow = identStream2.read(buffer
					&& read < expected.length; readNow = identStream2.read(
					buffer
				for (int index2 = 0; index2 < readNow; index2++) {
					assertEquals(expected[read + index2]
				}
				read += readNow;
			}

			assertEquals(expected.length
			identStream2.close();
		}
		identStream.close();
	}

	private static byte[] asBytes(String in) {
		try {
			return in.getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			throw new AssertionError();
		}
	}
}
