
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class IOTest {
	@Test
	public void testReadLine() throws Exception {
		Reader r = newReader("foo\nbar\nbaz\n");
		assertEquals("foo\n"
		assertEquals("bar\n"
		assertEquals("baz\n"
	}

	@Test
	public void testReadLineWithNoTrailingNewline() throws Exception {
		Reader r = newReader("foo\nbar\nbaz");
		assertEquals("foo\n"
		assertEquals("bar\n"
		assertEquals("baz"
	}

	@Test
	public void testReadLineWithSizeHint() throws Exception {
		Reader r = newReader("foo\nbar\nbaz\n");
		assertEquals("foo\n"
		assertEquals("bar\n"
		assertEquals("baz\n"
	}

	private static Reader newReader(String in) {
		return new InputStreamReader(
				new ByteArrayInputStream(Constants.encode(in)));
	}
}
