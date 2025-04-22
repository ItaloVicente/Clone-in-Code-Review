package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Test;

public class ReflogTest extends CLIRepositoryTestCase {
	@Test
	public void testClean() throws Exception {
		assertArrayEquals(new String[] { "" }
	}

	@Test
	public void testSingleCommit() throws Exception {
		new Git(db).commit().setMessage("initial commit").call();

		assertEquals("6fd41be HEAD@{0}: commit (initial): initial commit"
				execute("git reflog")[0]);
	}

	@Test
	public void testBranch() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("first commit").call();
		git.checkout().setCreateBranch(true).setName("side").call();
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("side commit").call();

		assertArrayEquals(new String[] {
				"38890c7 side@{0}: commit: side commit"
				"d216986 side@{1}: branch: Created from commit first commit"
				"" }
	}
}
