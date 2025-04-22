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
		String input = "Case read they must it of cold that.\n Speaking trifling an to unpacked moderate debating learning.\n An particular contrasted he excellence favourable on. Nay preference dispatched difficulty continuing joy one. Songs it be if ought hoped of.\n Too carriage attended him entrance desirous the saw.\n Twenty sister hearts garden limits put gay has. We hill lady will both sang room by. Desirous men exercise overcame procured speaking her followed. ";
		final byte[] bytes = asBytes(input);
		test(bytes
	}

	@Test
	public void testBasicUseCase() throws IOException {
		test(asBytes("$Id: fakeId0000000000000000000000000000000000 $")
				asBytes("$Id$")
	}

	@Test
	public void testIncorrectBlobName() throws IOException {
		final byte[] bytes = asBytes("$Id:fakeId$kkkk");
		test(bytes
	}

	@Test
	public void testNonWorkingNestedPattern() throws IOException {
		final byte[] bytes = asBytes("$Id:fake$Id:$");
		test(bytes
	}

	@Test
	public void testNonWorkingNestedPattern2() throws IOException {
		final byte[] bytes = asBytes("$Id:fake$Id:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx$");
		test(bytes
	}

	@Test
	public void testWorkingIncludingNestedPattern() throws IOException {
		String blobName = "xxfake$Id: xxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
		final byte[] bytes = asBytes("$Id: " + blobName + " $xxxxx");
		test(bytes
	}

	@Test
	public void testWorkingIncludedNestedPattern() throws IOException {
		String blobName = "$Id: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx $";
		final byte[] bytes = asBytes("$Id: xxfake" + blobName + "xxxxxx");
		test(bytes
	}

	@Test
	public void testBoundary() throws IOException {
		for (int i = IdentInputStream.BUFFER_SIZE - 10; i < IdentInputStream.BUFFER_SIZE + 10; i++) {
			String s1 = Strings.repeat("a"
			test(asBytes(s1)
		}
	}

	private static void test(byte[] input
			throws IOException {
		final InputStream inputStream = new ByteArrayInputStream(input);
		final InputStream identStream = new IdentInputStream(inputStream
				detectBinary);
		int index1 = 0;
		for (int b = identStream.read(); b != -1; b = identStream.read()) {
			assertEquals(new Character((char) expected[index1])
					(char) b));
			index1++;
		}

		assertEquals(expected.length

		for (int bufferSize = 1; bufferSize < 10; bufferSize++) {
			final byte[] buffer = new byte[bufferSize];
			final InputStream inputStream2 = new ByteArrayInputStream(input);
			final InputStream identStream2 = new IdentInputStream(inputStream2
					detectBinary);

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
