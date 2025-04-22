
package org.eclipse.jgit.lib;

import junit.framework.TestCase;

public class ObjectIdRefTest extends TestCase {
	private static final ObjectId ID_A = ObjectId
			.fromString("41eb0d88f833b558bddeb269b7ab77399cdf98ed");

	private static final ObjectId ID_B = ObjectId
			.fromString("698dd0b8d0c299f080559a1cffc7fe029479a408");

	private static final String name = "refs/heads/a.test.ref";

	public void testConstructor_PeeledStatusNotKnown() {
		ObjectIdRef r;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertSame(Ref.Storage.LOOSE
		assertSame(name
		assertSame(ID_A
		assertFalse("not peeled"
		assertNull("no peel id"
		assertSame("leaf is this"
		assertSame("target is this"
		assertFalse("not symbolic"

		r = new ObjectIdRef.Unpeeled(Ref.Storage.PACKED
		assertSame(Ref.Storage.PACKED

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE_PACKED
		assertSame(Ref.Storage.LOOSE_PACKED

		r = new ObjectIdRef.Unpeeled(Ref.Storage.NEW
		assertSame(Ref.Storage.NEW
		assertSame(name
		assertNull("no id on new ref"
		assertFalse("not peeled"
		assertNull("no peel id"
		assertSame("leaf is this"
		assertSame("target is this"
		assertFalse("not symbolic"
	}

	public void testConstructor_Peeled() {
		ObjectIdRef r;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertSame(Ref.Storage.LOOSE
		assertSame(name
		assertSame(ID_A
		assertFalse("not peeled"
		assertNull("no peel id"
		assertSame("leaf is this"
		assertSame("target is this"
		assertFalse("not symbolic"

		r = new ObjectIdRef.PeeledNonTag(Ref.Storage.LOOSE
		assertTrue("is peeled"
		assertNull("no peel id"

		r = new ObjectIdRef.PeeledTag(Ref.Storage.LOOSE
		assertTrue("is peeled"
		assertSame(ID_B
	}

	public void testToString() {
		ObjectIdRef r;

		r = new ObjectIdRef.Unpeeled(Ref.Storage.LOOSE
		assertEquals("Ref[" + name + "=" + ID_A.name() + "]"
	}
}
