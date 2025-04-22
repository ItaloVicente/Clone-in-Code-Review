
package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeSet;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.dircache.DirCacheBuilder;
import org.eclipse.jgit.dircache.DirCacheEditor;
import org.eclipse.jgit.dircache.DirCacheEditor.PathEdit;
import org.eclipse.jgit.dircache.DirCacheEntry;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.CoreConfig.AutoCRLF;
import org.eclipse.jgit.lib.IndexDiff.StageState;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.IO;
import org.junit.Test;

public class IndexDiffTest extends RepositoryTestCase {

	static PathEdit add(final Repository db
			final String path) throws FileNotFoundException
		ObjectInserter inserter = db.newObjectInserter();
		final File f = new File(workdir
		final ObjectId id = inserter.insert(Constants.OBJ_BLOB
				IO.readFully(f));
		return new PathEdit(path) {
			@Override
			public void apply(DirCacheEntry ent) {
				ent.setFileMode(FileMode.REGULAR_FILE);
				ent.setLength(f.length());
				ent.setObjectId(id);
			}
		};
	}

	@Test
	public void testAdded() throws IOException {
		writeTrashFile("file1"
		writeTrashFile("dir/subfile"
		ObjectId tree = insertTree(new TreeFormatter());

		DirCache index = db.lockDirCache();
		DirCacheEditor editor = index.editor();
		editor.add(add(db
		editor.add(add(db
		editor.commit();
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertEquals(2
		assertTrue(diff.getAdded().contains("file1"));
		assertTrue(diff.getAdded().contains("dir/subfile"));
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testMissing() throws Exception {
		File file2 = writeTrashFile("file2"
		File file3 = writeTrashFile("dir/file3"
		Git git = Git.wrap(db);
		git.add().addFilepattern("file2").addFilepattern("dir/file3").call();
		git.commit().setMessage("commit").call();
		assertTrue(file2.delete());
		assertTrue(file3.delete());
		IndexDiff diff = new IndexDiff(db
				new FileTreeIterator(db));
		diff.diff();
		assertEquals(2
		assertTrue(diff.getMissing().contains("file2"));
		assertTrue(diff.getMissing().contains("dir/file3"));
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testRemoved() throws IOException {
		writeTrashFile("file2"
		writeTrashFile("dir/file3"

		TreeFormatter dir = new TreeFormatter();
		dir.append("file3"

		TreeFormatter tree = new TreeFormatter();
		tree.append("file2"
		tree.append("dir"
		ObjectId treeId = insertTree(tree);

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertEquals(2
		assertTrue(diff.getRemoved().contains("file2"));
		assertTrue(diff.getRemoved().contains("dir/file3"));
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testModified() throws IOException

		writeTrashFile("file2"
		writeTrashFile("dir/file3"

		try (Git git = new Git(db)) {
			git.add().addFilepattern("file2").addFilepattern("dir/file3").call();
		}

		writeTrashFile("dir/file3"

		TreeFormatter dir = new TreeFormatter();
		dir.append("file3"

		TreeFormatter tree = new TreeFormatter();
		tree.append("dir"
		tree.append("file2"
		ObjectId treeId = insertTree(tree);

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertEquals(2
		assertTrue(diff.getChanged().contains("file2"));
		assertTrue(diff.getChanged().contains("dir/file3"));
		assertEquals(1
		assertTrue(diff.getModified().contains("dir/file3"));
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testConflicting() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING
		}

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();

		assertEquals("[b]"
				new TreeSet<>(diff.getChanged()).toString());
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[a]"
		assertEquals(StageState.BOTH_MODIFIED
				diff.getConflictingStageStates().get("a"));
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testConflictingDeletedAndModified() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			git.rm().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING
		}

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();

		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[a]"
		assertEquals(StageState.DELETED_BY_THEM
				diff.getConflictingStageStates().get("a"));
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testConflictingFromMultipleCreations() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING
		}

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();

		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[]"
		assertEquals("[b]"
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testUnchangedSimple() throws IOException
		writeTrashFile("a.b"
		writeTrashFile("a.c"
		writeTrashFile("a=c"
		writeTrashFile("a=d"
		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.b").call();
			git.add().addFilepattern("a.c").call();
			git.add().addFilepattern("a=c").call();
			git.add().addFilepattern("a=d").call();
		}

		TreeFormatter tree = new TreeFormatter();
		tree.append("a.b"
		tree.append("a.c"
		tree.append("a=c"
		tree.append("a=d"
		ObjectId treeId = insertTree(tree);

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testUnchangedComplex() throws IOException
		writeTrashFile("a.b"
		writeTrashFile("a.c"
		writeTrashFile("a/b.b/b"
		writeTrashFile("a/b"
		writeTrashFile("a/c"
		writeTrashFile("a=c"
		writeTrashFile("a=d"
		try (Git git = new Git(db)) {
			git.add().addFilepattern("a.b").addFilepattern("a.c")
					.addFilepattern("a/b.b/b").addFilepattern("a/b")
					.addFilepattern("a/c").addFilepattern("a=c")
					.addFilepattern("a=d").call();
		}


		TreeFormatter bb = new TreeFormatter();
		bb.append("b"

		TreeFormatter a = new TreeFormatter();
		a.append("b"
				.fromString("db89c972fc57862eae378f45b74aca228037d415"));
		a.append("b.b"
		a.append("c"

		TreeFormatter tree = new TreeFormatter();
		tree.append("a.b"
		tree.append("a.c"
		tree.append("a"
		tree.append("a=c"
		tree.append("a=d"
		ObjectId treeId = insertTree(tree);

		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(0
		assertEquals(Collections.EMPTY_SET
	}

	private ObjectId insertTree(TreeFormatter tree) throws IOException {
		try (ObjectInserter oi = db.newObjectInserter()) {
			ObjectId id = oi.insert(tree);
			oi.flush();
			return id;
		}
	}

	@Test
	public void testRemovedUntracked() throws Exception{
		String path = "file";
		try (Git git = new Git(db)) {
			writeTrashFile(path
			git.add().addFilepattern(path).call();
			git.commit().setMessage("commit").call();
		}
		removeFromIndex(path);
		FileTreeIterator iterator = new FileTreeIterator(db);
		IndexDiff diff = new IndexDiff(db
		diff.diff();
		assertTrue(diff.getRemoved().contains(path));
		assertTrue(diff.getUntracked().contains(path));
		assertEquals(Collections.EMPTY_SET
	}

	@Test
	public void testUntrackedFolders() throws Exception {
		try (Git git = new Git(db)) {
			IndexDiff diff = new IndexDiff(db
					new FileTreeIterator(db));
			diff.diff();
			assertEquals(Collections.EMPTY_SET

			writeTrashFile("readme"
			writeTrashFile("src/com/A.java"
			writeTrashFile("src/com/B.java"
			writeTrashFile("src/org/A.java"
			writeTrashFile("src/org/B.java"
			writeTrashFile("target/com/A.java"
			writeTrashFile("target/com/B.java"
			writeTrashFile("target/org/A.java"
			writeTrashFile("target/org/B.java"

			git.add().addFilepattern("src").addFilepattern("readme").call();
			git.commit().setMessage("initial").call();

			diff = new IndexDiff(db
					new FileTreeIterator(db));
			diff.diff();
			assertEquals(new HashSet<>(Arrays.asList("target"))
					diff.getUntrackedFolders());

			writeTrashFile("src/tst/A.java"
			writeTrashFile("src/tst/B.java"

			diff = new IndexDiff(db
			diff.diff();
			assertEquals(new HashSet<>(Arrays.asList("target"
					diff.getUntrackedFolders());

			git.rm().addFilepattern("src/com/B.java").addFilepattern("src/org")
					.call();
			git.commit().setMessage("second").call();
			writeTrashFile("src/org/C.java"

			diff = new IndexDiff(db
			diff.diff();
			assertEquals(
					new HashSet<>(Arrays.asList("src/org"
							"target"))
					diff.getUntrackedFolders());
		}
	}

	@Test
	public void testUntrackedNotIgnoredFolders() throws Exception {
		try (Git git = new Git(db)) {
			IndexDiff diff = new IndexDiff(db
					new FileTreeIterator(db));
			diff.diff();
			assertEquals(Collections.EMPTY_SET

			writeTrashFile("readme"
			writeTrashFile("sr/com/X.java"
			writeTrashFile("src/com/A.java"
			writeTrashFile("src/org/B.java"
			writeTrashFile("srcs/org/Y.java"
			writeTrashFile("target/com/A.java"
			writeTrashFile("target/org/B.java"
			writeTrashFile(".gitignore"

			git.add().addFilepattern("readme").addFilepattern(".gitignore")
					.addFilepattern("srcs/").call();
			git.commit().setMessage("initial").call();

			diff = new IndexDiff(db
			diff.diff();
			assertEquals(new HashSet<>(Arrays.asList("src"))
					diff.getUntrackedFolders());
			assertEquals(new HashSet<>(Arrays.asList("sr"
					diff.getIgnoredNotInIndex());

			git.add().addFilepattern("src").call();
			writeTrashFile("sr/com/X1.java"
			writeTrashFile("src/tst/A.java"
			writeTrashFile("src/tst/B.java"
			writeTrashFile("srcs/com/Y1.java"
			deleteTrashFile(".gitignore");

			diff = new IndexDiff(db
			diff.diff();
			assertEquals(
					new HashSet<>(Arrays.asList("srcs/com"
							"target"))
					diff.getUntrackedFolders());
		}
	}

	@Test
	public void testAssumeUnchanged() throws Exception {
		try (Git git = new Git(db)) {
			String path = "file";
			writeTrashFile(path
			git.add().addFilepattern(path).call();
			String path2 = "file2";
			writeTrashFile(path2
			String path3 = "file3";
			writeTrashFile(path3
			git.add().addFilepattern(path2).addFilepattern(path3).call();
			git.commit().setMessage("commit").call();
			assumeUnchanged(path2);
			assumeUnchanged(path3);
			writeTrashFile(path
			deleteTrashFile(path3);

			FileTreeIterator iterator = new FileTreeIterator(db);
			IndexDiff diff = new IndexDiff(db
			diff.diff();
			assertEquals(2
			assertEquals(1
			assertEquals(0
			assertTrue(diff.getAssumeUnchanged().contains("file2"));
			assertTrue(diff.getAssumeUnchanged().contains("file3"));
			assertTrue(diff.getModified().contains("file"));

			git.add().addFilepattern(".").call();

			iterator = new FileTreeIterator(db);
			diff = new IndexDiff(db
			diff.diff();
			assertEquals(2
			assertEquals(0
			assertEquals(1
			assertTrue(diff.getAssumeUnchanged().contains("file2"));
			assertTrue(diff.getAssumeUnchanged().contains("file3"));
			assertTrue(diff.getChanged().contains("file"));
			assertEquals(Collections.EMPTY_SET
		}
	}

	@Test
	public void testStageState() throws IOException {
		final int base = DirCacheEntry.STAGE_1;
		final int ours = DirCacheEntry.STAGE_2;
		final int theirs = DirCacheEntry.STAGE_3;
		verifyStageState(StageState.BOTH_DELETED
		verifyStageState(StageState.DELETED_BY_THEM
		verifyStageState(StageState.DELETED_BY_US
		verifyStageState(StageState.BOTH_MODIFIED
		verifyStageState(StageState.ADDED_BY_US
		verifyStageState(StageState.BOTH_ADDED
		verifyStageState(StageState.ADDED_BY_THEM

		assertTrue(StageState.BOTH_DELETED.hasBase());
		assertFalse(StageState.BOTH_DELETED.hasOurs());
		assertFalse(StageState.BOTH_DELETED.hasTheirs());
		assertFalse(StageState.BOTH_ADDED.hasBase());
		assertTrue(StageState.BOTH_ADDED.hasOurs());
		assertTrue(StageState.BOTH_ADDED.hasTheirs());
	}

	@Test
	public void testStageState_mergeAndReset_bug() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial commit")
					.call();

			final String branchName = Constants.R_HEADS + "branch";
			createBranch(initialCommit
			checkoutBranch(branchName);
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit branchCommit = git.commit().setMessage("branch commit")
					.call();

			checkoutBranch(Constants.R_HEADS + Constants.MASTER);
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("master commit").call();

			MergeResult result = git.merge().include(branchCommit).call();
			assertEquals(MergeStatus.CONFLICTING

			FileTreeIterator iterator = new FileTreeIterator(db);
			IndexDiff diff = new IndexDiff(db
			diff.diff();

			assertTrue(diff.getChanged().isEmpty());
			assertTrue(diff.getAdded().isEmpty());
			assertTrue(diff.getRemoved().isEmpty());
			assertTrue(diff.getMissing().isEmpty());
			assertTrue(diff.getModified().isEmpty());
			assertEquals(1
			assertTrue(diff.getConflicting().contains("b"));
			assertEquals(StageState.BOTH_ADDED
					.get("b"));
			assertTrue(diff.getUntrackedFolders().isEmpty());

			writeTrashFile("b"

			iterator = new FileTreeIterator(db);
			diff = new IndexDiff(db
			diff.diff();

			assertTrue(diff.getChanged().isEmpty());
			assertTrue(diff.getAdded().isEmpty());
			assertTrue(diff.getRemoved().isEmpty());
			assertTrue(diff.getMissing().isEmpty());
			assertTrue(diff.getModified().isEmpty());
			assertEquals(1
			assertTrue(diff.getConflicting().contains("b"));
			assertEquals(StageState.BOTH_ADDED
					.get("b"));
			assertTrue(diff.getUntrackedFolders().isEmpty());
		}
	}

	@Test
	public void testStageState_simulated_bug() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial commit")
					.call();

			final String branchName = Constants.R_HEADS + "branch";
			createBranch(initialCommit
			checkoutBranch(branchName);
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("branch commit")
					.call();

			checkoutBranch(Constants.R_HEADS + Constants.MASTER);
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("master commit").call();

			DirCacheBuilder builder = db.lockDirCache().builder();
			DirCacheEntry entry = createEntry("a"
					"content");
			builder.add(entry);
			entry = createEntry("b"
					"second file content - master");
			builder.add(entry);
			entry = createEntry("b"
					"second file content - branch");
			builder.add(entry);
			builder.commit();

			FileTreeIterator iterator = new FileTreeIterator(db);
			IndexDiff diff = new IndexDiff(db
			diff.diff();

			assertTrue(diff.getChanged().isEmpty());
			assertTrue(diff.getAdded().isEmpty());
			assertTrue(diff.getRemoved().isEmpty());
			assertTrue(diff.getMissing().isEmpty());
			assertTrue(diff.getModified().isEmpty());
			assertEquals(1
			assertTrue(diff.getConflicting().contains("b"));
			assertEquals(StageState.BOTH_ADDED
					.get("b"));
			assertTrue(diff.getUntrackedFolders().isEmpty());
		}
	}

	@Test
	public void testAutoCRLFInput() throws Exception {
		try (Git git = new Git(db)) {
			FileBasedConfig config = db.getConfig();

			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();

			writeTrashFile("crlf.txt"
			git.add().addFilepattern("crlf.txt").call();
			git.commit().setMessage("Add crlf.txt").call();

			config.setEnum(ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_AUTOCRLF
			config.save();

			FileTreeIterator iterator = new FileTreeIterator(db);
			IndexDiff diff = new IndexDiff(db
			diff.diff();

			assertTrue(
					"Expected no modified files
							+ diff.getModified()
		}
	}

	private void verifyStageState(StageState expected
			throws IOException {
		DirCacheBuilder builder = db.lockDirCache().builder();
		for (int stage : stages) {
			DirCacheEntry entry = createEntry("a"
					stage
			builder.add(entry);
		}
		builder.commit();

		IndexDiff diff = new IndexDiff(db
				new FileTreeIterator(db));
		diff.diff();

		assertEquals(
				"Conflict for entries in stages " + Arrays.toString(stages)
				expected
	}

	private void removeFromIndex(String path) throws IOException {
		final DirCache dirc = db.lockDirCache();
		final DirCacheEditor edit = dirc.editor();
		edit.add(new DirCacheEditor.DeletePath(path));
		if (!edit.commit())
			throw new IOException("could not commit");
	}

	private void assumeUnchanged(String path) throws IOException {
		final DirCache dirc = db.lockDirCache();
		final DirCacheEntry ent = dirc.getEntry(path);
		if (ent != null)
			ent.setAssumeValid(true);
		dirc.write();
		if (!dirc.commit())
			throw new IOException("could not commit");
	}

}
