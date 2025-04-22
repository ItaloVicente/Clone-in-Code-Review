
package org.eclipse.jgit.diff;

import org.eclipse.jgit.lib.Constants;

import junit.framework.TestCase;

public class RawTextIgnoreAllWhitespaceTest extends TestCase {
	public void testEqualsWithoutWhitespace() {
		final RawText a = new RawTextIgnoreAllWhitespace(Constants
				.encodeASCII("foo-a\nfoo-b\n"));
		final RawText b = new RawTextIgnoreAllWhitespace(Constants
				.encodeASCII("foo-b\nfoo-c\n"));

		assertEquals(2
		assertEquals(2

		assertFalse(a.equals(0
		assertFalse(b.equals(0

		assertTrue(a.equals(1
		assertTrue(b.equals(0
	}

	public void testEqualsWithWhitespace() {
		final RawText a = new RawTextIgnoreAllWhitespace(Constants
				.encodeASCII("foo-a\n         \n a b c\na      \nfoo\n"));
		final RawText b = new RawTextIgnoreAllWhitespace(Constants
				.encodeASCII("foo-a        b\n\nab  c\na\nf\n"));

		assertFalse(a.equals(0
		assertFalse(b.equals(0

		assertTrue(a.equals(1
		assertTrue(b.equals(1

		assertTrue(a.equals(2
		assertTrue(b.equals(2

		assertTrue(a.equals(3
		assertTrue(b.equals(3

		assertFalse(a.equals(4
		assertFalse(b.equals(4
	}
}
