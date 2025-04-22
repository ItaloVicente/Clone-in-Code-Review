
package org.eclipse.jgit.internal.storage.commitgraph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Collections;
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

public class InMemoryOidLookupTest extends RepositoryTestCase {

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
			InMemoryOidLookup lookup = InMemoryOidLookup.load(
					NullProgressMonitor.INSTANCE
			assertEquals(0
			assertEquals(0
			assertNull(lookup.getCommit(0));
		}
	}

	@Test
	public void testRepoWithoutExtraEdges() throws Exception {
		try (RevWalk rw = new RevWalk(db)) {
			RevCommit root = commit();
			RevCommit a = commit(root);
			RevCommit b = commit(root);
			RevCommit tip = commit(a
			InMemoryOidLookup lookup = InMemoryOidLookup.load(
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
			InMemoryOidLookup lookup = InMemoryOidLookup.load(
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
			InMemoryOidLookup lookup = InMemoryOidLookup.load(
					NullProgressMonitor.INSTANCE
					rw);
			assertEquals(1
			assertEquals(0
			assertEquals(a
			assertNull(lookup.getCommit(1));
			assertThrows(MissingObjectException.class
		}
	}

	private void verifyOrderOfLookup(List<RevCommit> commits
			InMemoryOidLookup lookup) throws MissingObjectException {
		commits = new ArrayList<>(commits);
		Collections.sort(commits);
		for (int i = 0; i < commits.size(); i++) {
			RevCommit c = commits.get(i);
			assertEquals(c
			assertEquals(i
		}
	}

	RevCommit commit(RevCommit... parents) throws Exception {
		return tr.commit(parents);
	}
}
