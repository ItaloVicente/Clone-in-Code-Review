
package org.eclipse.jgit.treewalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.Config;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectReader;
import org.junit.Test;


public class AbstractTreeIteratorTest {
	private static String prefix(String path) {
		final int s = path.lastIndexOf('/');
		return s > 0 ? path.substring(0
	}

	public static class FakeTreeIterator extends WorkingTreeIterator {
		public FakeTreeIterator(String pathName
			super(prefix(pathName)
			mode = fileMode.getBits();

			final int s = pathName.lastIndexOf('/');
			final byte[] name = Constants.encode(pathName.substring(s + 1));
			ensurePathCapacity(pathOffset + name.length
			System.arraycopy(name
			pathLen = pathOffset + name.length;
		}

		@Override
		public AbstractTreeIterator createSubtreeIterator(ObjectReader reader)
				throws IncorrectObjectTypeException
			return null;
		}
	}

	@Test
	public void testPathCompare() throws Exception {
		assertTrue(new FakeTreeIterator("a"
				new FakeTreeIterator("a"

		assertTrue(new FakeTreeIterator("a"
				new FakeTreeIterator("a"

		assertTrue(new FakeTreeIterator("a"
				new FakeTreeIterator("a"

		assertTrue(new FakeTreeIterator("a"
				new FakeTreeIterator("a"
	}

	@Test
	public void testGrowPath() throws Exception {
		final FakeTreeIterator i = new FakeTreeIterator("ab"
		final byte[] origpath = i.path;
		assertEquals(i.path[0]
		assertEquals(i.path[1]

		i.growPath(2);

		assertNotSame(origpath
		assertEquals(origpath.length * 2
		assertEquals(i.path[0]
		assertEquals(i.path[1]
	}

	@Test
	public void testEnsurePathCapacityFastCase() throws Exception {
		final FakeTreeIterator i = new FakeTreeIterator("ab"
		final int want = 50;
		final byte[] origpath = i.path;
		assertEquals(i.path[0]
		assertEquals(i.path[1]
		assertTrue(want < i.path.length);

		i.ensurePathCapacity(want

		assertSame(origpath
		assertEquals(i.path[0]
		assertEquals(i.path[1]
	}

	@Test
	public void testEnsurePathCapacityGrows() throws Exception {
		final FakeTreeIterator i = new FakeTreeIterator("ab"
		final int want = 384;
		final byte[] origpath = i.path;
		assertEquals(i.path[0]
		assertEquals(i.path[1]
		assertTrue(i.path.length < want);

		i.ensurePathCapacity(want

		assertNotSame(origpath
		assertEquals(512
		assertEquals(i.path[0]
		assertEquals(i.path[1]
	}

	@Test
	public void testEntryFileMode() {
		for (FileMode m : new FileMode[] { FileMode.TREE
				FileMode.REGULAR_FILE
				FileMode.GITLINK
			final FakeTreeIterator i = new FakeTreeIterator("a"
			assertEquals(m.getBits()
			assertSame(m
		}
	}

	@Test
	public void testEntryPath() {
		FakeTreeIterator i = new FakeTreeIterator("a/b/cd"
		assertEquals("a/b/cd"
		assertEquals(2
		byte[] b = new byte[3];
		b[0] = 0x0a;
		i.getName(b
		assertEquals(0x0a
		assertEquals('c'
		assertEquals('d'
	}

	@Test
	public void testCreateEmptyTreeIterator() {
		FakeTreeIterator i = new FakeTreeIterator("a/b/cd"
		EmptyTreeIterator e = i.createEmptyTreeIterator();
		assertNotNull(e);
		assertEquals(i.getEntryPathString() + "/"
	}
}
