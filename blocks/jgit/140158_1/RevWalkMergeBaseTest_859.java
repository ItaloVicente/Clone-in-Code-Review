package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.diff.DiffConfig;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.junit.TestRepository.CommitBuilder;
import org.eclipse.jgit.lib.Config;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RevWalkFollowFilterTest extends RevWalkTestCase {

	private static class DiffCollector extends RenameCallback {
		List<DiffEntry> diffs = new ArrayList<>();

		@Override
		public void renamed(DiffEntry diff) {
			diffs.add(diff);
		}
	}

	private DiffCollector diffCollector;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();
		diffCollector = new DiffCollector();
	}

	protected FollowFilter follow(String followPath) {
		FollowFilter followFilter =
			FollowFilter.create(followPath
		followFilter.setRenameCallback(diffCollector);
		rw.setTreeFilter(followFilter);
		return followFilter;
	}

	@Test
	public void testNoRename() throws Exception {
		final RevCommit a = commit(tree(file("0"
		follow("0");
		markStart(a);
		assertCommit(a
		assertNull(rw.next());

		assertNoRenames();
	}

	@Test
	public void testSingleRename() throws Exception {
		final RevCommit a = commit(tree(file("a"

		CommitBuilder commitBuilder = commitBuilder().parent(a)
				.add("b"
		RevCommit renameCommit = commitBuilder.create();

		follow("b");
		markStart(renameCommit);
		assertCommit(renameCommit
		assertCommit(a
		assertNull(rw.next());

		assertRenames("a->b");
	}

	@Test
	public void testMultiRename() throws Exception {
		final String contents = "A";
		final RevCommit a = commit(tree(file("a"

		CommitBuilder commitBuilder = commitBuilder().parent(a)
				.add("b"
		RevCommit renameCommit1 = commitBuilder.create();

		commitBuilder = commitBuilder().parent(renameCommit1)
				.add("c"
		RevCommit renameCommit2 = commitBuilder.create();

		commitBuilder = commitBuilder().parent(renameCommit2)
				.add("a"
		RevCommit renameCommit3 = commitBuilder.create();

		follow("a");
		markStart(renameCommit3);
		assertCommit(renameCommit3
		assertCommit(renameCommit2
		assertCommit(renameCommit1
		assertCommit(a
		assertNull(rw.next());

		assertRenames("c->a"
	}

	protected void assertRenames(String... expectedRenames) {
		Assert.assertEquals("Unexpected number of renames. Expected: " +
				expectedRenames.length + "
				expectedRenames.length

		for (int i = 0; i < expectedRenames.length; i++) {
			DiffEntry diff = diffCollector.diffs.get(i);
			Assert.assertNotNull(diff);
			String[] split = expectedRenames[i].split("->");

			Assert.assertNotNull(split);
			Assert.assertEquals(2
			String src = split[0];
			String target = split[1];

			Assert.assertEquals(src
			Assert.assertEquals(target
		}
	}

	protected void assertNoRenames() {
		Assert.assertEquals("Found unexpected rename/copy diff"
				diffCollector.diffs.size());
	}

}
