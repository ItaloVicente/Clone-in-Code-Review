package org.eclipse.jgit.revwalk;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.junit.Before;
import org.junit.Test;

public class RevCommitWithOverriddenParentTest {
	private TestRepository<InMemoryRepository> tr;

	private RevWalk rw;

	@Before
	public void setUp() throws Exception {
		tr = new TestRepository<>(
				new InMemoryRepository(new DfsRepositoryDescription("test")));
		rw = tr.getRevWalk();
	}

	@Test
	public void testParseBody() throws Exception {
		RevCommit a = tr.commit().add("a"
		RevCommit b = tr.commit().parent(a).add("b"
		RevCommit c = tr.commit().parent(b).message("commit3").add("a"
				.create();

		RevCommit cBar = new RevCommit(c.getId()) {
			@Override
			public int getParentCount() {
				return 1;
			}

			@Override
			public RevCommit getParent(int nth) {
				return a;
			}

			@Override
			public RevCommit[] getParents() {
				return new RevCommit[] { a };
			}
		};

		rw.parseBody(cBar);
		assertEquals(a
		assertEquals("commit3"
		assertEquals("foo'"
	}

	@Test
	public void testParseHeader() throws Exception {
		RevCommit a = tr.commit().add("a"
		RevCommit b = tr.commit().parent(a).add("b"
		RevCommit c = tr.commit().parent(b).message("commit3").add("a"
				.create();

		RevCommit cBar = new RevCommit(c.getId()) {
			@Override
			public int getParentCount() {
				return 1;
			}

			@Override
			public RevCommit getParent(int nth) {
				return a;
			}

			@Override
			public RevCommit[] getParents() {
				return new RevCommit[] { a };
			}
		};

		RevCommit parsed = rw.parseCommit(cBar.getId());
		rw.parseHeaders(cBar);

		assertEquals(c.getId()
		assertEquals(parsed.getTree()
	}

	@Test
	public void testFilter() throws Exception {
		RevCommit a = tr.commit().add("a"
		RevCommit b = tr.commit().parent(a).add("b"
		RevCommit c = tr.commit().parent(b).message("commit3").add("a"
				.create();

		RevCommit cBar = new RevCommit(c.getId()) {
			@Override
			public int getParentCount() {
				return 1;
			}

			@Override
			public RevCommit getParent(int nth) {
				return a;
			}

			@Override
			public RevCommit[] getParents() {
				return new RevCommit[] { a };
			}
		};

		rw.setRevFilter(RevFilter.ALL);
		rw.markStart(cBar);
		assertSame(cBar
		assertSame(a
		assertNull(rw.next());
	}

	@Test
	public void testFlag() throws Exception {
		RevCommit root = tr.commit().add("todelete"
		RevCommit orig = tr.commit().parent(root).rm("todelete")
				.add("foo"
				.add("dir/baz"

		RevCommit commitOrigBar = new RevCommit(orig.getId()) {
			@Override
			public int getParentCount() {
				return 1;
			}

			@Override
			public RevCommit getParent(int nth) {
				return root;
			}

			@Override
			public RevCommit[] getParents() {
				return new RevCommit[] { root };
			}
		};

		assertEquals(RevObject.PARSED
		assertEquals(0
		commitOrigBar.parseBody(rw);
		assertEquals(RevObject.PARSED
	}

	private String blobAsString(AnyObjectId treeish
			throws Exception {
		RevObject obj = tr.get(rw.parseTree(treeish)
		assertSame(RevBlob.class
		ObjectLoader loader = rw.getObjectReader().open(obj);
		return new String(loader.getCachedBytes()
	}
}
