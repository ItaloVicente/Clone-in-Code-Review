
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class IntListTest {
	@Test
	public void testEmpty_DefaultCapacity() {
		final IntList i = new IntList();
		assertEquals(0
		try {
			i.get(0);
			fail("Accepted 0 index on empty list");
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testEmpty_SpecificCapacity() {
		final IntList i = new IntList(5);
		assertEquals(0
		try {
			i.get(0);
			fail("Accepted 0 index on empty list");
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAdd_SmallGroup() {
		final IntList i = new IntList();
		final int n = 5;
		for (int v = 0; v < n; v++)
			i.add(10 + v);
		assertEquals(n

		for (int v = 0; v < n; v++)
			assertEquals(10 + v
		try {
			i.get(n);
			fail("Accepted out of bound index on list");
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testAdd_ZeroCapacity() {
		final IntList i = new IntList(0);
		assertEquals(0
		i.add(1);
		assertEquals(1
	}

	@Test
	public void testAdd_LargeGroup() {
		final IntList i = new IntList();
		final int n = 500;
		for (int v = 0; v < n; v++)
			i.add(10 + v);
		assertEquals(n

		for (int v = 0; v < n; v++)
			assertEquals(10 + v
		try {
			i.get(n);
			fail("Accepted out of bound index on list");
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testFillTo0() {
		final IntList i = new IntList();
		i.fillTo(0
		assertEquals(0
	}

	@Test
	public void testFillTo1() {
		final IntList i = new IntList();
		i.fillTo(1
		assertEquals(1
		i.add(0);
		assertEquals(Integer.MIN_VALUE
		assertEquals(0
	}

	@Test
	public void testFillTo100() {
		final IntList i = new IntList();
		i.fillTo(100
		assertEquals(100
		i.add(3);
		assertEquals(Integer.MIN_VALUE
		assertEquals(3
	}

	@Test
	public void testClear() {
		final IntList i = new IntList();
		final int n = 5;
		for (int v = 0; v < n; v++)
			i.add(10 + v);
		assertEquals(n

		i.clear();
		assertEquals(0
		try {
			i.get(0);
			fail("Accepted 0 index on empty list");
		} catch (ArrayIndexOutOfBoundsException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSet() {
		final IntList i = new IntList();
		i.add(1);
		assertEquals(1
		assertEquals(1

		i.set(0
		assertEquals(5

		try {
			i.set(5
			fail("accepted set of 5 beyond end of list");
		} catch (ArrayIndexOutOfBoundsException e){
			assertTrue(true);
		}

		i.set(1
		assertEquals(2
		assertEquals(2
	}

	@Test
	public void testContains() {
		IntList i = new IntList();
		i.add(1);
		i.add(4);
		assertTrue(i.contains(1));
		assertTrue(i.contains(4));
		assertFalse(i.contains(2));
	}

	@Test
	public void testToString() {
		final IntList i = new IntList();
		i.add(1);
		assertEquals("[1]"
		i.add(13);
		i.add(5);
		assertEquals("[1
	}

}
