
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.eclipse.jgit.lib.Constants.COMMIT_GENERATION_UNKNOWN;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Test;

public class CommitGraphTest extends RepositoryTestCase {

	private TestRepository<FileRepository> tr;

	private CommitGraph commitGraph;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		tr = new TestRepository<>(db
	}

	@Test
	public void testGraphWithSingleCommit() throws Exception {
		RevCommit root = commit();
		writeCommitGraph(Collections.singleton(root));
		verifyCommitGraph();
		assertEquals(1
	}

	@Test
	public void testGraphWithManyParents() throws Exception {
		int parentsNum = 40;
		RevCommit root = commit();

		RevCommit[] parents = new RevCommit[parentsNum];
		for (int i = 0; i < parents.length; i++) {
			parents[i] = commit(root);
		}
		RevCommit tip = commit(parents);

		Set<ObjectId> wants = Collections.singleton(tip);
		writeCommitGraph(wants);
		assertEquals(parentsNum + 2
		verifyCommitGraph();

		assertEquals(1
		for (RevCommit parent : parents) {
			assertEquals(2
		}
		assertEquals(3
	}

	@Test
	public void testGraphWithoutMerges() throws Exception {
		int commitNum = 20;
		RevCommit[] commits = new RevCommit[commitNum];
		for (int i = 0; i < commitNum; i++) {
			if (i == 0) {
				commits[i] = commit();
			} else {
				commits[i] = commit(commits[i - 1]);
			}
		}

		Set<ObjectId> wants = Collections.singleton(commits[commitNum - 1]);
		writeCommitGraph(wants);
		assertEquals(commitNum
		verifyCommitGraph();
		for (int i = 0; i < commitNum; i++) {
			assertEquals(i + 1
		}
	}

	@Test
	public void testGraphWithMerges() throws Exception {
		RevCommit c1 = commit();
		RevCommit c2 = commit(c1);
		RevCommit c3 = commit(c2);
		RevCommit c4 = commit(c1);
		RevCommit c5 = commit(c4);
		RevCommit c6 = commit(c1);
		RevCommit c7 = commit(c6);

		RevCommit m1 = commit(c2
		RevCommit m2 = commit(c4
		RevCommit m3 = commit(c3

		Set<ObjectId> wants = new HashSet<>();

		wants.add(m1);
		writeCommitGraph(wants);
		assertEquals(4
		verifyCommitGraph();

		wants.add(m2);
		writeCommitGraph(wants);
		assertEquals(6
		verifyCommitGraph();

		wants.add(m3);
		writeCommitGraph(wants);
		assertEquals(10
		verifyCommitGraph();

		RevCommit c8 = commit(m3);
		wants.add(c8);
		writeCommitGraph(wants);
		assertEquals(11
		verifyCommitGraph();

		assertEquals(getGenerationNumber(c1)
		assertEquals(getGenerationNumber(c2)
		assertEquals(getGenerationNumber(c4)
		assertEquals(getGenerationNumber(c6)
		assertEquals(getGenerationNumber(c3)
		assertEquals(getGenerationNumber(c5)
		assertEquals(getGenerationNumber(c7)
		assertEquals(getGenerationNumber(m1)
		assertEquals(getGenerationNumber(m2)
		assertEquals(getGenerationNumber(m3)
		assertEquals(getGenerationNumber(c8)
	}

	void writeCommitGraph(Set<ObjectId> wants) throws Exception {
		NullProgressMonitor m = NullProgressMonitor.INSTANCE;
		try (RevWalk walk = new RevWalk(db)) {
			CommitGraphWriter writer = new CommitGraphWriter(
					GraphCommits.fromWalk(m
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			writer.write(m
			InputStream inputStream = new ByteArrayInputStream(
					os.toByteArray());
			commitGraph = CommitGraphLoader.read(inputStream);
		}
	}

	void verifyCommitGraph() throws Exception {
		try (RevWalk walk = new RevWalk(db)) {
			for (int i = 0; i < commitGraph.getCommitCnt(); i++) {
				ObjectId objId = commitGraph.getObjectId(i);
				CommitGraph.CommitData commit = commitGraph.getCommitData(i);
				int[] pList = commit.getParents();

				RevCommit expect = walk.lookupCommit(objId);
				walk.parseBody(expect);

				assertEquals(expect.getCommitTime()
				assertEquals(expect.getTree()
				assertEquals(expect.getParentCount()

				if (pList.length > 0) {
					ObjectId[] parents = new ObjectId[pList.length];
					for (int j = 0; j < parents.length; j++) {
						parents[j] = commitGraph.getObjectId(pList[j]);
					}
					assertArrayEquals(expect.getParents()
				}
			}
		}
	}

	int getGenerationNumber(ObjectId id) {
		int graphPos = commitGraph.findGraphPosition(id);
		CommitGraph.CommitData commitData = commitGraph.getCommitData(graphPos);
		if (commitData != null) {
			return commitData.getGeneration();
		}
		return COMMIT_GENERATION_UNKNOWN;
	}

	RevCommit commit(RevCommit... parents) throws Exception {
		return tr.commit(parents);
	}
}
