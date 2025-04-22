package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.ResetCommand.ResetType;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.dircache.DirCache;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.FileMode;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class RevertCommandTest extends RepositoryTestCase {
	@Test
	public void testRevert() throws IOException
			GitAPIException {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("create a").call();

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

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("fixed b").call();

			git.revert().include(fixingA).call();

			assertEquals(RepositoryState.SAFE

			assertTrue(new File(db.getWorkTree()
			checkFile(new File(db.getWorkTree()
					"first line\nsec. line\nthird line\nfourth line\n");
			Iterator<RevCommit> history = git.log().call().iterator();
			RevCommit revertCommit = history.next();
			String expectedMessage = "Revert \"fixed a\"\n\n"
					+ "This reverts commit " + fixingA.getId().getName() + ".\n";
			assertEquals(expectedMessage
			assertEquals("fixed b"
			assertEquals("fixed a"
			assertEquals("enlarged a"
			assertEquals("create b"
			assertEquals("create a"
			assertFalse(history.hasNext());

			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
		}

	}

	@Test
	public void testRevertMultiple() throws IOException
			GitAPIException {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add first").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("add second").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit thirdCommit = git.commit().setMessage("add third").call();

			git.revert().include(thirdCommit).include(secondCommit).call();

			assertEquals(RepositoryState.SAFE

			checkFile(new File(db.getWorkTree()
			Iterator<RevCommit> history = git.log().call().iterator();
			RevCommit revertCommit = history.next();
			String expectedMessage = "Revert \"add second\"\n\n"
					+ "This reverts commit "
					+ secondCommit.getId().getName() + ".\n";
			assertEquals(expectedMessage
			revertCommit = history.next();
			expectedMessage = "Revert \"add third\"\n\n"
					+ "This reverts commit " + thirdCommit.getId().getName()
					+ ".\n";
			assertEquals(expectedMessage
			assertEquals("add third"
			assertEquals("add second"
			assertEquals("add first"
			assertFalse(history.hasNext());

			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
		}

	}

	@Test
	public void testRevertMultipleWithFail() throws IOException
			JGitInternalException
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add first").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("add second").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("add third").call();

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit fourthCommit = git.commit().setMessage("add fourth").call();

			git.revert().include(fourthCommit).include(secondCommit).call();

			assertEquals(RepositoryState.REVERTING

			checkFile(new File(db.getWorkTree()
					+ "<<<<<<< master\n" + "second\n" + "third\n" + "=======\n"
					+ ">>>>>>> " + secondCommit.getId().abbreviate(7).name()
					+ " add second\n");
			Iterator<RevCommit> history = git.log().call().iterator();
			RevCommit revertCommit = history.next();
			String expectedMessage = "Revert \"add fourth\"\n\n"
					+ "This reverts commit " + fourthCommit.getId().getName()
					+ ".\n";
			assertEquals(expectedMessage
			assertEquals("add fourth"
			assertEquals("add third"
			assertEquals("add second"
			assertEquals("add first"
			assertFalse(history.hasNext());

			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: Revert \""));
		}

	}

	@Test
	public void testRevertDirtyIndex() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			writeTrashFile("a"
			git.add().addFilepattern("a").call();

			doRevertAndCheckResult(git
					MergeFailureReason.DIRTY_INDEX);
		}
}

	@Test
	public void testRevertDirtyWorktree() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			writeTrashFile("a"

			doRevertAndCheckResult(git
					MergeFailureReason.DIRTY_WORKTREE);
		}
	}

	@Test
	public void testRevertConflictResolution() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			RevertCommand revert = git.revert();
			RevCommit newHead = revert.include(sideCommit.getId()).call();
			assertNull(newHead);
			MergeResult result = revert.getFailingResult();
			assertEquals(MergeStatus.CONFLICTING
			assertTrue(new File(db.getDirectory()
			assertEquals("Revert \"" + sideCommit.getShortMessage()
					+ "\"\n\nThis reverts commit " + sideCommit.getId().getName()
					+ ".\n\nConflicts:\n\ta\n"
					db.readMergeCommitMsg());
			assertTrue(new File(db.getDirectory()
					.exists());
			assertEquals(sideCommit.getId()
			assertEquals(RepositoryState.REVERTING

			writeTrashFile("a"
			git.add().addFilepattern("a").call();

			assertEquals(RepositoryState.REVERTING_RESOLVED
					db.getRepositoryState());

			git.commit().setOnly("a").setMessage("resolve").call();

			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testRevertkConflictReset() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			RevertCommand revert = git.revert();
			RevCommit newHead = revert.include(sideCommit.getId()).call();
			assertNull(newHead);
			MergeResult result = revert.getFailingResult();

			assertEquals(MergeStatus.CONFLICTING
			assertEquals(RepositoryState.REVERTING
			assertTrue(new File(db.getDirectory()
					.exists());

			git.reset().setMode(ResetType.MIXED).setRef("HEAD").call();

			assertEquals(RepositoryState.SAFE
			assertFalse(new File(db.getDirectory()
					.exists());
		}
	}

	@Test
	public void testRevertOverExecutableChangeOnNonExectuableFileSystem()
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

			RevertCommand revert = git.revert();
			RevCommit newHead = revert.include(commit2).call();
			assertNotNull(newHead);
		}
	}

	@Test
	public void testRevertConflictMarkers() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			RevertCommand revert = git.revert();
			RevCommit newHead = revert.include(sideCommit.getId())
					.call();
			assertNull(newHead);
			MergeResult result = revert.getFailingResult();
			assertEquals(MergeStatus.CONFLICTING

			String expected = "<<<<<<< master\na(latest)\n=======\na\n>>>>>>> ca96c31 second master\n";
			checkFile(new File(db.getWorkTree()
		}
	}

	@Test
	public void testRevertOurCommitName() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit sideCommit = prepareRevert(git);

			RevertCommand revert = git.revert();
			RevCommit newHead = revert.include(sideCommit.getId())
					.setOurCommitName("custom name").call();
			assertNull(newHead);
			MergeResult result = revert.getFailingResult();
			assertEquals(MergeStatus.CONFLICTING

			String expected = "<<<<<<< custom name\na(latest)\n=======\na\n>>>>>>> ca96c31 second master\n";
			checkFile(new File(db.getWorkTree()
		}
	}

	private RevCommit prepareRevert(Git git) throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("first master").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit oldCommit = git.commit().setMessage("second master").call();

		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		git.commit().setMessage("side").call();

		return oldCommit;
	}

	private void doRevertAndCheckResult(final Git git
			final RevCommit sideCommit
			throws Exception {
		String indexState = indexState(CONTENT);

		RevertCommand revert = git.revert();
		RevCommit resultCommit = revert.include(sideCommit.getId()).call();
		assertNull(resultCommit);
		MergeResult result = revert.getFailingResult();
		assertEquals(MergeStatus.FAILED
		assertEquals(1
		assertEquals(reason
		assertEquals("a(modified)"
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE

		if (reason == null) {
			ReflogReader reader = db.getReflogReader(Constants.HEAD);
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: "));
			reader = db.getReflogReader(db.getBranch());
			assertTrue(reader.getLastEntry().getComment()
					.startsWith("revert: "));
		}
	}
}
