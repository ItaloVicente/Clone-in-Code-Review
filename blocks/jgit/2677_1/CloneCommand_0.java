package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.MergeResult.MergeStatus;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class CloneCommandTest extends RepositoryTestCase {

	private Git git;

	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Initial commit").call();

		git.branchCreate().setName("test").call();
		RefUpdate rup = db.updateRef(Constants.HEAD);
		rup.link("refs/heads/test");

		writeTrashFile("Test.txt"
		git.add().addFilepattern("Test.txt").call();
		git.commit().setMessage("Second commit").call();
	}

	@Test
	public void testCloneRepository() {
		try {
			File directory = createTempDirectory("testCloneRepository");
			CloneCommand command = Git.cloneRepository();
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			Git git2 = command.call();
			assertNotNull(git2);

			PullResult res = git2.pull().call();
			assertTrue(res.getFetchResult().getTrackingRefUpdates().isEmpty());
			assertTrue(res.getMergeResult().getMergeStatus()
					.equals(MergeStatus.ALREADY_UP_TO_DATE));
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testCloneRepositoryWithBranch() {
		try {
			File directory = createTempDirectory("testCloneRepositoryWithBranch");
			CloneCommand command = Git.cloneRepository();
			command.setBranch("refs/heads/test");
			command.setDirectory(directory);
					+ git.getRepository().getWorkTree().getPath());
			Git git2 = command.call();
			assertNotNull(git2);
			assertEquals(git2.getRepository().getFullBranch()
					"refs/heads/test");
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	public static File createTempDirectory(String name) throws IOException {
		final File temp;
		temp = File.createTempFile(name

		if (!(temp.delete())) {
			throw new IOException("Could not delete temp file: "
					+ temp.getAbsolutePath());
		}

		if (!(temp.mkdir())) {
			throw new IOException("Could not create temp directory: "
					+ temp.getAbsolutePath());
		}
		return temp;
	}

}
