package org.eclipse.jgit.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class SizeLimitedLinkedHashMapTest {
	private SizeLimitedLinkedHashMap<String

	@Before
	public void clearMap() {
		map.clear();
	}

	@Test
	public void sizeLimitZero() {
		map.setSizeLimit(0);
		map.put("a"
		assertEquals(0
	}

	@Test
	public void sizeLimitOne() {
		map.setSizeLimit(1);

		map.put("a"
		assertEquals(1
		assertTrue(map.containsKey("a"));

		map.put("b"
		assertEquals(1
		assertTrue(map.containsKey("b"));
	}

	@Test
	public void lowerSizeLimit() {
		map.put("a"
		map.put("b"
		map.put("c"
		map.put("d"
		map.put("e"
		assertEquals(5

		map.setSizeLimit(4);
		assertEquals(4
		assertFalse(map.containsKey("a"));

		map.setSizeLimit(2);
		assertEquals(2
		assertTrue(map.containsKey("e"));
		assertTrue(map.containsKey("d"));
	}

	@Test
	public void getSizeLimit() {
		map.setSizeLimit(5);
		assertEquals(5
	}

	@Test
	public void negativeSizeLimit() {
		map.setSizeLimit(-10);
		map.put("a"
		assertEquals(0

		map.setSizeLimit(10);
		map.put("a"
		assertEquals(1

		map.setSizeLimit(-10);
		assertEquals(0
	}
}
