
package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.junit.Test;

public class EmptyTreeIteratorTest extends RepositoryTestCase {
	@Test
	public void testAtEOF() throws Exception {
		final EmptyTreeIterator etp = new EmptyTreeIterator();
		assertTrue(etp.first());
		assertTrue(etp.eof());
	}

	@Test
	public void testCreateSubtreeIterator() throws Exception {
		final EmptyTreeIterator etp = new EmptyTreeIterator();
		final ObjectReader reader = db.newObjectReader();
		final AbstractTreeIterator sub = etp.createSubtreeIterator(reader);
		assertNotNull(sub);
		assertTrue(sub.first());
		assertTrue(sub.eof());
		assertTrue(sub instanceof EmptyTreeIterator);
	}

	@Test
	public void testEntryObjectId() throws Exception {
		final EmptyTreeIterator etp = new EmptyTreeIterator();
		assertSame(ObjectId.zeroId()
		assertNotNull(etp.idBuffer());
		assertEquals(0
		assertEquals(ObjectId.zeroId()
	}

	@Test
	public void testNextDoesNothing() throws Exception {
		final EmptyTreeIterator etp = new EmptyTreeIterator();
		etp.next(1);
		assertTrue(etp.first());
		assertTrue(etp.eof());
		assertEquals(ObjectId.zeroId()

		etp.next(1);
		assertTrue(etp.first());
		assertTrue(etp.eof());
		assertEquals(ObjectId.zeroId()
	}

	@Test
	public void testBackDoesNothing() throws Exception {
		final EmptyTreeIterator etp = new EmptyTreeIterator();
		etp.back(1);
		assertTrue(etp.first());
		assertTrue(etp.eof());
		assertEquals(ObjectId.zeroId()

		etp.back(1);
		assertTrue(etp.first());
		assertTrue(etp.eof());
		assertEquals(ObjectId.zeroId()
	}

	@Test
	public void testStopWalkCallsParent() throws Exception {
		final boolean called[] = new boolean[1];
		assertFalse(called[0]);

		final EmptyTreeIterator parent = new EmptyTreeIterator() {
			@Override
			public void stopWalk() {
				called[0] = true;
			}
		};
		final ObjectReader reader = db.newObjectReader();
		parent.createSubtreeIterator(reader).stopWalk();
		assertTrue(called[0]);
	}
}
