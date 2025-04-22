
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Constants;
import org.junit.Test;

public class RevObjectTest extends RevWalkTestCase {
	@Test
	public void testId() throws Exception {
		final RevCommit a = commit();
		assertSame(a
	}

	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals() throws Exception {
		final RevCommit a1 = commit();
		final RevCommit b1 = commit();

		assertTrue(a1.equals(a1));
		assertTrue(a1.equals((Object) a1));
		assertFalse(a1.equals(b1));

		assertTrue(a1.equals(a1));
		assertTrue(a1.equals((Object) a1));
		assertFalse(a1.equals(""));

		final RevCommit a2;
		final RevCommit b2;
		try (RevWalk rw2 = new RevWalk(db)) {
			a2 = rw2.parseCommit(a1);
			b2 = rw2.parseCommit(b1);
		}
		assertNotSame(a1
		assertNotSame(b1

		assertTrue(a1.equals(a2));
		assertTrue(b1.equals(b2));

		assertEquals(a1.hashCode()
		assertEquals(b1.hashCode()

		assertTrue(AnyObjectId.equals(a1
		assertTrue(AnyObjectId.equals(b1
	}

	@Test
	public void testRevObjectTypes() throws Exception {
		assertEquals(Constants.OBJ_TREE
		assertEquals(Constants.OBJ_COMMIT
		assertEquals(Constants.OBJ_BLOB
		assertEquals(Constants.OBJ_TAG
	}

	@Test
	public void testHasRevFlag() throws Exception {
		final RevCommit a = commit();
		assertFalse(a.has(RevFlag.UNINTERESTING));
		a.flags |= RevWalk.UNINTERESTING;
		assertTrue(a.has(RevFlag.UNINTERESTING));
	}

	@Test
	public void testHasAnyFlag() throws Exception {
		final RevCommit a = commit();
		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");
		final RevFlagSet s = new RevFlagSet();
		s.add(flag1);
		s.add(flag2);

		assertFalse(a.hasAny(s));
		a.flags |= flag1.mask;
		assertTrue(a.hasAny(s));
	}

	@Test
	public void testHasAllFlag() throws Exception {
		final RevCommit a = commit();
		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");
		final RevFlagSet s = new RevFlagSet();
		s.add(flag1);
		s.add(flag2);

		assertFalse(a.hasAll(s));
		a.flags |= flag1.mask;
		assertFalse(a.hasAll(s));
		a.flags |= flag2.mask;
		assertTrue(a.hasAll(s));
	}

	@Test
	public void testAddRevFlag() throws Exception {
		final RevCommit a = commit();
		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");
		assertEquals(RevWalk.PARSED

		a.add(flag1);
		assertEquals(RevWalk.PARSED | flag1.mask

		a.add(flag2);
		assertEquals(RevWalk.PARSED | flag1.mask | flag2.mask
	}

	@Test
	public void testAddRevFlagSet() throws Exception {
		final RevCommit a = commit();
		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");
		final RevFlagSet s = new RevFlagSet();
		s.add(flag1);
		s.add(flag2);

		assertEquals(RevWalk.PARSED

		a.add(s);
		assertEquals(RevWalk.PARSED | flag1.mask | flag2.mask
	}

	@Test
	public void testRemoveRevFlag() throws Exception {
		final RevCommit a = commit();
		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");
		a.add(flag1);
		a.add(flag2);
		assertEquals(RevWalk.PARSED | flag1.mask | flag2.mask
		a.remove(flag2);
		assertEquals(RevWalk.PARSED | flag1.mask
	}

	@Test
	public void testRemoveRevFlagSet() throws Exception {
		final RevCommit a = commit();
		final RevFlag flag1 = rw.newFlag("flag1");
		final RevFlag flag2 = rw.newFlag("flag2");
		final RevFlag flag3 = rw.newFlag("flag3");
		final RevFlagSet s = new RevFlagSet();
		s.add(flag1);
		s.add(flag2);
		a.add(flag3);
		a.add(s);
		assertEquals(RevWalk.PARSED | flag1.mask | flag2.mask | flag3.mask
		a.remove(s);
		assertEquals(RevWalk.PARSED | flag3.mask
	}
}
