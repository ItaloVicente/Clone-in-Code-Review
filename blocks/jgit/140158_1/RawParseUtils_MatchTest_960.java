
package org.eclipse.jgit.util;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.jgit.errors.BinaryBlobException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RawParseUtils_LineMapTest {
	@Rule
	public ExpectedException exception = ExpectedException.none();


	@Test
	public void testEmpty() throws Exception {
		final IntList map = RawParseUtils.lineMap(new byte[] {}
		assertNotNull(map);
		assertArrayEquals(new int[]{Integer.MIN_VALUE
	}

	@Test
	public void testOneBlankLine() throws Exception  {
		final IntList map = RawParseUtils.lineMap(new byte[] { '\n' }
		assertArrayEquals(new int[]{Integer.MIN_VALUE
	}

	@Test
	public void testTwoLineFooBar() {
		final byte[] buf = "foo\nbar\n".getBytes(ISO_8859_1);
		final IntList map = RawParseUtils.lineMap(buf
		assertArrayEquals(new int[]{Integer.MIN_VALUE
	}

	@Test
	public void testTwoLineNoLF() {
		final byte[] buf = "foo\nbar".getBytes(ISO_8859_1);
		final IntList map = RawParseUtils.lineMap(buf
		assertArrayEquals(new int[]{Integer.MIN_VALUE
	}

	@Test
	public void testNulByte() {
		final byte[] buf = "xxxfoo\nb\0ar".getBytes(ISO_8859_1);
		final IntList map = RawParseUtils.lineMap(buf
		assertArrayEquals(new int[] { Integer.MIN_VALUE
				asInts(map));
	}

	@Test
	public void testLineMapOrBinary() throws Exception {
		final byte[] buf = "xxxfoo\nb\0ar".getBytes(ISO_8859_1);
		exception.expect(BinaryBlobException.class);
		RawParseUtils.lineMapOrBinary(buf
	}

	@Test
	public void testFourLineBlanks() {
		final byte[] buf = "foo\n\n\nbar\n".getBytes(ISO_8859_1);
		final IntList map = RawParseUtils.lineMap(buf

		assertArrayEquals(new int[]{
				Integer.MIN_VALUE
		}
	}

	private int[] asInts(IntList l) {
		int[] result = new int[l.size()];
		for (int i = 0; i < l.size(); i++) {
			result[i] = l.get(i);
		}
		return result;
	}
}
