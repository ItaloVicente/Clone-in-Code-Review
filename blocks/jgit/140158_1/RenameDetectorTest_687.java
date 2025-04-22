
package org.eclipse.jgit.diff;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Test;

public class RawTextTest {
	@Test
	public void testEmpty() {
		final RawText r = new RawText(new byte[0]);
		assertEquals(0
	}

	@Test
	public void testNul() {
		String input = "foo-a\nf\0o-b\n";
		byte[] data = Constants.encodeASCII(input);
		final RawText a = new RawText(data);
		assertArrayEquals(a.content
		assertEquals(2
		assertEquals("foo-a\n"
		assertEquals("f\0o-b\n"
		assertEquals("foo-a"
		assertEquals("f\0o-b"
	}

	@Test
	public void testCrLfTextYes() {
		assertTrue(RawText
				.isCrLfText(Constants.encodeASCII("line 1\r\nline 2\r\n")));
	}

	@Test
	public void testCrLfTextNo() {
		assertFalse(
				RawText.isCrLfText(Constants.encodeASCII("line 1\nline 2\n")));
	}

	@Test
	public void testCrLfTextBinary() {
		assertFalse(RawText
				.isCrLfText(Constants.encodeASCII("line 1\r\nline\0 2\r\n")));
	}

	@Test
	public void testCrLfTextMixed() {
		assertTrue(RawText
				.isCrLfText(Constants.encodeASCII("line 1\nline 2\r\n")));
	}

	@Test
	public void testCrLfTextCutShort() {
		assertFalse(
				RawText.isCrLfText(Constants.encodeASCII("line 1\nline 2\r")));
	}

	@Test
	public void testEquals() {
		final RawText a = new RawText(Constants.encodeASCII("foo-a\nfoo-b\n"));
		final RawText b = new RawText(Constants.encodeASCII("foo-b\nfoo-c\n"));
		RawTextComparator cmp = RawTextComparator.DEFAULT;

		assertEquals(2
		assertEquals(2

		assertFalse(cmp.equals(a
		assertFalse(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b
	}

	@Test
	public void testWriteLine1() throws IOException {
		final RawText a = new RawText(Constants.encodeASCII("foo-a\nfoo-b\n"));
		final ByteArrayOutputStream o = new ByteArrayOutputStream();
		a.writeLine(o
		final byte[] r = o.toByteArray();
		assertEquals("foo-a"
	}

	@Test
	public void testWriteLine2() throws IOException {
		final RawText a = new RawText(Constants.encodeASCII("foo-a\nfoo-b"));
		final ByteArrayOutputStream o = new ByteArrayOutputStream();
		a.writeLine(o
		final byte[] r = o.toByteArray();
		assertEquals("foo-b"
	}

	@Test
	public void testWriteLine3() throws IOException {
		final RawText a = new RawText(Constants.encodeASCII("a\n\nb\n"));
		final ByteArrayOutputStream o = new ByteArrayOutputStream();
		a.writeLine(o
		final byte[] r = o.toByteArray();
		assertEquals(""
	}

	@Test
	public void testComparatorReduceCommonStartEnd() {
		final RawTextComparator c = RawTextComparator.DEFAULT;
		Edit e;

		e = c.reduceCommonStartEnd(t("")
		assertEquals(new Edit(0

		e = c.reduceCommonStartEnd(t("a")
		assertEquals(new Edit(0

		e = c.reduceCommonStartEnd(t("a")
		assertEquals(new Edit(1

		e = c.reduceCommonStartEnd(t("axB")
		assertEquals(new Edit(2

		e = c.reduceCommonStartEnd(t("Bxy")
		assertEquals(new Edit(0

		e = c.reduceCommonStartEnd(t("bc")
		assertEquals(new Edit(0

		e = new Edit(0
		e = c.reduceCommonStartEnd(t("abQxy")
		assertEquals(new Edit(2

		RawText a = new RawText("p\na b\nQ\nc d\n".getBytes(UTF_8));
		RawText b = new RawText("p\na  b \nR\n c  d \n".getBytes(UTF_8));
		e = new Edit(0
		e = RawTextComparator.WS_IGNORE_ALL.reduceCommonStartEnd(a
		assertEquals(new Edit(2
	}

	@Test
	public void testComparatorReduceCommonStartEnd_EmptyLine() {
		RawText a;
		RawText b;
		Edit e;

		a = new RawText("R\n y\n".getBytes(UTF_8));
		b = new RawText("S\n\n y\n".getBytes(UTF_8));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0

		a = new RawText("S\n\n y\n".getBytes(UTF_8));
		b = new RawText("R\n y\n".getBytes(UTF_8));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0
	}

	@Test
	public void testComparatorReduceCommonStartButLastLineNoEol() {
		RawText a;
		RawText b;
		Edit e;
		a = new RawText("start".getBytes(UTF_8));
		b = new RawText("start of line".getBytes(UTF_8));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0
	}

	@Test
	public void testComparatorReduceCommonStartButLastLineNoEol_2() {
		RawText a;
		RawText b;
		Edit e;
		a = new RawText("start".getBytes(UTF_8));
		b = new RawText("start of\nlastline".getBytes(UTF_8));
		e = new Edit(0
		e = RawTextComparator.DEFAULT.reduceCommonStartEnd(a
		assertEquals(new Edit(0
	}

	@Test
	public void testLineDelimiter() throws Exception {
		RawText rt = new RawText(Constants.encodeASCII("foo\n"));
		assertEquals("\n"
		assertFalse(rt.isMissingNewlineAtEnd());
		rt = new RawText(Constants.encodeASCII("foo\r\n"));
		assertEquals("\r\n"
		assertFalse(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("foo\nbar"));
		assertEquals("\n"
		assertTrue(rt.isMissingNewlineAtEnd());
		rt = new RawText(Constants.encodeASCII("foo\r\nbar"));
		assertEquals("\r\n"
		assertTrue(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("foo\nbar\r\n"));
		assertEquals("\n"
		assertFalse(rt.isMissingNewlineAtEnd());
		rt = new RawText(Constants.encodeASCII("foo\r\nbar\n"));
		assertEquals("\r\n"
		assertFalse(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("foo"));
		assertNull(rt.getLineDelimiter());
		assertTrue(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII(""));
		assertNull(rt.getLineDelimiter());
		assertTrue(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("\n"));
		assertEquals("\n"
		assertFalse(rt.isMissingNewlineAtEnd());

		rt = new RawText(Constants.encodeASCII("\r\n"));
		assertEquals("\r\n"
		assertFalse(rt.isMissingNewlineAtEnd());
	}

	@Test
	public void testLineDelimiter2() throws Exception {
		RawText rt = new RawText(Constants.encodeASCII("\nfoo"));
		assertEquals("\n"
		assertTrue(rt.isMissingNewlineAtEnd());
	}

	private static RawText t(String text) {
		StringBuilder r = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			r.append(text.charAt(i));
			r.append('\n');
		}
		return new RawText(r.toString().getBytes(UTF_8));
	}
}
