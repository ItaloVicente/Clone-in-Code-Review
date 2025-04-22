package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class ReflogResolveTest extends RepositoryTestCase {

	@Test
	public void resolveMasterCommits() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("edit file").call();

			assertEquals(c2
			assertEquals(c1
		}
	}

	@Test
	public void resolveUnnamedCurrentBranchCommits() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("edit file").call();

			assertEquals(c2
			assertEquals(c1

			git.checkout().setCreateBranch(true).setName("newbranch")
					.setStartPoint(c1).call();

			assertEquals(c1
			try {
				assertEquals(c1
			} catch (RevisionSyntaxException e) {
				assertNotNull(e);
			}

			git.checkout().setName(c2.getName()).call();
			assertEquals(c2
			assertEquals(c1
			assertEquals(c2
		}
	}

	@Test
	public void resolveReflogParent() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("edit file").call();

			assertEquals(c1
		}
	}

	@Test
	public void resolveNonExistingBranch() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("create file").call();
			assertNull(db.resolve("notabranch@{7}"));
		}
	}

	@Test
	public void resolvePreviousBranch() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c1 = git.commit().setMessage("create file").call();
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			RevCommit c2 = git.commit().setMessage("edit file").call();

			git.checkout().setCreateBranch(true).setName("newbranch")
					.setStartPoint(c1).call();

			git.checkout().setName(c1.getName()).call();

			git.checkout().setName("master").call();

			assertEquals(c1.getName()
			assertEquals("newbranch"
			assertEquals("master"

			try {
				db.resolve("@{-1}@{0}");
				fail();
			} catch (RevisionSyntaxException e) {
			}
			assertEquals(c1.getName()

			assertEquals(c2.getName()
		}
	}

	@Test
	public void resolveDate() throws Exception {
		try (Git git = new Git(db)) {
			writeTrashFile("file.txt"
			git.add().addFilepattern("file.txt").call();
			git.commit().setMessage("create file").call();
			try {
				db.resolve("master@{yesterday}");
				fail("Exception not thrown");
			} catch (RevisionSyntaxException e) {
				assertNotNull(e);
			}
		}
	}
}
