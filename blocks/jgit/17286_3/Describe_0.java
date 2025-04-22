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
	}

	private void initialCommitAndTag() throws Exception {
		git.commit().setMessage("initial commit").call();
		git.tag().setName("v1.0").call();
	}

	@Test
	public void testNoHead() throws Exception {
		assertArrayEquals(
				new String[] { "fatal: No names found
				execute("git describe"));
	}

	@Test
	public void testHeadNoTag() throws Exception {
		git.commit().setMessage("initial commit").call();
		assertArrayEquals(
				new String[] { "fatal: No names found
				execute("git describe"));
	}

	@Test
	public void testDescribeTag() throws Exception {
		initialCommitAndTag();
		assertArrayEquals(new String[] { "v1.0"
				execute("git describe HEAD"));
	}

	@Test
	public void testDescribeCommit() throws Exception {
		initialCommitAndTag();
		writeTrashFile("greeting"
		git.add().addFilepattern("greeting").call();
		git.commit().setMessage("2nd commit").call();
		assertArrayEquals(new String[] { "v1.0-1-g56f6ceb"
				execute("git describe"));
	}
}
