
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

public class ObjectIdOwnerMapTest {
	private MutableObjectId idBuf;

	private SubId id_1

	@Before
	public void init() {
		idBuf = new MutableObjectId();
		id_1 = new SubId(id(1));
		id_2 = new SubId(id(2));
		id_3 = new SubId(id(3));
		id_a31 = new SubId(id(31));
		id_b31 = new SubId(id((1 << 8) + 31));
	}

	@Test
	public void testEmptyMap() {
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<SubId>();
		assertTrue(m.isEmpty());
		assertEquals(0

		Iterator<SubId> i = m.iterator();
		assertNotNull(i);
		assertFalse(i.hasNext());

		assertFalse(m.contains(id(1)));
	}

	@Test
	public void testAddGetAndContains() {
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<SubId>();
		m.add(id_1);
		m.add(id_2);
		m.add(id_3);
		m.add(id_a31);
		m.add(id_b31);
		assertFalse(m.isEmpty());
		assertEquals(5

		assertSame(id_1
		assertSame(id_1
		assertSame(id_1
		assertSame(id_2
		assertSame(id_3
		assertSame(id_a31
		assertSame(id_b31

		assertTrue(m.contains(id_1));
	}

	@Test
	public void testClear() {
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<SubId>();

		m.add(id_1);
		assertSame(id_1

		m.clear();
		assertTrue(m.isEmpty());
		assertEquals(0

		Iterator<SubId> i = m.iterator();
		assertNotNull(i);
		assertFalse(i.hasNext());

		assertFalse(m.contains(id(1)));
	}

	@Test
	public void testAddIfAbsent() {
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<SubId>();
		m.add(id_1);

		assertSame(id_1
		assertEquals(1

		assertSame(id_2
		assertEquals(2
		assertSame(id_a31
		assertSame(id_b31

		assertSame(id_a31
		assertSame(id_b31
		assertEquals(4
	}

	@Test
	public void testAddGrowsWithObjects() {
		int n = 16384;
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<SubId>();
		m.add(id_1);
		for (int i = 32; i < n; i++)
			m.add(new SubId(id(i)));
		assertEquals(n - 32 + 1

		assertSame(id_1
		for (int i = 32; i < n; i++)
			assertTrue(m.contains(id(i)));
	}

	@Test
	public void testAddIfAbsentGrowsWithObjects() {
		int n = 16384;
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<SubId>();
		m.add(id_1);
		for (int i = 32; i < n; i++)
			m.addIfAbsent(new SubId(id(i)));
		assertEquals(n - 32 + 1

		assertSame(id_1
		for (int i = 32; i < n; i++)
			assertTrue(m.contains(id(i)));
	}

	@Test
	public void testIterator() {
		ObjectIdOwnerMap<SubId> m = new ObjectIdOwnerMap<SubId>();
		m.add(id_1);
		m.add(id_2);
		m.add(id_3);

		Iterator<SubId> i = m.iterator();
		assertTrue(i.hasNext());
		assertSame(id_1
		assertTrue(i.hasNext());
		assertSame(id_2
		assertTrue(i.hasNext());
		assertSame(id_3

		assertFalse(i.hasNext());
		try {
			i.next();
			fail("did not fail on next with no next");
		} catch (NoSuchElementException expected) {
		}

		i = m.iterator();
		assertSame(id_1
		try {
			i.remove();
			fail("did not fail on remove");
		} catch (UnsupportedOperationException expected) {
		}
	}

	private AnyObjectId id(int val) {
		idBuf.setByte(0
		idBuf.setByte(3
		return idBuf;
	}

	private static class SubId extends ObjectIdOwnerMap.Entry {
		SubId(AnyObjectId id) {
			super(id);
		}
	}
}
