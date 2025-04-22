
package org.eclipse.jgit.revwalk;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.Arrays;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.AnyObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.junit.Before;
import org.junit.Test;

public class FilteredRevCommitTest {
	private TestRepository<InMemoryRepository> tr;

	private RevWalk rw;

	@Before
	public void setUp() throws Exception {
		tr = new TestRepository<>(
				new InMemoryRepository(new DfsRepositoryDescription("test")));
		rw = tr.getRevWalk();
	}

	@Test
	public void testParseBody_noParent() throws Exception {
		RevCommit root = tr.commit().add("todelete"
		RevCommit orig = tr.commit().parent(root).rm("todelete")
				.add("foo"
				.add("dir/baz"
		FilteredRevCommit filteredRevCommit = new FilteredRevCommit(orig);
		filteredRevCommit.parseBody(rw);
		tr.branch("master").update(filteredRevCommit);
		assertEquals("foo contents"
		assertEquals("bar contents"
		assertEquals("baz contents"
				blobAsString(filteredRevCommit
	}

	@Test
	public void testParseBody_withParents() throws Exception {
		RevCommit commit1 = tr.commit().add("foo"
		RevCommit commit2 = tr.commit().parent(commit1)
				.message("original message").add("bar"
				.create();
		RevCommit commit3 = tr.commit().parent(commit2).message("commit3")
				.add("foo"

		FilteredRevCommit filteredCommitHead = new FilteredRevCommit(commit3
				Arrays.asList(commit1));

		rw.parseBody(filteredCommitHead);
		assertEquals(commit1
				.findFirst().get());
		assertEquals("commit3"
		assertEquals("foo contents\n new line\n"
				blobAsString(filteredCommitHead
	}

	@Test
	public void testParseCommit_withParents() throws Exception {
		RevCommit commit1 = tr.commit().add("foo"
		RevCommit commit2 = tr.commit().parent(commit1)
				.message("original message").add("bar"
				.create();
		RevCommit commit3 = tr.commit().parent(commit2).message("commit3")
				.add("foo"

		FilteredRevCommit filteredCommitHead = new FilteredRevCommit(commit3
				Arrays.asList(commit1));
		rw.updateCommit(filteredCommitHead);

		RevCommit parsedCommit = rw.parseCommit(filteredCommitHead.getId());

		assertEquals(filteredCommitHead.getId()
		assertEquals(Arrays.stream(parsedCommit.getParents()).findFirst().get()
				Arrays.stream(filteredCommitHead.getParents()).findFirst()
						.get());
	}

	@Test
	public void testFlag() throws Exception {
		RevCommit root = tr.commit().add("todelete"
		RevCommit orig = tr.commit().parent(root).rm("todelete")
				.add("foo"
				.add("dir/baz"

		FilteredRevCommit filteredRevCommit = new FilteredRevCommit(orig
				Arrays.asList(root));
		assertEquals(RevObject.PARSED
		assertEquals(0
		filteredRevCommit.parseBody(rw);
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
