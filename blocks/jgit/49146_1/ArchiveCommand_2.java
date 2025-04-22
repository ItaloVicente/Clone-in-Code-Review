
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.Sets;
import org.eclipse.jgit.revwalk.ObjectWalk;
import org.eclipse.jgit.revwalk.filter.MessageRevFilter;
import org.eclipse.jgit.revwalk.filter.NotRevFilter;
import org.eclipse.jgit.revwalk.filter.ObjectFilter;

import java.io.IOException;
import java.util.Set;

public class ObjectWalkFilterTest {
	private TestRepository<InMemoryRepository> tr;
	private ObjectWalk rw;

	private static final int OBJECT_COUNT = 12;

	@Before
	public void setUp() throws Exception {
		tr = new TestRepository<>(new InMemoryRepository(
				new DfsRepositoryDescription("test")));
		rw = new ObjectWalk(tr.getRepository());

		rw.markStart(tr.branch("master").commit()
			.add("a/a"
			.add("b/b"
			.add("c/c"
			.message("initial commit")

			.child()
			.rm("a/a")
			.add("a/A"
			.message("capitalize a/a")

			.child()
			.rm("a/A")
			.add("a/a"
			.message("make a/A lowercase again")
			.create());
	}

	@After
	public void tearDown() {
		rw.close();
		tr.getRepository().close();
	}

	private static class BlacklistObjectFilter extends ObjectFilter {
		final Set<AnyObjectId> badObjects;

		BlacklistObjectFilter(Set<AnyObjectId> badObjects) {
			this.badObjects = badObjects;
		}

		@Override
		public boolean include(ObjectWalk walker
			return !badObjects.contains(o);
		}
	}

	private AnyObjectId resolve(String revstr) throws Exception {
		return tr.getRepository().resolve(revstr);
	}

	private int countObjects() throws IOException {
		int n = 0;
		while (rw.next() != null) {
			n++;
		}
		while (rw.nextObject() != null) {
			n++;
		}
		return n;
	}

	@Test
	public void testDefaultFilter() throws Exception {
		assertTrue("filter is ALL"
				rw.getObjectFilter() == ObjectFilter.ALL);
		assertEquals(OBJECT_COUNT
	}

	@Test
	public void testObjectFilterCanFilterOutBlob() throws Exception {
		AnyObjectId one = rw.parseAny(resolve("master:a/a"));
		AnyObjectId two = rw.parseAny(resolve("master:b/b"));
		rw.setObjectFilter(new BlacklistObjectFilter(Sets.of(one

		assertEquals(OBJECT_COUNT - 2
	}

	@Test
	public void testFilteringCommitsHasNoEffect() throws Exception {
		AnyObjectId initial = rw.parseCommit(resolve("master^^"));
		rw.setObjectFilter(new BlacklistObjectFilter(Sets.of(initial)));
		assertEquals(OBJECT_COUNT
	}

	@Test
	public void testRevFilterAndObjectFilterCanCombine() throws Exception {
		AnyObjectId one = rw.parseAny(resolve("master:a/a"));
		AnyObjectId two = rw.parseAny(resolve("master:b/b"));
		rw.setObjectFilter(new BlacklistObjectFilter(Sets.of(one
		rw.setRevFilter(NotRevFilter.create(
				MessageRevFilter.create("capitalize")));

		assertEquals(OBJECT_COUNT - 5
	}

	@Test
	public void testFilteringTreeFiltersSubtrees() throws Exception {
		AnyObjectId capitalizeTree = rw.parseAny(resolve("master^:"));
		rw.setObjectFilter(new BlacklistObjectFilter(
				Sets.of(capitalizeTree)));

		assertEquals(OBJECT_COUNT - 2
	}

	@Test
	public void testFilteringTreeFiltersReferencedBlobs() throws Exception {
		AnyObjectId a1 = rw.parseAny(resolve("master:a"));
		AnyObjectId a2 = rw.parseAny(resolve("master^:a"));
		rw.setObjectFilter(new BlacklistObjectFilter(Sets.of(a1

		assertEquals(OBJECT_COUNT - 3
	}
}
