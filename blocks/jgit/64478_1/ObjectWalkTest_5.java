
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class T0002_TreeTest extends SampleDataRepositoryTestCase {
	private static final ObjectId SOME_FAKE_ID = ObjectId.fromString(
			"0123456789abcdef0123456789abcdef01234567");

	private static int compareNamesUsingSpecialCompare(String a
			throws UnsupportedEncodingException {
		char lasta = '\0';
		byte[] abytes;
		if (a.length() > 0 && a.charAt(a.length()-1) == '/') {
			lasta = '/';
			a = a.substring(0
		}
		abytes = a.getBytes("ISO-8859-1");
		char lastb = '\0';
		byte[] bbytes;
		if (b.length() > 0 && b.charAt(b.length()-1) == '/') {
			lastb = '/';
			b = b.substring(0
		}
		bbytes = b.getBytes("ISO-8859-1");
		return Tree.compareNames(abytes
	}

	@Test
	public void test000_sort_01() throws UnsupportedEncodingException {
		assertEquals(0
	}

	@Test
	public void test000_sort_02() throws UnsupportedEncodingException {
		assertEquals(-1
		assertEquals(1
	}

	@Test
	public void test000_sort_03() throws UnsupportedEncodingException {
		assertEquals(1
		assertEquals(1
		assertEquals(-1
		assertEquals(-1
		assertEquals(1
		assertEquals(-1
	}

	@Test
	public void test000_sort_04() throws UnsupportedEncodingException {
		assertEquals(-1
		assertEquals(1
	}

	@Test
	public void test000_sort_05() throws UnsupportedEncodingException {
		assertEquals(-1
		assertEquals(1

	}

	@Test
	public void test001_createEmpty() throws IOException {
		final Tree t = new Tree(db);
		assertTrue("isLoaded"
		assertTrue("isModified"
		assertTrue("no parent"
		assertTrue("isRoot"
		assertTrue("no name"
		assertTrue("no nameUTF8"
		assertTrue("has entries array"
		assertEquals("entries is empty"
		assertEquals("full name is empty"
		assertTrue("no id"
		assertTrue("database is r"
		assertTrue("no foo child"
		assertTrue("no foo child"
	}

	@Test
	public void test002_addFile() throws IOException {
		final Tree t = new Tree(db);
		t.setId(SOME_FAKE_ID);
		assertTrue("has id"
		assertFalse("not modified"

		final String n = "bob";
		final FileTreeEntry f = t.addFile(n);
		assertNotNull("have file"
		assertEquals("name matches"
		assertEquals("name matches"
				"UTF-8"));
		assertEquals("full name matches"
		assertTrue("no id"
		assertTrue("is modified"
		assertTrue("has no id"
		assertTrue("found bob"

		final TreeEntry[] i = t.members();
		assertNotNull("members array not null"
		assertTrue("iterator is not empty"
		assertTrue("iterator returns file"
		assertTrue("iterator is empty"
	}

	@Test
	public void test004_addTree() throws IOException {
		final Tree t = new Tree(db);
		t.setId(SOME_FAKE_ID);
		assertTrue("has id"
		assertFalse("not modified"

		final String n = "bob";
		final Tree f = t.addTree(n);
		assertNotNull("have tree"
		assertEquals("name matches"
		assertEquals("name matches"
				"UTF-8"));
		assertEquals("full name matches"
		assertTrue("no id"
		assertTrue("parent matches"
		assertTrue("repository matches"
		assertTrue("isLoaded"
		assertFalse("has items"
		assertFalse("is root"
		assertTrue("parent is modified"
		assertTrue("parent has no id"
		assertTrue("found bob child"

		final TreeEntry[] i = t.members();
		assertTrue("iterator is not empty"
		assertTrue("iterator returns file"
		assertEquals("iterator is empty"
	}

	@Test
	public void test005_addRecursiveFile() throws IOException {
		final Tree t = new Tree(db);
		final FileTreeEntry f = t.addFile("a/b/c");
		assertNotNull("created f"
		assertEquals("c"
		assertEquals("b"
		assertEquals("a"
		assertTrue("t is great-grandparent"
				.getParent());
	}

	@Test
	public void test005_addRecursiveTree() throws IOException {
		final Tree t = new Tree(db);
		final Tree f = t.addTree("a/b/c");
		assertNotNull("created f"
		assertEquals("c"
		assertEquals("b"
		assertEquals("a"
		assertTrue("t is great-grandparent"
				.getParent());
	}

	@Test
	public void test006_addDeepTree() throws IOException {
		final Tree t = new Tree(db);

		final Tree e = t.addTree("e");
		assertNotNull("have e"
		assertTrue("e.parent == t"
		final Tree f = t.addTree("f");
		assertNotNull("have f"
		assertTrue("f.parent == t"
		final Tree g = f.addTree("g");
		assertNotNull("have g"
		assertTrue("g.parent == f"
		final Tree h = g.addTree("h");
		assertNotNull("have h"
		assertTrue("h.parent = g"

		h.setId(SOME_FAKE_ID);
		assertTrue("h not modified"
		g.setId(SOME_FAKE_ID);
		assertTrue("g not modified"
		f.setId(SOME_FAKE_ID);
		assertTrue("f not modified"
		e.setId(SOME_FAKE_ID);
		assertTrue("e not modified"
		t.setId(SOME_FAKE_ID);
		assertTrue("t not modified."

		assertEquals("full path of h ok"
		assertTrue("Can find h"
		assertTrue("Can't find f/z"
		assertTrue("Can't find y/z"

		final FileTreeEntry i = h.addFile("i");
		assertNotNull(i);
		assertEquals("full path of i ok"
		assertTrue("Can find i"
		assertTrue("h modified"
		assertTrue("g modified"
		assertTrue("f modified"
		assertTrue("e not modified"
		assertTrue("t modified"

		assertTrue("h no id"
		assertTrue("g no id"
		assertTrue("f no id"
		assertTrue("e has id"
		assertTrue("t no id"
	}

	@Test
	public void test007_manyFileLookup() throws IOException {
		final Tree t = new Tree(db);
		final List<FileTreeEntry> files = new ArrayList<FileTreeEntry>(26 * 26);
		for (char level1 = 'a'; level1 <= 'z'; level1++) {
			for (char level2 = 'a'; level2 <= 'z'; level2++) {
				final String n = "." + level1 + level2 + "9";
				final FileTreeEntry f = t.addFile(n);
				assertNotNull("File " + n + " added."
				assertEquals(n
				files.add(f);
			}
		}
		assertEquals(files.size()
		final TreeEntry[] ents = t.members();
		assertNotNull(ents);
		assertEquals(files.size()
		for (int k = 0; k < ents.length; k++) {
			assertTrue("File " + files.get(k).getName()
					+ " is at " + k + "."
		}
	}

	@Test
	public void test008_SubtreeInternalSorting() throws IOException {
		final Tree t = new Tree(db);
		final FileTreeEntry e0 = t.addFile("a-b");
		final FileTreeEntry e1 = t.addFile("a-");
		final FileTreeEntry e2 = t.addFile("a=b");
		final Tree e3 = t.addTree("a");
		final FileTreeEntry e4 = t.addFile("a=");

		final TreeEntry[] ents = t.members();
		assertSame(e1
		assertSame(e0
		assertSame(e3
		assertSame(e4
		assertSame(e2
	}

	@Test
	public void test009_SymlinkAndGitlink() throws IOException {
		final Tree symlinkTree = mapTree("symlink");
		assertTrue("Symlink entry exists"
		final Tree gitlinkTree = mapTree("gitlink");
		assertTrue("Gitlink entry exists"
	}

	private Tree mapTree(String name) throws IOException {
		ObjectId id = db.resolve(name + "^{tree}");
		return new Tree(db
	}
}
