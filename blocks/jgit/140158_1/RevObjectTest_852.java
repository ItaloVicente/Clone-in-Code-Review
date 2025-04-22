
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;

import org.junit.Test;

public class RevFlagSetTest extends RevWalkTestCase {
	@Test
	public void testEmpty() {
		final RevFlagSet set = new RevFlagSet();
		assertEquals(0
		assertEquals(0
		assertNotNull(set.iterator());
		assertFalse(set.iterator().hasNext());
	}

	@Test
	public void testAddOne() {
		final String flagName = "flag";
		final RevFlag flag = rw.newFlag(flagName);
		assertTrue(0 != flag.mask);
		assertSame(flagName

		final RevFlagSet set = new RevFlagSet();
		assertTrue(set.add(flag));
		assertFalse(set.add(flag));
		assertEquals(flag.mask
		assertEquals(1
		final Iterator<RevFlag> i = set.iterator();
		assertTrue(i.hasNext());
		assertSame(flag
		assertFalse(i.hasNext());
	}

	@Test
	public void testAddTwo() {
		final RevFlag flag1 = rw.newFlag("flag_1");
		final RevFlag flag2 = rw.newFlag("flag_2");
		assertEquals(0

		final RevFlagSet set = new RevFlagSet();
		assertTrue(set.add(flag1));
		assertTrue(set.add(flag2));
		assertEquals(flag1.mask | flag2.mask
		assertEquals(2
	}

	@Test
	public void testContainsAll() {
		final RevFlag flag1 = rw.newFlag("flag_1");
		final RevFlag flag2 = rw.newFlag("flag_2");
		final RevFlagSet set1 = new RevFlagSet();
		assertTrue(set1.add(flag1));
		assertTrue(set1.add(flag2));

		assertTrue(set1.containsAll(Arrays
				.asList(new RevFlag[] { flag1

		final RevFlagSet set2 = new RevFlagSet();
		set2.add(rw.newFlag("flag_3"));
		assertFalse(set1.containsAll(set2));
	}

	@Test
	public void testEquals() {
		final RevFlag flag1 = rw.newFlag("flag_1");
		final RevFlag flag2 = rw.newFlag("flag_2");
		final RevFlagSet set = new RevFlagSet();
		assertTrue(set.add(flag1));
		assertTrue(set.add(flag2));

		assertEquals(set
		assertTrue(new RevFlagSet(Arrays.asList(new RevFlag[] { flag1
				.equals(set));
	}

	@Test
	public void testRemove() {
		final RevFlag flag1 = rw.newFlag("flag_1");
		final RevFlag flag2 = rw.newFlag("flag_2");
		final RevFlagSet set = new RevFlagSet();
		assertTrue(set.add(flag1));
		assertTrue(set.add(flag2));

		assertTrue(set.remove(flag1));
		assertFalse(set.remove(flag1));
		assertEquals(flag2.mask
		assertFalse(set.contains(flag1));
	}

	@Test
	public void testContains() {
		final RevFlag flag1 = rw.newFlag("flag_1");
		final RevFlag flag2 = rw.newFlag("flag_2");
		final RevFlagSet set = new RevFlagSet();
		set.add(flag1);
		assertTrue(set.contains(flag1));
		assertFalse(set.contains(flag2));
	}
}
