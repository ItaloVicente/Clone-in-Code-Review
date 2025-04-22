
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.junit.Test;

public class RefListTest {
	private static final ObjectId ID = ObjectId
			.fromString("41eb0d88f833b558bddeb269b7ab77399cdf98ed");

	private static final Ref REF_A = newRef("A");

	private static final Ref REF_B = newRef("B");

	private static final Ref REF_c = newRef("c");

	@Test
	public void testEmpty() {
		RefList<Ref> list = RefList.emptyList();
		assertEquals(0
		assertTrue(list.isEmpty());
		assertFalse(list.iterator().hasNext());
		assertEquals(-1
		assertEquals(-1
		assertFalse(list.contains("a"));
		assertNull(list.get("a"));
		try {
			list.get(0);
			fail("RefList.emptyList should have 0 element array");
		} catch (ArrayIndexOutOfBoundsException err) {
		}
	}

	@Test
	public void testEmptyBuilder() {
		RefList<Ref> list = new RefList.Builder<>().toRefList();
		assertEquals(0
		assertFalse(list.iterator().hasNext());
		assertEquals(-1
		assertEquals(-1
		assertFalse(list.contains("a"));
		assertNull(list.get("a"));
		assertTrue(list.asList().isEmpty());
		assertEquals("[]"

		assertNull(list.get(16 - 1));
		try {
			list.get(16);
			fail("default RefList should have 16 element array");
		} catch (ArrayIndexOutOfBoundsException err) {
		}
	}

	@Test
	public void testBuilder_AddThenSort() {
		RefList.Builder<Ref> builder = new RefList.Builder<>(1);
		builder.add(REF_B);
		builder.add(REF_A);

		RefList<Ref> list = builder.toRefList();
		assertEquals(2
		assertSame(REF_B
		assertSame(REF_A

		builder.sort();
		list = builder.toRefList();
		assertEquals(2
		assertSame(REF_A
		assertSame(REF_B
	}

	@Test
	public void testBuilder_AddThenDedupe() {
		RefList.Builder<Ref> builder = new RefList.Builder<>(1);
		builder.add(REF_B);
		builder.add(REF_A);
		builder.add(REF_A);
		builder.add(REF_B);
		builder.add(REF_c);

		builder.sort();
		builder.dedupe((a
		RefList<Ref> list = builder.toRefList();

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c
	}

	@Test
	public void testBuilder_AddThenDedupe_Border() {
		RefList.Builder<Ref> builder = new RefList.Builder<>(1);
		builder.sort();
		builder.dedupe((a
		RefList<Ref> list = builder.toRefList();
		assertTrue(list.isEmpty());

		builder = new RefList.Builder<>(1);
		builder.add(REF_A);
		builder.sort();
		builder.dedupe((a
		list = builder.toRefList();
		assertEquals(1
	}

	@Test
	public void testBuilder_AddAll() {
		RefList.Builder<Ref> builder = new RefList.Builder<>(1);
		Ref[] src = { REF_A
		builder.addAll(src

		RefList<Ref> list = builder.toRefList();
		assertEquals(2
		assertSame(REF_B
		assertSame(REF_c
	}

	@Test
	public void testBuilder_Set() {
		RefList.Builder<Ref> builder = new RefList.Builder<>();
		builder.add(REF_A);
		builder.add(REF_A);

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_A

		RefList<Ref> list = builder.toRefList();
		assertEquals(2
		assertSame(REF_A
		assertSame(REF_A
		builder.set(1

		list = builder.toRefList();
		assertEquals(2
		assertSame(REF_A
		assertSame(REF_B
	}

	@Test
	public void testBuilder_Remove() {
		RefList.Builder<Ref> builder = new RefList.Builder<>();
		builder.add(REF_A);
		builder.add(REF_B);
		builder.remove(0);

		assertEquals(1
		assertSame(REF_B
	}

	@Test
	public void testSet() {
		RefList<Ref> one = toList(REF_A
		RefList<Ref> two = one.set(1
		assertNotSame(one

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_A

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_B
	}

	@Test
	public void testAddToEmptyList() {
		RefList<Ref> one = toList();
		RefList<Ref> two = one.add(0
		assertNotSame(one

		assertEquals(0
		assertEquals(1
		assertFalse(two.isEmpty());
		assertSame(REF_B
	}

	@Test
	public void testAddToFrontOfList() {
		RefList<Ref> one = toList(REF_A);
		RefList<Ref> two = one.add(0
		assertNotSame(one

		assertEquals(1
		assertSame(REF_A
		assertEquals(2
		assertSame(REF_B
		assertSame(REF_A
	}

	@Test
	public void testAddToEndOfList() {
		RefList<Ref> one = toList(REF_A);
		RefList<Ref> two = one.add(1
		assertNotSame(one

		assertEquals(1
		assertSame(REF_A
		assertEquals(2
		assertSame(REF_A
		assertSame(REF_B
	}

	@Test
	public void testAddToMiddleOfListByInsertionPosition() {
		RefList<Ref> one = toList(REF_A

		assertEquals(-2

		RefList<Ref> two = one.add(one.find(REF_B.getName())
		assertNotSame(one

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_c

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c
	}

	@Test
	public void testPutNewEntry() {
		RefList<Ref> one = toList(REF_A
		RefList<Ref> two = one.put(REF_B);
		assertNotSame(one

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_c

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c
	}

	@Test
	public void testPutReplaceEntry() {
		Ref otherc = newRef(REF_c.getName());
		assertNotSame(REF_c

		RefList<Ref> one = toList(REF_A
		RefList<Ref> two = one.put(otherc);
		assertNotSame(one

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_c

		assertEquals(2
		assertSame(REF_A
		assertSame(otherc
	}

	@Test
	public void testRemoveFrontOfList() {
		RefList<Ref> one = toList(REF_A
		RefList<Ref> two = one.remove(0);
		assertNotSame(one

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c

		assertEquals(2
		assertSame(REF_B
		assertSame(REF_c
	}

	@Test
	public void testRemoveMiddleOfList() {
		RefList<Ref> one = toList(REF_A
		RefList<Ref> two = one.remove(1);
		assertNotSame(one

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_c
	}

	@Test
	public void testRemoveEndOfList() {
		RefList<Ref> one = toList(REF_A
		RefList<Ref> two = one.remove(2);
		assertNotSame(one

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_B
	}

	@Test
	public void testRemoveMakesEmpty() {
		RefList<Ref> one = toList(REF_A);
		RefList<Ref> two = one.remove(1);
		assertNotSame(one
		assertSame(two
	}

	@Test
	public void testToString() {
		StringBuilder exp = new StringBuilder();
		exp.append("[");
		exp.append(REF_A);
		exp.append("
		exp.append(REF_B);
		exp.append("]");

		RefList<Ref> list = toList(REF_A
		assertEquals(exp.toString()
	}

	@Test
	public void testBuilder_ToString() {
		StringBuilder exp = new StringBuilder();
		exp.append("[");
		exp.append(REF_A);
		exp.append("
		exp.append(REF_B);
		exp.append("]");

		RefList.Builder<Ref> list = new RefList.Builder<>();
		list.add(REF_A);
		list.add(REF_B);
		assertEquals(exp.toString()
	}

	@Test
	public void testFindContainsGet() {
		RefList<Ref> list = toList(REF_A

		assertEquals(0
		assertEquals(1
		assertEquals(2

		assertEquals(-1
		assertEquals(-2
		assertEquals(-3
		assertEquals(-4

		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c
		assertNull(list.get("AB"));
		assertNull(list.get("z"));

		assertTrue(list.contains("A"));
		assertTrue(list.contains("B"));
		assertTrue(list.contains("c"));
		assertFalse(list.contains("AB"));
		assertFalse(list.contains("z"));
	}

	@Test
	public void testIterable() {
		RefList<Ref> list = toList(REF_A

		int idx = 0;
		for (Ref ref : list)
			assertSame(list.get(idx++)
		assertEquals(3

		Iterator<Ref> i = RefList.emptyList().iterator();
		try {
			i.next();
			fail("did not throw NoSuchElementException");
		} catch (NoSuchElementException err) {
		}

		i = list.iterator();
		assertTrue(i.hasNext());
		assertSame(REF_A
		try {
			i.remove();
			fail("did not throw UnsupportedOperationException");
		} catch (UnsupportedOperationException err) {
		}
	}

	@Test
	public void testCopyLeadingPrefix() {
		RefList<Ref> one = toList(REF_A
		RefList<Ref> two = one.copy(2).toRefList();
		assertNotSame(one

		assertEquals(3
		assertSame(REF_A
		assertSame(REF_B
		assertSame(REF_c

		assertEquals(2
		assertSame(REF_A
		assertSame(REF_B
	}

	@Test
	public void testCopyConstructorReusesArray() {
		RefList.Builder<Ref> one = new RefList.Builder<>();
		one.add(REF_A);

		RefList<Ref> two = new RefList<>(one.toRefList());
		one.set(0
		assertSame(REF_B
	}

	private static RefList<Ref> toList(Ref... refs) {
		RefList.Builder<Ref> b = new RefList.Builder<>(refs.length);
		b.addAll(refs
		return b.toRefList();
	}

	private static Ref newRef(String name) {
		return new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
	}
}
