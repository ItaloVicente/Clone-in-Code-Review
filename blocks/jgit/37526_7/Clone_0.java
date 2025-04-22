package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.junit.MockSystemReader;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;

public class CloneTest extends CLIRepositoryTestCase {

	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		git = new Git(db);
	}

	@Test
	public void testClone() throws Exception {
		createInitialCommit();

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		StringBuilder cmd = new StringBuilder("git clone ").append(sourceURI
				+ " " + target.getPath());
		String[] result = execute(cmd.toString());
		assertArrayEquals(new String[] {
				"Cloning into '" + target.getPath() + "'..."
						""

		Git git2 = Git.open(target);
		List<Ref> branches = git2.branchList().call();
		assertEquals("expected 1 branch"
	}

	private void createInitialCommit() throws Exception {
		JGitTestUtil.writeTrashFile(db
		git.add().addFilepattern("hello.txt").call();
		git.commit().setMessage("Initial commit").call();
	}

	@Test
	public void testCloneEmpty() throws Exception {
		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		File target = createTempDirectory("target");
		StringBuilder cmd = new StringBuilder("git clone ").append(sourceURI
				+ " " + target.getPath());
		String[] result = execute(cmd.toString());
		assertArrayEquals(new String[] {
				"Cloning into '" + target.getPath() + "'..."
				"warning: You appear to have cloned an empty repository."
				"" }

		Git git2 = Git.open(target);
		List<Ref> branches = git2.branchList().call();
		assertEquals("expected 0 branch"
	}

	@Test
	public void testCloneIntoCurrentDir() throws Exception {
		createInitialCommit();
		File target = createTempDirectory("target");

		MockSystemReader sr = (MockSystemReader) SystemReader.getInstance();
		sr.setProperty(Constants.OS_USER_DIR

		File gitDir = db.getDirectory();
		String sourceURI = gitDir.toURI().toString();
		String name = new URIish(sourceURI).getHumanishName();
		StringBuilder cmd = new StringBuilder("git clone ").append(sourceURI);
		String[] result = execute(cmd.toString());
		assertArrayEquals(new String[] {
				"Cloning into '" + new File(target
				""
		Git git2 = Git.open(new File(target
		List<Ref> branches = git2.branchList().call();
		assertEquals("expected 1 branch"
	}
}
