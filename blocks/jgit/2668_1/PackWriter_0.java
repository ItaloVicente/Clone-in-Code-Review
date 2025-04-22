
package org.eclipse.jgit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Test;

public class BlockListTest {
	@Test
	public void testEmptyList() {
		BlockList<String> empty;

		empty = new BlockList<String>();
		assertEquals(0
		assertTrue(empty.isEmpty());
		assertFalse(empty.iterator().hasNext());

		empty = new BlockList<String>(0);
		assertEquals(0
		assertTrue(empty.isEmpty());
		assertFalse(empty.iterator().hasNext());

		empty = new BlockList<String>(1);
		assertEquals(0
		assertTrue(empty.isEmpty());
		assertFalse(empty.iterator().hasNext());

		empty = new BlockList<String>(64);
		assertEquals(0
		assertTrue(empty.isEmpty());
		assertFalse(empty.iterator().hasNext());
	}

	@Test
	public void testGet() {
		BlockList<String> list = new BlockList<String>(4);

		try {
			list.get(-1);
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(-1)
		}

		try {
			list.get(0);
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(0)
		}

		try {
			list.get(4);
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(4)
		}

		String fooStr = "foo";
		String barStr = "bar";
		String foobarStr = "foobar";

		list.add(fooStr);
		list.add(barStr);
		list.add(foobarStr);

		assertSame(fooStr
		assertSame(barStr
		assertSame(foobarStr

		try {
			list.get(3);
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(3)
		}
	}

	@Test
	public void testSet() {
		BlockList<String> list = new BlockList<String>(4);

		try {
			list.set(-1
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(-1)
		}

		try {
			list.set(0
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(0)
		}

		try {
			list.set(4
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(4)
		}

		String fooStr = "foo";
		String barStr = "bar";
		String foobarStr = "foobar";

		list.add(fooStr);
		list.add(barStr);
		list.add(foobarStr);

		assertSame(fooStr
		assertSame(barStr
		assertSame(foobarStr

		assertSame(fooStr
		assertSame(barStr

		assertSame(barStr
		assertSame(fooStr

		try {
			list.set(3
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(3)
		}
	}

	@Test
	public void testAddToEnd() {
		BlockList<Integer> list = new BlockList<Integer>(4);
		int cnt = BlockList.BLOCK_SIZE * 3;

		for (int i = 0; i < cnt; i++)
			list.add(Integer.valueOf(42 + i));
		assertEquals(cnt

		for (int i = 0; i < cnt; i++)
			assertEquals(Integer.valueOf(42 + i)

		list.clear();
		assertEquals(0
		assertTrue(list.isEmpty());

		for (int i = 0; i < cnt; i++)
			list.add(i
		assertEquals(cnt

		for (int i = 0; i < cnt; i++)
			assertEquals(Integer.valueOf(42 + i)
	}

	@Test
	public void testAddSlowPath() {
		BlockList<String> list = new BlockList<String>(4);

		String fooStr = "foo";
		String barStr = "bar";
		String foobarStr = "foobar";
		String firstStr = "first";
		String zeroStr = "zero";

		list.add(fooStr);
		list.add(barStr);
		list.add(foobarStr);
		assertEquals(3

		list.add(1
		assertEquals(4
		assertSame(fooStr
		assertSame(firstStr
		assertSame(barStr
		assertSame(foobarStr

		list.add(0
		assertEquals(5
		assertSame(zeroStr
		assertSame(fooStr
		assertSame(firstStr
		assertSame(barStr
		assertSame(foobarStr
	}

	@Test
	public void testRemoveFromEnd() {
		BlockList<String> list = new BlockList<String>(4);

		String fooStr = "foo";
		String barStr = "bar";
		String foobarStr = "foobar";

		list.add(fooStr);
		list.add(barStr);
		list.add(foobarStr);

		assertSame(foobarStr
		assertEquals(2

		assertSame(barStr
		assertEquals(1

		assertSame(fooStr
		assertEquals(0
	}

	@Test
	public void testRemoveSlowPath() {
		BlockList<String> list = new BlockList<String>(4);

		String fooStr = "foo";
		String barStr = "bar";
		String foobarStr = "foobar";

		list.add(fooStr);
		list.add(barStr);
		list.add(foobarStr);

		assertSame(barStr
		assertEquals(2
		assertSame(fooStr
		assertSame(foobarStr

		assertSame(fooStr
		assertEquals(1
		assertSame(foobarStr

		assertSame(foobarStr
		assertEquals(0
	}

	@Test
	public void testAddRemoveAdd() {
		BlockList<Integer> list = new BlockList<Integer>();
		for (int i = 0; i < BlockList.BLOCK_SIZE + 1; i++)
			list.add(Integer.valueOf(i));
		assertEquals(Integer.valueOf(BlockList.BLOCK_SIZE)
				list.remove(list.size() - 1));
		assertEquals(Integer.valueOf(BlockList.BLOCK_SIZE - 1)
				list.remove(list.size() - 1));
		assertTrue(list.add(Integer.valueOf(1)));
		assertEquals(Integer.valueOf(1)
	}

	@Test
	public void testFastIterator() {
		BlockList<Integer> list = new BlockList<Integer>(4);
		int cnt = BlockList.BLOCK_SIZE * 3;

		for (int i = 0; i < cnt; i++)
			list.add(Integer.valueOf(42 + i));
		assertEquals(cnt

		Iterator<Integer> itr = list.iterator();
		for (int i = 0; i < cnt; i++) {
			assertTrue(itr.hasNext());
			assertEquals(Integer.valueOf(42 + i)
		}
		assertFalse(itr.hasNext());
	}

	@Test
	public void testAddRejectsBadIndexes() {
		BlockList<Integer> list = new BlockList<Integer>(4);
		list.add(Integer.valueOf(41));

		try {
			list.add(-1
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(-1)
		}

		try {
			list.add(4
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(4)
		}
	}

	@Test
	public void testRemoveRejectsBadIndexes() {
		BlockList<Integer> list = new BlockList<Integer>(4);
		list.add(Integer.valueOf(41));

		try {
			list.remove(-1);
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(-1)
		}

		try {
			list.remove(4);
		} catch (IndexOutOfBoundsException badIndex) {
			assertEquals(String.valueOf(4)
		}
	}
}
