
package org.eclipse.jgit.treewalk;

import static org.eclipse.jgit.lib.FileMode.GITLINK;
import static org.eclipse.jgit.lib.FileMode.REGULAR_FILE;
import static org.eclipse.jgit.lib.FileMode.SYMLINK;
import static org.eclipse.jgit.lib.FileMode.TREE;
import static org.eclipse.jgit.lib.ObjectId.fromString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.TreeFormatter;
import org.junit.Test;

public class RawTreeIteratorTest {
	private final ObjectId idA = fromString("ab9c715d21d5486e59083fb6071566aa6ecd4d42");
	private final ObjectId idB = fromString("b213e8e25bb2442326e86cbfb9ef56319f482869");
	private final ObjectId idC = fromString("c61814fe9c3fc0503d4654ef4aace6a804da5ae7");

	private final ObjectId idD = fromString("d5cc76524bc29d856340736a9de8d0889b17bc13");

	@Test
	public void testEmptyTreeHasNoNext() {
		TreeFormatter tree = new TreeFormatter();
		RawTreeIterator itr = new RawTreeIterator(tree.toByteArray());
		assertSame(itr
		assertFalse(itr.hasNext());
	}

	@Test
	public void testOneEntry() {
		TreeFormatter tree = new TreeFormatter();
		tree.append("a"
		RawTreeIterator itr = new RawTreeIterator(tree.toByteArray());

		assertTrue(itr.hasNext());
		assertSame(itr
		assertEquals(REGULAR_FILE.getBits()
		assertEquals("a"
		assertEquals(idA
		assertFalse(itr.hasNext());
	}

	@Test
	public void testFindAttributesWhenFirst() {
		TreeFormatter tree = new TreeFormatter();
		tree.append(".gitattributes"
		RawTreeIterator itr = new RawTreeIterator(tree.toByteArray());

		assertTrue(itr.findFile(".gitattributes"));
		assertEquals(REGULAR_FILE.getBits()
		assertEquals(".gitattributes"
		assertEquals(idA
	}

	@Test
	public void testFindAttributesWhenSecond() {
		TreeFormatter tree = new TreeFormatter();
		tree.append(".config"
		tree.append(".gitattributes"
		RawTreeIterator itr = new RawTreeIterator(tree.toByteArray());

		assertTrue(itr.findFile(".gitattributes"));
		assertEquals(REGULAR_FILE.getBits()
		assertEquals(".gitattributes"
		assertEquals(idB
	}

	@Test
	public void testFindAttributesWhenMissing() {
		TreeFormatter tree = new TreeFormatter();
		tree.append("src"
		tree.append("zoo"
		RawTreeIterator itr = new RawTreeIterator(tree.toByteArray());

		assertFalse(itr.findFile(".gitattributes"));
		assertEquals(11
		assertEquals("src"
	}

	@SuppressWarnings("boxing")
	@Test
	public void testOldMode() throws IOException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		buf.write(Constants.encode(String.format("%o"
		buf.write(' ');
		buf.write(Constants.encode("a"));
		buf.write(0);
		idA.copyRawTo(buf);
		RawTreeIterator itr = new RawTreeIterator(buf.toByteArray());

		assertTrue(itr.hasNext());
		assertSame(itr
		assertEquals(0100664
		assertEquals(REGULAR_FILE
		assertEquals("a"
		assertEquals(idA
		assertFalse(itr.hasNext());
	}

	@Test
	public void testMultipleEntries() {
		TreeFormatter tree = new TreeFormatter();
		tree.append("a"
		tree.append("foo"
		tree.append("git"
		tree.append("sym"
		RawTreeIterator itr = new RawTreeIterator(tree.toByteArray());

		assertTrue(itr.hasNext());
		assertSame(itr
		assertEquals(REGULAR_FILE.getBits()
		assertEquals("a"
		assertEquals(idA

		assertSame(itr
		assertEquals(TREE.getBits()
		assertEquals("foo"
		assertEquals(idB

		assertSame(itr
		assertEquals(GITLINK.getBits()
		assertEquals("git"
		assertEquals(idC

		assertSame(itr
		assertEquals(SYMLINK.getBits()
		assertEquals("sym"
		assertEquals(idD
		assertFalse(itr.hasNext());
	}

	@Test
	public void testLongEntryName() throws Exception {
		int n = AbstractTreeIterator.DEFAULT_PATH_SIZE * 4;
		StringBuilder b = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			b.append('q');
		}
		String name = b.toString();

		TreeFormatter tree = new TreeFormatter();
		tree.append(name
		RawTreeIterator itr = new RawTreeIterator(tree.toByteArray());

		assertTrue(itr.hasNext());
		assertSame(itr
		assertEquals(name
	}
}
