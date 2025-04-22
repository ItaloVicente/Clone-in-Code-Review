
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class LongMapTest {
	private LongMap<Long> map;

	@Before
	public void setUp() throws Exception {
		map = new LongMap<>();
	}

	@Test
	public void testEmptyMap() {
		assertFalse(map.containsKey(0));
		assertFalse(map.containsKey(1));

		assertNull(map.get(0));
		assertNull(map.get(1));

		assertNull(map.remove(0));
		assertNull(map.remove(1));
	}

	@Test
	public void testInsertMinValue() {
		final Long min = Long.valueOf(Long.MIN_VALUE);
		assertNull(map.put(Long.MIN_VALUE
		assertTrue(map.containsKey(Long.MIN_VALUE));
		assertSame(min
		assertFalse(map.containsKey(Integer.MIN_VALUE));
	}

	@Test
	public void testReplaceMaxValue() {
		final Long min = Long.valueOf(Long.MAX_VALUE);
		final Long one = Long.valueOf(1);
		assertNull(map.put(Long.MAX_VALUE
		assertSame(min
		assertSame(min
		assertSame(one
	}

	@Test
	public void testRemoveOne() {
		final long start = 1;
		assertNull(map.put(start
		assertEquals(Long.valueOf(start)
		assertFalse(map.containsKey(start));
	}

	@Test
	public void testRemoveCollision1() {
		assertNull(map.put(0
		assertNull(map.put(1
		assertEquals(Long.valueOf(0)

		assertFalse(map.containsKey(0));
		assertTrue(map.containsKey(1));
	}

	@Test
	public void testRemoveCollision2() {
		assertNull(map.put(0
		assertNull(map.put(1
		assertEquals(Long.valueOf(1)

		assertTrue(map.containsKey(0));
		assertFalse(map.containsKey(1));
	}

	@Test
	public void testSmallMap() {
		final long start = 12;
		final long n = 8;
		for (long i = start; i < start + n; i++)
			assertNull(map.put(i
		for (long i = start; i < start + n; i++)
			assertEquals(Long.valueOf(i)
	}

	@Test
	public void testLargeMap() {
		final long start = Integer.MAX_VALUE;
		final long n = 100000;
		for (long i = start; i < start + n; i++)
			assertNull(map.put(i
		for (long i = start; i < start + n; i++)
			assertEquals(Long.valueOf(i)
	}
}
