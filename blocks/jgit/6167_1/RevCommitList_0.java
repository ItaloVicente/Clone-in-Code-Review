package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.junit.Test;

public class RevCommitListTest extends RepositoryTestCase {

	private RevCommitList<RevCommit> list;

	public void setup(int count) throws Exception {
		Git git = new Git(db);
		for (int i = 0; i < count; i++)
			git.commit().setCommitter(committer).setAuthor(author)
					.setMessage("commit " + i).call();
		list = new RevCommitList<RevCommit>();
		RevWalk w = new RevWalk(db);
		w.markStart(w.lookupCommit(db.resolve(Constants.HEAD)));
		list.source(w);
	}

	@Test
	public void testFillToHighMark2() throws Exception {
		setup(3);
		list.fillTo(1);
		assertEquals(2
		assertEquals("commit 2"
		assertEquals("commit 1"
		assertNull("commit 0 shouldn't be loaded"
	}

	@Test
	public void testFillToHighMarkAll() throws Exception {
		setup(3);
		list.fillTo(2);
		assertEquals(3
		assertEquals("commit 2"
		assertEquals("commit 0"
	}

	@Test
	public void testFillToHighMark4() throws Exception {
		setup(3);
		list.fillTo(3);
		assertEquals(3
		assertEquals("commit 2"
		assertEquals("commit 0"
		assertNull("commit 3 can't be loaded"
	}

	@Test
	public void testFillToHighMarkMulitpleBlocks() throws Exception {
		setup(258);
		list.fillTo(257);
		assertEquals(258
	}

	@Test
	public void testFillToCommit() throws Exception {
		setup(3);

		RevWalk w = new RevWalk(db);
		w.markStart(w.lookupCommit(db.resolve(Constants.HEAD)));

		w.next();
		RevCommit c = w.next();
		assertNotNull("should have found 2. commit"

		list.fillTo(c
		assertEquals(2
		assertEquals("commit 1"
		assertNull(list.get(3));
	}

	@Test
	public void testFillToUnknownCommit() throws Exception {
		setup(258);
		RevCommit c = new RevCommit(
				ObjectId.fromString("9473095c4cb2f12aefe1db8a355fe3fafba42f67"));

		list.fillTo(c
		assertEquals("loading to unknown commit should load all commits"
				list.size());
	}

	@Test
	public void testFillToNullCommit() throws Exception {
		setup(3);
		list.fillTo(null
		assertNull(list.get(0));
	}
}
