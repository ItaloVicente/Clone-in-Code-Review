package org.eclipse.jgit.pgm;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BlameTest extends CLIRepositoryTestCase {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testBlameNoHead() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("inIndex.txt"
			git.add().addFilepattern("inIndex.txt").call();
		}
		thrown.expect(Die.class);
		thrown.expectMessage("no such ref: HEAD");
		execute("git blame inIndex.txt");
	}

	@Test
	public void testBlameCommitted() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			writeTrashFile("committed.txt"
			git.add().addFilepattern("committed.txt").call();
			git.commit().setMessage("commit").call();
		}
		assertStringArrayEquals(
				"1ad3399c (GIT_COMMITTER_NAME 2009-08-15 20:12:58 -0330 1) committed"
				execute("git blame committed.txt"));
	}

	@Test
	public void testBlameStaged() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			writeTrashFile("inIndex.txt"
			git.add().addFilepattern("inIndex.txt").call();
		}
		assertStringArrayEquals(
				"         (Not Committed Yet          1) index"
				execute("git blame inIndex.txt"));
	}

	@Test
	public void testBlameUnstaged() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
		}
		writeTrashFile("onlyInWorkingTree.txt"
		thrown.expect(Die.class);
		thrown.expectMessage("no such path 'onlyInWorkingTree.txt' in HEAD");
		execute("git blame onlyInWorkingTree.txt");
	}

	@Test
	public void testBlameNonExisting() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
		}
		thrown.expect(Die.class);
		thrown.expectMessage("no such path 'does_not_exist.txt' in HEAD");
		execute("git blame does_not_exist.txt");
	}

	@Test
	public void testBlameNonExistingInSubdir() throws Exception {
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
		}
		thrown.expect(Die.class);
		thrown.expectMessage("no such path 'sub/does_not_exist.txt' in HEAD");
		execute("git blame sub/does_not_exist.txt");
	}
}
