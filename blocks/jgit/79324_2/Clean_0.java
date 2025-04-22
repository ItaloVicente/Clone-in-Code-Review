package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Test;
import static org.eclipse.jgit.junit.JGitTestUtil.check;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CleanTest extends CLIRepositoryTestCase {
	@Test
	public void testCleanLeaveDirs() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			writeTrashFile("dir/file"
			writeTrashFile("a"
			writeTrashFile("b"

			assertTrue(check(db
			assertTrue(check(db
			assertTrue(check(db

			assertArrayOfLinesEquals(
					new String[] { "Removing a"
					execute("git clean"));
			assertFalse(check(db
			assertFalse(check(db
			assertTrue(check(db
		}
	}

	@Test
	public void testCleanDeleteDirs() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			writeTrashFile("dir/file"
			writeTrashFile("a"
			writeTrashFile("b"

			assertTrue(check(db
			assertTrue(check(db
			assertTrue(check(db

			assertArrayOfLinesEquals(
					new String[] { "Removing a"
							"Removing dir/" }
					execute("git clean -d"));
			assertFalse(check(db
			assertFalse(check(db
			assertFalse(check(db
		}
	}
}
