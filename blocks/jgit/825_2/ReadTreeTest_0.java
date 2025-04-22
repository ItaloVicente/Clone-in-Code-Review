package org.eclipse.jgit.lib;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.CorruptObjectException;

public class DirCacheCheckoutTest extends ReadTreeTest {
	protected Checkout getCheckoutImpl(Tree head
		try {
			return new DirCacheCheckoutImpl(head
		} catch (IOException e) {
			return null;
		}
	}

	public void testDirectoryFileSimple() throws IOException {
		theIndex = new GitIndex(db);
		theIndex.add(trash
		Tree treeDF = db.mapTree(theIndex.writeTree());

		recursiveDelete(new File(trash
		theIndex = new GitIndex(db);
		theIndex.add(trash
		Tree treeDFDF = db.mapTree(theIndex.writeTree());

		theIndex = new GitIndex(db);
		recursiveDelete(new File(trash

		theIndex.add(trash
		theReadTree = getCheckoutImpl(treeDF
		theReadTree.prescanTwoTrees();

		assertTrue(theReadTree.removed().contains("DF"));
		assertTrue(theReadTree.updated().containsKey("DF/DF"));

		recursiveDelete(new File(trash
		theIndex = new GitIndex(db);
		theIndex.add(trash

		theReadTree = getCheckoutImpl(treeDFDF
		theReadTree.prescanTwoTrees();
		assertTrue(theReadTree.removed().contains("DF/DF"));

	}

	public void testDirectoryFileConflicts_1() throws Exception {
		doit(mk("DF/DF")
		assertRemoved("DF/DF");
	}

	public void testDirectoryFileConflicts_9() throws Exception {
		doit(mk("DF")
	}

	public void testDirectoryFileConflicts_10() throws Exception {
		cleanUpDF();
		doit(mk("DF")
	}

	public void testDirectoryFileConflicts_15() throws Exception {
		doit(mkmap()
		assertUpdated("DF/DF");
	}

	public void testDirectoryFileConflicts_15b() throws Exception {
		doit(mkmap()
		assertUpdated("DF/DF/DF/DF");
	}

	public void testDirectoryFileConflicts_16() throws Exception {
		cleanUpDF();
		doit(mkmap()
		assertRemoved("DF/DF/DF");
	}

	public void testDirectoryFileConflicts_17() throws Exception {
		cleanUpDF();
		setupCase(mkmap()
		writeTrashFile("DF/DF/DF"
		go();
		assertConflict("DF/DF/DF");
	}

	public void testDirectoryFileConflicts_19() throws Exception {
		cleanUpDF();
		doit(mk("DF/DF/DF/DF")
	}

	public void testUntrackedConflicts() throws IOException {
		setupCase(null
		writeTrashFile("foo"
		go();


		recursiveDelete(new File(trash
		setupCase(null
		writeTrashFile("foo/bar/baz"
		writeTrashFile("foo/blahblah"
		go();


		recursiveDelete(new File(trash

		setupCase(mkmap("foo/bar"
				mkmap("foo/bar"
		assertTrue(new File(trash
		go();

	}

	class DirCacheCheckoutImpl implements Checkout {
		private Tree head;
		private Tree merge;

		private DirCache dc;

		private DirCacheCheckout co;
		private ObjectWriter ow = new ObjectWriter(db);

		public DirCacheCheckoutImpl(Tree head
				throws IOException {
			this.head = head;
			this.merge = merge;
			head.setId(ow.writeTree(head));
			merge.setId(ow.writeTree(merge));
			this.dc = dc;
			co = new DirCacheCheckout(db
		}

		public HashMap<String
			return co.getUpdated();
		}

		public ArrayList<String> conflicts() {
			return co.getConflicts();
		}

		public ArrayList<String> removed() {
			return co.getRemoved();
		}

		public void prescanTwoTrees() throws IOException {
			if (head == null) {
				co.prescanOneTree(dc
			} else {
				co.preScanTwoTrees(head.getTreeId()
			}
		}

		public void checkout() throws IOException {
			co.checkout();
		}

		public void assertIndex(HashMap<String
				throws CorruptObjectException
			Set<String> foundPathes = new HashSet<String>();
			String expectedValue;
			String path;
			assertEquals("Index has not the right size."
					dc.getEntryCount());
			for (int j = 0; j < dc.getEntryCount(); j++) {
				path = dc.getEntry(j).getPathString();
				expectedValue = i.get(path);
				assertNotNull(expectedValue
						+ path + " in index");
				assertTrue("unexpected content for path " + path
						+ " in index. Expected: <" + expectedValue + ">"
						Arrays.equals(db.openBlob(dc.getEntry(j).getObjectId())
								.getBytes()
				foundPathes.add(path);
			}
			dc.unlock();
		}
	}

	protected void assertIndex(HashMap<String
			throws CorruptObjectException
		Set<String> foundPathes = new HashSet<String>();
		DirCache index = DirCache.lock(db);
		String expectedValue;
		String path;
		assertEquals("Index has not the right size."
				index.getEntryCount());
		for (int j = 0; j < index.getEntryCount(); j++) {
			path = index.getEntry(j).getPathString();
			expectedValue = i.get(path);
			assertNotNull(expectedValue
					+ path + " in index");
			assertTrue(
					"unexpected content for path " + path
							+ " in index. Expected: <" + expectedValue + ">"
					Arrays.equals(db.openBlob(index.getEntry(j).getObjectId())
							.getBytes()
							i.get(index.getEntry(j).getPathString()).getBytes()));
			foundPathes.add(path);
		}
		index.unlock();
	}
}
