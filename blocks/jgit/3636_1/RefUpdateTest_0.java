package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.ReflogEntry;
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
		git.rm().addFilepattern(FILE).call();
		commit2 = git.commit().setMessage("Removed file").call();
		git.notesAdd().setObjectId(commit1)
				.setMessage("data").call();
	}

	@Test
	public void testReflog() throws Exception {
		Collection<ReflogEntry> reflog = git.reflog().call();
		assertTrue(reflog.size() == 2);
		ReflogEntry[] reflogs = reflog.toArray(new ReflogEntry[reflog.size()]);
		assertEquals(reflogs[1].getComment()
		assertEquals(reflogs[0].getNewId()
		assertEquals(reflogs[0].getOldId()
	}

}
