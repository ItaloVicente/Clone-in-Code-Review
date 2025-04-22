package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class DescribeTest extends CLIRepositoryTestCase {

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
		git.commit().setMessage("initial commit").call();
		git.tag().setName("v1.0").call();
	}

	@Test
	public void testDescribeTag() throws Exception {
		assertArrayEquals(new String[] { "v1.0"
				execute("git describe HEAD"));
	}

	@Test
	public void testDescribeCommit() throws Exception {
		writeTrashFile("greeting"
		git.add().addFilepattern("greeting").call();
		git.commit().setMessage("2nd commit").call();
		assertArrayEquals(new String[] { "v1.0-1-g56f6ceb"
				execute("git describe"));
	}
}
