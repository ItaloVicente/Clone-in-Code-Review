package org.eclipse.jgit.util.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.jgit.junit.JGitTestUtil;
import org.junit.Assert;
import org.junit.Test;

public class IdentOutputStreamTest {

	private static final String BLOB_NAME_VALUE = "azertyuiopqsdfghjklmwxcvbnAZERTYUIOPQSDF";

	@Test
	public void testEmptyStream() throws IOException {
		String empty = "";
		assertExpectedOutput(empty
	}

	@Test
	public void testNoChange() throws IOException {
		String noPatternText = "azertyuiopqsdfghjklmwxcvbn";
		assertExpectedOutput(noPatternText
	}

	@Test
	public void testBasicUseCase() throws IOException {
		assertExpectedOutput("$Id: " + BLOB_NAME_VALUE + " $"
	}

	@Test
	public void testBasicUseCaseInfBuffSize() throws IOException {
		String extraText = Strings.repeat("x"
		String pattern = "$Id: " + BLOB_NAME_VALUE + " $";
		assertExpectedOutput(extraText + pattern + extraText
				+ "$Id$" + extraText);
	}

	@Test
	public void testBasicUseCaseSupBuffSize() throws IOException {
		String extraText = Strings.repeat("x"
				IdentOutputStream.BUFFER_SIZE / 2);
		String pattern = "$Id: " + BLOB_NAME_VALUE + " $";
		StringBuilder input = new StringBuilder();
		input.append(extraText);
		input.append(pattern);
		input.append(extraText);
		StringBuilder expected = new StringBuilder();
		expected.append(extraText);
		expected.append("$Id$");
		expected.append(extraText);
		assertExpectedOutput(input.toString()
	}

	@Test
	public void testNPattern() throws IOException {
		String pattern = "$Id$";
		String expectedPattern = "$Id: " + BLOB_NAME_VALUE + " $";

		StringBuilder input = new StringBuilder();
		StringBuilder expected = new StringBuilder();
		for (int separatorSize = 1; separatorSize < 11; separatorSize++) {
			String separator = Strings.repeat("x"
			for (int i = 1; i < 10; i++) {
				input.append(separator).append(pattern).append(separator);
				expected.append(separator).append(expectedPattern)
						.append(separator);
			}
			assertExpectedOutput(expected.toString()
		}
	}

	private void assertExpectedOutput(String expect
			throws IOException {
		byte[] inbytes = input.getBytes("UTF-8");
		byte[] expectBytes = expect.getBytes("UTF-8");
		for (int i = -10; i < 10; ++i) {
			int size = Math.abs(i);
			byte[] buf = new byte[size];
			InputStream in = new ByteArrayInputStream(inbytes);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OutputStream out = new IdentOutputStream(bos
					BLOB_NAME_VALUE.getBytes());
			if (i > 0) {
				int n;
				while ((n = in.read(buf)) >= 0) {
					out.write(buf
				}
			} else if (i < 0) {
				int n;
				while ((n = in.read(buf)) >= 0) {
					byte[] b = new byte[n];
					System.arraycopy(buf
					out.write(b);
				}
			} else {
				int c;
				while ((c = in.read()) != -1)
					out.write(c);
			}
			out.flush();
			in.close();
			out.close();
			byte[] actualBytes = bos.toByteArray();
			Assert.assertEquals("bufsize=" + i
					JGitTestUtil.encode(expectBytes)
					JGitTestUtil.encode(actualBytes));
		}
	}

}
