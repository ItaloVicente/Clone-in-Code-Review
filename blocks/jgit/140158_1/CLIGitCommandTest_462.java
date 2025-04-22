package org.eclipse.jgit.pgm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.CLIRepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.pgm.internal.CLIText;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class BranchTest extends CLIRepositoryTestCase {
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
		}
	}

	@Test
	public void testHelpAfterDelete() throws Exception {
		String err = toString(executeUnchecked("git branch -d"));
		String help = toString(executeUnchecked("git branch -h"));
		String errAndHelp = toString(executeUnchecked("git branch -d -h"));
		assertEquals(CLIText.fatalError(CLIText.get().branchNameRequired)
		assertEquals(toString(err
	}

	@Test
	public void testList() throws Exception {
		assertEquals("* master"
		assertEquals("* master 6fd41be initial commit"
				toString(execute("git branch -v")));
	}

	@Test
	public void testListDetached() throws Exception {
		RefUpdate updateRef = db.updateRef(Constants.HEAD
		updateRef.setNewObjectId(db.resolve("6fd41be"));
		updateRef.update();
		assertEquals(
				toString("* (no branch) 6fd41be initial commit"
						"master      6fd41be initial commit")
				toString(execute("git branch -v")));
	}

	@Test
	public void testListContains() throws Exception {
		try (Git git = new Git(db)) {
			git.branchCreate().setName("initial").call();
			RevCommit second = git.commit().setMessage("second commit")
					.call();
			assertEquals(toString("  initial"
					toString(execute("git branch --contains 6fd41be")));
			assertEquals("* master"
					toString(execute("git branch --contains " + second.name())));
		}
	}

	@Test
	public void testExistingBranch() throws Exception {
		assertEquals("fatal: A branch named 'master' already exists."
				toString(executeUnchecked("git branch master")));
	}

	@Test
	public void testRenameSingleArg() throws Exception {
		try {
			toString(execute("git branch -m"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch -m slave"));
		assertEquals(""
		result = toString(execute("git branch -a"));
		assertEquals("* slave"
	}

	@Test
	public void testRenameTwoArgs() throws Exception {
		String result = toString(execute("git branch -m master slave"));
		assertEquals(""
		result = toString(execute("git branch -a"));
		assertEquals("* slave"
	}

	@Test
	public void testCreate() throws Exception {
		try {
			toString(execute("git branch a b"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch second"));
		assertEquals(""
		result = toString(execute("git branch"));
		assertEquals(toString("* master"
		result = toString(execute("git branch -v"));
		assertEquals(toString("* master 6fd41be initial commit"
				"second 6fd41be initial commit")
	}

	@Test
	public void testDelete() throws Exception {
		try {
			toString(execute("git branch -d"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch second"));
		assertEquals(""
		result = toString(execute("git branch -d second"));
		assertEquals(""
		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testDeleteMultiple() throws Exception {
		String result = toString(execute("git branch second"
				"git branch third"
		assertEquals(""
		result = toString(execute("git branch -d second third fourth"));
		assertEquals(""
		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testDeleteForce() throws Exception {
		try {
			toString(execute("git branch -D"));
			fail("Must die");
		} catch (Die e) {
		}
		String result = toString(execute("git branch second"));
		assertEquals(""
		result = toString(execute("git checkout second"));
		assertEquals("Switched to branch 'second'"

		File a = writeTrashFile("a"
		assertTrue(a.exists());
		execute("git add a"

		result = toString(execute("git checkout master"));
		assertEquals("Switched to branch 'master'"

		result = toString(execute("git branch"));
		assertEquals(toString("* master"

		try {
			toString(execute("git branch -d second"));
			fail("Must die");
		} catch (Die e) {
		}
		result = toString(execute("git branch -D second"));
		assertEquals(""

		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testDeleteForceMultiple() throws Exception {
		String result = toString(execute("git branch second"
				"git branch third"

		assertEquals(""
		result = toString(execute("git checkout second"));
		assertEquals("Switched to branch 'second'"

		File a = writeTrashFile("a"
		assertTrue(a.exists());
		execute("git add a"

		result = toString(execute("git checkout master"));
		assertEquals("Switched to branch 'master'"

		result = toString(execute("git branch"));
		assertEquals(toString("fourth"

		try {
			toString(execute("git branch -d second third fourth"));
			fail("Must die");
		} catch (Die e) {
		}
		result = toString(execute("git branch"));
		assertEquals(toString("fourth"

		result = toString(execute("git branch -D second third fourth"));
		assertEquals(""

		result = toString(execute("git branch"));
		assertEquals("* master"
	}

	@Test
	public void testCreateFromOldCommit() throws Exception {
		File a = writeTrashFile("a"
		assertTrue(a.exists());
		execute("git add a"
		File b = writeTrashFile("b"
		assertTrue(b.exists());
		execute("git add b"
		String result = toString(execute("git log -n 1 --reverse"));
		String firstCommitId = result.substring("commit ".length()
				result.indexOf('\n'));

		result = toString(execute("git branch -f second " + firstCommitId));
		assertEquals(""

		result = toString(execute("git branch"));
		assertEquals(toString("* master"

		result = toString(execute("git checkout second"));
		assertEquals("Switched to branch 'second'"
		assertFalse(b.exists());
	}
}
