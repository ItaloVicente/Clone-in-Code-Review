
package org.eclipse.jgit.revwalk;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.jgit.internal.storage.file.GC;
import org.eclipse.jgit.lib.CommitGraph;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.treewalk.filter.AndTreeFilter;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.treewalk.filter.TreeFilter;
import org.junit.Test;

public class RevWalkCommitGraphTest extends RevWalkTestCase {

	@Test
	public void testTreeFilter() throws Exception {
		RevCommit c1 = commitFile("file1"
		RevCommit c2 = commitFile("file2"
		RevCommit c3 = commitFile("file1"
		RevCommit c4 = commitFile("file2"

		writeCommitGraph();
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		assertCommitCntInGraph(4);

		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file1")
				TreeFilter.ANY_DIFF));
		assertEquals(c3
		assertEquals(c1
		assertNull(rw.next());

		rw.dispose();
		rw.markStart(rw.lookupCommit(c4));
		rw.setTreeFilter(AndTreeFilter.create(PathFilter.create("file2")
				TreeFilter.ANY_DIFF));
		assertEquals(c4
		assertEquals(c2
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

		testRevWalkBehavior("commits/1"
		assertCommitCntInGraph(0);

		writeCommitGraph();
		testRevWalkBehavior("commits/1"
		assertCommitCntInGraph(3);

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

		writeCommitGraph();
		assertCommitCntInGraph(10);
		testRevWalkBehavior("merge/1"
		testRevWalkBehavior("merge/1"
		testRevWalkBehavior("merge/2"

		RevCommit c8 = commit(m3);
		branch(c8

		testRevWalkBehavior("commits/8"
		testRevWalkBehavior("commits/8"

		writeCommitGraph();
		assertCommitCntInGraph(11);
		testRevWalkBehavior("commits/8"
		testRevWalkBehavior("commits/8"
	}

	void assertCommitCntInGraph(int expect) {
		rw.dispose();
		CommitGraph graph = rw.getCommitGraph();

		if (expect <= 0) {
			assertNull(graph);
			return;
		}
		assertNotNull(graph);
		assertEquals(expect
	}

	void testRevWalkBehavior(String branch
		rw.setTreeFilter(TreeFilter.ALL);
		rw.setRevFilter(RevFilter.MERGE_BASE);
		testGraphTwoModes(branch

		rw.setRevFilter(RevFilter.ALL);
		rw.sort(RevSort.TOPO);
		testGraphTwoModes(branch);
		testGraphTwoModes(compare);

		rw.setRevFilter(RevFilter.ALL);
		rw.sort(RevSort.COMMIT_TIME_DESC);
		testGraphTwoModes(branch);
		testGraphTwoModes(compare);
	}

	void branch(RevCommit commit
		createBranch(commit
	}

	void writeCommitGraph() throws Exception {
		GC gc = new GC(db);
		gc.writeCommitGraph();
	}

	private void testGraphTwoModes(String branch) throws Exception {
		testGraphTwoModes(Collections.singleton(db.resolve(branch)));
	}

	private void testGraphTwoModes(String branch
			throws Exception {
		List<ObjectId> commits = new ArrayList<>();
		commits.add(db.resolve(branch));
		commits.add(db.resolve(compare));
		testGraphTwoModes(commits);
	}

	private void testGraphTwoModes(Collection<ObjectId> starts)
			throws Exception {
		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withOutGraph = new ArrayList<>();

		for (RevCommit commit : rw) {
			withOutGraph.add(commit);
		}

		db.getConfig().setBoolean(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_COMMIT_GRAPH_SECTION
		rw.dispose();

		for (ObjectId start : starts) {
			markStart(rw.lookupCommit(start));
		}
		List<RevCommit> withGraph = new ArrayList<>();

		for (RevCommit commit : rw) {
			withGraph.add(commit);
		}
		rw.dispose();

		assertEquals(withOutGraph.size()

		for (int i = 0; i < withGraph.size(); i++) {
			RevCommit expect = withOutGraph.get(i);
			RevCommit commit = withGraph.get(i);

			assertEquals(expect.getId()
			assertEquals(expect.getTree()
			assertEquals(expect.getCommitTime()
			assertArrayEquals(expect.getParents()
			assertArrayEquals(expect.getRawBuffer()
		}
	}
}
