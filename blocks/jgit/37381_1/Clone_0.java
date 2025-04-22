package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Ref;
import org.junit.Before;
import org.junit.Test;

public class CloneTest extends CLIRepositoryTestCase {

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		Git git = new Git(db);
		JGitTestUtil.writeTrashFile(db
		git.add().addFilepattern("hello.txt").call();
		git.commit().setMessage("Initial commit").call();
	}

	@Test
	public void testClone() throws Exception {
		File gitDir = db.getDirectory();
		String sourcePath = gitDir.getAbsolutePath();
		String targetPath = (new File(sourcePath)).getParentFile()
				.getParentFile().getAbsolutePath()
				+ "/target";
		StringBuilder cmd = new StringBuilder("git clone ").append(sourcePath
				+ " " + targetPath);
		execute(cmd.toString());
		Git git = Git.open(new File(targetPath));
		List<Ref> branches = git.branchList().call();
		assertEquals("expected 1 branch"
	}

	@Test
	public void testCloneBare() throws Exception {
		File gitDir = db.getDirectory();
		String sourcePath = gitDir.getAbsolutePath();
		String targetPath = (new File(sourcePath)).getParentFile()
				.getParentFile().getAbsolutePath()
				+ "/target.git";
		StringBuilder cmd = new StringBuilder("git clone --bare ")
				.append(sourcePath + " " + targetPath);
		execute(cmd.toString());
		Git git = Git.open(new File(targetPath));
		List<Ref> branches = git.branchList().call();
		assertEquals("expected 1 branch"
		assertTrue("expected bare repository"
	}
}
