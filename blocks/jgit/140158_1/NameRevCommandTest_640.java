package org.eclipse.jgit.api;

import static org.eclipse.jgit.lib.Constants.MASTER;
import static org.eclipse.jgit.lib.Constants.R_HEADS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.util.Iterator;
import java.util.regex.Pattern;

import org.eclipse.jgit.api.MergeCommand.FastForwardMode;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.InvalidMergeHeadsException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRepository.BranchBuilder;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.Sets;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.merge.ResolveMerger.MergeFailureReason;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FileUtils;
import org.eclipse.jgit.util.GitDateFormatter;
import org.eclipse.jgit.util.GitDateFormatter.Format;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class MergeCommandTest extends RepositoryTestCase {

	public static @DataPoints
	MergeStrategy[] mergeStrategies = MergeStrategy.get();

	private GitDateFormatter dateFormatter;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dateFormatter = new GitDateFormatter(Format.DEFAULT);
	}

	@Test
	public void testMergeInItself() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			MergeResult result = git.merge().include(db.exactRef(Constants.HEAD)).call();
			assertEquals(MergeResult.MergeStatus.ALREADY_UP_TO_DATE
		}
		assertEquals("commit (initial): initial commit"
				db
				.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("commit (initial): initial commit"
				db
				.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}

	@Test
	public void testAlreadyUpToDate() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit first = git.commit().setMessage("initial commit").call();
			createBranch(first

			RevCommit second = git.commit().setMessage("second commit").call();
			MergeResult result = git.merge().include(db.exactRef("refs/heads/branch1")).call();
			assertEquals(MergeResult.MergeStatus.ALREADY_UP_TO_DATE
			assertEquals(second
		}
		assertEquals("commit: second commit"
				.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("commit: second commit"
				.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}

	@Test
	public void testFastForward() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit first = git.commit().setMessage("initial commit").call();
			createBranch(first

			RevCommit second = git.commit().setMessage("second commit").call();

			checkoutBranch("refs/heads/branch1");

			MergeResult result = git.merge().include(db.exactRef(R_HEADS + MASTER)).call();

			assertEquals(MergeResult.MergeStatus.FAST_FORWARD
			assertEquals(second
		}
		assertEquals("merge refs/heads/master: Fast-forward"
				db.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("merge refs/heads/master: Fast-forward"
				db.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}

	@Test
	public void testFastForwardNoCommit() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit first = git.commit().setMessage("initial commit").call();
			createBranch(first

			RevCommit second = git.commit().setMessage("second commit").call();

			checkoutBranch("refs/heads/branch1");

			MergeResult result = git.merge().include(db.exactRef(R_HEADS + MASTER))
					.setCommit(false).call();

			assertEquals(MergeResult.MergeStatus.FAST_FORWARD
					result.getMergeStatus());
			assertEquals(second
		}
		assertEquals("merge refs/heads/master: Fast-forward"
				.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("merge refs/heads/master: Fast-forward"
				.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}

	@Test
	public void testFastForwardWithFiles() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit first = git.commit().setMessage("initial commit").call();

			assertTrue(new File(db.getWorkTree()
			createBranch(first

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit second = git.commit().setMessage("second commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/branch1");
			assertFalse(new File(db.getWorkTree()

			MergeResult result = git.merge().include(db.exactRef(R_HEADS + MASTER)).call();

			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertEquals(MergeResult.MergeStatus.FAST_FORWARD
			assertEquals(second
		}
		assertEquals("merge refs/heads/master: Fast-forward"
				db.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals("merge refs/heads/master: Fast-forward"
				db.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}

	@Test
	public void testMultipleHeads() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit first = git.commit().setMessage("initial commit").call();
			createBranch(first

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit second = git.commit().setMessage("second commit").call();

			writeTrashFile("file3"
			git.add().addFilepattern("file3").call();
			git.commit().setMessage("third commit").call();

			checkoutBranch("refs/heads/branch1");
			assertFalse(new File(db.getWorkTree()
			assertFalse(new File(db.getWorkTree()

			MergeCommand merge = git.merge();
			merge.include(second.getId());
			merge.include(db.exactRef(R_HEADS + MASTER));
			try {
				merge.call();
				fail("Expected exception not thrown when merging multiple heads");
			} catch (InvalidMergeHeadsException e) {
			}
		}
	}

	@Theory
	public void testMergeSuccessAllStrategies(MergeStrategy mergeStrategy)
			throws Exception {
		try (Git git = new Git(db)) {
			RevCommit first = git.commit().setMessage("first").call();
			createBranch(first

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("second").call();

			checkoutBranch("refs/heads/side");
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			git.commit().setMessage("third").call();

			MergeResult result = git.merge().setStrategy(mergeStrategy)
					.include(db.exactRef(R_HEADS + MASTER)).call();
			assertEquals(MergeStatus.MERGED
		}
		assertEquals(
				"merge refs/heads/master: Merge made by "
						+ mergeStrategy.getName() + "."
				db.getReflogReader(Constants.HEAD).getLastEntry().getComment());
		assertEquals(
				"merge refs/heads/master: Merge made by "
						+ mergeStrategy.getName() + "."
				db.getReflogReader(db.getBranch()).getLastEntry().getComment());
	}

	@Theory
	public void testMergeSuccessAllStrategiesNoCommit(
			MergeStrategy mergeStrategy) throws Exception {
		try (Git git = new Git(db)) {
			RevCommit first = git.commit().setMessage("first").call();
			createBranch(first

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("second").call();

			checkoutBranch("refs/heads/side");
			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit thirdCommit = git.commit().setMessage("third").call();

			MergeResult result = git.merge().setStrategy(mergeStrategy)
					.setCommit(false)
					.include(db.exactRef(R_HEADS + MASTER)).call();
			assertEquals(MergeStatus.MERGED_NOT_COMMITTED
			assertEquals(db.exactRef(Constants.HEAD).getTarget().getObjectId()
					thirdCommit.getId());
		}
	}

	@Test
	public void testContentMerge() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n"
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING

			assertEquals(
					"1\n<<<<<<< HEAD\na(main)\n=======\na(side)\n>>>>>>> 86503e7e397465588cc267b65d778538bffccb83\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()

			assertEquals(1
			assertEquals(3

			assertEquals(RepositoryState.MERGING
		}
	}

	@Test
	public void testMergeTag() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("b"
			git.add().addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();
			Ref tag = git.tag().setAnnotated(true).setMessage("my tag 01")
					.setName("tag01").setObjectId(secondCommit).call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(tag).setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED
		}
	}

	@Test
	public void testMergeMessage() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			Ref sideBranch = db.exactRef("refs/heads/side");

			git.merge().include(sideBranch)
					.setStrategy(MergeStrategy.RESOLVE).call();

			assertEquals("Merge branch 'side'\n\nConflicts:\n\ta\n"
					db.readMergeCommitMsg());
		}

	}

	@Test
	public void testMergeNonVersionedPaths() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n"
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			git.commit().setMessage("main").call();

			writeTrashFile("d"
			assertTrue(new File(db.getWorkTree()

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING

			assertEquals(
					"1\n<<<<<<< HEAD\na(main)\n=======\na(side)\n>>>>>>> 86503e7e397465588cc267b65d778538bffccb83\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nd\n3\n"
			File dir = new File(db.getWorkTree()
			assertTrue(dir.isDirectory());

			assertEquals(1
			assertEquals(3

			assertEquals(RepositoryState.MERGING
		}
	}

	@Test
	public void testMultipleCreations() throws Exception {
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
	}

	@Test
	public void testMultipleCreationsSameContent() throws Exception {
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
			assertEquals(MergeStatus.MERGED
			assertEquals("1\nb(1)\n3\n"
			assertEquals("merge " + secondCommit.getId().getName()
					+ ": Merge made by resolve."
					.getReflogReader(Constants.HEAD)
					.getLastEntry().getComment());
			assertEquals("merge " + secondCommit.getId().getName()
					+ ": Merge made by resolve."
					.getReflogReader(db.getBranch())
					.getLastEntry().getComment());
		}
	}

	@Test
	public void testSuccessfulContentMerge() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n"
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			RevCommit thirdCommit = git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED

			assertEquals("1(side)\na\n3(main)\n"
					"a")));
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					"c/c/c")));

			assertEquals(null

			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			assertEquals(newHead
			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit
			assertEquals(
					"Merge commit '3fa334456d236a92db020289fe0bf481d91777b4'"
					newHead.getFullMessage());
			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testSuccessfulContentMergeNoCommit() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n"
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			RevCommit thirdCommit = git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setCommit(false)
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED_NOT_COMMITTED
			assertEquals(db.exactRef(Constants.HEAD).getTarget().getObjectId()
					thirdCommit.getId());

			assertEquals("1(side)\na\n3(main)\n"
					"a")));
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()

			assertEquals(null

			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit
			assertNull(result.getNewHead());
			assertEquals(RepositoryState.MERGING_RESOLVED
		}
	}

	@Test
	public void testSuccessfulContentMergeAndDirtyworkingTree()
			throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("d"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").addFilepattern("d").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertEquals("1\nb(side)\n3\n"
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			RevCommit thirdCommit = git.commit().setMessage("main").call();

			writeTrashFile("d"
			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED

			assertEquals("1(side)\na\n3(main)\n"
					"a")));
			assertEquals("1\nb(side)\n3\n"
			assertEquals("1\nc(main)\n3\n"
					"c/c/c")));
			assertEquals("--- dirty ---"

			assertEquals(null

			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			assertEquals(newHead
			assertEquals(2
			assertEquals(thirdCommit
			assertEquals(secondCommit
			assertEquals(
					"Merge commit '064d54d98a4cdb0fed1802a21c656bfda67fe879'"
					newHead.getFullMessage());

			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testSingleDeletion() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("d"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").addFilepattern("d").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			assertTrue(new File(db.getWorkTree()
			git.add().addFilepattern("b").setUpdate(true).call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertFalse(new File(db.getWorkTree()
			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			RevCommit thirdCommit = git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED

			assertEquals("1\na\n3(main)\n"
			assertFalse(new File(db.getWorkTree()
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nd\n3\n"

			checkoutBranch("refs/heads/side");
			assertFalse(new File(db.getWorkTree()

			result = git.merge().include(thirdCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED

			assertEquals("1\na\n3(main)\n"
			assertFalse(new File(db.getWorkTree()
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nd\n3\n"
		}
	}

	@Test
	public void testMultipleDeletions() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			assertTrue(new File(db.getWorkTree()
			git.add().addFilepattern("a").setUpdate(true).call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertFalse(new File(db.getWorkTree()
			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()

			assertTrue(new File(db.getWorkTree()
			git.add().addFilepattern("a").setUpdate(true).call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.MERGED
		}
	}

	@Test
	public void testDeletionAndConflict() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			writeTrashFile("d"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("b")
					.addFilepattern("c/c/c").addFilepattern("d").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			assertTrue(new File(db.getWorkTree()
			writeTrashFile("a"
			git.add().addFilepattern("b").setUpdate(true).call();
			git.add().addFilepattern("a").setUpdate(true).call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			assertFalse(new File(db.getWorkTree()
			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()

			writeTrashFile("a"
			writeTrashFile("c/c/c"
			git.add().addFilepattern("a").addFilepattern("c/c/c").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING

			assertEquals(
					"1\na\n<<<<<<< HEAD\n3(main)\n=======\n3(side)\n>>>>>>> 54ffed45d62d252715fc20e41da92d44c48fb0ff\n"
					read(new File(db.getWorkTree()
			assertFalse(new File(db.getWorkTree()
			assertEquals("1\nc(main)\n3\n"
					read(new File(db.getWorkTree()
			assertEquals("1\nd\n3\n"
		}
	}

	@Test
	public void testDeletionOnMasterConflict() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");
			git.rm().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING

			assertTrue(new File(db.getWorkTree()
			assertEquals("1\na(side)\n3\n"
			assertEquals("1\nb\n3\n"
		}
	}

	@Test
	public void testDeletionOnSideConflict() throws Exception {
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

			assertTrue(new File(db.getWorkTree()
			assertEquals("1\na(main)\n3\n"
			assertEquals("1\nb\n3\n"

			assertEquals(1
			assertEquals(3
		}
	}

	@Test
	public void testModifiedAndRenamed() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("x"
			git.add().addFilepattern("x").call();
			RevCommit initial = git.commit().setMessage("add x").call();

			createBranch(initial
			createBranch(initial

			checkoutBranch("refs/heads/d1");
			new File(db.getWorkTree()
					.renameTo(new File(db.getWorkTree()
			git.rm().addFilepattern("x").call();
			git.add().addFilepattern("y").call();
			RevCommit d1Commit = git.commit().setMessage("d1 rename x -> y").call();

			checkoutBranch("refs/heads/d2");
			writeTrashFile("x"
			git.add().addFilepattern("x").call();
			RevCommit d2Commit = git.commit().setMessage("d2 change in x").call();

			checkoutBranch("refs/heads/master");
			MergeResult d1Merge = git.merge().include(d1Commit).call();
			assertEquals(MergeResult.MergeStatus.FAST_FORWARD
					d1Merge.getMergeStatus());

			MergeResult d2Merge = git.merge().include(d2Commit).call();
			assertEquals(MergeResult.MergeStatus.CONFLICTING
					d2Merge.getMergeStatus());
			assertEquals(1
			assertEquals(3
		}
	}

	@Test
	public void testMergeFailingWithDirtyWorkingTree() throws Exception {
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

			assertEquals("1\nb(side)\n3\n"
			checkoutBranch("refs/heads/master");
			assertEquals("1\nb\n3\n"

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			writeTrashFile("a"
			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			assertEquals(MergeStatus.FAILED

			assertEquals("--- dirty ---"
			assertEquals("1\nb\n3\n"

			assertEquals(null

			assertEquals(RepositoryState.SAFE
		}
	}

	@Test
	public void testMergeConflictFileFolder() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			writeTrashFile("b"
			git.add().addFilepattern("a").addFilepattern("b").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("c/c/c"
			writeTrashFile("d"
			git.add().addFilepattern("c/c/c").addFilepattern("d").call();
			RevCommit secondCommit = git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("c"
			writeTrashFile("d/d/d"
			git.add().addFilepattern("c").addFilepattern("d/d/d").call();
			git.commit().setMessage("main").call();

			MergeResult result = git.merge().include(secondCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			assertEquals(MergeStatus.CONFLICTING

			assertEquals("1\na\n3\n"
			assertEquals("1\nb\n3\n"
			assertEquals("1\nc(main)\n3\n"
			assertEquals("1\nd(main)\n3\n"

			assertEquals(null

			assertEquals(RepositoryState.MERGING
		}
	}

	@Test
	public void testSuccessfulMergeFailsDueToDirtyIndex() throws Exception {
		try (Git git = new Git(db)) {
			File fileA = writeTrashFile("a"
			RevCommit initialCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			write(fileA
			writeTrashFile("b"
			RevCommit sideCommit = addAllAndCommit(git);

			checkoutBranch("refs/heads/master");
			writeTrashFile("c"
			addAllAndCommit(git);

			write(fileA
			git.add().addFilepattern("a").call();

			String indexState = indexState(CONTENT);

			MergeResult result = git.merge().include(sideCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			checkMergeFailedResult(result
					indexState
		}
	}

	@Test
	public void testConflictingMergeFailsDueToDirtyIndex() throws Exception {
		try (Git git = new Git(db)) {
			File fileA = writeTrashFile("a"
			RevCommit initialCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			write(fileA
			writeTrashFile("b"
			RevCommit sideCommit = addAllAndCommit(git);

			checkoutBranch("refs/heads/master");
			write(fileA
			writeTrashFile("c"
			addAllAndCommit(git);

			write(fileA
			git.add().addFilepattern("a").call();

			String indexState = indexState(CONTENT);

			MergeResult result = git.merge().include(sideCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			checkMergeFailedResult(result
					indexState
		}
	}

	@Test
	public void testSuccessfulMergeFailsDueToDirtyWorktree() throws Exception {
		try (Git git = new Git(db)) {
			File fileA = writeTrashFile("a"
			RevCommit initialCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			write(fileA
			writeTrashFile("b"
			RevCommit sideCommit = addAllAndCommit(git);

			checkoutBranch("refs/heads/master");
			writeTrashFile("c"
			addAllAndCommit(git);

			write(fileA

			String indexState = indexState(CONTENT);

			MergeResult result = git.merge().include(sideCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			checkMergeFailedResult(result
					indexState
		}
	}

	@Test
	public void testConflictingMergeFailsDueToDirtyWorktree() throws Exception {
		try (Git git = new Git(db)) {
			File fileA = writeTrashFile("a"
			RevCommit initialCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			write(fileA
			writeTrashFile("b"
			RevCommit sideCommit = addAllAndCommit(git);

			checkoutBranch("refs/heads/master");
			write(fileA
			writeTrashFile("c"
			addAllAndCommit(git);

			write(fileA

			String indexState = indexState(CONTENT);

			MergeResult result = git.merge().include(sideCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			checkMergeFailedResult(result
					indexState
		}
	}

	@Test
	public void testMergeRemovingFolders() throws Exception {
		File folder1 = new File(db.getWorkTree()
		File folder2 = new File(db.getWorkTree()
		FileUtils.mkdir(folder1);
		FileUtils.mkdir(folder2);
		File file = new File(folder1
		write(file
		file = new File(folder1
		write(file
		file = new File(folder2
		write(file
		file = new File(folder2
		write(file

		try (Git git = new Git(db)) {
			git.add().addFilepattern(folder1.getName())
					.addFilepattern(folder2.getName()).call();
			RevCommit commit1 = git.commit().setMessage("adding folders").call();

			recursiveDelete(folder1);
			recursiveDelete(folder2);
			git.rm().addFilepattern("folder1/file1.txt")
					.addFilepattern("folder1/file2.txt")
					.addFilepattern("folder2/file1.txt")
					.addFilepattern("folder2/file2.txt").call();
			RevCommit commit2 = git.commit()
					.setMessage("removing folders on 'branch'").call();

			git.checkout().setName(commit1.name()).call();

			MergeResult result = git.merge().include(commit2.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeResult.MergeStatus.FAST_FORWARD
					result.getMergeStatus());
			assertEquals(commit2
			assertFalse(folder1.exists());
			assertFalse(folder2.exists());
		}
	}

	@Test
	public void testMergeRemovingFoldersWithoutFastForward() throws Exception {
		File folder1 = new File(db.getWorkTree()
		File folder2 = new File(db.getWorkTree()
		FileUtils.mkdir(folder1);
		FileUtils.mkdir(folder2);
		File file = new File(folder1
		write(file
		file = new File(folder1
		write(file
		file = new File(folder2
		write(file
		file = new File(folder2
		write(file

		try (Git git = new Git(db)) {
			git.add().addFilepattern(folder1.getName())
					.addFilepattern(folder2.getName()).call();
			RevCommit base = git.commit().setMessage("adding folders").call();

			recursiveDelete(folder1);
			recursiveDelete(folder2);
			git.rm().addFilepattern("folder1/file1.txt")
					.addFilepattern("folder1/file2.txt")
					.addFilepattern("folder2/file1.txt")
					.addFilepattern("folder2/file2.txt").call();
			RevCommit other = git.commit()
					.setMessage("removing folders on 'branch'").call();

			git.checkout().setName(base.name()).call();

			file = new File(folder2
			write(file

			git.add().addFilepattern(folder2.getName()).call();
			git.commit().setMessage("adding another file").call();

			MergeResult result = git.merge().include(other.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();

			assertEquals(MergeResult.MergeStatus.MERGED
					result.getMergeStatus());
			assertFalse(folder1.exists());
		}
	}

	@Test
	public void testFileModeMerge() throws Exception {
		assumeTrue(FS.DETECTED.supportsExecute());
		try (Git git = new Git(db)) {
			writeTrashFile("mergeableMode"
			setExecutable(git
			writeTrashFile("conflictingModeWithBase"
			setExecutable(git
			RevCommit initialCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			setExecutable(git
			writeTrashFile("conflictingModeNoBase"
			setExecutable(git
			RevCommit sideCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side2");
			setExecutable(git
			assertFalse(new File(git.getRepository().getWorkTree()
					"conflictingModeNoBase").exists());
			writeTrashFile("conflictingModeNoBase"
			setExecutable(git
			addAllAndCommit(git);

			MergeResult result = git.merge().include(sideCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.CONFLICTING
			assertTrue(canExecute(git
			assertFalse(canExecute(git
		}
	}

	@Test
	public void testFileModeMergeWithDirtyWorkTree() throws Exception {
		assumeTrue(FS.DETECTED.supportsExecute());

		try (Git git = new Git(db)) {
			writeTrashFile("mergeableButDirty"
			setExecutable(git
			RevCommit initialCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");
			setExecutable(git
			RevCommit sideCommit = addAllAndCommit(git);

			createBranch(initialCommit
			checkoutBranch("refs/heads/side2");
			setExecutable(git
			addAllAndCommit(git);

			writeTrashFile("mergeableButDirty"

			MergeResult result = git.merge().include(sideCommit.getId())
					.setStrategy(MergeStrategy.RESOLVE).call();
			assertEquals(MergeStatus.FAILED
			assertFalse(canExecute(git
		}
	}

	@Test
	public void testSquashFastForward() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit first = git.commit().setMessage("initial commit").call();

			assertTrue(new File(db.getWorkTree()
			createBranch(first
			checkoutBranch("refs/heads/branch1");

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit second = git.commit().setMessage("second commit").call();
			assertTrue(new File(db.getWorkTree()

			writeTrashFile("file3"
			git.add().addFilepattern("file3").call();
			RevCommit third = git.commit().setMessage("third commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()
			assertFalse(new File(db.getWorkTree()
			assertFalse(new File(db.getWorkTree()

			MergeResult result = git.merge()
					.include(db.exactRef("refs/heads/branch1"))
					.setSquash(true)
					.call();

			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertEquals(MergeResult.MergeStatus.FAST_FORWARD_SQUASHED
					result.getMergeStatus());
			assertEquals(first
			assertEquals(first

			assertEquals(
					"Squashed commit of the following:\n\ncommit "
							+ third.getName()
							+ "\nAuthor: "
							+ third.getAuthorIdent().getName()
							+ " <"
							+ third.getAuthorIdent().getEmailAddress()
							+ ">\nDate:   "
							+ dateFormatter.formatDate(third
									.getAuthorIdent())
							+ "\n\n\tthird commit\n\ncommit "
							+ second.getName()
							+ "\nAuthor: "
							+ second.getAuthorIdent().getName()
							+ " <"
							+ second.getAuthorIdent().getEmailAddress()
							+ ">\nDate:   "
							+ dateFormatter.formatDate(second
									.getAuthorIdent()) + "\n\n\tsecond commit\n"
					db.readSquashCommitMsg());
			assertNull(db.readMergeCommitMsg());

			Status stat = git.status().call();
			assertEquals(Sets.of("file2"
		}
	}

	@Test
	public void testSquashMerge() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit first = git.commit().setMessage("initial commit").call();

			assertTrue(new File(db.getWorkTree()
			createBranch(first

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit second = git.commit().setMessage("second commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/branch1");

			writeTrashFile("file3"
			git.add().addFilepattern("file3").call();
			RevCommit third = git.commit().setMessage("third commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertFalse(new File(db.getWorkTree()

			MergeResult result = git.merge()
					.include(db.exactRef("refs/heads/branch1"))
					.setSquash(true)
					.call();

			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertEquals(MergeResult.MergeStatus.MERGED_SQUASHED
					result.getMergeStatus());
			assertEquals(second
			assertEquals(second

			assertEquals(
					"Squashed commit of the following:\n\ncommit "
							+ third.getName()
							+ "\nAuthor: "
							+ third.getAuthorIdent().getName()
							+ " <"
							+ third.getAuthorIdent().getEmailAddress()
							+ ">\nDate:   "
							+ dateFormatter.formatDate(third
									.getAuthorIdent()) + "\n\n\tthird commit\n"
					db.readSquashCommitMsg());
			assertNull(db.readMergeCommitMsg());

			Status stat = git.status().call();
			assertEquals(Sets.of("file3")
		}
	}

	@Test
	public void testSquashMergeConflict() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file1"
			git.add().addFilepattern("file1").call();
			RevCommit first = git.commit().setMessage("initial commit").call();

			assertTrue(new File(db.getWorkTree()
			createBranch(first

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit second = git.commit().setMessage("second commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/branch1");

			writeTrashFile("file2"
			git.add().addFilepattern("file2").call();
			RevCommit third = git.commit().setMessage("third commit").call();
			assertTrue(new File(db.getWorkTree()

			checkoutBranch("refs/heads/master");
			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()

			MergeResult result = git.merge()
					.include(db.exactRef("refs/heads/branch1"))
					.setSquash(true)
					.call();

			assertTrue(new File(db.getWorkTree()
			assertTrue(new File(db.getWorkTree()
			assertEquals(MergeResult.MergeStatus.CONFLICTING
					result.getMergeStatus());
			assertNull(result.getNewHead());
			assertEquals(second

			assertEquals(
					"Squashed commit of the following:\n\ncommit "
							+ third.getName()
							+ "\nAuthor: "
							+ third.getAuthorIdent().getName()
							+ " <"
							+ third.getAuthorIdent().getEmailAddress()
							+ ">\nDate:   "
							+ dateFormatter.formatDate(third
									.getAuthorIdent()) + "\n\n\tthird commit\n"
					db.readSquashCommitMsg());
			assertEquals("\nConflicts:\n\tfile2\n"

			Status stat = git.status().call();
			assertEquals(Sets.of("file2")
		}
	}

	@Test
	public void testFastForwardOnly() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit initialCommit = git.commit().setMessage("initial commit")
					.call();
			createBranch(initialCommit
			git.commit().setMessage("second commit").call();
			checkoutBranch("refs/heads/branch1");

			MergeCommand merge = git.merge();
			merge.setFastForward(FastForwardMode.FF_ONLY);
			merge.include(db.exactRef(R_HEADS + MASTER));
			MergeResult result = merge.call();

			assertEquals(MergeStatus.FAST_FORWARD
		}
	}

	@Test
	public void testNoFastForward() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit initialCommit = git.commit().setMessage("initial commit")
					.call();
			createBranch(initialCommit
			git.commit().setMessage("second commit").call();
			checkoutBranch("refs/heads/branch1");

			MergeCommand merge = git.merge();
			merge.setFastForward(FastForwardMode.NO_FF);
			merge.include(db.exactRef(R_HEADS + MASTER));
			MergeResult result = merge.call();

			assertEquals(MergeStatus.MERGED
		}
	}

	@Test
	public void testNoFastForwardNoCommit() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit initialCommit = git.commit().setMessage("initial commit")
					.call();
			createBranch(initialCommit
			RevCommit secondCommit = git.commit().setMessage("second commit")
					.call();
			checkoutBranch("refs/heads/branch1");

			MergeCommand merge = git.merge();
			merge.setFastForward(FastForwardMode.NO_FF);
			merge.include(db.exactRef(R_HEADS + MASTER));
			merge.setCommit(false);
			MergeResult result = merge.call();

			assertEquals(MergeStatus.MERGED_NOT_COMMITTED
			assertEquals(2
			assertEquals(initialCommit
			assertEquals(secondCommit
			assertNull(result.getNewHead());
			assertEquals(RepositoryState.MERGING_RESOLVED
		}
	}

	@Test
	public void testFastForwardOnlyNotPossible() throws Exception {
		try (Git git = new Git(db)) {
			RevCommit initialCommit = git.commit().setMessage("initial commit")
					.call();
			createBranch(initialCommit
			git.commit().setMessage("second commit").call();
			checkoutBranch("refs/heads/branch1");
			writeTrashFile("file1"
			git.add().addFilepattern("file").call();
			git.commit().setMessage("second commit on branch1").call();
			MergeCommand merge = git.merge();
			merge.setFastForward(FastForwardMode.FF_ONLY);
			merge.include(db.exactRef(R_HEADS + MASTER));
			MergeResult result = merge.call();

			assertEquals(MergeStatus.ABORTED
		}
	}

	@Test
	public void testRecursiveMergeWithConflict() throws Exception {
		try (TestRepository<Repository> db_t = new TestRepository<>(db)) {
			BranchBuilder master = db_t.branch("master");
			RevCommit m0 = master.commit()
					.add("f"
					.create();
			RevCommit m1 = master.commit()
					.add("f"
					.message("m1").create();
			db_t.getRevWalk().parseCommit(m1);

			BranchBuilder side = db_t.branch("side");
			RevCommit s1 = side.commit().parent(m0)
					.add("f"
					.create();
			RevCommit s2 = side.commit().parent(m1)
					.add("f"
							"1-master\n2\n3\n4\n5\n6\n7-res(side)\n8\n9-side\n")
					.message("s2(merge)").create();
			master.commit().parent(s1)
					.add("f"
							"1-master\n2\n3\n4\n5\n6\n7-conflict\n8\n9-side\n")
					.message("m2(merge)").create();

			Git git = Git.wrap(db);
			git.checkout().setName("master").call();

			MergeResult result = git.merge()
					.setStrategy(MergeStrategy.RECURSIVE).include("side"
					.call();
			assertEquals(MergeStatus.CONFLICTING
		}
	}

	private Ref prepareSuccessfulMerge(Git git) throws Exception {
		writeTrashFile("a"
		git.add().addFilepattern("a").call();
		RevCommit initialCommit = git.commit().setMessage("initial").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");

		writeTrashFile("b"
		git.add().addFilepattern("b").call();
		git.commit().setMessage("side").call();

		checkoutBranch("refs/heads/master");

		writeTrashFile("c"
		git.add().addFilepattern("c").call();
		git.commit().setMessage("main").call();

		return db.exactRef("refs/heads/side");
	}

	@Test
	public void testMergeWithMessageOption() throws Exception {
		try (Git git = new Git(db)) {
			Ref sideBranch = prepareSuccessfulMerge(git);

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setMessage("user message").call();

			assertNull(db.readMergeCommitMsg());

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			assertEquals("user message"
		}
	}

	@Test
	public void testMergeWithChangeId() throws Exception {
		try (Git git = new Git(db)) {
			Ref sideBranch = prepareSuccessfulMerge(git);

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setInsertChangeId(true).call();

			assertNull(db.readMergeCommitMsg());

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			String commitMessage = newHead.getFullMessage();
			assertTrue(Pattern.compile("\nChange-Id: I[0-9a-fA-F]{40}\n")
					.matcher(commitMessage).find());
		}
	}

	@Test
	public void testMergeWithMessageAndChangeId() throws Exception {
		try (Git git = new Git(db)) {
			Ref sideBranch = prepareSuccessfulMerge(git);

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setMessage("user message").setInsertChangeId(true).call();

			assertNull(db.readMergeCommitMsg());

			Iterator<RevCommit> it = git.log().call().iterator();
			RevCommit newHead = it.next();
			String commitMessage = newHead.getFullMessage();
			assertTrue(commitMessage.startsWith("user message\n\n"));
			assertTrue(Pattern.compile("\nChange-Id: I[0-9a-fA-F]{40}\n")
					.matcher(commitMessage).find());
		}
	}

	@Test
	public void testMergeConflictWithMessageOption() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			RevCommit initialCommit = git.commit().setMessage("initial").call();

			createBranch(initialCommit
			checkoutBranch("refs/heads/side");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("side").call();

			checkoutBranch("refs/heads/master");

			writeTrashFile("a"
			git.add().addFilepattern("a").call();
			git.commit().setMessage("main").call();

			Ref sideBranch = db.exactRef("refs/heads/side");

			git.merge().include(sideBranch).setStrategy(MergeStrategy.RESOLVE)
					.setMessage("user message").call();

			assertEquals("user message\n\nConflicts:\n\ta\n"
					db.readMergeCommitMsg());
		}
	}

	private static void setExecutable(Git git
		FS.DETECTED.setExecute(
				new File(git.getRepository().getWorkTree()
	}

	private static boolean canExecute(Git git
		return FS.DETECTED.canExecute(new File(git.getRepository()
				.getWorkTree()
	}

	private static RevCommit addAllAndCommit(Git git) throws Exception {
		git.add().addFilepattern(".").call();
		return git.commit().setMessage("message").call();
	}

	private void checkMergeFailedResult(final MergeResult result
			final MergeFailureReason reason
			final String indexState
		assertEquals(MergeStatus.FAILED
		assertEquals(reason
		assertEquals("a(modified)"
		assertFalse(new File(db.getWorkTree()
		assertEquals("c"
		assertEquals(indexState
		assertEquals(null
		assertEquals(RepositoryState.SAFE
	}
}
