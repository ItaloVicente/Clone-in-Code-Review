
package org.eclipse.jgit.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
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
		tr = new TestRepository<>(db);
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
	public void ref() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("refs/heads/master"
		tr.update("refs/tags/tag"
		assertOneResult("master"
				git.nameRev().addRef(db.exactRef("refs/heads/master"))
		assertOneResult("tag"
				git.nameRev().addRef(db.exactRef("refs/tags/tag"))
	}

	@Test
	public void annotatedTags() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("refs/heads/master"
		tr.update("refs/tags/tag1"
		tr.update("refs/tags/tag2"
		assertOneResult("tag2"
	}

	@Test
	public void annotatedTagsNoResult() throws Exception {
		RevCommit c = tr.commit().create();
		tr.update("refs/heads/master"
		tr.update("refs/tags/tag1"
		tr.update("refs/tags/tag2"
		Map<ObjectId
				.add(c)
				.addAnnotatedTags()
				.call();
		assertTrue(result.toString()
	}

	@Test
	public void simpleAncestor() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c1).create();
		tr.update("master"
		Map<ObjectId
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
		assertOneResult("master~2"
	}

	@Test
	public void onePathMergeSecondParent() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c0).create();
		RevCommit c3 = tr.commit().parent(c2).create();
		RevCommit c4 = tr.commit().parent(c1).parent(c3).create();
		tr.update("master"
		assertOneResult("master^2"
		assertOneResult("master^2~1"
	}

	@Test
	public void onePathMergeLongerFirstParentPath() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c2 = tr.commit().parent(c1).create();
		RevCommit c3 = tr.commit().parent(c0).create();
		RevCommit c4 = tr.commit().parent(c2).parent(c3).create();
		tr.update("master"
		assertOneResult("master^2"
		assertOneResult("master~3"
	}

	@Test
	public void multiplePathsSecondParent() throws Exception {
		RevCommit c0 = tr.commit().create();
		RevCommit c1 = tr.commit().parent(c0).create();
		RevCommit c = c0;
		int mergeCost = 5;
		for (int i = 0; i < mergeCost; i++) {
			c = tr.commit().parent(c).create();
		}
		RevCommit c2 = tr.commit().parent(c).parent(c1).create();
		tr.update("master"
		assertOneResult("master^2~1"
	}

	private static void assertOneResult(String expected
			ObjectId id) throws Exception {
		Map<ObjectId
		assertEquals(1
		assertEquals(expected
	}

	private void assertOneResult(String expected
		assertOneResult(expected
	}
}
