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
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();
		assertEquals("6fd41be HEAD@{0}: commit (initial): initial commit"
				execute("git reflog")[0]);
	}
}
