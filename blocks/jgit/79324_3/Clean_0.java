package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Test;
import static org.eclipse.jgit.junit.JGitTestUtil.check;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class CleanTest extends CLIRepositoryTestCase {
	@Test
	public void testCleanRequiresForce() throws Exception {
		try (Git git = new Git(db)) {
			assertArrayOfLinesEquals(
					new String[] { "Removing a"
					execute("git clean"));
		} catch (Die e) {
			assertEquals(
					"fatal: clean.requireForce defaults to true and neither -i
					e.getMessage());
		}
	}

	@Test
	public void testCleanRequiresForceConfig() throws Exception {
		try (Git git = new Git(db)) {
			git.getRepository().getConfig().setBoolean("clean"
					"requireForce"
			assertArrayOfLinesEquals(
					new String[] { "" }
					execute("git clean"));
		}
	}

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
					execute("git clean -n"));
			assertTrue(check(db
			assertTrue(check(db
			assertTrue(check(db

			assertArrayOfLinesEquals(
					new String[] { "Removing a"
					execute("git clean -f"));
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
					execute("git clean -d -f"));
			assertFalse(check(db
			assertFalse(check(db
			assertFalse(check(db
		}
	}
}
