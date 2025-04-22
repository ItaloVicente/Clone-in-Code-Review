
package org.eclipse.jgit.notes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.io.IOException;

import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.MutableObjectId;
import org.junit.Test;

public class LeafBucketTest {
	@Test
	public void testEmpty() {
		LeafBucket b = new LeafBucket(0);
		assertNull(b.getNote(id(0x00)
		assertNull(b.getNote(id(0x01)
		assertNull(b.getNote(id(0xfe)
	}

	@Test
	public void testParseFive() {
		LeafBucket b = new LeafBucket(0);

		b.parseOneEntry(id(0x11)
		b.parseOneEntry(id(0x22)
		b.parseOneEntry(id(0x33)
		b.parseOneEntry(id(0x44)
		b.parseOneEntry(id(0x55)

		assertNull(b.getNote(id(0x01)
		assertEquals(id(0x81)
		assertEquals(id(0x82)
		assertEquals(id(0x83)
		assertEquals(id(0x84)
		assertEquals(id(0x85)
		assertNull(b.getNote(id(0x66)
	}

	@Test
	public void testSetFive_InOrder() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b

		assertNull(b.getNote(id(0x01)
		assertEquals(id(0x81)
		assertEquals(id(0x82)
		assertEquals(id(0x83)
		assertEquals(id(0x84)
		assertEquals(id(0x85)
		assertNull(b.getNote(id(0x66)
	}

	@Test
	public void testSetFive_ReverseOrder() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b

		assertNull(b.getNote(id(0x01)
		assertEquals(id(0x81)
		assertEquals(id(0x82)
		assertEquals(id(0x83)
		assertEquals(id(0x84)
		assertEquals(id(0x85)
		assertNull(b.getNote(id(0x66)
	}

	@Test
	public void testSetFive_MixedOrder() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertSame(b
		assertSame(b

		assertSame(b
		assertSame(b

		assertNull(b.getNote(id(0x01)
		assertEquals(id(0x81)
		assertEquals(id(0x82)
		assertEquals(id(0x83)
		assertEquals(id(0x84)
		assertEquals(id(0x85)
		assertNull(b.getNote(id(0x66)
	}

	@Test
	public void testSet_Replace() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertEquals(id(0x81)

		assertSame(b
		assertEquals(id(0x01)
	}

	@Test
	public void testRemoveMissingNote() throws IOException {
		LeafBucket b = new LeafBucket(0);
		assertNull(b.getNote(id(0x11)
		assertSame(b
		assertNull(b.getNote(id(0x11)
	}

	@Test
	public void testRemoveFirst() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b

		assertSame(b

		assertNull(b.getNote(id(0x01)
		assertNull(b.getNote(id(0x11)
		assertEquals(id(0x82)
		assertEquals(id(0x83)
		assertEquals(id(0x84)
		assertEquals(id(0x85)
		assertNull(b.getNote(id(0x66)
	}

	@Test
	public void testRemoveMiddle() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b

		assertSame(b

		assertNull(b.getNote(id(0x01)
		assertEquals(id(0x81)
		assertEquals(id(0x82)
		assertNull(b.getNote(id(0x33)
		assertEquals(id(0x84)
		assertEquals(id(0x85)
		assertNull(b.getNote(id(0x66)
	}

	@Test
	public void testRemoveLast() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b
		assertSame(b

		assertSame(b

		assertNull(b.getNote(id(0x01)
		assertEquals(id(0x81)
		assertEquals(id(0x82)
		assertEquals(id(0x83)
		assertEquals(id(0x84)
		assertNull(b.getNote(id(0x55)
		assertNull(b.getNote(id(0x66)
	}

	@Test
	public void testRemoveMakesEmpty() throws IOException {
		LeafBucket b = new LeafBucket(0);

		assertSame(b
		assertEquals(id(0x81)

		assertNull(b.set(id(0x11)
		assertNull(b.getNote(id(0x11)
	}

	private static AnyObjectId id(int first) {
		MutableObjectId id = new MutableObjectId();
		id.setByte(1
		return id;
	}
}
