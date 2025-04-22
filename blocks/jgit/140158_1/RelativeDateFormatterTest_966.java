
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.SymbolicRef;
import org.junit.Before;
import org.junit.Test;

public class RefMapTest {
	private static final ObjectId ID_ONE = ObjectId
			.fromString("41eb0d88f833b558bddeb269b7ab77399cdf98ed");

	private static final ObjectId ID_TWO = ObjectId
			.fromString("698dd0b8d0c299f080559a1cffc7fe029479a408");

	private RefList<Ref> packed;

	private RefList<Ref> loose;

	private RefList<Ref> resolved;

	@Before
	public void setUp() throws Exception {
		packed = RefList.emptyList();
		loose = RefList.emptyList();
		resolved = RefList.emptyList();
	}

	@Test
	public void testEmpty_NoPrefix1() {
		RefMap map = new RefMap(""
		assertEquals(0

		assertFalse(map.entrySet().iterator().hasNext());
		assertFalse(map.keySet().iterator().hasNext());
		assertFalse(map.containsKey("a"));
		assertNull(map.get("a"));
	}

	@Test
	public void testEmpty_NoPrefix2() {
		RefMap map = new RefMap();
		assertEquals(0

		assertFalse(map.entrySet().iterator().hasNext());
		assertFalse(map.keySet().iterator().hasNext());
		assertFalse(map.containsKey("a"));
		assertNull(map.get("a"));
	}

	@Test
	public void testNotEmpty_NoPrefix() {
		final Ref master = newRef("refs/heads/master"
		packed = toList(master);

		RefMap map = new RefMap(""
		assertEquals(1
		assertSame(master
	}

	@Test
	public void testEmpty_WithPrefix() {
		final Ref master = newRef("refs/heads/master"
		packed = toList(master);

		RefMap map = new RefMap("refs/tags/"
		assertEquals(0

		assertFalse(map.entrySet().iterator().hasNext());
		assertFalse(map.keySet().iterator().hasNext());
	}

	@Test
	public void testNotEmpty_WithPrefix() {
		final Ref master = newRef("refs/heads/master"
		packed = toList(master);

		RefMap map = new RefMap("refs/heads/"
		assertEquals(1
		assertSame(master
	}

	@Test
	public void testClear() {
		final Ref master = newRef("refs/heads/master"
		loose = toList(master);

		RefMap map = new RefMap(""
		assertSame(master

		map.clear();
		assertNull(map.get("refs/heads/master"));
		assertTrue(map.isEmpty());
		assertEquals(0
	}

	@Test
	public void testIterator_RefusesRemove() {
		final Ref master = newRef("refs/heads/master"
		loose = toList(master);

		RefMap map = new RefMap(""
		Iterator<Ref> itr = map.values().iterator();
		assertTrue(itr.hasNext());
		assertSame(master
		try {
			itr.remove();
			fail("iterator allowed remove");
		} catch (UnsupportedOperationException err) {
		}
	}

	@Test
	public void testIterator_FailsAtEnd() {
		final Ref master = newRef("refs/heads/master"
		loose = toList(master);

		RefMap map = new RefMap(""
		Iterator<Ref> itr = map.values().iterator();
		assertTrue(itr.hasNext());
		assertSame(master
		try {
			itr.next();
			fail("iterator allowed next");
		} catch (NoSuchElementException err) {
		}
	}

	@Test
	public void testIterator_MissingUnresolvedSymbolicRefIsBug() {
		final Ref master = newRef("refs/heads/master"
		final Ref headR = newRef("HEAD"

		loose = toList(master);
		resolved = toList(headR);

		RefMap map = new RefMap(""
		Iterator<Ref> itr = map.values().iterator();
		try {
			itr.hasNext();
			fail("iterator did not catch bad input");
		} catch (IllegalStateException err) {
		}
	}

	@Test
	public void testMerge_HeadMaster() {
		final Ref master = newRef("refs/heads/master"
		final Ref headU = newRef("HEAD"
		final Ref headR = newRef("HEAD"

		loose = toList(headU
		resolved = toList(headR);

		RefMap map = new RefMap(""
		assertEquals(2
		assertFalse(map.isEmpty());
		assertTrue(map.containsKey("refs/heads/master"));
		assertSame(master

		assertSame(headR

		Iterator<Ref> itr = map.values().iterator();
		assertTrue(itr.hasNext());
		assertSame(headR
		assertTrue(itr.hasNext());
		assertSame(master
		assertFalse(itr.hasNext());
	}

	@Test
	public void testMerge_PackedLooseLoose() {
		final Ref refA = newRef("A"
		final Ref refB_ONE = newRef("B"
		final Ref refB_TWO = newRef("B"
		final Ref refc = newRef("c"

		packed = toList(refA
		loose = toList(refB_TWO

		RefMap map = new RefMap(""
		assertEquals(3
		assertFalse(map.isEmpty());
		assertTrue(map.containsKey(refA.getName()));
		assertSame(refA

		assertSame(refB_TWO

		Iterator<Ref> itr = map.values().iterator();
		assertTrue(itr.hasNext());
		assertSame(refA
		assertTrue(itr.hasNext());
		assertSame(refB_TWO
		assertTrue(itr.hasNext());
		assertSame(refc
		assertFalse(itr.hasNext());
	}

	@Test
	public void testMerge_WithPrefix() {
		final Ref a = newRef("refs/heads/A"
		final Ref b = newRef("refs/heads/foo/bar/B"
		final Ref c = newRef("refs/heads/foo/rab/C"
		final Ref g = newRef("refs/heads/g"
		packed = toList(a

		RefMap map = new RefMap("refs/heads/foo/"
		assertEquals(2

		assertSame(b
		assertSame(c
		assertNull(map.get("refs/heads/foo/bar/B"));
		assertNull(map.get("refs/heads/A"));

		assertTrue(map.containsKey("bar/B"));
		assertTrue(map.containsKey("rab/C"));
		assertFalse(map.containsKey("refs/heads/foo/bar/B"));
		assertFalse(map.containsKey("refs/heads/A"));

		Iterator<Map.Entry<String
		Map.Entry<String
		assertTrue(itr.hasNext());
		ent = itr.next();
		assertEquals("bar/B"
		assertSame(b
		assertTrue(itr.hasNext());
		ent = itr.next();
		assertEquals("rab/C"
		assertSame(c
		assertFalse(itr.hasNext());
	}

	@Test
	public void testPut_KeyMustMatchName_NoPrefix() {
		final Ref refA = newRef("refs/heads/A"
		RefMap map = new RefMap(""
		try {
			map.put("FOO"
			fail("map accepted invalid key/value pair");
		} catch (IllegalArgumentException err) {
		}
	}

	@Test
	public void testPut_KeyMustMatchName_WithPrefix() {
		final Ref refA = newRef("refs/heads/A"
		RefMap map = new RefMap("refs/heads/"
		try {
			map.put("FOO"
			fail("map accepted invalid key/value pair");
		} catch (IllegalArgumentException err) {
		}
	}

	@Test
	public void testPut_NoPrefix() {
		final Ref refA_one = newRef("refs/heads/A"
		final Ref refA_two = newRef("refs/heads/A"

		packed = toList(refA_one);

		RefMap map = new RefMap(""
		assertSame(refA_one
		assertSame(refA_one

		assertSame(refA_two
		assertSame(refA_one
		assertEquals(0

		assertSame(refA_two
		assertSame(refA_one
	}

	@Test
	public void testPut_WithPrefix() {
		final Ref refA_one = newRef("refs/heads/A"
		final Ref refA_two = newRef("refs/heads/A"

		packed = toList(refA_one);

		RefMap map = new RefMap("refs/heads/"
		assertSame(refA_one
		assertSame(refA_one

		assertSame(refA_two
		assertSame(refA_one
		assertEquals(0

		assertSame(refA_two
		assertSame(refA_one
	}

	@Test
	public void testPut_CollapseResolved() {
		final Ref master = newRef("refs/heads/master"
		final Ref headU = newRef("HEAD"
		final Ref headR = newRef("HEAD"
		final Ref a = newRef("refs/heads/A"

		loose = toList(headU
		resolved = toList(headR);

		RefMap map = new RefMap(""
		assertNull(map.put(a.getName()
		assertSame(a
		assertSame(headR
	}

	@Test
	public void testRemove() {
		final Ref master = newRef("refs/heads/master"
		final Ref headU = newRef("HEAD"
		final Ref headR = newRef("HEAD"

		packed = toList(master);
		loose = toList(headU
		resolved = toList(headR);

		RefMap map = new RefMap(""
		assertNull(map.remove("not.a.reference"));

		assertSame(master
		assertNull(map.get("refs/heads/master"));

		assertSame(headR
		assertNull(map.get("HEAD"));

		assertTrue(map.isEmpty());
	}

	@Test
	public void testToString_NoPrefix() {
		final Ref a = newRef("refs/heads/A"
		final Ref b = newRef("refs/heads/B"

		packed = toList(a

		StringBuilder exp = new StringBuilder();
		exp.append("[");
		exp.append(a.toString());
		exp.append("
		exp.append(b.toString());
		exp.append("]");

		RefMap map = new RefMap(""
		assertEquals(exp.toString()
	}

	@Test
	public void testToString_WithPrefix() {
		final Ref a = newRef("refs/heads/A"
		final Ref b = newRef("refs/heads/foo/B"
		final Ref c = newRef("refs/heads/foo/C"
		final Ref g = newRef("refs/heads/g"

		packed = toList(a

		StringBuilder exp = new StringBuilder();
		exp.append("[");
		exp.append(b.toString());
		exp.append("
		exp.append(c.toString());
		exp.append("]");

		RefMap map = new RefMap("refs/heads/foo/"
		assertEquals(exp.toString()
	}

	@Test
	public void testEntryType() {
		final Ref a = newRef("refs/heads/A"
		final Ref b = newRef("refs/heads/B"

		packed = toList(a

		RefMap map = new RefMap("refs/heads/"
		Iterator<Map.Entry<String
		Map.Entry<String
		Map.Entry<String

		assertEquals(ent_a.hashCode()
		assertEquals(ent_a
		assertFalse(ent_a.equals(ent_b));

		assertEquals(a.toString()
	}

	@Test
	public void testEntryTypeSet() {
		final Ref refA_one = newRef("refs/heads/A"
		final Ref refA_two = newRef("refs/heads/A"

		packed = toList(refA_one);

		RefMap map = new RefMap("refs/heads/"
		assertSame(refA_one

		Map.Entry<String
		assertEquals("A"
		assertSame(refA_one

		assertSame(refA_one
		assertSame(refA_two
		assertSame(refA_two
		assertEquals(1
	}

	private static RefList<Ref> toList(Ref... refs) {
		RefList.Builder<Ref> b = new RefList.Builder<>(refs.length);
		b.addAll(refs
		return b.toRefList();
	}

	private static Ref newRef(String name
		return newRef(name
				new ObjectIdRef.Unpeeled(Ref.Storage.NEW
	}

	private static Ref newRef(String name
		return new SymbolicRef(name
	}

	private static Ref newRef(String name
		return new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
	}
}
