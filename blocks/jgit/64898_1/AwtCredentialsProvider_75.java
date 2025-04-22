
package org.eclipse.jgit.util;

import static org.eclipse.jgit.util.Paths.compare;
import static org.eclipse.jgit.util.Paths.compareSameName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.junit.Test;

public class PathsTest {
	@Test
	public void testStripTrailingSeparator() {
		assertNull(Paths.stripTrailingSeparator(null));
		assertEquals(""
		assertEquals("a"
		assertEquals("a/boo"
		assertEquals("a/boo"
		assertEquals("a/boo"
		assertEquals("a/boo"
	}

	@Test
	public void testPathCompare() {
		byte[] a = Constants.encode("afoo/bar.c");
		byte[] b = Constants.encode("bfoo/bar.c");

		assertEquals(0
		assertEquals(-1
		assertEquals(1

		a = Constants.encode("a");
		b = Constants.encode("aa");
		assertEquals(-97
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(-50
		assertEquals(97

		a = Constants.encode("a");
		b = Constants.encode("a");
		assertEquals(0
				a
				b
		assertEquals(0
				a
				b
		assertEquals(-47
				a
				b
		assertEquals(47
				a
				b

		assertEquals(0
				a
				b
		assertEquals(0
				a
				b

		a = Constants.encode("a.c");
		b = Constants.encode("a");
		byte[] c = Constants.encode("a0c");
		assertEquals(-1
				a
				b
		assertEquals(-1
				b
				c
	}
}
