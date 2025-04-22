package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ResetTest extends CLIRepositoryTestCase {

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test
	public void testPathOptionHelp() throws Exception {
		String[] result = execute("git reset -h");
		assertTrue("Unexpected argument: " + result[1]
				result[1].endsWith("[-- path ...]"));
	}

	@Test
	public void testZombieArgument_Bug484951() throws Exception {
		String[] result = execute("git reset -h");
		assertFalse("Unexpected argument: " + result[0]
				result[0].contains("[VAL ...]"));
	}

	@Test
	public void testResetSelf() throws Exception {
		RevCommit commit = git.commit().setMessage("initial commit").call();
		assertStringArrayEquals(""
				execute("git reset --hard " + commit.getId().name()));
		assertEquals(commit.getId()
				git.getRepository().exactRef("HEAD").getObjectId());
	}

	@Test
	public void testResetPrevious() throws Exception {
		RevCommit commit = git.commit().setMessage("initial commit").call();
		git.commit().setMessage("second commit").call();
		assertStringArrayEquals(""
				execute("git reset --hard " + commit.getId().name()));
		assertEquals(commit.getId()
				git.getRepository().exactRef("HEAD").getObjectId());
	}

	@Test
	public void testResetEmptyPath() throws Exception {
		RevCommit commit = git.commit().setMessage("initial commit").call();
		assertStringArrayEquals(""
				execute("git reset --hard " + commit.getId().name() + " --"));
		assertEquals(commit.getId()
				git.getRepository().exactRef("HEAD").getObjectId());
	}

	@Test
	public void testResetPathDoubleDash() throws Exception {
		resetPath(true
	}

	@Test
	public void testResetPathNoDoubleDash() throws Exception {
		resetPath(false
	}

	@Test
	public void testResetPathDoubleDashNoRef() throws Exception {
		resetPath(true
	}

	@Ignore("Currently we cannote recognize if a name is a commit-ish or a path
			+ "so 'git reset a' will not work if 'a' is not a branch name but a file path")
	@Test
	public void testResetPathNoDoubleDashNoRef() throws Exception {
		resetPath(false
	}

	private void resetPath(boolean useDoubleDash
			throws Exception {
		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();
		RevCommit commit = git.commit().setMessage("files a & b").call();

		writeTrashFile("a"
		writeTrashFile("b"
		git.add().addFilepattern(".").call();

		String cmd = String.format("git reset %s%s a"
				supplyCommit ? commit.getId().name() : ""
				useDoubleDash ? " --" : "");
		assertStringArrayEquals(""
		assertEquals(commit.getId()
				git.getRepository().exactRef("HEAD").getObjectId());

		org.eclipse.jgit.api.Status status = git.status().call();
		assertArrayEquals(new String[] { "a" }
				status.getModified().toArray());
		assertArrayEquals(new String[] { "b" }
				status.getChanged().toArray());
	}

}
