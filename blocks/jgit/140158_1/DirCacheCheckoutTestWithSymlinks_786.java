package org.eclipse.jgit.lib;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.CheckoutCommand;
import org.eclipse.jgit.api.CheckoutResult;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.dircache.DirCacheCheckout.CheckoutMetadata;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.errors.CheckoutConflictException;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.events.ChangeRecorder;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.WorkingTreeIterator;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Assume;
import org.junit.Test;

public class DirCacheCheckoutTest extends RepositoryTestCase {
	private DirCacheCheckout dco;
	protected ObjectId theHead;
	protected ObjectId theMerge;
	private DirCache dirCache;

	private void prescanTwoTrees(ObjectId head
			throws IllegalStateException
		DirCache dc = db.lockDirCache();
		try {
			dco = new DirCacheCheckout(db
			dco.preScanTwoTrees();
		} finally {
			dc.unlock();
		}
	}

	private void checkout() throws IOException {
		DirCache dc = db.lockDirCache();
		try {
			dco = new DirCacheCheckout(db
			dco.checkout();
		} finally {
			dc.unlock();
		}
	}

	private List<String> getRemoved() {
		return dco.getRemoved();
	}

	private Map<String
		return dco.getUpdated();
	}

	private List<String> getConflicts() {
		return dco.getConflicts();
	}

	private static HashMap<String
		return mkmap(a
	}

	private static HashMap<String
		if ((args.length % 2) > 0)
			throw new IllegalArgumentException("needs to be pairs");

		HashMap<String
		for (int i = 0; i < args.length; i += 2) {
			map.put(args[i]
		}

		return map;
	}

	@Test
	public void testResetHard() throws IOException
			GitAPIException {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile("f"
			writeTrashFile("D/g"
			git.add().addFilepattern(".").call();
			git.commit().setMessage("inital").call();
			assertIndex(mkmap("f"
			recorder.assertNoEvent();
			git.branchCreate().setName("topic").call();
			recorder.assertNoEvent();

			writeTrashFile("f"
			writeTrashFile("D/g"
			writeTrashFile("E/h"
			git.add().addFilepattern(".").call();
			RevCommit master = git.commit().setMessage("master-1").call();
			assertIndex(mkmap("f"
			recorder.assertNoEvent();

			checkoutBranch("refs/heads/topic");
			assertIndex(mkmap("f"
			recorder.assertEvent(new String[] { "f"
					new String[] { "E/h" });

			writeTrashFile("f"
			assertTrue(new File(db.getWorkTree()
			writeTrashFile("G/i"
			git.add().addFilepattern(".").call();
			git.add().addFilepattern(".").setUpdate(true).call();
			RevCommit topic = git.commit().setMessage("topic-1").call();
			assertIndex(mkmap("f"
			recorder.assertNoEvent();

			writeTrashFile("untracked"

			resetHard(master);
			assertIndex(mkmap("f"
			recorder.assertEvent(new String[] { "f"
					new String[] { "G"

			resetHard(topic);
			assertIndex(mkmap("f"
			assertWorkDir(mkmap("f"
					"untracked"));
			recorder.assertEvent(new String[] { "f"
					new String[] { "D"

			assertEquals(MergeStatus.CONFLICTING
					.call().getMergeStatus());
			assertEquals(
					"[D/g
					indexState(0));
			recorder.assertEvent(new String[] { "f"
					ChangeRecorder.EMPTY);

			resetHard(master);
			assertIndex(mkmap("f"
			assertWorkDir(mkmap("f"
					"h()"
			recorder.assertEvent(new String[] { "f"
					new String[] { "G"

		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testResetHardFromIndexEntryWithoutFileToTreeWithoutFile()
			throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile("x"
			git.add().addFilepattern("x").call();
			RevCommit id1 = git.commit().setMessage("c1").call();

			writeTrashFile("f/g"
			git.rm().addFilepattern("x").call();
			recorder.assertEvent(ChangeRecorder.EMPTY
			git.add().addFilepattern("f/g").call();
			git.commit().setMessage("c2").call();
			deleteTrashFile("f/g");
			deleteTrashFile("f");

			git.reset().setMode(ResetType.HARD).setRef(id1.getName()).call();
			assertIndex(mkmap("x"
			recorder.assertEvent(new String[] { "x" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testInitialCheckout() throws Exception {
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db);
				TestRepository<Repository> db_t = new TestRepository<>(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			BranchBuilder master = db_t.branch("master");
			master.commit().add("f"
			assertFalse(new File(db.getWorkTree()
			git.checkout().setName("master").call();
			assertTrue(new File(db.getWorkTree()
			recorder.assertEvent(new String[] { "f" }
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	private DirCacheCheckout resetHard(RevCommit commit)
			throws NoWorkTreeException
			CorruptObjectException
		DirCacheCheckout dc;
		dc = new DirCacheCheckout(db
				commit.getTree());
		dc.setFailOnConflict(true);
		assertTrue(dc.checkout());
		return dc;
	}

	private void assertIndex(HashMap<String
			throws CorruptObjectException
		String expectedValue;
		String path;
		DirCache read = DirCache.read(db.getIndexFile()

		assertEquals("Index has not the right size."
				read.getEntryCount());
		for (int j = 0; j < read.getEntryCount(); j++) {
			path = read.getEntry(j).getPathString();
			expectedValue = i.get(path);
			assertNotNull("found unexpected entry for path " + path
					+ " in index"
			assertTrue("unexpected content for path " + path
					+ " in index. Expected: <" + expectedValue + ">"
					Arrays.equals(db.open(read.getEntry(j).getObjectId())
							.getCachedBytes()
		}
	}

	@Test
	public void testRules1thru3_NoIndexEntry() throws IOException {
		ObjectId head = buildTree(mk("foo"));
		ObjectId merge = db.newObjectInserter().insert(Constants.OBJ_TREE
				new byte[0]);

		prescanTwoTrees(head

		assertTrue(getRemoved().contains("foo"));

		prescanTwoTrees(merge

		assertTrue(getUpdated().containsKey("foo"));

		merge = buildTree(mkmap("foo"

		prescanTwoTrees(head

		assertConflict("foo");
	}

	void setupCase(HashMap<String
		theHead = buildTree(headEntries);
		theMerge = buildTree(mergeEntries);
		buildIndex(indexEntries);
	}

	private void buildIndex(HashMap<String
		dirCache = new DirCache(db.getIndexFile()
		if (indexEntries != null) {
			assertTrue(dirCache.lock());
			DirCacheEditor editor = dirCache.editor();
			for (java.util.Map.Entry<String
				writeTrashFile(e.getKey()
				ObjectInserter inserter = db.newObjectInserter();
				final ObjectId id = inserter.insert(Constants.OBJ_BLOB
						Constants.encode(e.getValue()));
				editor.add(new DirCacheEditor.DeletePath(e.getKey()));
				editor.add(new DirCacheEditor.PathEdit(e.getKey()) {
					@Override
					public void apply(DirCacheEntry ent) {
						ent.setFileMode(FileMode.REGULAR_FILE);
						ent.setObjectId(id);
						ent.setUpdateNeeded(false);
					}
				});
			}
			assertTrue(editor.commit());
		}

	}

	static final class AddEdit extends PathEdit {

		private final ObjectId data;

		private final long length;

		public AddEdit(String entryPath
			super(entryPath);
			this.data = data;
			this.length = length;
		}

		@Override
		public void apply(DirCacheEntry ent) {
			ent.setFileMode(FileMode.REGULAR_FILE);
			ent.setLength(length);
			ent.setObjectId(data);
		}

	}

	private ObjectId buildTree(HashMap<String
			throws IOException {
		DirCache lockDirCache = DirCache.newInCore();
		DirCacheEditor editor = lockDirCache.editor();
		if (headEntries != null) {
			for (java.util.Map.Entry<String
				AddEdit addEdit = new AddEdit(e.getKey()
						genSha1(e.getValue())
				editor.add(addEdit);
			}
		}
		editor.finish();
		return lockDirCache.writeTree(db.newObjectInserter());
	}

	ObjectId genSha1(String data) {
		try (ObjectInserter w = db.newObjectInserter()) {
			ObjectId id = w.insert(Constants.OBJ_BLOB
			w.flush();
			return id;
		} catch (IOException e) {
			fail(e.toString());
		}
		return null;
	}

	protected void go() throws IllegalStateException
		prescanTwoTrees(theHead
	}

	@Test
	public void testRules4thru13_IndexEntryNotInHead() throws IOException {
		HashMap<String

		idxMap = new HashMap<>();
		idxMap.put("foo"
		setupCase(null
		go();

		assertTrue(getUpdated().isEmpty());
		assertTrue(getRemoved().isEmpty());
		assertTrue(getConflicts().isEmpty());

		idxMap = new HashMap<>();
		idxMap.put("foo"
		setupCase(null
		go();

		assertAllEmpty();

		HashMap<String
		mergeMap = new HashMap<>();

		mergeMap.put("foo"
		setupCase(null
		go();

		assertTrue(getUpdated().isEmpty());
		assertTrue(getRemoved().isEmpty());
		assertTrue(getConflicts().contains("foo"));


		HashMap<String
		headMap.put("foo"
		setupCase(headMap
		go();

		assertTrue(getRemoved().contains("foo"));
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().isEmpty());

		setupCase(headMap
		assertTrue(new File(trash
		writeTrashFile("foo"
		db.readDirCache().getEntry(0).setUpdateNeeded(true);
		go();

		assertTrue(getRemoved().isEmpty());
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().contains("foo"));

		headMap.put("foo"
		setupCase(headMap
		go();

		assertTrue(getRemoved().isEmpty());
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().contains("foo"));

		setupCase(headMap
		go();

		assertAllEmpty();

		setupCase(headMap
		assertTrue(getConflicts().contains("foo"));

		setupCase(headMap
		assertAllEmpty();

		setupCase(idxMap
		assertTrue(getUpdated().containsKey("foo"));

		setupCase(idxMap
		assertTrue(new File(trash
		writeTrashFile("foo"
		db.readDirCache().getEntry(0).setUpdateNeeded(true);
		go();
		assertTrue(getConflicts().contains("foo"));
	}

	private void assertAllEmpty() {
		assertTrue(getRemoved().isEmpty());
		assertTrue(getUpdated().isEmpty());
		assertTrue(getConflicts().isEmpty());
	}

	@Test
	public void testDirectoryFileSimple() throws IOException {
		ObjectId treeDF = buildTree(mkmap("DF"
		ObjectId treeDFDF = buildTree(mkmap("DF/DF"
		buildIndex(mkmap("DF"

		prescanTwoTrees(treeDF

		assertTrue(getRemoved().contains("DF"));
		assertTrue(getUpdated().containsKey("DF/DF"));

		recursiveDelete(new File(trash
		buildIndex(mkmap("DF/DF"

		prescanTwoTrees(treeDFDF
		assertTrue(getRemoved().contains("DF/DF"));
		assertTrue(getUpdated().containsKey("DF"));
	}

	@Test
	public void testDirectoryFileConflicts_1() throws Exception {
		doit(mk("DF/DF")
		assertNoConflicts();
		assertUpdated("DF");
		assertRemoved("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_2() throws Exception {
		setupCase(mk("DF/DF")
		writeTrashFile("DF/DF"
		go();
		assertConflict("DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_3() throws Exception {
		doit(mk("DF/DF")
		assertNoConflicts();
	}

	@Test
	public void testDirectoryFileConflicts_4() throws Exception {
		doit(mk("DF/DF")
		assertConflict("DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_5() throws Exception {
		doit(mk("DF/DF")
		assertRemoved("DF/DF");
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testDirectoryFileConflicts_5b() throws Exception {
		doit(mk("DF/DF")
		assertRemoved("DF/DF");
		assertConflict("DF");
		assertEquals(0
	}

	@Test
	public void testDirectoryFileConflicts_6() throws Exception {
		setupCase(mk("DF/DF")
		writeTrashFile("DF"
		go();
		assertRemoved("DF/DF");
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testDirectoryFileConflicts_6b() throws Exception {
		setupCase(mk("DF/DF")
		writeTrashFile("DF"
		go();
		assertRemoved("DF/DF");
		assertConflict("DF");
		assertEquals(0
	}

	@Test
	public void testDirectoryFileConflicts_7() throws Exception {
		doit(mk("DF")
		assertUpdated("DF");
		assertRemoved("DF/DF");

		cleanUpDF();
		setupCase(mk("DF/DF")
		go();
		assertRemoved("DF/DF/DF/DF/DF");
		assertUpdated("DF/DF");

		cleanUpDF();
		setupCase(mk("DF/DF")
		writeTrashFile("DF/DF/DF/DF/DF"
		go();
		assertConflict("DF/DF/DF/DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_8() throws Exception {
		setupCase(mk("DF")
		recursiveDelete(new File(db.getWorkTree()
		writeTrashFile("DF"
		go();
		assertConflict("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_9() throws Exception {
		doit(mkmap("DF"
		assertRemoved("DF/DF");
		assertUpdated("DF");
	}

	@Test
	public void testDirectoryFileConflicts_10() throws Exception {
		cleanUpDF();
		doit(mk("DF")
		assertNoConflicts();
	}

	@Test
	public void testDirectoryFileConflicts_11() throws Exception {
		doit(mk("DF")
		assertConflict("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_12() throws Exception {
		cleanUpDF();
		doit(mk("DF")
		assertRemoved("DF");
		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_13() throws Exception {
		cleanUpDF();
		setupCase(mk("DF")
		writeTrashFile("DF"
		go();
		assertConflict("DF");
		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_14() throws Exception {
		cleanUpDF();
		doit(mk("DF")
		assertConflict("DF");
		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_15() throws Exception {
		doit(mkmap()


		assertUpdated("DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_15b() throws Exception {
		doit(mkmap()


		assertUpdated("DF/DF/DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_16() throws Exception {
		cleanUpDF();
		doit(mkmap()
		assertRemoved("DF/DF/DF");
		assertUpdated("DF");
	}

	@Test
	public void testDirectoryFileConflicts_17() throws Exception {
		cleanUpDF();
		setupCase(mkmap()
		writeTrashFile("DF/DF/DF"
		go();
		assertConflict("DF/DF/DF");

	}

	@Test
	public void testDirectoryFileConflicts_18() throws Exception {
		cleanUpDF();
		doit(mk("DF/DF")
		assertRemoved("DF/DF");
		assertUpdated("DF/DF/DF/DF");
	}

	@Test
	public void testDirectoryFileConflicts_19() throws Exception {
		cleanUpDF();
		doit(mk("DF/DF/DF/DF")
		assertRemoved("DF/DF/DF/DF");
		assertUpdated("DF/DF/DF");
	}

	protected void cleanUpDF() throws Exception {
		tearDown();
		setUp();
		recursiveDelete(new File(trash
	}

	protected void assertConflict(String s) {
		assertTrue(getConflicts().contains(s));
	}

	protected void assertUpdated(String s) {
		assertTrue(getUpdated().containsKey(s));
	}

	protected void assertRemoved(String s) {
		assertTrue(getRemoved().contains(s));
	}

	protected void assertNoConflicts() {
		assertTrue(getConflicts().isEmpty());
	}

	protected void doit(HashMap<String
			throws IOException {
				setupCase(h
				go();
			}

	@Test
	public void testUntrackedConflicts() throws IOException {
		setupCase(null
		writeTrashFile("foo"
		go();

		recursiveDelete(new File(trash
		setupCase(mk("other")
				mk("other"));
		writeTrashFile("foo"
		try {
			checkout();
			fail("didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertConflict("foo");
			assertEquals("foo"
			assertWorkDir(mkmap("foo"
			assertIndex(mk("other"));
		}

		recursiveDelete(new File(trash
		recursiveDelete(new File(trash
		setupCase(null
		writeTrashFile("foo"
		try {
			checkout();
			fail("didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertConflict("foo");
			assertWorkDir(mkmap("foo"
			assertIndex(mkmap("other"
		}


		recursiveDelete(new File(trash
		recursiveDelete(new File(trash
		setupCase(null
		writeTrashFile("foo/bar/baz"
		writeTrashFile("foo/blahblah"
		go();

		assertConflict("foo");
		assertConflict("foo/bar/baz");
		assertConflict("foo/blahblah");

		recursiveDelete(new File(trash

		setupCase(mkmap("foo/bar"
				mk("foo")
		assertTrue(new File(trash
		go();

		assertNoConflicts();
	}

	@Test
	public void testCloseNameConflictsX0() throws IOException {
		setupCase(mkmap("a/a"
		checkout();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		go();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		assertNoConflicts();
	}

	@Test
	public void testCloseNameConflicts1() throws IOException {
		setupCase(mkmap("a/a"
		checkout();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		go();
		assertIndex(mkmap("a/a"
		assertWorkDir(mkmap("a/a"
		assertNoConflicts();
	}

	@Test
	public void testCheckoutHierarchy() throws IOException {
		setupCase(
				mkmap("a"
						"e/g")
				mkmap("a"
						"e/g2")
				mkmap("a"
						"e/g3"));
		try {
			checkout();
			fail("did not throw CheckoutConflictException");
		} catch (CheckoutConflictException e) {
			assertWorkDir(mkmap("a"
					"e/f"
			assertConflict("e/g");
			assertEquals("e/g"
		}
	}

	@Test
	public void testCheckoutOutChanges() throws IOException {
		setupCase(mk("foo")
		checkout();
		assertIndex(mk("foo/bar"));
		assertWorkDir(mk("foo/bar"));

		assertFalse(new File(trash
		assertTrue(new File(trash
		recursiveDelete(new File(trash

		assertWorkDir(mkmap());

		setupCase(mk("foo/bar")
		checkout();

		assertIndex(mk("foo"));
		assertWorkDir(mk("foo"));

		assertFalse(new File(trash
		assertTrue(new File(trash

		setupCase(mk("foo")

		assertIndex(mkmap("foo"
		assertWorkDir(mkmap("foo"

		try {
			checkout();
			fail("did not throw exception");
		} catch (CheckoutConflictException e) {
			assertIndex(mkmap("foo"
			assertWorkDir(mkmap("foo"
		}
	}

	@Test
	public void testCheckoutChangeLinkToEmptyDir() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		String fname = "was_file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();

			String linkName = "link";
			File link = writeLink(linkName
			git.add().addFilepattern(linkName).call();
			git.commit().setMessage("Added file and link").call();

			assertWorkDir(mkmap(linkName

			FileUtils.delete(link);
			FileUtils.mkdir(link);
			assertTrue("Link must be a directory now"

			writeTrashFile(fname
			assertWorkDir(mkmap(fname
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname)
					.addPath(linkName).call();

			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname
					ChangeRecorder.EMPTY);

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutChangeLinkToEmptyDirs() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		String fname = "was_file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();

			String linkName = "link";
			File link = writeLink(linkName
			git.add().addFilepattern(linkName).call();
			git.commit().setMessage("Added file and link").call();

			assertWorkDir(mkmap(linkName

			FileUtils.delete(link);
			FileUtils.mkdirs(new File(link
			assertTrue("Link must be a directory now"

			assertFalse("Must not delete non empty directory"

			writeTrashFile(fname
			assertWorkDir(mkmap(fname
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname)
					.addPath(linkName).call();

			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname
					ChangeRecorder.EMPTY);

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutChangeLinkToNonEmptyDirs() throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		String fname = "file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();

			String linkName = "link";
			File link = writeLink(linkName
			git.add().addFilepattern(linkName).call();
			git.commit().setMessage("Added file and link").call();

			assertWorkDir(mkmap(linkName

			FileUtils.delete(link);

			writeTrashFile(linkName + "/dir1"

			writeTrashFile(linkName + "/dir2"

			assertTrue("File must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname
					linkName + "/dir2/file2"
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(linkName)
					.call();

			assertWorkDir(mkmap(linkName
			recorder.assertEvent(new String[] { linkName }
					ChangeRecorder.EMPTY);

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutChangeLinkToNonEmptyDirsAndNewIndexEntry()
			throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		String fname = "file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();

			String linkName = "link";
			File link = writeLink(linkName
			git.add().addFilepattern(linkName).call();
			git.commit().setMessage("Added file and link").call();

			assertWorkDir(mkmap(linkName

			FileUtils.delete(link);

			writeTrashFile(linkName + "/dir1"
			git.add().addFilepattern(linkName + "/dir1/file1").call();

			writeTrashFile(linkName + "/dir2"

			assertTrue("File must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname
					linkName + "/dir2/file2"
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(linkName)
					.call();

			assertWorkDir(mkmap(linkName
			recorder.assertEvent(new String[] { linkName }
					ChangeRecorder.EMPTY);

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutChangeFileToEmptyDir() throws Exception {
		String fname = "was_file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("Added file").call();

			FileUtils.delete(file);
			FileUtils.mkdir(file);
			assertTrue("File must be a directory now"
			assertWorkDir(mkmap(fname
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();
			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname }

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutChangeFileToEmptyDirs() throws Exception {
		String fname = "was_file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("Added file").call();

			FileUtils.delete(file);
			FileUtils.mkdirs(new File(file
			assertTrue("File must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname + "/dummyDir"
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();
			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname }

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutChangeFileToNonEmptyDirs() throws Exception {
		String fname = "was_file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("Added file").call();

			assertWorkDir(mkmap(fname

			FileUtils.delete(file);

			writeTrashFile(fname + "/dir1"

			writeTrashFile(fname + "/dir2"

			assertTrue("File must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname + "/dir1/file1"
					fname + "/dir2/file2"
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();

			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname }

			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutChangeFileToNonEmptyDirsAndNewIndexEntry()
			throws Exception {
		String fname = "was_file";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("Added file").call();

			assertWorkDir(mkmap(fname

			FileUtils.delete(file);

			writeTrashFile(fname + "/dir"
			git.add().addFilepattern(fname + "/dir/file1").call();

			writeTrashFile(fname + "/dir"

			assertTrue("File must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname + "/dir/file1"
					"d"));
			recorder.assertNoEvent();

			git.checkout().setStartPoint(Constants.HEAD).addPath(fname).call();
			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname }
			Status st = git.status().call();
			assertTrue(st.isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testCheckoutOutChangesAutoCRLFfalse() throws IOException {
		setupCase(mk("foo")
		checkout();
		assertIndex(mkmap("foo/bar"
		assertWorkDir(mkmap("foo/bar"
	}

	@Test
	public void testCheckoutOutChangesAutoCRLFInput() throws IOException {
		setupCase(mk("foo")
		db.getConfig().setString("core"
		checkout();
		assertIndex(mkmap("foo/bar"
		assertWorkDir(mkmap("foo/bar"
	}

	@Test
	public void testCheckoutOutChangesAutoCRLFtrue() throws IOException {
		setupCase(mk("foo")
		db.getConfig().setString("core"
		checkout();
		assertIndex(mkmap("foo/bar"
		assertWorkDir(mkmap("foo/bar"
	}

	@Test
	public void testCheckoutOutChangesAutoCRLFtrueBinary() throws IOException {
		setupCase(mk("foo")
		db.getConfig().setString("core"
		checkout();
		assertIndex(mkmap("foo/bar"
		assertWorkDir(mkmap("foo/bar"
	}

	@Test
	public void testCheckoutUncachedChanges() throws IOException {
		setupCase(mk("foo")
		writeTrashFile("foo"
		checkout();
		assertIndex(mk("foo"));
		assertWorkDir(mkmap("foo"
		assertTrue(new File(trash
	}

	@Test
	public void testDontOverwriteDirtyFile() throws IOException {
		setupCase(mk("foo")
		writeTrashFile("foo"
		try {
			checkout();
			fail("Didn't got the expected conflict");
		} catch (CheckoutConflictException e) {
			assertIndex(mk("foo"));
			assertWorkDir(mkmap("foo"
			assertEquals(Arrays.asList("foo")
			assertTrue(new File(trash
		}
	}

	@Test
	public void testDontOverwriteEmptyFolder() throws IOException {
		setupCase(mk("foo")
		FileUtils.mkdir(new File(db.getWorkTree()
		checkout();
		assertWorkDir(mkmap("foo"
	}

	@Test
	public void testOverwriteUntrackedIgnoredFile() throws IOException
			GitAPIException {
		String fname="file.txt";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("create file").call();

			git.branchCreate().setName("side").call();

			writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("modify file").call();
			recorder.assertNoEvent();

			git.checkout().setName("side").call();
			recorder.assertEvent(new String[] { fname }
			git.rm().addFilepattern(fname).call();
			recorder.assertEvent(ChangeRecorder.EMPTY
			writeTrashFile(".gitignore"
			git.add().addFilepattern(".gitignore").call();
			git.commit().setMessage("delete and ignore file").call();

			writeTrashFile(fname
			recorder.assertNoEvent();
			git.checkout().setName("master").call();
			assertWorkDir(mkmap(fname
			recorder.assertEvent(new String[] { fname }
					new String[] { ".gitignore" });
			assertTrue(git.status().call().isClean());
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testOverwriteUntrackedFileModeChange()
			throws IOException
		String fname = "file.txt";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile(fname
			git.add().addFilepattern(fname).call();
			git.commit().setMessage("create file").call();
			assertWorkDir(mkmap(fname

			git.branchCreate().setName("side").call();

			git.checkout().setName("side").call();
			recorder.assertNoEvent();

			FileUtils.delete(file);

			writeTrashFile(fname + "/dir1"
			git.add().addFilepattern(fname + "/dir1/file1").call();

			writeTrashFile(fname + "/dir2"

			assertTrue("File must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname + "/dir1/file1"
					fname + "/dir2/file2"

			try {
				git.checkout().setName("master").call();
				fail("did not throw exception");
			} catch (Exception e) {
				assertWorkDir(mkmap(fname + "/dir1/file1"
						fname + "/dir2/file2"
			}
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testOverwriteUntrackedLinkModeChange()
			throws Exception {
		Assume.assumeTrue(FS.DETECTED.supportsSymlinks());
		String fname = "file.txt";
		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			writeTrashFile(fname
			git.add().addFilepattern(fname).call();

			String linkName = "link";
			File link = writeLink(linkName
			git.add().addFilepattern(linkName).call();
			git.commit().setMessage("Added file and link").call();

			assertWorkDir(mkmap(linkName

			git.branchCreate().setName("side").call();

			git.checkout().setName("side").call();
			recorder.assertNoEvent();

			FileUtils.delete(link);

			writeTrashFile(linkName + "/dir1"
			git.add().addFilepattern(linkName + "/dir1/file1").call();

			writeTrashFile(linkName + "/dir2"

			assertTrue("Link must be a directory now"
			assertFalse("Must not delete non empty directory"

			assertWorkDir(mkmap(fname
					linkName + "/dir2/file2"

			try {
				git.checkout().setName("master").call();
				fail("did not throw exception");
			} catch (Exception e) {
				assertWorkDir(mkmap(fname
						linkName + "/dir2/file2"
			}
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testFileModeChangeWithNoContentChangeUpdate() throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit1").call();
			assertFalse(db.getFS().canExecute(file));

			git.branchCreate().setName("b1").call();

			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit2").call();
			recorder.assertNoEvent();

			Status status = git.status().call();
			assertTrue(status.getModified().isEmpty());
			assertTrue(status.getChanged().isEmpty());
			assertTrue(db.getFS().canExecute(file));

			git.checkout().setName("b1").call();

			status = git.status().call();
			assertTrue(status.getModified().isEmpty());
			assertTrue(status.getChanged().isEmpty());
			assertFalse(db.getFS().canExecute(file));
			recorder.assertEvent(new String[] { "file.txt" }
					ChangeRecorder.EMPTY);
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testFileModeChangeAndContentChangeConflict() throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit1").call();
			assertFalse(db.getFS().canExecute(file));

			git.branchCreate().setName("b1").call();

			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit2").call();

			Status status = git.status().call();
			assertTrue(status.getModified().isEmpty());
			assertTrue(status.getChanged().isEmpty());
			assertTrue(db.getFS().canExecute(file));

			writeTrashFile("file.txt"

			CheckoutCommand checkout = git.checkout().setName("b1");
			try {
				checkout.call();
				fail("Checkout exception not thrown");
			} catch (org.eclipse.jgit.api.errors.CheckoutConflictException e) {
				CheckoutResult result = checkout.getResult();
				assertNotNull(result);
				assertNotNull(result.getConflictList());
				assertEquals(1
				assertTrue(result.getConflictList().contains("file.txt"));
			}
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testDirtyFileModeEqualHeadMerge()
			throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit1").call();
			assertFalse(db.getFS().canExecute(file));

			git.branchCreate().setName("b1").call();

			writeTrashFile("file2.txt"
			git.add().addFilepattern("file2.txt").call();
			git.commit().setMessage("commit2").call();

			writeTrashFile("file.txt"
			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();

			writeTrashFile("file.txt"

			assertEquals(
					"[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertNoEvent();

			git.checkout().setName("b1").call();
			assertEquals("[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertEvent(ChangeRecorder.EMPTY
					new String[] { "file2.txt" });
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testDirtyFileModeEqualIndexMerge()
			throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file = writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit1").call();
			assertFalse(db.getFS().canExecute(file));

			git.branchCreate().setName("b1").call();

			file = writeTrashFile("file.txt"
			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("commit2").call();

			writeTrashFile("file.txt"
			db.getFS().setExecute(file
			git.add().addFilepattern("file.txt").call();

			writeTrashFile("file.txt"
			db.getFS().setExecute(file

			assertEquals("[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertNoEvent();

			git.checkout().setName("b1").call();
			assertEquals("[file.txt
					indexState(CONTENT));
			assertWorkDir(mkmap("file.txt"
			recorder.assertNoEvent();
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test
	public void testFileModeChangeAndContentChangeNoConflict() throws Exception {
		if (!FS.DETECTED.supportsExecute())
			return;

		ChangeRecorder recorder = new ChangeRecorder();
		ListenerHandle handle = null;
		try (Git git = new Git(db)) {
			handle = db.getListenerList()
					.addWorkingTreeModifiedListener(recorder);
			File file1 = writeTrashFile("file1.txt"
			git.add().addFilepattern("file1.txt").call();
			git.commit().setMessage("commit1").call();
			assertFalse(db.getFS().canExecute(file1));

			File file2 = writeTrashFile("file2.txt"
			git.add().addFilepattern("file2.txt").call();
			git.commit().setMessage("commit2").call();
			assertFalse(db.getFS().canExecute(file2));
			recorder.assertNoEvent();

			assertNotNull(git.checkout().setCreateBranch(true).setName("b1")
					.setStartPoint(Constants.HEAD + "~1").call());
			recorder.assertEvent(ChangeRecorder.EMPTY
					new String[] { "file2.txt" });

			file1 = writeTrashFile("file1.txt"
			db.getFS().setExecute(file1
			git.add().addFilepattern("file1.txt").call();

			assertNotNull(git.checkout().setName(Constants.MASTER).call());
			recorder.assertEvent(new String[] { "file2.txt" }
					ChangeRecorder.EMPTY);
		} finally {
			if (handle != null) {
				handle.remove();
			}
		}
	}

	@Test(expected = CheckoutConflictException.class)
	public void testFolderFileConflict() throws Exception {
		RevCommit headCommit = commitFile("f/a"
		RevCommit checkoutCommit = commitFile("f/a"
		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("f"
		new DirCacheCheckout(db
				checkoutCommit.getTree()).checkout();
	}

	@Test
	public void testMultipleContentConflicts() throws Exception {
		commitFile("a"
		RevCommit headCommit = commitFile("b"
		commitFile("a"
		RevCommit checkoutCommit = commitFile("b"
		writeTrashFile("a"
		writeTrashFile("b"

		try {
			new DirCacheCheckout(db
					checkoutCommit.getTree()).checkout();
			fail();
		} catch (CheckoutConflictException expected) {
			assertEquals(2
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("a"));
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("b"));
			assertEquals("changed content"
			assertEquals("changed content"
		}
	}

	@Test
	public void testFolderFileAndContentConflicts() throws Exception {
		RevCommit headCommit = commitFile("f/a"
		commitFile("b"
		RevCommit checkoutCommit = commitFile("f/a"
		FileUtils.delete(new File(db.getWorkTree()
		writeTrashFile("f"
		writeTrashFile("b"

		try {
			new DirCacheCheckout(db
					checkoutCommit.getTree()).checkout();
			fail();
		} catch (CheckoutConflictException expected) {
			assertEquals(2
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("b"));
			assertTrue(Arrays.asList(expected.getConflictingFiles())
					.contains("f"));
			assertEquals("file instead of a folder"
			assertEquals("changed content"
		}
	}

	@Test
	public void testLongFilename() throws Exception {
		char[] bytes = new char[253];
		Arrays.fill(bytes
		String longFileName = new String(bytes);
		doit(mkmap(longFileName
				mkmap(longFileName
		writeTrashFile(longFileName
		checkout();
		assertNoConflicts();
		assertUpdated(longFileName);
	}

	@Test
	public void testIgnoredDirectory() throws Exception {
		writeTrashFile(".gitignore"
		writeTrashFile("src/ignored/sub/foo.txt"
		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("adding .gitignore")
					.call();
			writeTrashFile("foo.txt"
			writeTrashFile("zzz.txt"
			git.add().addFilepattern("foo.txt").call();
			git.commit().setMessage("add file").call();
			assertEquals("Should not have entered ignored directory"
					resetHardAndCount(commit));
		}
	}

	@Test
	public void testIgnoredDirectoryWithTrackedContent() throws Exception {
		writeTrashFile("src/ignored/sub/foo.txt"
		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			git.commit().setMessage("adding foo.txt").call();
			writeTrashFile(".gitignore"
			writeTrashFile("src/ignored/sub/foo.txt"
			writeTrashFile("src/ignored/other/bar.txt"
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("adding .gitignore")
					.call();
			writeTrashFile("foo.txt"
			writeTrashFile("zzz.txt"
			git.add().addFilepattern("foo.txt").call();
			git.commit().setMessage("add file").call();
			File file = writeTrashFile("src/ignored/sub/foo.txt"
			assertEquals("Should have entered ignored directory"
					resetHardAndCount(commit));
			checkFile(file
		}
	}

	@Test
	public void testResetWithChangeInGitignore() throws Exception {
		writeTrashFile(".gitignore"
		writeTrashFile("src/ignored/sub/foo.txt"
		try (Git git = new Git(db)) {
			git.add().addFilepattern(".").call();
			RevCommit initial = git.commit().setMessage("initial").call();
			writeTrashFile("src/newignored/foo.txt"
			writeTrashFile("src/.gitignore"
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setMessage("newignored").call();
			assertEquals("Should not have entered src/newignored directory"
					resetHardAndCount(initial));
			assertEquals("Should have entered src/newignored directory"
					resetHardAndCount(commit));
			deleteTrashFile("src/.gitignore");
			git.rm().addFilepattern("src/.gitignore").call();
			RevCommit top = git.commit().setMessage("Unignore newignore")
					.call();
			assertEquals("Should have entered src/newignored directory"
					resetHardAndCount(initial));
			assertEquals("Should have entered src/newignored directory"
					resetHardAndCount(commit));
			assertEquals("Should not have entered src/newignored directory"
					resetHardAndCount(top));

		}
	}

	private static class TestFileTreeIterator extends FileTreeIterator {

		private final int[] count;

		public TestFileTreeIterator(Repository repo
			super(repo);
			this.count = count;
		}

		protected TestFileTreeIterator(final WorkingTreeIterator p
				final File root
				int[] count) {
			super(p
			this.count = count;
		}

		@Override
		protected AbstractTreeIterator enterSubtree() {
			count[0] += 1;
			return new TestFileTreeIterator(this
					((FileEntry) current()).getFile()
					count);
		}
	}

	private int resetHardAndCount(RevCommit commit) throws Exception {
		int[] callCount = { 0 };
		DirCache cache = db.lockDirCache();
		FileTreeIterator workingTreeIterator = new TestFileTreeIterator(db
				callCount);
		try {
			DirCacheCheckout checkout = new DirCacheCheckout(db
					commit.getTree().getId()
			checkout.setFailOnConflict(false);
			checkout.checkout();
		} finally {
			cache.unlock();
		}
		return callCount[0];
	}

	public void assertWorkDir(Map<String
			throws CorruptObjectException
			IOException {
		try (TreeWalk walk = new TreeWalk(db)) {
			walk.setRecursive(false);
			walk.addTree(new FileTreeIterator(db));
			String expectedValue;
			String path;
			int nrFiles = 0;
			FileTreeIterator ft;
			while (walk.next()) {
				ft = walk.getTree(0
				path = ft.getEntryPathString();
				expectedValue = i.get(path);
				File file = new File(db.getWorkTree()
				assertTrue(file.exists());
				if (file.isFile()) {
					assertNotNull("found unexpected file for path " + path
							+ " in workdir"
					try (FileInputStream is = new FileInputStream(file)) {
						byte[] buffer = new byte[(int) file.length()];
						int offset = 0;
						int numRead = 0;
						while (offset < buffer.length
								&& (numRead = is.read(buffer
										buffer.length - offset)) >= 0) {
							offset += numRead;
						}
						assertArrayEquals(
								"unexpected content for path " + path
										+ " in workDir. "
								buffer
					}
					nrFiles++;
				} else if (file.isDirectory()) {
					String[] files = file.list();
					if (files != null && files.length == 0) {
						assertEquals("found unexpected empty folder for path "
								+ path + " in workDir. "
						nrFiles++;
					}
				}
				if (walk.isSubtree()) {
					walk.enterSubtree();
				}
			}
			assertEquals("WorkDir has not the right size."
		}
	}
}
