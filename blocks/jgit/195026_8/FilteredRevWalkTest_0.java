package org.eclipse.jgit.revwalk;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
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
	public void testParseHeaders_noParent() throws Exception {
		RevCommit root = tr.commit().add("todelete"
		RevCommit orig = tr.commit().parent(root).rm("todelete")
				.add("foo"
				.add("dir/baz"
		FilteredRevCommit filteredRevCommit = new FilteredRevCommit(orig);
		filteredRevCommit.parseHeaders(rw);
		tr.branch("master").update(filteredRevCommit);
		assertEquals("foo contents"
		assertEquals("bar contents"
		assertEquals("baz contents"
				blobAsString(filteredRevCommit
	}

	@Test
	public void testParents() throws Exception {
		RevCommit commit1 = tr.commit().add("foo"
		RevCommit commit2 = tr.commit().parent(commit1)
				.message("original message").add("bar"
				.create();
		RevCommit commit3 = tr.commit().parent(commit2).message("commit3")
				.add("foo"

		FilteredRevCommit filteredCommitHead = new FilteredRevCommit(commit3
				commit1);

		assertEquals(commit1
				.findFirst().get());
		assertEquals("commit3"
		assertEquals("foo contents\n new line\n"
				blobAsString(filteredCommitHead
		assertEquals(filteredCommitHead.getTree()

	}

	@Test
	public void testFlag() throws Exception {
		RevCommit root = tr.commit().add("todelete"
		RevCommit orig = tr.commit().parent(root).rm("todelete")
				.add("foo"
				.add("dir/baz"

		FilteredRevCommit filteredRevCommit = new FilteredRevCommit(orig
		assertEquals(RevObject.PARSED
		assertEquals(RevObject.PARSED
	}

	@Test
	public void testCommitState() throws Exception {
		RevCommit root = tr.commit().add("todelete"
		RevCommit orig = tr.commit().parent(root).rm("todelete")
				.add("foo"
				.add("dir/baz"

		FilteredRevCommit filteredRevCommit = new FilteredRevCommit(orig
		assertEquals(filteredRevCommit.getFullMessage()
		assertEquals(filteredRevCommit.commitTime
		assertSame(filteredRevCommit.getTree()
		assertSame(filteredRevCommit.getRawBuffer()
		assertSame(filteredRevCommit.parents
	}

	@Test
	public void testParseCommit_withParents_parsesRealParents()
			throws Exception {
		RevCommit commit1 = tr.commit().add("foo"
		RevCommit commit2 = tr.commit().parent(commit1)
				.message("original message").add("bar"
				.create();
		RevCommit commit3 = tr.commit().parent(commit2).message("commit3")
				.add("foo"

		FilteredRevCommit filteredCommitHead = new FilteredRevCommit(commit3
				commit1);

		RevCommit parsedCommit = rw.parseCommit(filteredCommitHead.getId());
		assertEquals(filteredCommitHead.getId()
		assertNotEquals(
				Arrays.stream(parsedCommit.getParents()).findFirst().get()
				Arrays.stream(filteredCommitHead.getParents()).findFirst()
						.get());
	}

	private String blobAsString(AnyObjectId treeish
			throws Exception {
		RevObject obj = tr.get(rw.parseTree(treeish)
		assertSame(RevBlob.class
		ObjectLoader loader = rw.getObjectReader().open(obj);
		return new String(loader.getCachedBytes()
	}
}
