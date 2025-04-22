
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SymbolicRefTest {
	private static final ObjectId ID_A = ObjectId
			.fromString("41eb0d88f833b558bddeb269b7ab77399cdf98ed");

	private static final ObjectId ID_B = ObjectId
			.fromString("698dd0b8d0c299f080559a1cffc7fe029479a408");

	private static final String targetName = "refs/heads/a.test.ref";

	private static final String name = "refs/remotes/origin/HEAD";

	@Test
	public void testConstructor() {
		Ref t;
		SymbolicRef r;

		t = new ObjectIdRef.Unpeeled(Ref.Storage.NEW
		r = new SymbolicRef(name
		assertSame(Ref.Storage.LOOSE
		assertSame(name
		assertNull("no id on new ref"
		assertFalse("not peeled"
		assertNull("no peel id"
		assertSame("leaf is t"
		assertSame("target is t"
		assertTrue("is symbolic"
		assertTrue("holds update index"

		t = new ObjectIdRef.Unpeeled(Ref.Storage.PACKED
		r = new SymbolicRef(name
		assertSame(Ref.Storage.LOOSE
		assertSame(name
		assertSame(ID_A
		assertFalse("not peeled"
		assertNull("no peel id"
		assertSame("leaf is t"
		assertSame("target is t"
		assertTrue("is symbolic"
		assertTrue("holds update index"
	}

	@Test
	public void testLeaf() {
		Ref a;
		SymbolicRef b

		a = new ObjectIdRef.PeeledTag(Ref.Storage.PACKED
		b = new SymbolicRef("B"
		c = new SymbolicRef("C"
		d = new SymbolicRef("D"

		assertSame(c
		assertSame(b
		assertSame(a

		assertSame(a
		assertSame(a
		assertSame(a
		assertSame(a

		assertSame(ID_A
		assertSame(ID_A
		assertSame(ID_A

		assertTrue(d.isPeeled());
		assertTrue(c.isPeeled());
		assertTrue(b.isPeeled());

		assertSame(ID_B
		assertSame(ID_B
		assertSame(ID_B
	}

	@Test
	public void testToString() {
		Ref a;
		SymbolicRef b

		a = new ObjectIdRef.PeeledTag(Ref.Storage.PACKED
		b = new SymbolicRef("B"
		c = new SymbolicRef("C"
		d = new SymbolicRef("D"

		assertEquals("SymbolicRef[D -> C -> B -> " + targetName + "="
				+ ID_A.name() + "(-1)]"
	}
}
