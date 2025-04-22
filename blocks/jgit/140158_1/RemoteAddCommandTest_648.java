package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class ReflogCommandTest extends RepositoryTestCase {

	private Git git;

	private RevCommit commit1

	private static final String FILE = "test.txt";

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		git = new Git(db);
		writeTrashFile(FILE
		git.add().addFilepattern(FILE).call();
		commit1 = git.commit().setMessage("Initial commit").call();
		git.checkout().setCreateBranch(true).setName("b1").call();
		git.rm().addFilepattern(FILE).call();
		commit2 = git.commit().setMessage("Removed file").call();
		git.notesAdd().setObjectId(commit1).setMessage("data").call();
	}

	@Test
	public void testHeadReflog() throws Exception {
		Collection<ReflogEntry> reflog = git.reflog().call();
		assertNotNull(reflog);
		assertEquals(3
		ReflogEntry[] reflogs = reflog.toArray(new ReflogEntry[0]);
		assertEquals(reflogs[2].getComment()
				"commit (initial): Initial commit");
		assertEquals(reflogs[2].getNewId()
		assertEquals(reflogs[2].getOldId()
		assertEquals(reflogs[1].getComment()
				"checkout: moving from master to b1");
		assertEquals(reflogs[1].getNewId()
		assertEquals(reflogs[1].getOldId()
		assertEquals(reflogs[0].getComment()
		assertEquals(reflogs[0].getNewId()
		assertEquals(reflogs[0].getOldId()
	}

	@Test
	public void testBranchReflog() throws Exception {
		Collection<ReflogEntry> reflog = git.reflog()
				.setRef(Constants.R_HEADS + "b1").call();
		assertNotNull(reflog);
		assertEquals(2
		ReflogEntry[] reflogs = reflog.toArray(new ReflogEntry[0]);
		assertEquals(reflogs[0].getComment()
		assertEquals(reflogs[0].getNewId()
		assertEquals(reflogs[0].getOldId()
		assertEquals(reflogs[1].getComment()
				"branch: Created from commit Initial commit");
		assertEquals(reflogs[1].getNewId()
		assertEquals(reflogs[1].getOldId()
	}

	@Test
	public void testAmendReflog() throws Exception {
		RevCommit commit2a = git.commit().setAmend(true)
				.setMessage("Deleted file").call();
		Collection<ReflogEntry> reflog = git.reflog().call();
		assertNotNull(reflog);
		assertEquals(4
		ReflogEntry[] reflogs = reflog.toArray(new ReflogEntry[0]);
		assertEquals(reflogs[3].getComment()
				"commit (initial): Initial commit");
		assertEquals(reflogs[3].getNewId()
		assertEquals(reflogs[3].getOldId()
		assertEquals(reflogs[2].getComment()
				"checkout: moving from master to b1");
		assertEquals(reflogs[2].getNewId()
		assertEquals(reflogs[2].getOldId()
		assertEquals(reflogs[1].getComment()
		assertEquals(reflogs[1].getNewId()
		assertEquals(reflogs[1].getOldId()
		assertEquals(reflogs[0].getComment()
		assertEquals(reflogs[0].getNewId()
		assertEquals(reflogs[0].getOldId()
	}
}
