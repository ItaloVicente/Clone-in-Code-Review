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

import org.eclipse.jgit.api.CreateBranchCommand.SetupUpstreamMode;
import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.api.RebaseResult.Status;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryState;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.merge.MergeStrategy;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class PullCommandWithRebaseTest extends RepositoryTestCase {
	protected Repository dbTarget;

	private Git source;

	private Git target;

	private File sourceFile;

	private File targetFile;

	@Test
	public void testPullFastForward() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(Status.UP_TO_DATE

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Some change in remote").call();

		res = target.pull().call();

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(Status.FAST_FORWARD
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.SAFE
				.getRepositoryState());

		res = target.pull().call();
		assertEquals(Status.UP_TO_DATE
	}

	@Test
	public void testPullFastForwardWithBranchInSource() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(Status.UP_TO_DATE

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		RevCommit initialCommit = source.commit()
				.setMessage("Some change in remote").call();

		createBranch(initialCommit
		checkoutBranch("refs/heads/side");
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		RevCommit sideCommit = source.commit()
				.setMessage("Some change in remote").call();

		checkoutBranch("refs/heads/master");
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Some change in remote").call();

		MergeResult result = source.merge().include(sideCommit.getId())
				.setStrategy(MergeStrategy.RESOLVE).call();
		assertEquals(MergeStatus.MERGED

	}

	@Test
	public void testPullFastForwardDetachedHead() throws Exception {
		Repository repository = source.getRepository();
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("2nd commit").call();

		try (RevWalk revWalk = new RevWalk(repository)) {
			String initialBranch = repository.getBranch();
			Ref initialRef = repository.findRef(Constants.HEAD);
			RevCommit initialCommit = revWalk
					.parseCommit(initialRef.getObjectId());
			assertEquals("this test need linear history"
					initialCommit.getParentCount());
			source.checkout().setName(initialCommit.getParent(0).getName())
					.call();
			assertFalse("expected detached HEAD"
					repository.getFullBranch().startsWith(Constants.R_HEADS));

			File otherFile = new File(sourceFile.getParentFile()
					System.currentTimeMillis() + ".tst");
			writeToFile(otherFile
			source.add().addFilepattern(otherFile.getName()).call();
			RevCommit newCommit = source.commit().setMessage("other 2nd commit")
					.call();

			source.pull().setRebase(true).setRemote(".")
					.setRemoteBranchName(initialBranch)
					.call();

			assertEquals(RepositoryState.SAFE
					source.getRepository().getRepositoryState());
			Ref head = source.getRepository().findRef(Constants.HEAD);
			RevCommit headCommit = revWalk.parseCommit(head.getObjectId());

			assertEquals(1
			assertEquals(initialCommit

			assertFileContentsEqual(sourceFile
			assertFileContentsEqual(otherFile
			assertEquals(newCommit.getShortMessage()
					headCommit.getShortMessage());
		}
	}

	@Test
	public void testPullConflict() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(Status.UP_TO_DATE

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Target change in local").call();

		res = target.pull().call();

		String remoteUri = target
				.getRepository()
				.getConfig()
				.getString(ConfigConstants.CONFIG_REMOTE_SECTION
						ConfigConstants.CONFIG_KEY_URL);

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(Status.STOPPED
		String result = "<<<<<<< Upstream
				+ remoteUri
				+ "\nSource change\n=======\nTarget change\n>>>>>>> 42453fd Target change in local\n";
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.REBASING_MERGE
				.getRepository().getRepositoryState());
	}

	@Test
	public void testPullLocalConflict() throws Exception {
		target.branchCreate().setName("basedOnMaster").setStartPoint(
				"refs/heads/master").setUpstreamMode(SetupUpstreamMode.NOTRACK)
				.call();
		StoredConfig config = target.getRepository().getConfig();
		config.setString("branch"
		config.setString("branch"
				"refs/heads/master");
		config.setBoolean("branch"
		config.save();
		target.getRepository().updateRef(Constants.HEAD).link(
				"refs/heads/basedOnMaster");
		PullResult res = target.pull().call();
		assertNull(res.getFetchResult());
		assertEquals(Status.UP_TO_DATE

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

		assertNull(res.getFetchResult());
		assertEquals(Status.STOPPED
		String result = "<<<<<<< Upstream
				+ "Master change\n=======\nSlave change\n>>>>>>> 4049c9e Source change in based on master\n";
		assertFileContentsEqual(targetFile
		assertEquals(RepositoryState.REBASING_MERGE
				.getRepository().getRepositoryState());
	}

    @Test
	public void testPullFastForwardWithLocalCommitAndRebaseFlagSet() throws Exception {
		final String SOURCE_COMMIT_MESSAGE = "Source commit message for rebase flag test";
		final String TARGET_COMMIT_MESSAGE = "Target commit message for rebase flag test";

		assertFalse(SOURCE_COMMIT_MESSAGE.equals(TARGET_COMMIT_MESSAGE));

		final String SOURCE_FILE_CONTENTS = "Source change";
		final String NEW_FILE_CONTENTS = "New file from target";

		StoredConfig targetConfig = dbTarget.getConfig();
		targetConfig.setBoolean("branch"
		targetConfig.save();

		writeToFile(sourceFile
		source.add().addFilepattern(sourceFile.getName()).call();
		source.commit().setMessage(SOURCE_COMMIT_MESSAGE).call();

		File newFile = new File(dbTarget.getWorkTree().getPath() + "/newFile.txt");
		writeToFile(newFile
		target.add().addFilepattern(newFile.getName()).call();
		target.commit().setMessage(TARGET_COMMIT_MESSAGE).call();

		assertFalse(targetConfig.getBoolean("branch"

		PullResult pullResult = target.pull().setRebase(true).call();

		assertTrue(pullResult.isSuccessful());

		RebaseResult rebaseResult = pullResult.getRebaseResult();
		assertNotNull(rebaseResult);
		assertNull(rebaseResult.getFailingPaths());
		assertEquals(Status.OK

		Repository targetRepo = target.getRepository();
		try (RevWalk revWalk = new RevWalk(targetRepo)) {
			ObjectId headId = targetRepo.resolve(Constants.HEAD);
			RevCommit root = revWalk.parseCommit(headId);
			revWalk.markStart(root);
			RevCommit head = revWalk.next();
			RevCommit beforeHead = revWalk.next();

			assertEquals(TARGET_COMMIT_MESSAGE
			assertEquals(SOURCE_COMMIT_MESSAGE

			assertFileContentsEqual(sourceFile
			assertFileContentsEqual(newFile
			assertEquals(RepositoryState.SAFE
				.getRepository().getRepositoryState());
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
		target.checkout().setStartPoint("refs/remotes/origin/master").setName(
				"master").call();

		targetConfig
				.setString("branch"
		targetConfig.setBoolean("branch"
		targetConfig.save();

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
