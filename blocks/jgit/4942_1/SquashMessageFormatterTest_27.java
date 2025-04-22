package org.eclipse.jgit.merge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.treewalk.FileTreeIterator;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Assert;
import org.junit.Test;

public class ResolveMergerTest extends RepositoryTestCase {

	@Test
	public void failingPathsShouldNotResultInOKReturnValue() throws Exception {
		File folder1 = new File(db.getWorkTree()
		FileUtils.mkdir(folder1);
		File file = new File(folder1
		write(file
		file = new File(folder1
		write(file

		Git git = new Git(db);
		git.add().addFilepattern(folder1.getName()).call();
		RevCommit base = git.commit().setMessage("adding folder").call();

		recursiveDelete(folder1);
		git.rm().addFilepattern("folder1/file1.txt")
				.addFilepattern("folder1/file2.txt").call();
		RevCommit other = git.commit()
				.setMessage("removing folders on 'other'").call();

		git.checkout().setName(base.name()).call();

		file = new File(db.getWorkTree()
		write(file

		git.add().addFilepattern("unrelated").call();
		RevCommit head = git.commit().setMessage("Adding another file").call();

		file = new File(folder1
		write(file

		ResolveMerger merger = new ResolveMerger(db
		merger.setCommitNames(new String[] { "BASE"
		merger.setWorkingTreeIterator(new FileTreeIterator(db));
		boolean ok = merger.merge(head.getId()

		assertFalse(merger.getFailingPaths().isEmpty());
		assertFalse(ok);
	}

	@Test
	public void checkMergeConflictingTreesWithoutIndex() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.commit().setAll(true).setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		assertEquals(MergeStatus.CONFLICTING
		assertEquals(
				"[d/1
				indexState(CONTENT));
	}

	@Test
	public void checkMergeMergeableTreesWithoutIndex() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.commit().setAll(true).setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals("[d/1
				indexState(CONTENT));
	}

	@Test
	public void checkMergeEqualTreesWithoutIndex() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.commit().setAll(true).setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals("[d/1
				indexState(CONTENT));
	}

	@Test
	public void checkMergeEqualTreesInCore() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit first = git.commit().setMessage("added d/1").call();

		writeTrashFile("d/1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		RevCommit sideCommit = git.commit().setAll(true)
				.setMessage("modified d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();

		ThreeWayMerger resolveMerger = MergeStrategy.RESOLVE
				.newMerger(db
		boolean noProblems = resolveMerger.merge(masterCommit
		assertTrue(noProblems);
	}

	@Test
	public void checkMergeEqualNewTrees() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("2"
		git.add().addFilepattern("2").call();
		RevCommit first = git.commit().setMessage("added 2").call();

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("added d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		git.commit().setAll(true).setMessage("added d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		assertEquals(MergeStatus.MERGED
		assertEquals(
				"[2
				indexState(CONTENT));
	}

	@Test
	public void checkMergeConflictingNewTrees() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("2"
		git.add().addFilepattern("2").call();
		RevCommit first = git.commit().setMessage("added 2").call();

		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("added d/1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("d/1"
		git.add().addFilepattern("d/1").call();
		git.commit().setAll(true).setMessage("added d/1 on side").call();

		git.rm().addFilepattern("d/1").call();
		git.rm().addFilepattern("d").call();
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		assertEquals(MergeStatus.CONFLICTING
		assertEquals(
				"[2
				indexState(CONTENT));
	}

	@Test
	public void checkMergeConflictingFilesWithTreeInIndex() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("0"
		git.add().addFilepattern("0").call();
		RevCommit first = git.commit().setMessage("added 0").call();

		writeTrashFile("0"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified 0 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("0"
		git.commit().setAll(true).setMessage("modified 0 on side").call();

		git.rm().addFilepattern("0").call();
		writeTrashFile("0/0"
		git.add().addFilepattern("0/0").call();
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		assertEquals(MergeStatus.FAILED
	}

	@Test
	public void checkMergeMergeableFilesWithTreeInIndex() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("0"
		writeTrashFile("1"
		git.add().addFilepattern("0").addFilepattern("1").call();
		RevCommit first = git.commit().setMessage("added 0

		writeTrashFile("1"
		RevCommit masterCommit = git.commit().setAll(true)
				.setMessage("modified 1 on master").call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("1"
		git.commit().setAll(true).setMessage("modified 1 on side").call();

		git.rm().addFilepattern("0").call();
		writeTrashFile("0/0"
		git.add().addFilepattern("0/0").call();
		try {
			git.merge().include(masterCommit).call();
			Assert.fail("Didn't get the expected exception");
		} catch (CheckoutConflictException e) {
			assertEquals(1
			assertEquals("0/0"
		}
	}

	@Test
	public void checkLockedFilesToBeDeleted() throws Exception {
		Git git = Git.wrap(db);

		writeTrashFile("a.txt"
		writeTrashFile("b.txt"
		git.add().addFilepattern("a.txt").addFilepattern("b.txt").call();
		RevCommit first = git.commit().setMessage("added a.txt

		writeTrashFile("a.txt"
		git.rm().addFilepattern("b.txt").call();
		RevCommit masterCommit = git.commit()
				.setMessage("modified a.txt
				.call();

		git.checkout().setCreateBranch(true).setStartPoint(first)
				.setName("side").call();
		writeTrashFile("c.txt"
		git.add().addFilepattern("c.txt").call();
		git.commit().setMessage("added c.txt").call();

		FileInputStream fis = new FileInputStream(new File(db.getWorkTree()
				"b.txt"));
		MergeResult mergeRes = git.merge().include(masterCommit).call();
		if (mergeRes.getMergeStatus().equals(MergeStatus.FAILED)) {
			assertEquals(1
			assertEquals(MergeFailureReason.COULD_NOT_DELETE
					.getFailingPaths().get("b.txt"));
		}
		assertEquals("[a.txt
				+ "[c.txt
		fis.close();
	}

	@Test
	public void checkForCorrectIndex() throws Exception {
		File f;
		long lastTs4
		Git git = Git.wrap(db);
		File indexFile = db.getIndexFile();

		f = writeTrashFiles(false
		lastTs4 = f.lastModified();

		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit firstCommit = git.commit().setMessage("initial commit")
				.call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
		assertEquals("Commit should not touch working tree file 4"
				new File(db.getWorkTree()
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		f = writeTrashFiles(false
				null);
		fsTick(f);
		git.add().addFilepattern(".").call();
		RevCommit masterCommit = git.commit().setMessage("master commit")
				.call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
				+ lastTsIndex
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		git.checkout().setCreateBranch(true).setStartPoint(firstCommit)
				.setName("side").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("1"
				+ lastTsIndex
		lastTsIndex = indexFile.lastModified();

		assertEquals("[0
				+ "[1
				+ "[2
				+ "[3
				+ "[4
				indexState(CONTENT));
		fsTick(indexFile);
		f = writeTrashFiles(false
		lastTs4 = f.lastModified();
		fsTick(f);
		git.add().addFilepattern(".").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("*" + lastTsIndex
				"4"
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		f = writeTrashFiles(false
		fsTick(f);
		git.add().addFilepattern(".").call();
		git.commit().setMessage("side commit").call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("0"
				+ lastTsIndex
		lastTsIndex = indexFile.lastModified();

		fsTick(indexFile);
		git.merge().include(masterCommit).call();
		checkConsistentLastModified("0"
		checkModificationTimeStampOrder("4"
				+ lastTsIndex
		assertEquals(
				"[0
						+ "[1
						+ "[2
						+ "[3
						+ "[4
				indexState(CONTENT));
	}

	private void checkConsistentLastModified(String... pathes)
			throws IOException {
		DirCache dc = db.readDirCache();
		File workTree = db.getWorkTree();
		for (String path : pathes)
			assertEquals(
					"IndexEntry with path "
							+ path
							+ " has lastmodified with is different from the worktree file"
					new File(workTree
							.getLastModified());
	}

	private void checkModificationTimeStampOrder(String... pathes) {
		long lastMod = Long.MIN_VALUE;
		for (String p : pathes) {
			boolean strong = p.startsWith("<");
			boolean fixed = p.charAt(strong ? 1 : 0) == '*';
			p = p.substring((strong ? 1 : 0) + (fixed ? 1 : 0));
			long curMod = fixed ? Long.valueOf(p).longValue() : new File(
					db.getWorkTree()
			if (strong)
				assertTrue("path " + p + " is not younger than predecesssor"
						curMod > lastMod);
			else
				assertTrue("path " + p + " is older than predecesssor"
						curMod >= lastMod);
		}
	}
}
