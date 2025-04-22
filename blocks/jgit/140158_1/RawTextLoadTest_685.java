
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class RawTextIgnoreWhitespaceChangeTest {
	private final RawTextComparator cmp = RawTextComparator.WS_IGNORE_CHANGE;

	@Test
	public void testEqualsWithoutWhitespace() {
		final RawText a = new RawText(Constants
				.encodeASCII("foo-a\nfoo-b\nfoo\n"));
		final RawText b = new RawText(Constants
				.encodeASCII("foo-b\nfoo-c\nf\n"));

		assertEquals(3
		assertEquals(3

		assertFalse(cmp.equals(a
		assertFalse(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b

		assertFalse(cmp.equals(a
		assertFalse(cmp.equals(b
	}

	@Test
	public void testEqualsWithWhitespace() {
		final RawText a = new RawText(Constants
				.encodeASCII("foo-a\n         \n a b c\na      \n  foo\na  b  c\n"));
		final RawText b = new RawText(Constants
				.encodeASCII("foo-a        b\n\nab  c\na\nfoo\na b     c  \n"));

		assertFalse(cmp.equals(a
		assertFalse(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b

		assertFalse(cmp.equals(a
		assertFalse(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b

		assertFalse(cmp.equals(a
		assertFalse(cmp.equals(b

		assertTrue(cmp.equals(a
		assertTrue(cmp.equals(b
	}
}
