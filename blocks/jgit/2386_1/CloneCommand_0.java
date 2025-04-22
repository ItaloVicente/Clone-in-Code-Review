
package org.eclipse.jgit.storage.pack;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntSetTest {
	@Test
	public void testAdd() {
		IntSet s = new IntSet();

		assertTrue(s.add(1));
		assertFalse(s.add(1));

		for (int i = 2; i < 64; i++)
			assertTrue(s.add(i));
		for (int i = 2; i < 64; i++)
			assertFalse(s.add(i));

		assertTrue(s.add(-1));
		assertFalse(s.add(-1));

		assertTrue(s.add(-2));
		assertFalse(s.add(-2));

		assertTrue(s.add(128));
		assertFalse(s.add(128));

		assertFalse(s.add(1));
	}
}
