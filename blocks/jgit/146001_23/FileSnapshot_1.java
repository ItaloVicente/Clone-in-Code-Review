package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.util.Stats;
import org.junit.Test;

public class StatsTest {
	@Test
	public void testStatsTrivial() {
		Stats s = new Stats();
		s.add(1);
		s.add(1);
		s.add(1);
		assertEquals(3
		assertEquals(1.0
		assertEquals(1.0
		assertEquals(1.0
		assertEquals(0.0
		assertEquals(0.0
	}

	@Test
	public void testStats() {
		Stats s = new Stats();
		s.add(1);
		s.add(2);
		s.add(3);
		s.add(4);
		assertEquals(4
		assertEquals(1.0
		assertEquals(4.0
		assertEquals(2.5
		assertEquals(1.666667
		assertEquals(1.290994
	}

	@Test
	public void testStatsCancellationExample1() {
		Stats s = new Stats();
		s.add(1E8 + 4);
		s.add(1E8 + 7);
		s.add(1E8 + 13);
		s.add(1E8 + 16);
		assertEquals(4
		assertEquals(1E8 + 4
		assertEquals(1E8 + 16
		assertEquals(1E8 + 10
		assertEquals(30
		assertEquals(5.477226
	}

	@Test
	public void testStatsCancellationExample2() {
		Stats s = new Stats();
		s.add(1E9 + 4);
		s.add(1E9 + 7);
		s.add(1E9 + 13);
		s.add(1E9 + 16);
		assertEquals(4
		assertEquals(1E9 + 4
		assertEquals(1E9 + 16
		assertEquals(1E9 + 10
		assertEquals(30
		assertEquals(5.477226
	}

	@Test
	public void testNoValues() {
		Stats s = new Stats();
		assertTrue(Double.isNaN(s.var()));
		assertTrue(Double.isNaN(s.stddev()));
		s.add(42.3);
		assertTrue(Double.isNaN(s.var()));
		assertTrue(Double.isNaN(s.stddev()));
	}
}
