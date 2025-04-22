package org.eclipse.jgit.api;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class PullCommandTest extends RepositoryTestCase {
	protected Repository dbTarget;

	private Git source;

	private Git target;

	private File sourceFile;

	private File targetFile;

	@Test
	public void testPullFastForward() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus().equals(
				MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Some change in remote").call();

		res = target.pull().call();

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.FAST_FORWARD);
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.SAFE
				.getRepositoryState());

		res = target.pull().call();
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.ALREADY_UP_TO_DATE);
	}

	@Test
	public void testPullMerge() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus()
				.equals(MergeStatus.ALREADY_UP_TO_DATE));

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt");
		RevCommit sourceCommit = source.commit()
				.setMessage("Source change in remote").call();

		File targetFile2 = new File(dbTarget.getWorkTree()
		writeToFile(targetFile2
		target.add().addFilepattern("OtherFile.txt").call();
		RevCommit targetCommit = target.commit()
				.setMessage("Unconflicting change in local").call();

		res = target.pull().call();

		MergeResult mergeResult = res.getMergeResult();
		ObjectId[] mergedCommits = mergeResult.getMergedCommits();
		assertEquals(targetCommit.getId()
		assertEquals(sourceCommit.getId()
		try (RevWalk rw = new RevWalk(dbTarget)) {
			RevCommit mergeCommit = rw.parseCommit(mergeResult.getNewHead());
			String message = "Merge branch 'master' of "
					+ db.getWorkTree().getAbsolutePath();
			assertEquals(message
		}
	}

	@Test
	public void testPullConflict() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getMergeResult().getMergeStatus().equals(
				MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Target change in local").call();

		res = target.pull().call();

		String sourceChangeString = "Source change\n>>>>>>> branch 'master' of "
				+ target.getRepository().getConfig().getString("remote"
						"origin"

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.CONFLICTING);
		String result = "<<<<<<< HEAD\nTarget change\n=======\n"
				+ sourceChangeString + "\n";
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.MERGING
				.getRepositoryState());
	}

	@Test
	public void testPullWithUntrackedStash() throws Exception {
		target.pull().call();

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(new File(dbTarget.getWorkTree()
				"untracked");
		RevCommit stash = target.stashCreate().setIndexMessage("message here")
				.setIncludeUntracked(true).call();
		assertNotNull(stash);
		assertTrue(target.status().call().isClean());

		assertTrue(target.pull().call().isSuccessful());
		assertEquals("[SomeFile.txt
				indexState(dbTarget
		assertFalse(JGitTestUtil.check(dbTarget
		assertEquals("Source change"
				JGitTestUtil.read(dbTarget

		target.stashApply().setStashRef(stash.getName()).call();
		assertEquals("[SomeFile.txt
				indexState(dbTarget
		assertEquals("untracked"
		assertEquals("Source change"
				JGitTestUtil.read(dbTarget
	}

	@Test
	public void testPullLocalConflict() throws Exception {
		target.branchCreate().setName("basedOnMaster").setStartPoint(
				"refs/heads/master").setUpstreamMode(SetupUpstreamMode.TRACK)
				.call();
		target.getRepository().updateRef(Constants.HEAD).link(
				"refs/heads/basedOnMaster");
		PullResult res = target.pull().call();
		assertNull(res.getFetchResult());
		assertTrue(res.getMergeResult().getMergeStatus().equals(
				MergeStatus.ALREADY_UP_TO_DATE));

		assertFileContentsEqual(targetFile

		target.getRepository().updateRef(Constants.HEAD).link(
				"refs/heads/master");
		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Source change in master").call();

		target.getRepository().updateRef(Constants.HEAD).link(
				"refs/heads/basedOnMaster");
		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Source change in based on master").call();

		res = target.pull().call();

		String sourceChangeString = "Master change\n>>>>>>> branch 'master' of local repository";

		assertNull(res.getFetchResult());
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.CONFLICTING);
		String result = "<<<<<<< HEAD\nSlave change\n=======\n"
				+ sourceChangeString + "\n";
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.MERGING
				.getRepositoryState());
	}

	@Test(expected = NoHeadException.class)
	public void testPullEmptyRepository() throws Exception {
		Repository empty = createWorkRepository();
		RefUpdate delete = empty.updateRef(Constants.HEAD
		delete.setForceUpdate(true);
		delete.delete();
		Git.wrap(empty).pull().call();
	}

	@Test
	public void testPullMergeProgrammaticConfiguration() throws Exception {
		source.checkout().setCreateBranch(true).setName("other").call();
		sourceFile = new File(db.getWorkTree()
		writeToFile(sourceFile
		source.add().addFilepattern("file2.txt").call();
		RevCommit sourceCommit = source.commit()
				.setMessage("source commit on branch other").call();

		File targetFile2 = new File(dbTarget.getWorkTree()
		writeToFile(targetFile2
		target.add().addFilepattern("OtherFile.txt").call();
		RevCommit targetCommit = target.commit()
				.setMessage("Unconflicting change in local").call();

		PullResult res = target.pull().setRemote("origin")
				.setRemoteBranchName("other")
				.setRebase(false).call();

		MergeResult mergeResult = res.getMergeResult();
		ObjectId[] mergedCommits = mergeResult.getMergedCommits();
		assertEquals(targetCommit.getId()
		assertEquals(sourceCommit.getId()
		try (RevWalk rw = new RevWalk(dbTarget)) {
			RevCommit mergeCommit = rw.parseCommit(mergeResult.getNewHead());
			String message = "Merge branch 'other' of "
					+ db.getWorkTree().getAbsolutePath();
			assertEquals(message
		}
	}

	@Test
	public void testPullMergeProgrammaticConfigurationImpliedTargetBranch()
			throws Exception {
		source.checkout().setCreateBranch(true).setName("other").call();
		sourceFile = new File(db.getWorkTree()
		writeToFile(sourceFile
		source.add().addFilepattern("file2.txt").call();
		RevCommit sourceCommit = source.commit()
				.setMessage("source commit on branch other").call();

		target.checkout().setCreateBranch(true).setName("other").call();
		File targetFile2 = new File(dbTarget.getWorkTree()
		writeToFile(targetFile2
		target.add().addFilepattern("OtherFile.txt").call();
		RevCommit targetCommit = target.commit()
				.setMessage("Unconflicting change in local").call();

		PullResult res = target.pull().setRemote("origin").setRebase(false)
				.call();

		MergeResult mergeResult = res.getMergeResult();
		ObjectId[] mergedCommits = mergeResult.getMergedCommits();
		assertEquals(targetCommit.getId()
		assertEquals(sourceCommit.getId()
		try (RevWalk rw = new RevWalk(dbTarget)) {
			RevCommit mergeCommit = rw.parseCommit(mergeResult.getNewHead());
			String message = "Merge branch 'other' of "
					+ db.getWorkTree().getAbsolutePath() + " into other";
			assertEquals(message
		}
	}

	private enum TestPullMode {
		MERGE
	}

	@Test
	public void testPullWithRebasePreserve1Config() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebasePreserveConfig2() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebasePreserveConfig3() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebaseConfig1() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebaseConfig2() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithRebaseConfig3() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithoutConfig() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithMergeConfig() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.setString("branch"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	@Test
	public void testPullWithMergeConfig2() throws Exception {
		Callable<PullResult> setup = new Callable<PullResult>() {
			@Override
			public PullResult call() throws Exception {
				StoredConfig config = dbTarget.getConfig();
				config.setString("pull"
				config.save();
				return target.pull().call();
			}
		};
		doTestPullWithRebase(setup
	}

	private void doTestPullWithRebase(Callable<PullResult> pullSetup
			TestPullMode expectedPullMode) throws Exception {
		writeToFile(sourceFile
		source.add().addFilepattern(sourceFile.getName()).call();
		RevCommit sourceCommit = source.commit().setMessage("source commit")
				.call();

		File loxalFile = new File(dbTarget.getWorkTree()
		writeToFile(loxalFile
		target.add().addFilepattern("local.txt").call();
		RevCommit t1 = target.commit().setMessage("target commit 1").call();

		target.checkout().setCreateBranch(true).setName("side").call();

		String newContent = "initial\n" + "and more\n";
		writeToFile(loxalFile
		target.add().addFilepattern("local.txt").call();
		RevCommit t2 = target.commit().setMessage("target commit 2").call();

		target.checkout().setName("master").call();

		MergeResult mergeResult = target.merge()
				.setFastForward(MergeCommand.FastForwardMode.NO_FF).include(t2)
				.call();
		assertEquals(MergeStatus.MERGED
		assertFileContentsEqual(loxalFile
		ObjectId merge = mergeResult.getNewHead();

		PullResult res = pullSetup.call();
		assertNotNull(res.getFetchResult());

		if (expectedPullMode == TestPullMode.MERGE) {
			assertEquals(MergeStatus.MERGED
					.getMergeStatus());
			assertNull(res.getRebaseResult());
		} else {
			assertNull(res.getMergeResult());
			assertEquals(RebaseResult.OK_RESULT
		}
		assertFileContentsEqual(sourceFile

		try (RevWalk rw = new RevWalk(dbTarget)) {
			rw.sort(RevSort.TOPO);
			rw.markStart(rw.parseCommit(dbTarget.resolve("refs/heads/master")));

			RevCommit next;
			if (expectedPullMode == TestPullMode.MERGE) {
				next = rw.next();
				assertEquals(2
				assertEquals(merge
				assertEquals(sourceCommit
			} else {
				if (expectedPullMode == TestPullMode.REBASE_PREASERVE) {
					next = rw.next();
					assertEquals(2
				}
				next = rw.next();
				assertEquals(t2.getShortMessage()
				next = rw.next();
				assertEquals(t1.getShortMessage()
				next = rw.next();
				assertEquals(sourceCommit
				next = rw.next();
				assertEquals("Initial commit for source"
						next.getShortMessage());
				next = rw.next();
				assertNull(next);
			}
		}
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		dbTarget = createWorkRepository();
		source = new Git(db);
		target = new Git(dbTarget);

		sourceFile = new File(db.getWorkTree()
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Initial commit for source").call();

		StoredConfig targetConfig = dbTarget.getConfig();
		targetConfig.setString("branch"
		targetConfig
				.setString("branch"
		RemoteConfig config = new RemoteConfig(targetConfig

		config
				.addURI(new URIish(source.getRepository().getWorkTree()
						.getAbsolutePath()));
		config.addFetchRefSpec(new RefSpec(
		config.update(targetConfig);
		targetConfig.save();

		targetFile = new File(dbTarget.getWorkTree()
		target.pull().call();
		assertFileContentsEqual(targetFile
	}

	private static void writeToFile(File actFile
			throws IOException {
		try (FileOutputStream fos = new FileOutputStream(actFile)) {
			fos.write(string.getBytes(UTF_8));
		}
	}

	private static void assertFileContentsEqual(File actFile
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[100];
		try (FileInputStream fis = new FileInputStream(actFile)) {
			int read = fis.read(buffer);
			while (read > 0) {
				bos.write(buffer
				read = fis.read(buffer);
			}
			String content = new String(bos.toByteArray()
			assertEquals(string
		}
	}
}
