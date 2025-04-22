
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Before;
import org.junit.Test;

public class GraphCommitsTest extends RepositoryTestCase {

	private TestRepository<FileRepository> tr;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();
		tr = new TestRepository<>(db
	}

	@Test
	public void testEmptyRepo() throws Exception {
		try (RevWalk rw = new RevWalk(db)) {
			GraphCommits commits = GraphCommits.fromWalk(
					NullProgressMonitor.INSTANCE
			assertEquals(0
			assertEquals(0
			assertFalse(commits.iterator().hasNext());
		}
	}

	@Test
	public void testRepoWithoutExtraEdges() throws Exception {
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit root = commit();
			RevCommit a = commit(root);
			RevCommit b = commit(root);
			RevCommit tip = commit(a
			GraphCommits commits = GraphCommits.fromWalk(
					NullProgressMonitor.INSTANCE
					rw);
			assertEquals(4
			assertEquals(0
			verifyOrderOfLookup(List.of(root
		}
	}

	@Test
	public void testRepoWithExtraEdges() throws Exception {
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit root = commit();
			RevCommit a = commit(root);
			RevCommit b = commit(root);
			RevCommit c = commit(root);
			RevCommit tip = commit(a
			GraphCommits commits = GraphCommits.fromWalk(
					NullProgressMonitor.INSTANCE
					rw);
			assertEquals(5
			assertEquals(2
			verifyOrderOfLookup(List.of(root
		}
	}

	@Test
	public void testCommitNotInLookup() throws Exception {
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit a = commit();
			RevCommit b = commit(a);
			GraphCommits commits = GraphCommits.fromWalk(
					NullProgressMonitor.INSTANCE
			assertEquals(1
			assertEquals(0
			Iterator<RevCommit> iterator = commits.iterator();
			assertEquals(a
			assertFalse(iterator.hasNext());
			assertThrows(MissingObjectException.class
					() -> commits.getOidPosition(b));
		}
	}

	private void verifyOrderOfLookup(List<RevCommit> commits
			GraphCommits graphCommits) throws MissingObjectException {
		commits = new ArrayList<>(commits);
		Collections.sort(commits);
		Iterator<RevCommit> expected = commits.iterator();
		Iterator<RevCommit> actual = graphCommits.iterator();
		while (expected.hasNext()) {
			assertEquals(expected.next()
		}
		assertFalse(actual.hasNext());
	}

	RevCommit commit(RevCommit... parents) throws Exception {
		return tr.commit(parents);
	}
}
