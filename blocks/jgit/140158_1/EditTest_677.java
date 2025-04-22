
package org.eclipse.jgit.diff;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class EditListTest {
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEmpty() {
		final EditList l = new EditList();
		assertEquals(0
		assertTrue(l.isEmpty());
		assertEquals("EditList[]"

		assertEquals(l
		assertEquals(new EditList()
		assertFalse(l.equals(""));
		assertEquals(l.hashCode()
	}

	@Test
	public void testAddOne() {
		final Edit e = new Edit(1
		final EditList l = new EditList();
		l.add(e);
		assertEquals(1
		assertFalse(l.isEmpty());
		assertSame(e
		assertSame(e

		assertEquals(l
		assertFalse(l.equals(new EditList()));

		final EditList l2 = new EditList();
		l2.add(e);
		assertEquals(l2
		assertEquals(l
		assertEquals(l.hashCode()
	}

	@Test
	public void testAddTwo() {
		final Edit e1 = new Edit(1
		final Edit e2 = new Edit(8
		final EditList l = new EditList();
		l.add(e1);
		l.add(e2);
		assertEquals(2
		assertSame(e1
		assertSame(e2

		final Iterator<Edit> i = l.iterator();
		assertSame(e1
		assertSame(e2

		assertEquals(l
		assertFalse(l.equals(new EditList()));

		final EditList l2 = new EditList();
		l2.add(e1);
		l2.add(e2);
		assertEquals(l2
		assertEquals(l
		assertEquals(l.hashCode()
	}

	@Test
	public void testSet() {
		final Edit e1 = new Edit(1
		final Edit e2 = new Edit(3
		final EditList l = new EditList();
		l.add(e1);
		assertSame(e1
		assertSame(e1
		assertSame(e2
	}

	@Test
	public void testRemove() {
		final Edit e1 = new Edit(1
		final Edit e2 = new Edit(8
		final EditList l = new EditList();
		l.add(e1);
		l.add(e2);
		l.remove(e1);
		assertEquals(1
		assertSame(e2
	}
}
