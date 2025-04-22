
package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryTestCase;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Before;
import org.junit.Test;

public class NameRevCommandTest extends RepositoryTestCase {
	private TestRepository<Repository> tr;
	private Git git;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		tr = new TestRepository<Repository>(db);
		git = new Git(db);
	}

	@Test
	public void nameExact() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("master"
		assertOneResult("master"
	}

	@Test
	public void prefix() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("refs/heads/master"
		tr.update("refs/tags/tag"
		assertOneResult("master"
		assertOneResult("master"
				git.nameRev().addPrefix("refs/heads/").addPrefix("refs/tags/")
				c);
		assertOneResult("tag"
				git.nameRev().addPrefix("refs/tags/").addPrefix("refs/heads/")
				c);
	}

	@Test
	public void simpleAncestor() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c1).create();
		tr.update("master"
		Map<AnyObjectId
			.call();
		assertEquals(3
		assertEquals("master~2"
		assertEquals("master~1"
		assertEquals("master"
	}

	@Test
	public void multiplePathsNoMerge() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c0).create();
		RevCommit c3 = tr.commit().parent(c2).create();
		tr.update("master"
		tr.update("branch"
		assertOneResult("master~1"
	}

	@Test
	public void onePathMerge() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c0).create();
		RevCommit c3 = tr.commit().parent(c1).parent(c2).create();
		tr.update("master"
		assertOneResult("master^1~1"
	}

	@Test
	public void oneMergeDifferentLengths() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c1).create();
		RevCommit c3 = tr.commit().parent(c0).create();
		RevCommit c4 = tr.commit().parent(c2).parent(c3).create();
		tr.update("master"
		assertOneResult("master^2~1"
	}

	@Test
	public void longerPathWithoutMerge() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c1).create();
		RevCommit c3 = tr.commit().parent(c1).create();
		RevCommit c4 = tr.commit().parent(c2).parent(c3).create();
		RevCommit c5 = tr.commit().parent(c0).create();
		RevCommit c6 = tr.commit().parent(c5).create();
		RevCommit c7 = tr.commit().parent(c6).create();
		RevCommit c8 = tr.commit().parent(c7).create();
		RevCommit c9 = tr.commit().parent(c8).create();
		tr.update("master"
		tr.update("branch"
		assertOneResult("branch~5"
	}

	private static void assertOneResult(String expected
			AnyObjectId id) throws Exception {
		Map<AnyObjectId
		assertEquals(1
		assertEquals(expected
	}

	private void assertOneResult(String expected
			throws Exception {
		assertOneResult(expected
	}
}
