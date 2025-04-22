package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class TagTest extends CLIRepositoryTestCase {
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		git.commit().setMessage("initial commit").call();
	}

	@Test
	public void testTagTwice() throws Exception {
		git.tag().setName("test").call();
		writeTrashFile("file"
		git.add().addFilepattern("file").call();
		git.commit().setMessage("commit").call();

		assertEquals("fatal: tag 'test' already exists"
				execute("git tag test")[0]);
	}
}
