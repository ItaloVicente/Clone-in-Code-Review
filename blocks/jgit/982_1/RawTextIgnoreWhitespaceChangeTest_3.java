
package org.eclipse.jgit.diff;

import org.eclipse.jgit.lib.Constants;

import junit.framework.TestCase;

public class RawTextIgnoreTrailingWhitespaceTest extends TestCase {
	public void testEqualsWithoutWhitespace() {
		final RawText a = new RawTextIgnoreTrailingWhitespace(Constants
				.encodeASCII("foo-a\nfoo-b\nfoo\n"));
		final RawText b = new RawTextIgnoreTrailingWhitespace(Constants
				.encodeASCII("foo-b\nfoo-c\nf\n"));

		assertEquals(3
		assertEquals(3

		assertFalse(a.equals(0
		assertFalse(b.equals(0

		assertTrue(a.equals(1
		assertTrue(b.equals(0

		assertFalse(a.equals(2
		assertFalse(b.equals(2
	}

	public void testEqualsWithWhitespace() {
		final RawText a = new RawTextIgnoreTrailingWhitespace(Constants
				.encodeASCII("foo-a\n         \n a b c\na      \n    b\n"));
		final RawText b = new RawTextIgnoreTrailingWhitespace(Constants
				.encodeASCII("foo-a        b\n\nab  c\na\nb\n"));

		assertFalse(a.equals(0
		assertFalse(b.equals(0

		assertTrue(a.equals(1
		assertTrue(b.equals(1

		assertFalse(a.equals(2
		assertFalse(b.equals(2

		assertTrue(a.equals(3
		assertTrue(b.equals(3

		assertFalse(a.equals(4
		assertFalse(b.equals(4
	}
}
