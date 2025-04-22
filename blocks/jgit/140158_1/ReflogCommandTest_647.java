package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.RebaseCommand.InteractiveHandler;
import org.eclipse.jgit.api.RebaseCommand.Operation;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.api.errors.InvalidRebaseStepException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.UnmergedPathsException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.dircache.DirCacheCheckout;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IllegalTodoFileModification;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.events.ListenerHandle;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.AbbreviatedObjectId;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.RebaseTodoLine;
import org.eclipse.jgit.lib.RebaseTodoLine.Action;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.IO;
import org.eclipse.jgit.util.RawParseUtils;
import org.junit.Before;
import org.junit.Test;

public class RebaseCommandTest extends RepositoryTestCase {
	private static final String GIT_REBASE_TODO = "rebase-merge/git-rebase-todo";

	private static final String FILE1 = "file1";

	protected Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		this.git = new Git(db);
	}

	private void checkoutCommit(RevCommit commit) throws IllegalStateException
			IOException {
		RevCommit head;
		try (RevWalk walk = new RevWalk(db)) {
			head = walk.parseCommit(db.resolve(Constants.HEAD));
			DirCacheCheckout dco = new DirCacheCheckout(db
					db.lockDirCache()
			dco.setFailOnConflict(true);
			dco.checkout();
		}
		RefUpdate refUpdate = db.updateRef(Constants.HEAD
		refUpdate.setNewObjectId(commit);
		refUpdate.setRefLogMessage("checkout: moving to " + head.getName()
				false);
		refUpdate.forceUpdate();
	}

	@Test
	public void testFastForwardWithNewFile() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		File file2 = writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/topic");
		assertFalse(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertTrue(new File(db.getWorkTree()
		checkFile(file2
		assertEquals(Status.FAST_FORWARD

		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		List<ReflogEntry> topicLog = db.getReflogReader("refs/heads/topic")
				.getReverseEntries();
		List<ReflogEntry> masterLog = db.getReflogReader("refs/heads/master")
				.getReverseEntries();
		assertEquals("rebase finished: returning to refs/heads/topic"
				.get(0).getComment());
		assertEquals("checkout: moving from topic to " + second.getName()
				headLog.get(1).getComment());
		assertEquals(2
		assertEquals(2
		assertEquals(
				"rebase finished: refs/heads/topic onto " + second.getName()
				topicLog.get(0).getComment());
	}

	@Test
	public void testFastForwardWithMultipleCommits() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()
		createBranch(first
		File file2 = writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("Change content of file2")
				.call();

		checkoutBranch("refs/heads/topic");
		assertFalse(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertTrue(new File(db.getWorkTree()
		checkFile(file2
		assertEquals(Status.FAST_FORWARD

		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		List<ReflogEntry> topicLog = db.getReflogReader("refs/heads/topic")
				.getReverseEntries();
		List<ReflogEntry> masterLog = db.getReflogReader("refs/heads/master")
				.getReverseEntries();
		assertEquals("rebase finished: returning to refs/heads/topic"
				.get(0).getComment());
		assertEquals("checkout: moving from topic to " + second.getName()
				headLog.get(1).getComment());
		assertEquals(3
		assertEquals(2
		assertEquals(
				"rebase finished: refs/heads/topic onto " + second.getName()
				topicLog.get(0).getComment());
	}

	@Test
	public void testRebaseShouldIgnoreMergeCommits()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit a = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		createBranch(a

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit b = git.commit().setMessage("updated file1 on master").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit c = git.commit()
				.setMessage("update file3 on topic").call();

		createBranch(c

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit d = git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/side");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit e = git.commit().setMessage("update file2 on side")
				.call();

		checkoutBranch("refs/heads/topic");
		MergeResult result = git.merge().include(e.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED
		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.OK

		try (RevWalk rw = new RevWalk(db)) {
			rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
			assertDerivedFrom(rw.next()
			assertDerivedFrom(rw.next()
			assertDerivedFrom(rw.next()
			assertEquals(b
			assertEquals(a
		}

		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		List<ReflogEntry> sideLog = db.getReflogReader("refs/heads/side")
				.getReverseEntries();
		List<ReflogEntry> topicLog = db.getReflogReader("refs/heads/topic")
				.getReverseEntries();
		List<ReflogEntry> masterLog = db.getReflogReader("refs/heads/master")
				.getReverseEntries();
		assertEquals("rebase finished: returning to refs/heads/topic"
				.get(0).getComment());
		assertEquals("rebase: update file2 on side"
				.getComment());
		assertEquals("rebase: Add file2"
		assertEquals("rebase: update file3 on topic"
				.getComment());
		assertEquals("checkout: moving from topic to " + b.getName()
				.get(4).getComment());
		assertEquals(2
		assertEquals(2
		assertEquals(5
		assertEquals("rebase finished: refs/heads/topic onto " + b.getName()
				topicLog.get(0).getComment());
	}

	static void assertDerivedFrom(RevCommit derived
		assertThat(derived
		assertEquals(original.getFullMessage()
	}

	@Test
	public void testRebasePreservingMerges1() throws Exception {
		doTestRebasePreservingMerges(true);
	}

	@Test
	public void testRebasePreservingMerges2() throws Exception {
		doTestRebasePreservingMerges(false);
	}

	private void doTestRebasePreservingMerges(boolean testConflict)
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit a = git.commit().setMessage("commit a").call();

		createBranch(a

		writeTrashFile(FILE1
		writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit b = git.commit().setMessage("commit b").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		RevCommit c = git.commit().setMessage("commit c").call();

		createBranch(c

		writeTrashFile("file2"
		if (testConflict)
			writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit d = git.commit().setMessage("commit d").call();
		assertTrue(new File(db.getWorkTree()

		checkoutBranch("refs/heads/side");
		writeTrashFile("file3"
		if (testConflict)
			writeTrashFile("conflict"
		git.add().addFilepattern(".").call();
		RevCommit e = git.commit().setMessage("commit e").call();

		checkoutBranch("refs/heads/topic");
		MergeResult result = git.merge().include(e.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		final RevCommit f;
		if (testConflict) {
			assertEquals(MergeStatus.CONFLICTING
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			f = git.commit().setMessage("commit f").call();
		} else {
			assertEquals(MergeStatus.MERGED
			try (RevWalk rw = new RevWalk(db)) {
				f = rw.parseCommit(result.getNewHead());
			}
		}

		RebaseResult res = git.rebase().setUpstream("refs/heads/master")
				.setPreserveMerges(true).call();
		if (testConflict) {
			assertEquals(Status.STOPPED
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			assertTrue(read("conflict").contains("\nb\n=======\nd\n"));
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();

			assertEquals(Status.STOPPED
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			assertTrue(read("conflict").contains("\nb\n=======\ne\n"));
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();

			assertEquals(Status.STOPPED
			assertEquals(Collections.singleton("conflict")
					.getConflicting());
			assertTrue(read("conflict").contains("\nd new\n=======\ne new\n"));
			writeTrashFile("conflict"
			git.add().addFilepattern("conflict").call();
			res = git.rebase().setOperation(Operation.CONTINUE).call();
		}
		assertEquals(Status.OK

		if (testConflict)
			assertEquals("f new resolved"
		assertEquals("blah"
		assertEquals("file2"
		assertEquals("more change"

		try (RevWalk rw = new RevWalk(db)) {
			rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
			RevCommit newF = rw.next();
			assertDerivedFrom(newF
			assertEquals(2
			RevCommit newD = rw.next();
			assertDerivedFrom(newD
			if (testConflict)
				assertEquals("d new"
			RevCommit newE = rw.next();
			assertDerivedFrom(newE
			if (testConflict)
				assertEquals("e new"
			assertEquals(newD
			assertEquals(newE
			assertDerivedFrom(rw.next()
			assertEquals(b
			assertEquals(a
		}
	}

	private String readFile(String path
		try (TreeWalk walk = TreeWalk.forPath(db
			ObjectLoader loader = db.open(walk.getObjectId(0)
					Constants.OBJ_BLOB);
			String result = RawParseUtils.decode(loader.getCachedBytes());
			return result;
		}
	}

	@Test
	public void testRebasePreservingMergesWithUnrelatedSide1() throws Exception {
		doTestRebasePreservingMergesWithUnrelatedSide(true);
	}

	@Test
	public void testRebasePreservingMergesWithUnrelatedSide2() throws Exception {
		doTestRebasePreservingMergesWithUnrelatedSide(false);
	}

	private void doTestRebasePreservingMergesWithUnrelatedSide(
			boolean testConflict) throws Exception {
		try (RevWalk rw = new RevWalk(db)) {
			rw.sort(RevSort.TOPO);

			writeTrashFile(FILE1
			git.add().addFilepattern(FILE1).call();
			RevCommit a = git.commit().setMessage("commit a").call();

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit b = git.commit().setMessage("commit b").call();

			createBranch(b
			checkoutBranch("refs/heads/topic");

			writeTrashFile("file3"
			writeTrashFile(FILE1
			git.add().addFilepattern("file3").addFilepattern(FILE1).call();
			RevCommit c = git.commit().setMessage("commit c").call();

			createBranch(a
			checkoutBranch("refs/heads/side");
			writeTrashFile("conflict"
			writeTrashFile(FILE1
			git.add().addFilepattern(".").call();
			RevCommit e = git.commit().setMessage("commit e").call();

			checkoutBranch("refs/heads/topic");
			MergeResult result = git.merge().include(e)
					.setStrategy(MergeStrategy.RESOLVE).call();

			assertEquals(MergeStatus.CONFLICTING
			assertEquals(result.getConflicts().keySet()
					Collections.singleton(FILE1));
			writeTrashFile(FILE1
			git.add().addFilepattern(FILE1).call();
			RevCommit d = git.commit().setMessage("commit d").call();

			RevCommit f = commitFile("file2"

			checkoutBranch("refs/heads/master");
			writeTrashFile("fileg"
			if (testConflict)
				writeTrashFile("conflict"
			git.add().addFilepattern(".").call();
			RevCommit g = git.commit().setMessage("commit g").call();

			checkoutBranch("refs/heads/topic");
			RebaseResult res = git.rebase().setUpstream("refs/heads/master")
					.setPreserveMerges(true).call();
			if (testConflict) {
				assertEquals(Status.STOPPED
				assertEquals(Collections.singleton("conflict")
						.getConflicting());
				writeTrashFile("conflict"
				git.add().addFilepattern("conflict").call();
				res = git.rebase().setOperation(Operation.CONTINUE).call();
			}
			assertEquals(Status.OK

			assertEquals("merge resolution"
			assertEquals("new content two"
			assertEquals("more changess"
			assertEquals("fileg"

			rw.markStart(rw.parseCommit(db.resolve("refs/heads/topic")));
			RevCommit newF = rw.next();
			assertDerivedFrom(newF
			RevCommit newD = rw.next();
			assertDerivedFrom(newD
			assertEquals(2
			RevCommit newC = rw.next();
			assertDerivedFrom(newC
			RevCommit newE = rw.next();
			assertEquals(e
			assertEquals(newC
			assertEquals(e
			assertEquals(g
			assertEquals(b
			assertEquals(a
		}
	}

	@Test
	public void testRebaseParentOntoHeadShouldBeUptoDate() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit parent = git.commit().setMessage("parent comment").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("head commit").call();

		RebaseResult result = git.rebase().setUpstream(parent).call();
		assertEquals(Status.UP_TO_DATE

		assertEquals(2
				.size());
		assertEquals(2
				.getReverseEntries().size());
	}

	@Test
	public void testUpToDate() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream(first).call();
		assertEquals(Status.UP_TO_DATE

		assertEquals(1
				.size());
		assertEquals(1
				.getReverseEntries().size());
	}

	@Test
	public void testUnknownUpstream() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1").call();

		assertTrue(new File(db.getWorkTree()

		try {
			git.rebase().setUpstream("refs/heads/xyz").call();
			fail("expected exception was not thrown");
		} catch (RefNotFoundException e) {
		}
	}

	@Test
	public void testConflictFreeWithSingleFile() throws Exception {
		File theFile = writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit second = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()
		writeTrashFile(FILE1
		checkFile(theFile
		git.add().addFilepattern(FILE1).call();
		RevCommit lastMasterChange = git.commit().setMessage(
				"change file1 in master").call();

		createBranch(second
		checkoutBranch("refs/heads/topic");
		checkFile(theFile

		assertTrue(new File(db.getWorkTree()
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit origHead = git.commit().setMessage("change file1 in topic")
				.call();

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.OK
		checkFile(theFile
		assertEquals("refs/heads/topic"
		try (RevWalk rw = new RevWalk(db)) {
			assertEquals(lastMasterChange
					db.resolve(Constants.HEAD)).getParent(0));
		}
		assertEquals(origHead
		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		List<ReflogEntry> topicLog = db.getReflogReader("refs/heads/topic")
				.getReverseEntries();
		List<ReflogEntry> masterLog = db.getReflogReader("refs/heads/master")
				.getReverseEntries();
		assertEquals(2
		assertEquals(3
		assertEquals("rebase finished: refs/heads/topic onto "
				+ lastMasterChange.getName()
		assertEquals("rebase finished: returning to refs/heads/topic"
				.get(0).getComment());
	}

	@Test
	public void testDetachedHead() throws Exception {
		File theFile = writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit second = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()
		writeTrashFile(FILE1
		checkFile(theFile
		git.add().addFilepattern(FILE1).call();
		RevCommit lastMasterChange = git.commit().setMessage(
				"change file1 in master").call();

		createBranch(second
		checkoutBranch("refs/heads/topic");
		checkFile(theFile

		assertTrue(new File(db.getWorkTree()
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit topicCommit = git.commit()
				.setMessage("change file1 in topic").call();
		checkoutBranch("refs/heads/master");
		checkoutCommit(topicCommit);
		assertEquals(topicCommit.getId().getName()

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.OK
		checkFile(theFile
		try (RevWalk rw = new RevWalk(db)) {
			assertEquals(lastMasterChange
					db.resolve(Constants.HEAD)).getParent(0));
		}

		List<ReflogEntry> headLog = db.getReflogReader(Constants.HEAD)
				.getReverseEntries();
		assertEquals(8
		assertEquals("rebase: change file1 in topic"
				.getComment());
		assertEquals("checkout: moving from " + topicCommit.getName() + " to "
				+ lastMasterChange.getName()
	}

	@Test
	public void testFilesAddedFromTwoBranches() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit masterCommit = git.commit().setMessage("Add file1 to master")
				.call();

		createBranch(masterCommit
		checkoutBranch("refs/heads/file2");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit addFile2 = git.commit().setMessage(
				"Add file2 to branch file2").call();

		createBranch(masterCommit
		checkoutBranch("refs/heads/file3");
		writeTrashFile("file3"
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("Add file3 to branch file3").call();

		assertTrue(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()

		RebaseResult res = git.rebase().setUpstream("refs/heads/file2").call();
		assertEquals(Status.OK

		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()

		assertEquals("refs/heads/file3"
		try (RevWalk rw = new RevWalk(db)) {
			assertEquals(addFile2
					db.resolve(Constants.HEAD)).getParent(0));
		}

		checkoutBranch("refs/heads/file2");
		assertTrue(new File(db.getWorkTree()
		assertTrue(new File(db.getWorkTree()
		assertFalse(new File(db.getWorkTree()
	}

	@Test
	public void testStopOnConflict() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1
		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		RevCommit conflicting = writeFileAndCommit(FILE1
				"change file1 in topic"

		RevCommit lastTopicCommit = writeFileAndCommit(FILE1
				"change file1 in topic again"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED
		assertEquals(conflicting
		checkFile(FILE1
				"<<<<<<< Upstream
				">>>>>>> e0d1dea change file1 in topic\n2\n3\ntopic4");

		assertEquals(RepositoryState.REBASING_MERGE
				.getRepositoryState());
		assertTrue(new File(db.getDirectory()
		assertEquals(1

		try {
			git.rebase().setUpstream("refs/heads/master").call();
			fail("Expected exception was not thrown");
		} catch (WrongRepositoryStateException e) {
		}

		res = git.rebase().setOperation(Operation.ABORT).call();
		assertEquals(res.getStatus()
		assertEquals("refs/heads/topic"
		checkFile(FILE1
		try (RevWalk rw = new RevWalk(db)) {
			assertEquals(lastTopicCommit
					rw.parseCommit(db.resolve(Constants.HEAD)));
		}
		assertEquals(RepositoryState.SAFE

		assertFalse(new File(db.getDirectory()
	}

	@Test
	public void testStopOnConflictAndAbortWithDetachedHEAD() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1
		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		RevCommit conflicting = writeFileAndCommit(FILE1
				"change file1 in topic"

		RevCommit lastTopicCommit = writeFileAndCommit(FILE1
				"change file1 in topic again"

		git.checkout().setName(lastTopicCommit.getName()).call();

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED
		assertEquals(conflicting
		checkFile(FILE1
				"<<<<<<< Upstream
				">>>>>>> e0d1dea change file1 in topic\n2\n3\ntopic4");

		assertEquals(RepositoryState.REBASING_MERGE
				db.getRepositoryState());
		assertTrue(new File(db.getDirectory()
		assertEquals(1

		try {
			git.rebase().setUpstream("refs/heads/master").call();
			fail("Expected exception was not thrown");
		} catch (WrongRepositoryStateException e) {
		}

		res = git.rebase().setOperation(Operation.ABORT).call();
		assertEquals(res.getStatus()
		assertEquals(lastTopicCommit.getName()
		checkFile(FILE1
		try (RevWalk rw = new RevWalk(db)) {
			assertEquals(lastTopicCommit
					rw.parseCommit(db.resolve(Constants.HEAD)));
		}
		assertEquals(RepositoryState.SAFE

		assertFalse(new File(db.getDirectory()
	}

	@Test
	public void testStopOnConflictAndContinue() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"2topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		try {
			res = git.rebase().setOperation(Operation.CONTINUE).call();
			fail("Expected Exception not thrown");
		} catch (UnmergedPathsException e) {
		}

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE

		ObjectId headId = db.resolve(Constants.HEAD);
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit rc = rw.parseCommit(headId);
			RevCommit parent = rw.parseCommit(rc.getParent(0));
			assertEquals("change file1 in topic\n\nThis is conflicting"
					.getFullMessage());
		}
	}

	@Test
	public void testStopOnConflictAndContinueWithNoDeltaToMaster()
			throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		try {
			res = git.rebase().setOperation(Operation.CONTINUE).call();
			fail("Expected Exception not thrown");
		} catch (UnmergedPathsException e) {
		}

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.NOTHING_TO_COMMIT
		assertEquals(RepositoryState.REBASING_MERGE
				db.getRepositoryState());

		git.rebase().setOperation(Operation.SKIP).call();

		ObjectId headId = db.resolve(Constants.HEAD);
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit rc = rw.parseCommit(headId);
			assertEquals("change file1 in master"
		}
	}

	@Test
	public void testStopOnConflictAndFailContinueIfFileIsDirty()
			throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"2topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		git.add().addFilepattern(FILE1).call();
		File trashFile = writeTrashFile(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.STOPPED
		checkFile(trashFile
	}

	@Test
	public void testStopOnLastConflictAndContinue() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testStopOnLastConflictAndSkip() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.SKIP).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testMergeFirstStopOnLastConflictAndSkip() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"2"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"2"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		writeFileAndAdd(FILE1

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertEquals(Status.STOPPED

		res = git.rebase().setOperation(Operation.SKIP).call();
		assertNotNull(res);
		assertEquals(Status.OK
		assertEquals(RepositoryState.SAFE
		checkFile(FILE1
	}

	@Test
	public void testStopOnConflictAndSkipNoConflict() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"3topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		res = git.rebase().setOperation(Operation.SKIP).call();

		checkFile(FILE1
		assertEquals(Status.OK
	}

	@Test
	public void testStopOnConflictAndSkipWithConflict() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1
				"3master"

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"3topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		res = git.rebase().setOperation(Operation.SKIP).call();
		checkFile(
				FILE1
				"1master\n2\n<<<<<<< Upstream
				">>>>>>> 5afc8df change file1 in topic again\n4\n5topic");
		assertEquals(Status.STOPPED
	}

	@Test
	public void testStopOnConflictCommitAndContinue() throws Exception {
		RevCommit firstInMaster = writeFileAndCommit(FILE1
				"2"
		writeFileAndCommit(FILE1

		checkFile(FILE1
		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");
		checkFile(FILE1

		writeFileAndCommit(FILE1
				"3"

		writeFileAndCommit(FILE1
				"change file1 in topic\n\nThis is conflicting"
				"3"

		writeFileAndCommit(FILE1
				"3topic"

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED

		try {
			res = git.rebase().setOperation(Operation.CONTINUE).call();
			fail("Expected Exception not thrown");
		} catch (UnmergedPathsException e) {
		}

		writeFileAndCommit(FILE1
				"3"

		res = git.rebase().setOperation(Operation.CONTINUE).call();
		assertNotNull(res);

		assertEquals(Status.NOTHING_TO_COMMIT
		assertEquals(RepositoryState.REBASING_MERGE
				db.getRepositoryState());

		git.rebase().setOperation(Operation.SKIP).call();

		ObjectId headId = db.resolve(Constants.HEAD);
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit rc = rw.parseCommit(headId);
			RevCommit parent = rw.parseCommit(rc.getParent(0));
			assertEquals("A different commit message"
		}
	}

	private RevCommit writeFileAndCommit(String fileName
			String... lines) throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		writeTrashFile(fileName
		git.add().addFilepattern(fileName).call();
		return git.commit().setMessage(commitMessage).call();
	}

	private void writeFileAndAdd(String fileName
			throws Exception {
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		writeTrashFile(fileName
		git.add().addFilepattern(fileName).call();
	}

	private void checkFile(String fileName
		File file = new File(db.getWorkTree()
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			sb.append(line);
			sb.append('\n');
		}
		checkFile(file
	}

	@Test
	public void testStopOnConflictFileCreationAndDeletion() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		File file2 = writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		File file3 = writeTrashFile("file3"
		git.add().addFilepattern("file3").call();

		RevCommit firstInMaster = git.commit()
				.setMessage("Add file 1

		File file4 = writeTrashFile("file4"
		git.add().addFilepattern("file4").call();

		deleteTrashFile("file2");
		git.add().setUpdate(true).addFilepattern("file2").call();
		writeTrashFile("folder6/file1"
		git.add().addFilepattern("folder6/file1").call();

		git.commit().setMessage(
				"Add file 4 and folder folder6

		createBranch(firstInMaster
		checkoutBranch("refs/heads/topic");

		deleteTrashFile("file3");
		git.add().setUpdate(true).addFilepattern("file3").call();
		File file5 = writeTrashFile("file5"
		git.add().addFilepattern("file5").call();
		git.commit().setMessage("Delete file3 and add file5 in topic").call();

		writeTrashFile("folder6"
		git.add().addFilepattern("folder6").call();
		File file7 = writeTrashFile("file7"
		git.add().addFilepattern("file7").call();

		deleteTrashFile("file5");
		git.add().setUpdate(true).addFilepattern("file5").call();
		RevCommit conflicting = git.commit().setMessage(
				"Delete file5

		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertEquals(Status.STOPPED
		assertEquals(conflicting

		assertEquals(RepositoryState.REBASING_MERGE
				.getRepositoryState());
		assertTrue(new File(db.getDirectory()
		assertEquals(0

		assertFalse(file2.exists());
		assertFalse(file3.exists());
		assertTrue(file4.exists());
		assertFalse(file5.exists());
		assertTrue(file7.exists());

		res = git.rebase().setOperation(Operation.ABORT).call();
		assertEquals(res.getStatus()
		assertEquals("refs/heads/topic"
		try (RevWalk rw = new RevWalk(db)) {
			assertEquals(conflicting
			assertEquals(RepositoryState.SAFE
		}

		assertFalse(new File(db.getDirectory()

		assertTrue(file2.exists());
		assertFalse(file3.exists());
		assertFalse(file4.exists());
		assertFalse(file5.exists());
		assertTrue(file7.exists());

	}

	@Test
	public void testAuthorScriptConverter() throws Exception {
		PersonIdent ident = new PersonIdent("Author name"
				123456789123L
		String convertedAuthor = git.rebase().toAuthorScript(ident);
		String[] lines = convertedAuthor.split("\n");
		assertEquals("GIT_AUTHOR_NAME='Author name'"
		assertEquals("GIT_AUTHOR_EMAIL='a.mail@some.com'"
		assertEquals("GIT_AUTHOR_DATE='@123456789 -0100'"

		PersonIdent parsedIdent = git.rebase().parseAuthor(
				convertedAuthor.getBytes(UTF_8));
		assertEquals(ident.getName()
		assertEquals(ident.getEmailAddress()
		assertEquals(123456789000L
		assertEquals(ident.getTimeZoneOffset()

		ident = new PersonIdent("Author name"
				123456789123L
		convertedAuthor = git.rebase().toAuthorScript(ident);
		lines = convertedAuthor.split("\n");
		assertEquals("GIT_AUTHOR_NAME='Author name'"
		assertEquals("GIT_AUTHOR_EMAIL='a.mail@some.com'"
		assertEquals("GIT_AUTHOR_DATE='@123456789 +0930'"

		parsedIdent = git.rebase().parseAuthor(
				convertedAuthor.getBytes(UTF_8));
		assertEquals(ident.getName()
		assertEquals(ident.getEmailAddress()
		assertEquals(123456789000L
		assertEquals(ident.getTimeZoneOffset()
	}

	@Test
	public void testRepositoryStateChecks() throws Exception {
		try {
			git.rebase().setOperation(Operation.ABORT).call();
			fail("Expected Exception not thrown");
		} catch (WrongRepositoryStateException e) {
		}
		try {
			git.rebase().setOperation(Operation.SKIP).call();
			fail("Expected Exception not thrown");
		} catch (WrongRepositoryStateException e) {
		}
		try {
			git.rebase().setOperation(Operation.CONTINUE).call();
			fail("Expected Exception not thrown");
		} catch (WrongRepositoryStateException e) {
		}
	}

	@Test
	public void testRebaseWithUntrackedFile() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file3"

		assertEquals(Status.OK
				.call().getStatus());
	}

	@Test
	public void testRebaseWithUnstagedTopicChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.UNCOMMITTED_CHANGES
		assertEquals(1
		assertEquals("file2"
	}

	@Test
	public void testRebaseWithUncommittedTopicChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		File uncommittedFile = writeTrashFile("file2"
		git.add().addFilepattern("file2").call();

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.UNCOMMITTED_CHANGES
		assertEquals(1
		assertEquals("file2"

		checkFile(uncommittedFile
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithUnstagedMasterChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile(FILE1

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.UNCOMMITTED_CHANGES
		assertEquals(1
		assertEquals(FILE1
	}

	@Test
	public void testRebaseWithUncommittedMasterChange() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.UNCOMMITTED_CHANGES
		assertEquals(1
		assertEquals(FILE1
	}

	@Test
	public void testRebaseWithUnstagedMasterChangeBaseCommit() throws Exception {
		writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.UNCOMMITTED_CHANGES
				git.rebase().setUpstream("refs/heads/master")
				.call().getStatus());
	}

	@Test
	public void testRebaseWithUncommittedMasterChangeBaseCommit()
			throws Exception {
		File file0 = writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		write(file0
		git.add().addFilepattern("file0").call();

		String indexState = indexState(CONTENT);

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.UNCOMMITTED_CHANGES
		assertEquals(1
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithUnstagedMasterChangeOtherCommit()
			throws Exception {
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.UNCOMMITTED_CHANGES
				git.rebase().setUpstream("refs/heads/master")
				.call().getStatus());
	}

	@Test
	public void testRebaseWithUncommittedMasterChangeOtherCommit()
			throws Exception {
		File file0 = writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		write(file0
		git.add().addFilepattern("file0").call();

		String indexState = indexState(CONTENT);

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.UNCOMMITTED_CHANGES
		assertEquals(1
		assertEquals("unstaged modified file0"
		assertEquals(indexState
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testFastForwardRebaseWithModification() throws Exception {
		writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		writeTrashFile("file0"

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.FAST_FORWARD
		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals("[file0
				+ "[file1
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithModificationShouldNotDeleteData()
			throws Exception {
		writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		writeTrashFile("file0"

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		if (result.getStatus() == Status.STOPPED)
			git.rebase().setOperation(Operation.ABORT).call();

		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
				"modified file1 on topic");
		assertEquals("[file0
				+ "[file1
				indexState(CONTENT));
	}

	@Test
	public void testRebaseWithUncommittedDelete() throws Exception {
		File file0 = writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/topic");
		git.rm().addFilepattern("file0").call();

		RebaseResult result = git.rebase().setUpstream("refs/heads/master")
				.call();
		assertEquals(Status.FAST_FORWARD
		assertFalse("File should still be deleted"
		assertEquals("[file1
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithAutoStash()
			throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.OK
				git.rebase().setUpstream("refs/heads/master").call()
						.getStatus());
		checkFile(new File(db.getWorkTree()
				"unstaged modified file0");
		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals("[file0
				+ "[file1
				+ "[file2
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

	@Test
	public void testRebaseWithAutoStashAndSubdirs() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("sub/file0"
		git.add().addFilepattern("sub/file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("sub/file0"

		Set<String> modifiedFiles = new HashSet<>();
		ListenerHandle handle = db.getListenerList()
				.addWorkingTreeModifiedListener(
						event -> modifiedFiles.addAll(event.getModified()));
		try {
			assertEquals(Status.OK
					.setUpstream("refs/heads/master").call().getStatus());
		} finally {
			handle.remove();
		}
		checkFile(new File(new File(db.getWorkTree()
				"unstaged modified file0");
		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals(
				"[file1
						+ "[file2
						+ "[sub/file0
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
		List<String> modified = new ArrayList<>(modifiedFiles);
		Collections.sort(modified);
		assertEquals("[file1
	}

	@Test
	public void testRebaseWithAutoStashConflictOnApply() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit
		checkoutBranch("refs/heads/topic");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("commit2").call();

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file1"

		assertEquals(Status.STASH_APPLY_CONFLICTS
				git.rebase().setUpstream("refs/heads/master").call()
						.getStatus());
		checkFile(new File(db.getWorkTree()
		checkFile(
				new File(db.getWorkTree()
				"<<<<<<< HEAD\nmodified file1\n=======\nunstaged modified file1\n>>>>>>> stash\n");
		checkFile(new File(db.getWorkTree()
		assertEquals(
				"[file0
						+ "[file1
						+ "[file1
						+ "[file1
						+ "[file2
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE

		List<DiffEntry> diffs = getStashedDiff();
		assertEquals(1
		assertEquals(DiffEntry.ChangeType.MODIFY
		assertEquals("file1"
	}

	@Test
	public void testFastForwardRebaseWithAutoStash() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_REBASE_SECTION
				ConfigConstants.CONFIG_KEY_AUTOSTASH
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("commit0").call();
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit commit = git.commit().setMessage("commit1").call();

		createBranch(commit

		checkoutBranch("refs/heads/master");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		checkoutBranch("refs/heads/topic");
		writeTrashFile("file0"

		assertEquals(Status.FAST_FORWARD
				git.rebase().setUpstream("refs/heads/master")
				.call().getStatus());
		checkFile(new File(db.getWorkTree()
				"unstaged modified file0");
		checkFile(new File(db.getWorkTree()
		assertEquals("[file0
				+ "[file1
				indexState(CONTENT));
		assertEquals(RepositoryState.SAFE
	}

	private List<DiffEntry> getStashedDiff() throws AmbiguousObjectException
			IncorrectObjectTypeException
		ObjectId stashId = db.resolve("stash@{0}");
		RevWalk revWalk = new RevWalk(db);
		RevCommit stashCommit = revWalk.parseCommit(stashId);
		List<DiffEntry> diffs = diffWorkingAgainstHead(stashCommit
		return diffs;
	}

	private TreeWalk createTreeWalk() {
		TreeWalk walk = new TreeWalk(db);
		walk.setRecursive(true);
		walk.setFilter(TreeFilter.ANY_DIFF);
		return walk;
	}

	private List<DiffEntry> diffWorkingAgainstHead(final RevCommit commit
			RevWalk revWalk)
			throws IOException {
		RevCommit parentCommit = revWalk.parseCommit(commit.getParent(0));
		try (TreeWalk walk = createTreeWalk()) {
			walk.addTree(parentCommit.getTree());
			walk.addTree(commit.getTree());
			return DiffEntry.scan(walk);
		}
	}

	private int countPicks() throws IOException {
		int count = 0;
		File todoFile = getTodoFile();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(todoFile)
			String line = br.readLine();
			while (line != null) {
				int firstBlank = line.indexOf(' ');
				if (firstBlank != -1) {
					String actionToken = line.substring(0
					Action action = null;
					try {
						action = Action.parse(actionToken);
					} catch (Exception e) {
					}
					if (Action.PICK.equals(action))
						count++;
				}
				line = br.readLine();
			}
			return count;
		}
	}

	@Test
	public void testFastForwardWithMultipleCommitsOnDifferentBranches()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit first = git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		createBranch(first

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit second = git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		createBranch(second

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master")
				.call();

		checkoutBranch("refs/heads/side");
		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		RevCommit fourth = git.commit().setMessage("update file2 on side")
				.call();

		checkoutBranch("refs/heads/master");
		MergeResult result = git.merge().include(fourth.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

		checkoutBranch("refs/heads/topic");
		RebaseResult res = git.rebase().setUpstream("refs/heads/master").call();
		assertTrue(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals(Status.FAST_FORWARD
	}

	@Test
	public void testRebaseShouldLeaveWorkspaceUntouchedWithUnstagedChangesConflict()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		RevCommit initial = git.commit().setMessage("initial commit").call();
		createBranch(initial

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated FILE1 on master").call();

		checkoutBranch("refs/heads/side");
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated FILE1 on side").call();

		File theFile = writeTrashFile(FILE1

		RebaseResult rebaseResult = git.rebase()
					.setUpstream("refs/heads/master").call();
		assertEquals(Status.UNCOMMITTED_CHANGES
		assertEquals(1
		assertEquals(FILE1

		checkFile(theFile

		assertEquals(RepositoryState.SAFE
				.getRepositoryState());
	}

	@Test
	public void testAbortShouldAlsoAbortNonInteractiveRebaseWithRebaseApplyDir()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("initial commit").call();

		File applyDir = new File(db.getDirectory()
		File headName = new File(applyDir
		FileUtils.mkdir(applyDir);
		write(headName
		db.writeOrigHead(db.resolve(Constants.HEAD));

		git.rebase().setOperation(Operation.ABORT).call();

		assertFalse("Abort should clean up .git/rebase-apply"
				applyDir.exists());
		assertEquals(RepositoryState.SAFE
				.getRepositoryState());
	}

	@Test
	public void testRebaseShouldBeAbleToHandleEmptyLinesInRebaseTodoFile()
			throws IOException {
		String emptyLine = "\n";
		String todo = "pick 1111111 Commit 1\n" + emptyLine
				+ "pick 2222222 Commit 2\n" + emptyLine
				+ "# Comment line at end\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
	}

	@Test
	public void testRebaseShouldBeAbleToHandleLinesWithoutCommitMessageInRebaseTodoFile()
			throws IOException {
		String todo = "pick 1111111 \n" + "pick 2222222 Commit 2\n"
				+ "# Comment line at end\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
	}

	@Test
	public void testRebaseShouldNotFailIfUserAddCommentLinesInPrepareSteps()
			throws Exception {
		commitFile(FILE1
		RevCommit c2 = commitFile("file2"

		commitFile(FILE1
		RevCommit c4 = commitFile("file2"

		RebaseResult res = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {
					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.add(0
								"# Comment that should not be processed"));
					}

					@Override
					public String modifyCommitMessage(String commit) {
						fail("modifyCommitMessage() was not expected to be called");
						return commit;
					}
				}).call();

		assertEquals(RebaseResult.Status.FAST_FORWARD

		RebaseResult res2 = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {
					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.COMMENT);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						fail("modifyCommitMessage() was not expected to be called");
						return commit;
					}
				}).call();

		assertEquals(RebaseResult.Status.OK

		ObjectId headId = db.resolve(Constants.HEAD);
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit rc = rw.parseCommit(headId);

			ObjectId head1Id = db.resolve(Constants.HEAD + "~1");
			RevCommit rc1 = rw.parseCommit(head1Id);

			assertEquals(rc.getFullMessage()
			assertEquals(rc1.getFullMessage()
		}
	}

	@Test
	public void testParseRewordCommand() throws Exception {
		String todo = "pick 1111111 Commit 1\n"
				+ "reword 2222222 Commit 2\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO

		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
		assertEquals(Action.REWORD
	}

	@Test
	public void testEmptyRebaseTodo() throws Exception {
		write(getTodoFile()
		assertEquals(0
		assertEquals(0
	}

	@Test
	public void testOnlyCommentRebaseTodo() throws Exception {
		write(getTodoFile()
		assertEquals(0
		List<RebaseTodoLine> lines = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(2
		for (RebaseTodoLine line : lines)
			assertEquals(Action.COMMENT
		write(getTodoFile()
		assertEquals(0
		lines = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(2
		for (RebaseTodoLine line : lines)
			assertEquals(Action.COMMENT
		write(getTodoFile()
		assertEquals(0
		lines = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(4
		for (RebaseTodoLine line : lines)
			assertEquals(Action.COMMENT
	}

	@Test
	public void testLeadingSpacesRebaseTodo() throws Exception {
		String todo =	"  \t\t pick 1111111 Commit 1\n"
					+ "\t\n"
					+ "\treword 2222222 Commit 2\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO

		assertEquals(2
		assertEquals("1111111"
		assertEquals("2222222"
		assertEquals(Action.REWORD
	}

	@Test
	public void testRebaseShouldTryToParseValidLineMarkedAsComment()
			throws IOException {
		String todo = "# pick 1111111 Valid line commented out with space\n"
				+ "#edit 2222222 Valid line commented out without space\n"
				+ "# pick invalidLine Comment line at end\n";
		write(getTodoFile()

		List<RebaseTodoLine> steps = db.readRebaseTodo(GIT_REBASE_TODO
		assertEquals(3

		RebaseTodoLine firstLine = steps.get(0);

		assertEquals("1111111"
		assertEquals("Valid line commented out with space"
				firstLine.getShortMessage());
		assertEquals("comment"

		try {
			firstLine.setAction(Action.PICK);
			assertEquals("1111111"
			assertEquals("pick"
		} catch (Exception e) {
			fail("Valid parsable RebaseTodoLine that has been commented out should allow to change the action
		}

		assertEquals("2222222"
		assertEquals("comment"

		assertEquals(null
		assertEquals(null
		assertEquals("comment"
		assertEquals("# pick invalidLine Comment line at end"
				.getComment());
		try {
			steps.get(2).setAction(Action.PICK);
			fail("A comment RebaseTodoLine that doesn't contain a valid parsable line should fail
		} catch (Exception e) {
		}

	}

	@SuppressWarnings("unused")
	@Test
	public void testRebaseTodoLineSetComment() throws Exception {
		try {
			new RebaseTodoLine("This is a invalid comment");
			fail("Constructing a comment line with invalid comment string should fail
		} catch (IllegalArgumentException e) {
		}
		RebaseTodoLine validCommentLine = new RebaseTodoLine(
				"# This is a comment");
		assertEquals(Action.COMMENT
		assertEquals("# This is a comment"

		RebaseTodoLine actionLineToBeChanged = new RebaseTodoLine(Action.EDIT
				AbbreviatedObjectId.fromString("1111111")
		assertEquals(null

		try {
			actionLineToBeChanged.setComment("invalid comment");
			fail("Setting a invalid comment string should fail but doesn't");
		} catch (IllegalArgumentException e) {
			assertEquals(null
		}

		actionLineToBeChanged.setComment("# valid comment");
		assertEquals("# valid comment"
		try {
			actionLineToBeChanged.setComment("invalid comment");
			fail("Setting a invalid comment string should fail but doesn't");
		} catch (IllegalArgumentException e) {
			assertEquals("# valid comment"
		}
		try {
			actionLineToBeChanged.setComment("# line1 \n line2");
			actionLineToBeChanged.setComment("line1 \n line2");
			actionLineToBeChanged.setComment("\n");
			actionLineToBeChanged.setComment("# line1 \r line2");
			actionLineToBeChanged.setComment("line1 \r line2");
			actionLineToBeChanged.setComment("\r");
			actionLineToBeChanged.setComment("# line1 \n\r line2");
			actionLineToBeChanged.setComment("line1 \n\r line2");
			actionLineToBeChanged.setComment("\n\r");
			fail("Setting a multiline comment string should fail but doesn't");
		} catch (IllegalArgumentException e) {
		}
		actionLineToBeChanged.setComment("# valid comment");
		assertEquals("# valid comment"

		actionLineToBeChanged.setComment("# \t \t valid comment");
		assertEquals("# \t \t valid comment"
				actionLineToBeChanged.getComment());

		actionLineToBeChanged.setComment("#       ");
		assertEquals("#       "

		actionLineToBeChanged.setComment("");
		assertEquals(""

		actionLineToBeChanged.setComment("  ");
		assertEquals("  "

		actionLineToBeChanged.setComment("\t\t");
		assertEquals("\t\t"

		actionLineToBeChanged.setComment(null);
		assertEquals(null
	}

	@Test
	public void testRebaseInteractiveReword() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on side").call();

		RebaseResult res = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.REWORD);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return "rewritten commit message";
					}
				}).call();
		assertTrue(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("rewritten commit message"
	}

	@Test
	public void testRebaseInteractiveEdit() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on side").call();

		RebaseResult res = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {
					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.EDIT);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
					}
				}).call();
		assertEquals(Status.EDIT
		RevCommit toBeEditted = git.log().call().iterator().next();
		assertEquals("updated file1 on master"

		writeTrashFile("file1"
		git.commit().setAll(true).setAmend(true)
				.setMessage("edited commit message").call();
		res = git.rebase().setOperation(Operation.CONTINUE).call();

		checkFile(new File(db.getWorkTree()
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("edited commit message"
	}

	@Test
	public void testParseSquashFixupSequenceCount() {
		int count = RebaseCommand
				.parseSquashFixupSequenceCount("# This is a combination of 3 commits.\n# newline");
		assertEquals(3
	}

	@Test
	public void testRebaseInteractiveSingleSquashAndModifyMessage() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on master\nnew line").call();

		git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(1).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						final File messageSquashFile = new File(db
								.getDirectory()
						final File messageFixupFile = new File(db
								.getDirectory()

						assertFalse(messageFixupFile.exists());
						assertTrue(messageSquashFile.exists());
						assertEquals(
								"# This is a combination of 2 commits.\n# The first commit's message is:\nAdd file2\nnew line\n# This is the 2nd commit message:\nupdated file1 on master\nnew line"
								commit);

						try {
							byte[] messageSquashBytes = IO
									.readFully(messageSquashFile);
							int end = RawParseUtils.prevLF(messageSquashBytes
									messageSquashBytes.length);
							String messageSquashContent = RawParseUtils.decode(
									messageSquashBytes
							assertEquals(messageSquashContent
						} catch (Throwable t) {
							fail(t.getMessage());
						}

						return "changed";
					}
				}).call();

		try (RevWalk walk = new RevWalk(db)) {
			ObjectId headId = db.resolve(Constants.HEAD);
			RevCommit headCommit = walk.parseCommit(headId);
			assertEquals(headCommit.getFullMessage()
					"update file2 on master\nnew line");

			ObjectId head2Id = db.resolve(Constants.HEAD + "^1");
			RevCommit head1Commit = walk.parseCommit(head2Id);
			assertEquals("changed"
		}
	}

	@Test
	public void testRebaseInteractiveMultipleSquash() throws Exception {
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("Add file0\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on master\nnew line").call();

		git.rebase().setUpstream("HEAD~4")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(1).setAction(Action.SQUASH);
							steps.get(2).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						final File messageSquashFile = new File(db.getDirectory()
								"rebase-merge/message-squash");
						final File messageFixupFile = new File(db.getDirectory()
								"rebase-merge/message-fixup");
						assertFalse(messageFixupFile.exists());
						assertTrue(messageSquashFile.exists());
						assertEquals(
								"# This is a combination of 3 commits.\n# The first commit's message is:\nAdd file1\nnew line\n# This is the 2nd commit message:\nAdd file2\nnew line\n# This is the 3rd commit message:\nupdated file1 on master\nnew line"
								commit);

						try {
							byte[] messageSquashBytes = IO
									.readFully(messageSquashFile);
							int end = RawParseUtils.prevLF(messageSquashBytes
									messageSquashBytes.length);
							String messageSquashContend = RawParseUtils.decode(
									messageSquashBytes
							assertEquals(messageSquashContend
						} catch (Throwable t) {
							fail(t.getMessage());
						}

						return "# This is a combination of 3 commits.\n# The first commit's message is:\nAdd file1\nnew line\n# This is the 2nd commit message:\nAdd file2\nnew line\n# This is the 3rd commit message:\nupdated file1 on master\nnew line";
					}
				}).call();

		try (RevWalk walk = new RevWalk(db)) {
			ObjectId headId = db.resolve(Constants.HEAD);
			RevCommit headCommit = walk.parseCommit(headId);
			assertEquals(headCommit.getFullMessage()
					"update file2 on master\nnew line");

			ObjectId head2Id = db.resolve(Constants.HEAD + "^1");
			RevCommit head1Commit = walk.parseCommit(head2Id);
			assertEquals(
					"Add file1\nnew line\nAdd file2\nnew line\nupdated file1 on master\nnew line"
					head1Commit.getFullMessage());
		}
	}

	@Test
	public void testRebaseInteractiveMixedSquashAndFixup() throws Exception {
		writeTrashFile("file0"
		git.add().addFilepattern("file0").call();
		git.commit().setMessage("Add file0\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on master\nnew line").call();

		git.rebase().setUpstream("HEAD~4")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(1).setAction(Action.FIXUP);
							steps.get(2).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						final File messageSquashFile = new File(db
								.getDirectory()
						final File messageFixupFile = new File(db
								.getDirectory()

						assertFalse(messageFixupFile.exists());
						assertTrue(messageSquashFile.exists());
						assertEquals(
								"# This is a combination of 3 commits.\n# The first commit's message is:\nAdd file1\nnew line\n# The 2nd commit message will be skipped:\n# Add file2\n# new line\n# This is the 3rd commit message:\nupdated file1 on master\nnew line"
								commit);

						try {
							byte[] messageSquashBytes = IO
									.readFully(messageSquashFile);
							int end = RawParseUtils.prevLF(messageSquashBytes
									messageSquashBytes.length);
							String messageSquashContend = RawParseUtils.decode(
									messageSquashBytes
							assertEquals(messageSquashContend
						} catch (Throwable t) {
							fail(t.getMessage());
						}

						return "changed";
					}
				}).call();

		try (RevWalk walk = new RevWalk(db)) {
			ObjectId headId = db.resolve(Constants.HEAD);
			RevCommit headCommit = walk.parseCommit(headId);
			assertEquals(headCommit.getFullMessage()
					"update file2 on master\nnew line");

			ObjectId head2Id = db.resolve(Constants.HEAD + "^1");
			RevCommit head1Commit = walk.parseCommit(head2Id);
			assertEquals("changed"
		}
	}

	@Test
	public void testRebaseInteractiveSingleFixup() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\nnew line").call();

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("update file2 on master\nnew line").call();

		git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(1).setAction(Action.FIXUP);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						fail("No callback to modify commit message expected for single fixup");
						return commit;
					}
				}).call();

		try (RevWalk walk = new RevWalk(db)) {
			ObjectId headId = db.resolve(Constants.HEAD);
			RevCommit headCommit = walk.parseCommit(headId);
			assertEquals("update file2 on master\nnew line"
					headCommit.getFullMessage());

			ObjectId head1Id = db.resolve(Constants.HEAD + "^1");
			RevCommit head1Commit = walk.parseCommit(head1Id);
			assertEquals("Add file2\nnew line"
					head1Commit.getFullMessage());
		}
	}

	@Test
	public void testRebaseInteractiveFixupWithBlankLines() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("updated file1 on master\n\nsome text").call();

		git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(1).setAction(Action.FIXUP);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						fail("No callback to modify commit message expected for single fixup");
						return commit;
					}
				}).call();

		try (RevWalk walk = new RevWalk(db)) {
			ObjectId headId = db.resolve(Constants.HEAD);
			RevCommit headCommit = walk.parseCommit(headId);
			assertEquals("Add file2"
					headCommit.getFullMessage());
		}
	}

	@Test(expected = InvalidRebaseStepException.class)
	public void testRebaseInteractiveFixupFirstCommitShouldFail()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		git.rebase().setUpstream("HEAD~1")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.FIXUP);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
	}

	@Test(expected = InvalidRebaseStepException.class)
	public void testRebaseInteractiveSquashFirstCommitShouldFail()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		git.rebase().setUpstream("HEAD~1")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
	}

	@Test
	public void testRebaseEndsIfLastStepIsEdit() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Add file2\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		git.rebase().setUpstream("HEAD~1")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.EDIT);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		git.commit().setAmend(true)
				.setMessage("Add file2\nnew line\nanother line").call();
		RebaseResult result = git.rebase().setOperation(Operation.CONTINUE)
				.call();
		assertEquals(Status.OK

	}

	@Test
	public void testRebaseShouldStopForEditInCaseOfConflict()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		RebaseResult result = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.remove(0);
						try {
							steps.get(0).setAction(Action.EDIT);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().setOperation(Operation.CONTINUE).call();
		assertEquals(Status.EDIT

	}

	@Test
	public void testRebaseShouldStopForRewordInCaseOfConflict()
			throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		RebaseResult result = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						steps.remove(0);
						try {
							steps.get(0).setAction(Action.REWORD);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return "rewritten commit message";
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().runInteractively(new InteractiveHandler() {

			@Override
			public void prepareSteps(List<RebaseTodoLine> steps) {
				steps.remove(0);
				try {
					steps.get(0).setAction(Action.REWORD);
				} catch (IllegalTodoFileModification e) {
					fail("unexpected exception: " + e);
				}
			}

			@Override
			public String modifyCommitMessage(String commit) {
				return "rewritten commit message";
			}
		}).setOperation(Operation.CONTINUE).call();
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("rewritten commit message"

	}

	@Test
	public void testRebaseShouldSquashInCaseOfConflict() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1\nnew line").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Change file2").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		RebaseResult result = git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.PICK);
							steps.remove(1);
							steps.get(1).setAction(Action.SQUASH);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return "squashed message";
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().runInteractively(new InteractiveHandler() {

			@Override
			public void prepareSteps(List<RebaseTodoLine> steps) {
				try {
					steps.get(0).setAction(Action.PICK);
					steps.remove(1);
					steps.get(1).setAction(Action.SQUASH);
				} catch (IllegalTodoFileModification e) {
					fail("unexpected exception: " + e);
				}
			}

			@Override
			public String modifyCommitMessage(String commit) {
				return "squashed message";
			}
		}).setOperation(Operation.CONTINUE).call();
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMag = logIterator.next().getShortMessage();
		assertEquals("squashed message"
	}

	@Test
	public void testRebaseShouldFixupInCaseOfConflict() throws Exception {
		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Add file1").call();
		assertTrue(new File(db.getWorkTree()

		writeTrashFile("file2"
		git.add().addFilepattern("file2").call();
		git.commit().setMessage("Change file2").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("Change file1").call();

		writeTrashFile(FILE1
		writeTrashFile("file3"
		git.add().addFilepattern(FILE1).call();
		git.add().addFilepattern("file3").call();
		git.commit().setMessage("Change file1

		RebaseResult result = git.rebase().setUpstream("HEAD~3")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.PICK);
							steps.remove(1);
							steps.get(1).setAction(Action.FIXUP);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		assertEquals(Status.STOPPED
		git.add().addFilepattern(FILE1).call();
		result = git.rebase().runInteractively(new InteractiveHandler() {

			@Override
			public void prepareSteps(List<RebaseTodoLine> steps) {
				try {
					steps.get(0).setAction(Action.PICK);
					steps.remove(1);
					steps.get(1).setAction(Action.FIXUP);
				} catch (IllegalTodoFileModification e) {
					fail("unexpected exception: " + e);
				}
			}

			@Override
			public String modifyCommitMessage(String commit) {
				return "commit";
			}
		}).setOperation(Operation.CONTINUE).call();
		assertEquals(Status.OK
		Iterator<RevCommit> logIterator = git.log().all().call().iterator();
		String actualCommitMsg = logIterator.next().getShortMessage();
		assertEquals("Change file2"
		actualCommitMsg = logIterator.next().getShortMessage();
		assertEquals("Add file1"
		assertTrue(new File(db.getWorkTree()

	}

	@Test
	public void testInteractiveRebaseWithModificationShouldNotDeleteDataOnAbort()
			throws Exception {
		writeTrashFile("file0"
		writeTrashFile(FILE1
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		git.commit().setMessage("commit1").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit2").call();

		writeTrashFile(FILE1
		git.add().addFilepattern(FILE1).call();
		git.commit().setMessage("commit3").call();

		writeTrashFile("file0"
		git.add().addFilepattern("file0").addFilepattern(FILE1).call();
		writeTrashFile("file0"

		RebaseResult result = git.rebase().setUpstream("HEAD~2")
				.runInteractively(new InteractiveHandler() {

					@Override
					public void prepareSteps(List<RebaseTodoLine> steps) {
						try {
							steps.get(0).setAction(Action.EDIT);
							steps.get(1).setAction(Action.PICK);
						} catch (IllegalTodoFileModification e) {
							fail("unexpected exception: " + e);
						}
					}

					@Override
					public String modifyCommitMessage(String commit) {
						return commit;
					}
				}).call();
		if (result.getStatus() == Status.EDIT)
			git.rebase().setOperation(Operation.ABORT).call();

		checkFile(new File(db.getWorkTree()
		checkFile(new File(db.getWorkTree()
				"modified file1 a second time");
		assertEquals("[file0
				+ "[file1
				indexState(CONTENT));

	}

	private File getTodoFile() {
		File todoFile = new File(db.getDirectory()
		return todoFile;
	}
}
