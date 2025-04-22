package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;
import org.junit.Before;
import org.junit.Test;

public class PullCommandWithRebaseTest extends RepositoryTestCase {
	protected FileRepository dbTarget;

	private Git source;

	private Git target;

	private File sourceFile;

	private File targetFile;

	@Test
	public void testPullFastForward() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getRebaseResult().getStatus().equals(Status.UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Some change in remote").call();

		res = target.pull().call();

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getRebaseResult().getStatus()
				.equals(Status.FAST_FORWARD));
		assertFileContentsEqual(targetFile
	}

	@Test
	public void testPullConflict() throws Exception {
		PullResult res = target.pull().call();
		assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getRebaseResult().getStatus().equals(Status.UP_TO_DATE));

		assertFileContentsEqual(targetFile

		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		source.commit().setMessage("Source change in remote").call();

		writeToFile(targetFile
		target.add().addFilepattern("SomeFile.txt").call();
		target.commit().setMessage("Target change in local").call();

		res = target.pull().call();

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertTrue(res.getRebaseResult().getStatus().equals(Status.STOPPED));
		String result = "<<<<<<< OURS\nSource change\n=======\nTarget change\n>>>>>>> THEIRS\n";
		assertFileContentsEqual(targetFile
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

		String sourceChangeString = "Master change\n>>>>>>> branch 'refs/heads/master' of local repository";

		assertNull(res.getFetchResult());
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.CONFLICTING);
		String result = "<<<<<<< HEAD\nSlave change\n=======\n"
				+ sourceChangeString + "\n";
		assertFileContentsEqual(targetFile
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
						.getPath()));
		config.addFetchRefSpec(new RefSpec(
		config.update(targetConfig);
		targetConfig.save();

		targetFile = new File(dbTarget.getWorkTree()
		target.pull().call();
		target.checkout().setStartPoint("refs/remotes/origin/master").setName(
				"master").call();

		targetConfig.setString("branch"
				"refs/remotes/origin/master");
		targetConfig.unset("branch"
		targetConfig.save();

		assertFileContentsEqual(targetFile
	}

	private void writeToFile(File actFile
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(actFile);
			fos.write(string.getBytes("UTF-8"));
			fos.close();
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	private void assertFileContentsEqual(File actFile
			throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		FileInputStream fis = null;
		byte[] buffer = new byte[100];
		try {
			fis = new FileInputStream(actFile);
			int read = fis.read(buffer);
			while (read > 0) {
				bos.write(buffer
				read = fis.read(buffer);
			}
			String content = new String(bos.toByteArray()
			assertEquals(string
		} finally {
			if (fis != null)
				fis.close();
		}
	}
}
