package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;

public class InstantComparatorTest {

	private final InstantComparator cmp = new InstantComparator();

	@Test
	public void compareNow() {
		Instant now = Instant.now();
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void compareSeconds() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond());
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void compareSecondsOnly() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond()
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void compareSecondsUnequal() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond());
		Instant s = Instant.ofEpochSecond(now.getEpochSecond() - 1L);
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
	}

	@Test
	public void compareMillisEqual() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond()
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
		assertEquals(0
		s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
		s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void compareMillisUnequal() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond()
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		t = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		t = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		s = Instant.ofEpochSecond(now.getEpochSecond() - 1L
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
	}

	@Test
	public void compareMicrosEqual() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond()
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
		s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void compareMicrosUnequal() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond()
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		t = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		t = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		s = Instant.ofEpochSecond(now.getEpochSecond() - 1L
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
	}

	@Test
	public void compareNanosEqual() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond()
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void compareNanosUnequal() {
		Instant now = Instant.now();
		Instant t = Instant.ofEpochSecond(now.getEpochSecond()
		Instant s = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		t = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		s = Instant.ofEpochSecond(now.getEpochSecond() - 1L
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
		s = Instant.ofEpochSecond(now.getEpochSecond()
		assertTrue(cmp.compare(s
		assertTrue(cmp.compare(t
	}
}
