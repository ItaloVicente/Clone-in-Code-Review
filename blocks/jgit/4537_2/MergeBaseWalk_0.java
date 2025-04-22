package org.eclipse.jgit.revwalk;

import java.io.*;
import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class MergeBaseWalkTest extends RevWalkTestCase {

	@Override
	protected RevWalk createRevWalk() {
		return new MergeBaseWalk(db);
	}

	@Test
	public void testSingleCommit() throws Exception {
		final RevCommit before = commit();
		final RevCommit base = commit(before);

		rw.markStart(base);
		assertRevWalk(base);
	}

	@Test
	public void testParentChild() throws Exception {
		final RevCommit before = commit();
		final RevCommit base = commit(before);
		final RevCommit tip = commit(base);

		rw.markStart(tip);
		assertRevWalk(tip);

		rw.reset();
		rw.markStart(tip);
		rw.markStart(base);
		assertRevWalk(tip
	}

	@Test
	public void testParentIntermediateChild() throws Exception {
		final RevCommit before = commit();
		final RevCommit base = commit(before);
		final RevCommit intermediate = commit(base);
		final RevCommit tip = commit(intermediate);

		rw.markStart(tip);
		rw.markStart(base);
		assertRevWalk(tip
	}

	@Test
	public void testMergeCommit() throws Exception {
		final RevCommit before = commit();
		final RevCommit base = commit(before);
		final RevCommit fork1 = commit(base);
		final RevCommit fork2 = commit(base);
		final RevCommit tip = commit(fork1

		rw.markStart(tip);
		rw.markStart(base);
		assertRevWalk(tip
	}

	@Test
	public void testForksBase() throws Exception {
		final RevCommit before = commit();
		final RevCommit base = commit(before);
		final RevCommit fork1 = commit(base);
		final RevCommit fork2 = commit(base);

		rw.markStart(fork1);
		rw.markStart(fork2);
		assertRevWalk(fork2
	}

	@Test
	public void testMultipleMergeFork() throws Exception {
		final RevCommit before = commit();
		final RevCommit root1 = commit(before);
		final RevCommit root2 = commit(before);
		final RevCommit base1 = commit(root1
		final RevCommit base2 = commit(root1
		final RevCommit fork1 = commit(base1);
		final RevCommit fork2 = commit(base1
		final RevCommit fork3 = commit(base2);
		final RevCommit tip1 = commit(fork1
		final RevCommit tip2 = commit(fork2

		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(base1);
		rw.markStart(base2);
		assertRevWalk(tip2
		              root1);
	}

	@Test
	public void testCommitWithOldTimestamp()
			throws Exception {
		final RevCommit notReported = commit();
		final RevCommit before = commit(notReported);
		final RevCommit base = commit(10
		final RevCommit intermediate = commit(-10
		final RevCommit tip1 = commit(+11
		final RevCommit tip2 = commit(base);

		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate);
		rw.sort(RevSort.TOPO);
		assertRevWalk(tip2
	}

	@Test
	public void testCommitWithOldTimestamp2()
			throws Exception {
		final RevCommit notReported = commit();
		final RevCommit before = commit(notReported);
		final RevCommit base = commit(10
		final RevCommit intermediate1 = commit(base);
		final RevCommit intermediate2 = commit(intermediate1);
		final RevCommit intermediate3 = commit(-10
		final RevCommit tip1 = commit(+11
		final RevCommit tip2 = commit(base);

		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate1);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate2);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate3);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate3);
		rw.sort(RevSort.TOPO);
		assertRevWalk(tip2
	}

	@Test
	public void testCommitWithOldTimestamp3()
			throws Exception {
		final RevCommit notReported = commit();
		final RevCommit before = commit(notReported);
		final RevCommit base = commit(10
		final RevCommit intermediate1 = commit(base);
		final RevCommit intermediate2 = commit(intermediate1);
		final RevCommit intermediate3 = commit(-10
		final RevCommit tip1 = commit(+11
		final RevCommit tip2 = commit(base

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate1);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate2);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate3);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate3);
		rw.sort(RevSort.TOPO);
		assertRevWalk(tip2
	}

	@Test
	public void testCommitWithTooRecentTimestamp()
			throws Exception {
		final RevCommit notReported = commit();
		final RevCommit before = commit(notReported);
		final RevCommit base = commit(10
		final RevCommit intermediate1 = commit(base);
		final RevCommit intermediate2 = commit(10
		final RevCommit intermediate3 = commit(-9
		final RevCommit tip1 = commit(intermediate3
		final RevCommit tip2 = commit(intermediate2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate1);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate2);
		assertRevWalk(intermediate2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate3);
		assertRevWalk(tip2

		rw.reset();
		rw.markStart(tip1);
		rw.markStart(tip2);
		rw.markStart(intermediate3);
		rw.sort(RevSort.TOPO);
		assertRevWalk(tip2
	}


	private void assertRevWalk(RevCommit... commits)
			throws IOException {
		final List<RevCommit> commitsList = new ArrayList<RevCommit>(
				Arrays.asList(commits));
		for (RevCommit commit = rw.next(); commit != null; commit = rw.next()) {
			assertEquals(commitsList.remove(0)
		}
		assertTrue(commitsList.isEmpty());
	}
}
