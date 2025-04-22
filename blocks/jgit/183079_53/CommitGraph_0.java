
package org.eclipse.jgit.revwalk;

import static org.eclipse.jgit.internal.storage.commitgraph.CommitGraph.EMPTY;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.internal.storage.file.GC;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.filter.MessageRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Test;

public class RevWalkCommitGraphTest extends RevWalkTestCase {

	private RevWalk rw;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		rw = new RevWalk(db);
	}

	@Test
	public void testParseHeaders() throws Exception {
		RevCommit c1 = commitFile("file1"

		RevCommit notParseInGraph = rw.lookupCommit(c1);
		rw.parseHeaders(notParseInGraph);
		assertFalse(notParseInGraph instanceof RevCommitCG);
		assertNotNull(notParseInGraph.getRawBuffer());
		assertEquals(Constants.COMMIT_GENERATION_UNKNOWN
				notParseInGraph.getGeneration());

		enableAndWriteCommitGraph();

		reinitializeRevWalk();
		RevCommit parseInGraph = rw.lookupCommit(c1);
		parseInGraph.parseHeaders(rw);

		assertTrue(parseInGraph instanceof RevCommitCG);
		assertNotNull(parseInGraph.getRawBuffer());
		assertEquals(1
		assertEquals(notParseInGraph.getId()
		assertEquals(notParseInGraph.getTree()
		assertEquals(notParseInGraph.getCommitTime()
		assertArrayEquals(notParseInGraph.getParents()

		reinitializeRevWalk();
		rw.setRetainBody(false);
		RevCommit noBody = rw.lookupCommit(c1);
		noBody.parseHeaders(rw);

		assertTrue(noBody instanceof RevCommitCG);
		assertNull(noBody.getRawBuffer());
		assertEquals(1
		assertEquals(notParseInGraph.getId()
		assertEquals(notParseInGraph.getTree()
		assertEquals(notParseInGraph.getCommitTime()
		assertArrayEquals(notParseInGraph.getParents()
	}

	@Test
	public void testInitializeShallowCommits() throws Exception {
		RevCommit c1 = commit(commit());
		branch(c1
		enableAndWriteCommitGraph();
		assertCommitCntInGraph(2);

		db.getObjectDatabase().setShallowCommits(Collections.singleton(c1));
		RevCommit parseInGraph = rw.lookupCommit(c1);
		parseInGraph.parseHeaders(rw);

		assertTrue(parseInGraph instanceof RevCommitCG);
		assertNotNull(parseInGraph.getRawBuffer());
		assertEquals(2
		assertEquals(0
	}

	@Test
	public void testTreeFilter() throws Exception {
		RevCommit c1 = commitFile("file1"
		RevCommit c2 = commitFile("file2"
		RevCommit c3 = commitFile("file1"
		RevCommit c4 = commitFile("file2"

		enableAndWriteCommitGraph();
		assertCommitCntInGraph(4);

		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file1")
				TreeFilter.ANY_DIFF));
		assertEquals(c3
		assertEquals(c1
		assertNull(rw.next());

		reinitializeRevWalk();
		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file2")
				TreeFilter.ANY_DIFF));
		assertEquals(c4
		assertEquals(c2
		assertNull(rw.next());
	}

	@Test
	public void testWalkWithCommitMessageFilter() throws Exception {
		RevCommit a = commit();
		RevCommit b = commitBuilder().parent(a)
				.message("The quick brown fox jumps over the lazy dog!")
				.create();
		RevCommit c = commitBuilder().parent(b).message("commit-c").create();
		branch(c

		enableAndWriteCommitGraph();
		assertCommitCntInGraph(3);

		rw.setRevFilter(MessageRevFilter.create("quick brown fox jumps"));
		rw.markStart(rw.lookupCommit(c));
		assertEquals(b
		assertNull(rw.next());
	}

	@Test
	public void testCommitsWalk() throws Exception {
		RevCommit c1 = commit();
		branch(c1
		RevCommit c2 = commit(c1);
		branch(c2
		RevCommit c3 = commit(c2);
		branch(c3

		enableAndWriteCommitGraph();
		assertCommitCntInGraph(3);
		testRevWalkBehavior("commits/1"

		RevCommit c4 = commit(c1);
		RevCommit c5 = commit(c4);
		RevCommit c6 = commit(c1);
		RevCommit c7 = commit(c6);

		RevCommit m1 = commit(c2
		branch(m1
		RevCommit m2 = commit(c4
		branch(m2
		RevCommit m3 = commit(c3
		branch(m3

		enableAndWriteCommitGraph();
		reinitializeRevWalk();
		assertCommitCntInGraph(10);
		testRevWalkBehavior("merge/1"
		testRevWalkBehavior("merge/1"
		testRevWalkBehavior("merge/2"

		RevCommit c8 = commit(m3);
		branch(c8

		testRevWalkBehavior("commits/8"
		testRevWalkBehavior("commits/8"

		enableAndWriteCommitGraph();
		reinitializeRevWalk();
		assertCommitCntInGraph(11);
		testRevWalkBehavior("commits/8"
		testRevWalkBehavior("commits/8"
	}

	void testRevWalkBehavior(String branch
		assertCommits(
				travel(TreeFilter.ALL
						branch
				travel(TreeFilter.ALL
						false

		assertCommits(
				travel(TreeFilter.ALL
						branch)
				travel(TreeFilter.ALL
						branch));

		assertCommits(
				travel(TreeFilter.ALL
						compare)
				travel(TreeFilter.ALL
						compare));

		assertCommits(
				travel(TreeFilter.ALL
						true
				travel(TreeFilter.ALL
						false

		assertCommits(
				travel(TreeFilter.ALL
						true
				travel(TreeFilter.ALL
						false
	}

	void assertCommitCntInGraph(int expect) {
		assertEquals(expect
	}

	void assertCommits(List<RevCommit> expect
		assertEquals(expect.size()

		for (int i = 0; i < expect.size(); i++) {
			RevCommit c1 = expect.get(i);
			RevCommit c2 = actual.get(i);

			assertEquals(c1.getId()
			assertEquals(c1.getTree()
			assertEquals(c1.getCommitTime()
			assertArrayEquals(c1.getParents()
			assertArrayEquals(c1.getRawBuffer()
		}
	}

	Ref branch(RevCommit commit
		return Git.wrap(db).branchCreate().setName(name)
				.setStartPoint(commit.name()).call();
	}

	List<RevCommit> travel(TreeFilter treeFilter
			RevSort revSort
			throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH

		try (RevWalk walk = new RevWalk(db)) {
			walk.setTreeFilter(treeFilter);
			walk.setRevFilter(revFilter);
			walk.sort(revSort);
			walk.setRetainBody(false);
			for (String start : starts) {
				walk.markStart(walk.lookupCommit(db.resolve(start)));
			}
			List<RevCommit> commits = new ArrayList<>();

			if (enableCommitGraph) {
				assertTrue(walk.commitGraph().getCommitCnt() > 0);
			} else {
				assertEquals(EMPTY
			}

			for (RevCommit commit : walk) {
				commits.add(commit);
			}
			return commits;
		}
	}

	void enableAndWriteCommitGraph() throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH
		db.getConfig().setBoolean(ConfigConstants.CONFIG_GC_SECTION
				ConfigConstants.CONFIG_KEY_WRITE_COMMIT_GRAPH
		GC gc = new GC(db);
		gc.gc();
	}

	private void reinitializeRevWalk() {
		rw.close();
		rw = new RevWalk(db);
	}
}
