package org.eclipse.jgit.internal.storage.file;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.FS.ExecutionResult;
import org.junit.Test;

public class CGitLockFileTest extends RepositoryTestCase {

	@Test
	public void testLockedTwiceFails() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit commit1 = git.commit().setMessage("create file").call();

			assertNotNull(commit1);
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			assertNotNull(git.commit().setMessage("edit file").call());

			LockFile lf = new LockFile(db.getIndexFile());
			assertTrue(lf.lock());
			try {
				String[] command = new String[] { "git"
						commit1.name() };
				ProcessBuilder pb = new ProcessBuilder(command);
				pb.directory(db.getWorkTree());
				ExecutionResult result = FS.DETECTED.execute(pb
				assertNotEquals(0
				String err = result.getStderr().toString().split("\\R")[0];
				assertTrue(err.matches(
			} finally {
				lf.unlock();
			}
		}
	}
}
