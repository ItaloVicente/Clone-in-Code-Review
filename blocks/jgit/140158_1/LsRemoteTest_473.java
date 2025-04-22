package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.util.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class LsFilesTest extends CLIRepositoryTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		try (Git git = new Git(db)) {
			JGitTestUtil.writeTrashFile(db
			JGitTestUtil.writeTrashFile(db
			git.add().addFilepattern("dir").call();
			git.commit().setMessage("Initial commit").call();

			JGitTestUtil.writeTrashFile(db
			git.add().addFilepattern("hello2").call();
			FileUtils.createSymLink(new File(db.getWorkTree()
					"target");
			FileUtils.mkdir(new File(db.getWorkTree()
			writeTrashFile("target/file"
			git.add().addFilepattern("target").addFilepattern("link").call();
			git.commit().setMessage("hello2").call();

			JGitTestUtil.writeTrashFile(db
			JGitTestUtil.deleteTrashFile(db
			git.add().addFilepattern("staged").call();
			JGitTestUtil.writeTrashFile(db
		}
	}

	@Test
	public void testHelp() throws Exception {
		String[] result = execute("git ls-files -h");
		assertTrue(result[1].startsWith("jgit ls-files"));
	}

	@Test
	public void testLsFiles() throws Exception {
		assertArrayEquals(new String[] { "dir/world"
				"staged"
	}
}
