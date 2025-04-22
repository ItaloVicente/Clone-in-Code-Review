
package org.eclipse.jgit.internal.storage.dfs;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Test;

public class DfsOutputStreamTest {
	@Test
	public void testReadEmpty() throws IOException {
		DfsOutputStream os = newDfsOutputStream(8);
		os.write(Constants.encode(""));
		assertEquals(""
		assertEquals(""
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testRead() throws IOException {
		DfsOutputStream os = newDfsOutputStream(8);
		os.write(Constants.encode("data"));
		assertEquals("data"
		assertEquals("data"
		assertEquals("ata"
		assertEquals("ata"
		assertEquals("ta"
		assertEquals("ta"
		assertEquals("a"
		assertEquals("a"
		assertEquals(""
		assertEquals(""
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testReadSmallBuffer() throws IOException {
		DfsOutputStream os = newDfsOutputStream(8);
		os.write(Constants.encode("data"));
		assertEquals("data"
		assertEquals("ata"
		assertEquals("ta"
		assertEquals("a"
		assertEquals(""
		assertEquals(""
	}

	@Test
	public void testReadMultipleBlocks() throws IOException {
		DfsOutputStream os = newDfsOutputStream(2);
		os.write(Constants.encode("data"));
		assertEquals("data"
		assertEquals("data"
		assertEquals("ata"
		assertEquals("ata"
		assertEquals("ta"
		assertEquals("ta"
		assertEquals("a"
		assertEquals("a"
		assertEquals(""
		assertEquals(""
		assertEquals(""
		assertEquals(""
	}

	private static DfsOutputStream newDfsOutputStream(final int blockSize) {
		return new InMemoryOutputStream() {
			@Override
			public int blockSize() {
				return blockSize;
			}
		};
	}

	private static String readBuffered(InputStream is
			throws IOException {
		StringBuilder out = new StringBuilder();
		byte[] buf = new byte[bufsize];
		while (true) {
			int n = is.read(buf
			if (n < 0)
				break;
			out.append(RawParseUtils.decode(buf
		}
		return out.toString();
	}

	private static String readBuffered(InputStream is) throws IOException {
		return readBuffered(is
	}

	private static String readByByte(InputStream is) throws IOException {
		StringBuilder out = new StringBuilder();
		while (true) {
			int c = is.read();
			if (c < 0)
				break;
			out.append(RawParseUtils.decode(new byte[] {(byte) c}));
		}
		return out.toString();
	}
}
