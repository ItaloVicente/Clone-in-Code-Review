package org.eclipse.jgit.revwalk;

import java.io.*;
import java.util.*;

import org.junit.*;

import static org.junit.Assert.*;

public class CommitReachabilityTest extends RevWalkTestCase {

	@Test
	public void testSelfReachability() throws Exception {
		final RevCommit base = commit();

		assertTrue(isAnyMergedInto(Collections.singleton(base)
		assertTrue(getBasesInTip(Collections.singleton(base)
				base));
		assertTrue(isMergedIntoAny(base
		assertTrue(getTipsInBase(base
				base));
	}

	@Test
	public void testParentChild() throws Exception {
		final RevCommit base = commit();
		final RevCommit tip = commit(base);

		assertTrue(isAnyMergedInto(Collections.singleton(base)
		assertTrue(getBasesInTip(Collections.singleton(base)
				base));
		assertTrue(isMergedIntoAny(base
		assertTrue(getTipsInBase(base
				.contains(tip));
	}

	@Test
	public void testParentIntermediateChild() throws Exception {
		final RevCommit base = commit();
		final RevCommit intermediate = commit(base);
		final RevCommit tip = commit(intermediate);

		assertTrue(isAnyMergedInto(Collections.singleton(base)
		assertTrue(getBasesInTip(Collections.singleton(base)
				base));
		assertTrue(isMergedIntoAny(base
		assertTrue(getTipsInBase(base
				.contains(tip));
	}

	@Test
	public void testParent2IntermediatesChild() throws Exception {
		final RevCommit base = commit();
		final RevCommit intermediate1 = commit(base);
		final RevCommit intermediate2 = commit(intermediate1);
		final RevCommit tip = commit(intermediate2);

		assertTrue(isAnyMergedInto(Collections.singleton(base)
		assertTrue(getBasesInTip(Collections.singleton(base)
				base));
		assertTrue(isMergedIntoAny(base
		assertTrue(getTipsInBase(base
				.contains(tip));
	}

	@Test
	public void testMergeCommit() throws Exception {
		final RevCommit base = commit();
		final RevCommit fork1 = commit(base);
		final RevCommit fork2 = commit(base);
		final RevCommit tip = commit(fork1

		assertTrue(isAnyMergedInto(Collections.singleton(base)
		assertTrue(getBasesInTip(Collections.singleton(base)
				base));
		assertTrue(isMergedIntoAny(base
		assertTrue(getTipsInBase(base
				.contains(tip));
	}

	@Test
	public void testMultipleMergeFork() throws Exception {
		final RevCommit base1 = commit();
		final RevCommit base2 = commit();
		final RevCommit fork1 = commit(base1);
		final RevCommit fork2 = commit(base1
		final RevCommit fork3 = commit(base2);
		final RevCommit tip1 = commit(fork1
		final RevCommit tip2 = commit(fork2

		assertTrue(isAnyMergedInto(Arrays.asList(base1
		assertEquals(new HashSet<RevCommit>(Arrays.asList(base1
				getBasesInTip(Arrays.asList(base1
		assertEquals(new HashSet<RevCommit>(Arrays.asList(base1
				getBasesInTip(Arrays.asList(base1
		assertTrue(isMergedIntoAny(base1
		assertEquals(new HashSet<RevCommit>(Arrays.asList(tip1
				getTipsInBase(base1
		assertEquals(new HashSet<RevCommit>(Arrays.asList(tip1
				getTipsInBase(base2
	}

	@Test
	public void testForksNotReachable() throws Exception {
		final RevCommit base = commit();
		final RevCommit fork1 = commit(base);
		final RevCommit fork2 = commit(base);

		assertFalse(isAnyMergedInto(Collections.singleton(fork1)
		assertTrue(getBasesInTip(Collections.singleton(fork1)
		assertFalse(isAnyMergedInto(Collections.singleton(fork2)
		assertTrue(getBasesInTip(Collections.singleton(fork2)
		assertFalse(isMergedIntoAny(fork1
		assertTrue(getTipsInBase(fork1
		assertFalse(isMergedIntoAny(fork2
		assertTrue(getTipsInBase(fork2
	}

	@Test
	public void testParentIntermediateChildrenWrongCommitTimeOrder()
			throws Exception {
		final RevCommit root = commit();
		final RevCommit base = commit(root);
		final RevCommit badIntermediate = commit(-10
		final RevCommit tip = commit(badIntermediate);

		assertTrue(isAnyMergedInto(Collections.singleton(base)
		assertTrue(getBasesInTip(Collections.singleton(base)
				base));
		assertTrue(isMergedIntoAny(base
		assertTrue(getTipsInBase(base
				.contains(tip));
	}

	private Set<RevCommit> getTipsInBase(RevCommit base
			Collection<RevCommit> tips) throws IOException {
		return CommitReachability.getTipsThatContainsBase(base
	}

	private boolean isMergedIntoAny(RevCommit tip
			throws IOException {
		return CommitReachability.isMergedIntoAny(tip
	}

	private Set<RevCommit> getBasesInTip(Collection<RevCommit> bases
			RevCommit tip) throws IOException {
		return CommitReachability
				.getBasesWhichAreContainedInTip(bases
	}

	private boolean isAnyMergedInto(Collection<RevCommit> bases
			throws IOException {
		return CommitReachability.isAnyMergedInto(bases
	}
}
