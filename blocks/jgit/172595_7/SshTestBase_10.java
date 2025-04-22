package org.eclipse.jgit.junit.ssh;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.junit.Test;

public abstract class SshBasicTestBase extends SshTestHarness {

	protected File defaultCloneDir;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		defaultCloneDir = new File(getTemporaryDirectory()
	}

	@Test
	public void testSshCloneWithConfig() throws Exception {
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
	}

	@Test
	public void testSshFetchWithConfig() throws Exception {
				defaultCloneDir
				"Host localhost"
				"HostName localhost"
				"Port " + testPort
				"User " + TEST_USER
				"IdentityFile " + privateKey1.getAbsolutePath());
		try (Git git = new Git(db)) {
			writeTrashFile("SomeOtherFile.txt"
			git.add().addFilepattern("SomeOtherFile.txt").call();
			git.commit().setMessage("New commit").call();
		}
		try (Git git = Git.open(localClone)) {
			File f = new File(git.getRepository().getWorkTree()
					"SomeOtherFile.txt");
			assertFalse(f.exists());
			git.pull().setRemote("origin").call();
			assertTrue(f.exists());
			assertEquals("Other commit"
		}
	}
}
