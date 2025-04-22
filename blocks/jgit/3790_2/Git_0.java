package org.eclipse.jgit.api;

import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class StashApplyCommandTest extends RepositoryTestCase {
	private Git git;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);

		writeTrashFile("File1.txt"
		writeTrashFile("File2.txt"

		git.add().addFilepattern("File1.txt").call();
		git.commit().setMessage("Test file commit").call();

		git.add().addFilepattern("File2.txt");
	}

	@Test
	public void testStash() {
		try {
			Status status = git.status().call();
			assertTrue(status.getUntracked().size() != 0);
			git.stashCreate().call();
			status = git.status().call();
			assertTrue(status.getUntracked().size() == 0);
			git.stashList().call();
			git.stashApply().call();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
