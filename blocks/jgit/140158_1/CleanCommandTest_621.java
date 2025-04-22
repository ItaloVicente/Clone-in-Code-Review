package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.CherryPickResult.CherryPickStatus;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.MultipleParentsNotAllowedException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class CherryPickCommandTest extends RepositoryTestCase {
	@Test
	public void testCherryPick() throws IOException
			GitAPIException {
		doTestCherryPick(false);
	}

	@Test
	public void testCherryPickNoCommit() throws IOException
			JGitInternalException
		doTestCherryPick(true);
	}

	private void doTestCherryPick(boolean noCommit) throws IOException
			JGitInternalException
			GitAPIException {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit firstCommit = git.commit().setMessage("create a").call();

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("create b").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("enlarged a").call();

			writeTrashFile("a"
					"first line\nsecond line\nthird line\nfourth line\n");
			git.add().addFilepattern("a").call();
			RevCommit fixingA = git.commit().setMessage("fixed a").call();

			git.branchCreate().setName("side").setStartPoint(firstCommit).call();
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("enhanced a").call();

			CherryPickResult pickResult = git.cherryPick().include(fixingA)
					.setNoCommit(noCommit).call();

			assertEquals(CherryPickStatus.OK
			assertFalse(new File(db.getWorkTree()
			checkFile(new File(db.getWorkTree()
					"first line\nsecond line\nthird line\nfeature++\n");
			Iterator<RevCommit> history = git.log().call().iterator();
			if (!noCommit)
				assertEquals("fixed a"
			assertEquals("enhanced a"
			assertEquals("create a"
			assertFalse(history.hasNext());
		}
	}

    @Test
    public void testSequentialCherryPick() throws IOException
            GitAPIException {
        try (Git git = new Git(db)) {
	        writeTrashFile("a"
	        git.add().addFilepattern("a").call();
	        RevCommit firstCommit = git.commit().setMessage("create a").call();

	        writeTrashFile("a"
	        git.add().addFilepattern("a").call();
	        RevCommit enlargingA = git.commit().setMessage("enlarged a").call();

	        writeTrashFile("a"
	                "first line\nsecond line\nthird line\nfourth line\n");
	        git.add().addFilepattern("a").call();
	        RevCommit fixingA = git.commit().setMessage("fixed a").call();

	        git.branchCreate().setName("side").setStartPoint(firstCommit).call();
	        checkoutBranch("refs/heads/side");

	        writeTrashFile("b"
	        git.add().addFilepattern("b").call();
	        git.commit().setMessage("create b").call();

	        CherryPickResult result = git.cherryPick().include(enlargingA).include(fixingA).call();
	        assertEquals(CherryPickResult.CherryPickStatus.OK

	        Iterator<RevCommit> history = git.log().call().iterator();
	        assertEquals("fixed a"
	        assertEquals("enlarged a"
	        assertEquals("create b"
	        assertEquals("create a"
	        assertFalse(history.hasNext());
        }
    }

	@Test
	public void testCherryPickDirtyIndex() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			writeTrashFile("a"
			git.add().addFilepattern("a").call();

			doCherryPickAndCheckResult(git
					MergeFailureReason.DIRTY_INDEX);
		}
	}

	@Test
	public void testCherryPickDirtyWorktree() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			writeTrashFile("a"

			doCherryPickAndCheckResult(git
					MergeFailureReason.DIRTY_WORKTREE);
		}
	}

	@Test
	public void testCherryPickConflictResolution() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			CherryPickResult result = git.cherryPick().include(sideCommit.getId())
					.call();

			assertEquals(CherryPickStatus.CONFLICTING
			assertTrue(new File(db.getDirectory()
			assertEquals("side\n\nConflicts:\n\ta\n"
			assertTrue(new File(db.getDirectory()
					.exists());
			assertEquals(sideCommit.getId()
			assertEquals(RepositoryState.CHERRY_PICKING

			writeTrashFile("a"
			git.add().addFilepattern("a").call();

			assertEquals(RepositoryState.CHERRY_PICKING_RESOLVED
					db.getRepositoryState());

			git.commit().setOnly("a").setMessage("resolve").call();

			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testCherryPickConflictResolutionNoCOmmit() throws Exception {
		Git git = new Git(db);
		RevCommit sideCommit = prepareCherryPick(git);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.setNoCommit(true).call();

		assertEquals(CherryPickStatus.CONFLICTING
		assertTrue(db.readDirCache().hasUnmergedPaths());
		String expected = "<<<<<<< master\na(master)\n=======\na(side)\n>>>>>>> 527460a side\n";
		assertEquals(expected
		assertTrue(new File(db.getDirectory()
		assertEquals("side\n\nConflicts:\n\ta\n"
		assertFalse(new File(db.getDirectory()
				.exists());
		assertEquals(RepositoryState.SAFE

		writeTrashFile("a"
		git.add().addFilepattern("a").call();

		assertEquals(RepositoryState.SAFE

		git.commit().setOnly("a").setMessage("resolve").call();

		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testCherryPickConflictReset() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			CherryPickResult result = git.cherryPick().include(sideCommit.getId())
					.call();

			assertEquals(CherryPickStatus.CONFLICTING
			assertEquals(RepositoryState.CHERRY_PICKING
			assertTrue(new File(db.getDirectory()
					.exists());

			git.reset().setMode(ResetType.MIXED).setRef("HEAD").call();

			assertEquals(RepositoryState.SAFE
			assertFalse(new File(db.getDirectory()
					.exists());
		}
	}

	@Test
	public void testCherryPickOverExecutableChangeOnNonExectuableFileSystem()
			throws Exception {
		try (Git git = new Git(db)) {
			File file = writeTrashFile("test.txt"
			assertNotNull(git.add().addFilepattern("test.txt").call());
			assertNotNull(git.commit().setMessage("commit1").call());

			assertNotNull(git.checkout().setCreateBranch(true).setName("a").call());

			writeTrashFile("test.txt"
			assertNotNull(git.add().addFilepattern("test.txt").call());
			RevCommit commit2 = git.commit().setMessage("commit2").call();
			assertNotNull(commit2);

			assertNotNull(git.checkout().setName(Constants.MASTER).call());

			DirCache cache = db.lockDirCache();
			cache.getEntry("test.txt").setFileMode(FileMode.EXECUTABLE_FILE);
			cache.write();
			assertTrue(cache.commit());
			cache.unlock();

			assertNotNull(git.commit().setMessage("commit3").call());

			db.getFS().setExecute(file
			git.getRepository()
					.getConfig()
					.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
							ConfigConstants.CONFIG_KEY_FILEMODE

			CherryPickResult result = git.cherryPick().include(commit2).call();
			assertNotNull(result);
			assertEquals(CherryPickStatus.OK
		}
	}

	@Test
	public void testCherryPickConflictMarkers() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			CherryPickResult result = git.cherryPick().include(sideCommit.getId())
					.call();
			assertEquals(CherryPickStatus.CONFLICTING

			String expected = "<<<<<<< master\na(master)\n=======\na(side)\n>>>>>>> 527460a side\n";
			checkFile(new File(db.getWorkTree()
		}
	}

	@Test
	public void testCherryPickOurCommitName() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareCherryPick(git);

			CherryPickResult result = git.cherryPick().include(sideCommit.getId())
					.setOurCommitName("custom name").call();
			assertEquals(CherryPickStatus.CONFLICTING

			String expected = "<<<<<<< custom name\na(master)\n=======\na(side)\n>>>>>>> 527460a side\n";
			checkFile(new File(db.getWorkTree()
		}
	}

	private RevCommit prepareCherryPick(Git git) throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit firstMasterCommit = git.commit().setMessage("first master")
				.call();

		createBranch(firstMasterCommit
		checkoutBranch("refs/heads/side");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit sideCommit = git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("second master").call();
		return sideCommit;
	}

	private void doCherryPickAndCheckResult(final Git git
			final RevCommit sideCommit
			throws Exception {
		String indexState = indexState(CONTENT);

		CherryPickResult result = git.cherryPick().include(sideCommit.getId())
				.call();
		assertEquals(CherryPickStatus.FAILED
		assertEquals(1
		assertEquals(reason
		assertEquals("a(modified)"
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE

		if (reason == null) {
			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("cherry-pick: "));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("cherry-pick: "));
		}
	}

	@Test
	public void testCherryPickMerge() throws Exception {
		try (Git git = new Git(db)) {
			commitFile("file"
			commitFile("file"
			checkoutBranch("refs/heads/side");
			RevCommit commitD = commitFile("file"
			commitFile("file"
			MergeResult mergeResult = git.merge().include(commitD).call();
			ObjectId commitM = mergeResult.getNewHead();
			checkoutBranch("refs/heads/master");
			RevCommit commitT = commitFile("another"

			try {
				git.cherryPick().include(commitM).call();
				fail("merges should not be cherry-picked by default");
			} catch (MultipleParentsNotAllowedException e) {
			}
			try {
				git.cherryPick().include(commitM).setMainlineParentNumber(3).call();
				fail("specifying a non-existent parent should fail");
			} catch (JGitInternalException e) {
				assertTrue(e.getMessage().endsWith(
						"does not have a parent number 3."));
			}

			CherryPickResult result = git.cherryPick().include(commitM)
					.setMainlineParentNumber(1).call();
			assertEquals(CherryPickStatus.OK
			checkFile(new File(db.getWorkTree()

			git.reset().setMode(ResetType.HARD).setRef(commitT.getName()).call();

			CherryPickResult result2 = git.cherryPick().include(commitM)
					.setMainlineParentNumber(2).call();
			assertEquals(CherryPickStatus.OK
			checkFile(new File(db.getWorkTree()
		}
	}
}
