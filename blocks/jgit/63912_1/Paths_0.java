
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
}
