package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertArrayEquals;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.junit.JGitTestUtil;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Before;
import org.junit.Test;

public class LsFilesTest extends CLIRepositoryTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		try (Git git = new Git(db)) {
			JGitTestUtil.writeTrashFile(db
			git.add().addFilepattern("hello").call();
			git.commit().setMessage("Initial commit").call();
			JGitTestUtil.writeTrashFile(db
			git.add().addFilepattern("hello2").call();
			git.commit().setMessage("hello2").call();
			JGitTestUtil.writeTrashFile(db
			git.add().addFilepattern("staged").call();
			JGitTestUtil.writeTrashFile(db
		}
	}

	@Test
	public void testHelp() throws Exception {
		assertArrayEquals(new String[] { ""
				"jgit ls-files [-- path ...] [--help (-h)]"
				" --help (-h) : display this help text (default: true)"
				"" }
	}

	@Test
	public void testLsFiles() throws Exception {
		assertArrayEquals(new String[] { "hello"
				execute("git ls-files"));
	}
}
