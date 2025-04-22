
package org.eclipse.jgit.util.io;

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
		byte[] inbytes = input.getBytes();
		byte[] expectBytes = expect.getBytes();
		for (int i = 0; i < 5; ++i) {
			byte[] buf = new byte[i];
			InputStream in = new ByteArrayInputStream(inbytes);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OutputStream out = new AutoCRLFOutputStream(bos);
			if (i > 0) {
				int n;
				while ((n = in.read(buf)) >= 0) {
					out.write(buf
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
					encode(actualBytes));
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
