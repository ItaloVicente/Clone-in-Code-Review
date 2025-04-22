package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class ReflogResolveTest extends RepositoryTestCase {

	@Test
	public void resolveMasterCommits() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c2 = git.commit().setMessage("edit file").call();

		assertEquals(c2
		assertEquals(c1
	}

	@Test
	public void resolveReflogParent() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		RevCommit c1 = git.commit().setMessage("create file").call();
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("edit file").call();

		assertEquals(c1
	}

	@Test
	public void resolveNonExistingBranch() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();
		assertNull(db.resolve("notabranch@{7}"));
	}

	@Test
	public void resolveNegativeEntryNumber() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();
		try {
			db.resolve("master@{-12}");
			fail("Exception not thrown");
		} catch (IOException e) {
			assertNotNull(e);
		}
	}

	@Test
	public void resolveDate() throws Exception {
		Git git = new Git(db);
		writeTrashFile("file.txt"
		git.add().addFilepattern("file.txt").call();
		git.commit().setMessage("create file").call();
		try {
			db.resolve("master@{yesterday}");
			fail("Exception not thrown");
		} catch (IOException e) {
			assertNotNull(e);
		}
	}
}
