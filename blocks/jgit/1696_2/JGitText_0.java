package org.eclipse.jgit.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteConfig;
import org.eclipse.jgit.transport.URIish;

public class PullCommandTest extends RepositoryTestCase {
	protected FileRepository dbTarget;

	private Git source;

	private Git target;

	private File sourceFile;

	private File targetFile;

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
	}

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

		assertFalse(res.getFetchResult().getTrackingRefUpdates().isEmpty());
		assertEquals(res.getMergeResult().getMergeStatus()
				MergeStatus.CONFLICTING);
		String result = "<<<<<<< HEAD\nTarget change\n=======\n"
				+ "Source change\n>>>>>>> refs/remotes/origin/master\n";
		assertFileContentsEqual(targetFile
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		dbTarget = createWorkRepository();
		source = new Git(db);
		target = new Git(dbTarget);

		sourceFile = new File(db.getWorkTree()
		writeToFile(sourceFile
		source.add().addFilepattern("SomeFile.txt").call();
		RevCommit commit = source.commit().setMessage(
				"Initial commit for source").call();

		RefUpdate upd = dbTarget.updateRef("refs/heads/master");
		upd.setNewObjectId(commit.getId());
		upd.update();

		StoredConfig targetConfig = dbTarget.getConfig();
		targetConfig.setString("branch"
		targetConfig
				.setString("branch"
		RemoteConfig config = new RemoteConfig(targetConfig

		config
				.addURI(new URIish(source.getRepository().getWorkTree()
						.getPath()));
		config.addFetchRefSpec(new RefSpec(
		targetConfig.save();
		config.update(targetConfig);

		targetFile = new File(dbTarget.getWorkTree()
		writeToFile(targetFile
		target.pull().call();
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
