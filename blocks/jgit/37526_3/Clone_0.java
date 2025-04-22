package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Ref;
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
		String sourcePath = gitDir.getAbsolutePath();
		String targetPath = (new File(sourcePath)).getParentFile()
				.getParentFile().getAbsolutePath()
				+ "/target";
		StringBuilder cmd = new StringBuilder("git clone ").append(sourcePath
				+ " " + targetPath);
		String[] result = execute(cmd.toString());
		assertArrayEquals(new String[] {
				"Cloning into '" + targetPath + "'..."

		Git git2 = Git.open(new File(targetPath));
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
		String sourcePath = gitDir.getAbsolutePath();
		String targetPath = (new File(sourcePath)).getParentFile()
				.getParentFile().getAbsolutePath()
				+ "/target";
		StringBuilder cmd = new StringBuilder("git clone ").append(sourcePath
				+ " " + targetPath);
		String[] result = execute(cmd.toString());
		assertArrayEquals(new String[] {
				"Cloning into '" + targetPath + "'..."
				"warning: You appear to have cloned an empty repository."
				"" }

		Git git2 = Git.open(new File(targetPath));
		List<Ref> branches = git2.branchList().call();
		assertEquals("expected 0 branch"
	}
}
