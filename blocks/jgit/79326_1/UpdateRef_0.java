package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Test;

public class UpdateRefTest extends CLIRepositoryTestCase {
	@Test
	public void testUpdateRefUsage() throws Exception {
		execute("git update-ref --help");
	}

	@Test
	public void testUpdateRefMove() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			assertEquals("6fd41be HEAD@{0}: commit (initial): initial commit"
					execute("git reflog")[0]);
			git.commit().setMessage("second commit").call();
			assertEquals("5ac9776 HEAD@{0}: commit: second commit"
					execute("git reflog")[0]);

			assertArrayEquals(new String[] { "" }
					execute("git update-ref refs/heads/master 6fd41be"));
			assertEquals("6fd41be master@{0}: "
					execute("git reflog refs/heads/master")[0]);

			assertArrayEquals(
					 new String[] { "" }
					execute("git update-ref refs/heads/master 5ac9776 -m \"A reason\""));
			assertEquals("5ac9776 master@{0}: A reason"
					execute("git reflog refs/heads/master")[0]);
		}
	}
}
