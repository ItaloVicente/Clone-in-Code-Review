
package org.eclipse.jgit.util.io;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Assert;
import org.junit.Test;

public class AutoCRLFOutputStreamTest {

	@Test
	public void test() throws IOException {
		assertNoCrLf(""
		assertNoCrLf("\r"
		assertNoCrLf("\r\n"
		assertNoCrLf("\r\n"
		assertNoCrLf("\r\r"
		assertNoCrLf("\r\n\r"
		assertNoCrLf("\r\n\r\r"
		assertNoCrLf("\r\n\r\n"
		assertNoCrLf("\r\n\r\n\r"
		assertNoCrLf("\0\n"
	}

	@Test
	public void testBoundary() throws IOException {
		for (int i = AutoCRLFOutputStream.BUFFER_SIZE - 10; i < AutoCRLFOutputStream.BUFFER_SIZE + 10; i++) {
			String s1 = Strings.repeat("a"
			assertNoCrLf(s1
			String s2 = Strings.repeat("\0"
			assertNoCrLf(s2
		}
	}

	private void assertNoCrLf(String string
		assertNoCrLfHelper(string
		assertNoCrLfHelper("\u00e5" + string
		assertNoCrLfHelper("\u00e5" + string + "\u00e5"
				+ "\u00e5");
		assertNoCrLfHelper(string + "\u00e5"
	}

	private void assertNoCrLfHelper(String expect
			throws IOException {
		byte[] inbytes = input.getBytes(UTF_8);
		byte[] expectBytes = expect.getBytes(UTF_8);
		for (int i = -4; i < 5; ++i) {
			int size = Math.abs(i);
			byte[] buf = new byte[size];
			try (InputStream in = new ByteArrayInputStream(inbytes);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					OutputStream out = new AutoCRLFOutputStream(bos)) {
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
				byte[] actualBytes = bos.toByteArray();
				Assert.assertEquals("bufsize=" + size
						encode(actualBytes));
			}
		}
	}

	String encode(byte[] in) {
		StringBuilder str = new StringBuilder();
		for (byte b : in) {
			if (b < 32)
				str.append(0xFF & b);
			else {
				str.append("'");
				str.append((char) b);
				str.append("'");
			}
			str.append(' ');
		}
		return str.toString();
	}

}
